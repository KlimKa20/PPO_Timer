package by.bsuir.ppo_timer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import by.bsuir.ppo_timer.Activity.TimerActivity;
import by.bsuir.ppo_timer.R;

public class Timer extends Service {

    ExecutorService es;
    SoundPool sp;
    int soundIdShot;

    public void onCreate() {
        super.onCreate();
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundIdShot = sp.load(this, R.raw.shot, 1);
        es = Executors.newFixedThreadPool(1);
    }

    public void onDestroy() {
        super.onDestroy();
        es.shutdownNow();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        int time = Integer.parseInt( intent.getStringExtra(TimerActivity.PARAM_TIME));
        String name = intent.getStringExtra(TimerActivity.PARAM_TASK);

        MyRun mr = new MyRun(startId, time, name);
        es.execute(mr);
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
            if (name.equals("Финиш")){
                intent.putExtra(TimerActivity.PARAM_TASK, name);
                intent.putExtra(TimerActivity.PARAM_STATUS, "");
                sendBroadcast(intent);
            }
            try {
                for (int i = time; i  > 0; i--) {

                    intent.putExtra(TimerActivity.PARAM_TASK, name);
                    intent.putExtra(TimerActivity.PARAM_STATUS, Integer.toString(i));
                    sendBroadcast(intent);
                    TimeUnit.SECONDS.sleep(1);
                    if (i <= 4) {
                        if (i == 1)
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
            sp.play(soundIdShot, 1, 1, 0, 0, 1);
        }
        void signal() {
            sp.play(soundIdShot, 1, 1, 0, 0, 1);
        }
    }
}