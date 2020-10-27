package by.bsuir.ppo_timer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import by.bsuir.ppo_timer.Activity.TimerActivity;
import by.bsuir.ppo_timer.R;

public class Timer extends Service {

    ScheduledExecutorService service;
    SoundPool soundPool;
    int soundIdPip;
    int soundIdPipAlter;
    int current_time;
    String name;
    ScheduledFuture<?> scheduledFuture;

    public void onCreate() {
        super.onCreate();
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundIdPip = soundPool.load(this, R.raw.censore_preview, 1);
        soundIdPipAlter = soundPool.load(this, R.raw.shot, 1);
        service = Executors.newScheduledThreadPool(1);
    }

    public void onDestroy() {
        service.shutdownNow();
        scheduledFuture.cancel(true);
        Intent intent = new Intent(TimerActivity.BROADCAST_ACTION);
        intent.putExtra(TimerActivity.PARAM_PAUSE, "pause");
        intent.putExtra(TimerActivity.PARAM_NAME_ELEMENT, name);
        intent.putExtra(TimerActivity.PARAM_TIME_ELEMENT, Integer.toString(current_time));
        sendBroadcast(intent);
        super.onDestroy();

    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        int time = Integer.parseInt(intent.getStringExtra(TimerActivity.PARAM_START_TIME));
        name = intent.getStringExtra(TimerActivity.PARAM_NAME_ELEMENT);

        MyRun mr = new MyRun(startId, time, name);
        if (scheduledFuture != null) {
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    scheduledFuture.cancel(true);
                    scheduledFuture = service.scheduleAtFixedRate(mr, 0, time+1, TimeUnit.SECONDS);
                    ;
                }
            }, 990, TimeUnit.MILLISECONDS);
        } else {
            scheduledFuture = service.scheduleAtFixedRate(mr, 0, time+1, TimeUnit.SECONDS);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyRun extends TimerTask {

        int time;
        int startId;
        String name;

        public MyRun(int startId, int time, String name) {
            this.time = time;
            this.startId = startId;
            this.name = name;
        }


        public void run() {
            Intent intent = new Intent(TimerActivity.BROADCAST_ACTION);
            if (name.equals(getResources().getString(R.string.Finish))) {
                intent.putExtra(TimerActivity.PARAM_PAUSE, "work");
                intent.putExtra(TimerActivity.PARAM_NAME_ELEMENT, name);
                intent.putExtra(TimerActivity.PARAM_TIME_ELEMENT, "");
                sendBroadcast(intent);
            }
            try {
                for (current_time = time; current_time > 0; current_time--) {
                    intent.putExtra(TimerActivity.PARAM_PAUSE, "work");
                    intent.putExtra(TimerActivity.PARAM_NAME_ELEMENT, name);
                    intent.putExtra(TimerActivity.PARAM_TIME_ELEMENT, Integer.toString(current_time));
                    sendBroadcast(intent);
                    TimeUnit.SECONDS.sleep(1);
                    if (current_time <= 4) {
                        if (current_time == 1)
                            allertsignal();
                        else
                            signal();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void allertsignal() {
            soundPool.play(soundIdPipAlter, 1, 1, 0, 0, 1);
        }

        void signal() {
            soundPool.play(soundIdPip, 1, 1, 0, 0, 1);
        }
    }
}