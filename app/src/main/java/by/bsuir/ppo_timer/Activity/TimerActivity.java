package by.bsuir.ppo_timer.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import by.bsuir.ppo_timer.Model.Workout;
import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.Service.Timer;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;

public class TimerActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView1;
    private Workout workout;
    private DBViewModel mViewModel;
    BroadcastReceiver br;

    public final static String PARAM_TIME = "time";
    public final static String PARAM_TASK = "task";
    public final static String PARAM_STATUS = "status";
    public final static String BROADCAST_ACTION = "by.bsuir.ppo_timer";

    ListView lvSimple;
    ArrayList<String> work = new ArrayList();
    ArrayAdapter<String> adapter;
    int element = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent intent = getIntent();
        mViewModel = ViewModelProviders.of(this).get(DBViewModel.class);
        workout = mViewModel.FindById(intent.getIntExtra("idWorkout", 0));
        textView = findViewById(R.id.time);
        textView1 = findViewById(R.id.time2);
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String task = intent.getStringExtra(PARAM_TASK);
                String status = intent.getStringExtra(PARAM_STATUS);
                if (status.equals("1")) {
                    element++;
                    if (element < adapter.getCount()) {
//                        lvSimple.setSelection(element);
                        String[] words = adapter.getItem(element).split(" : ");
                        AddNewService(words[0], words[1]);
                        textView.setText(task);
                        textView1.setText(status);
                    }
//                    ii = adapter.getItem(0);
//                    adapter.remove(ii);
                } else {
                    textView.setText(task);
                    textView1.setText(status);
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, intFilt);


        int set = Integer.parseInt(workout.getCountOfSets());
        int cycle = Integer.parseInt(workout.getCountOfCycles());
        work.add("Подготовка" + " : " + workout.getTimeOfPreparation());
        while (set > 0) {
            while (cycle > 0) {
                work.add("Работа" + " : " + workout.getTimeOfWork());
                work.add("Отдых" + " : " + workout.getTimeOfRest());
                cycle--;
            }
            set--;
            if (set != 0) {
                work.add("Отдых между сетами" + " : " + workout.getTimeOfRestBetweenSet());
            }
        }
        work.add("Финальный отдых" + " : " + workout.getTimeOfFinalRest());
        work.add("Финиш" + " : " + "0");
        lvSimple = findViewById(R.id.lvSimple);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, work);
        lvSimple.setAdapter(adapter);
        lvSimple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ChangeFieldListView(position);
            }
        });

    }

    public void ChangeFieldListView(int position) {
        String[] words = adapter.getItem(position).split(" : ");
        AddNewService(words[0], words[1]);
        stopService(new Intent(this, Timer.class));
        startService(new Intent(this, Timer.class).putExtra(PARAM_TIME, words[1])
                .putExtra(PARAM_TASK, words[0]));
    }

    public void AddNewService(String name, String time) {
        startService(new Intent(this, Timer.class).putExtra(PARAM_TIME, time)
                .putExtra(PARAM_TASK, name));
    }

    public void onStartClick(View view) {
        String ii = workout.getTimeOfPreparation();
        startService(new Intent(this, Timer.class).putExtra(PARAM_TIME, ii)
                .putExtra(PARAM_TASK, "Подготовка"));
        element = 0;
//        int set = Integer.parseInt( workout.getCountOfSets());
//        while (set>0) {
//            int cycle = Integer.parseInt(workout.getCountOfCycles());
//            while (cycle > 0) {
//                startService(new Intent(this, Timer.class).putExtra(PARAM_TIME, workout.getTimeOfWork())
//                        .putExtra(PARAM_TASK, "Работа"));
//                startService(new Intent(this, Timer.class).putExtra(PARAM_TIME, workout.getTimeOfRest())
//                        .putExtra(PARAM_TASK, "Отдых"));
//                cycle--;
//            }
//            set--;
//            if (set!=0)
//            {
//                startService(new Intent(this, Timer.class).putExtra(PARAM_TIME, workout.getTimeOfRestBetweenSet())
//                        .putExtra(PARAM_TASK, "Отдых между сетами"));
//            }
//        }
//        startService(new Intent(this, Timer.class).putExtra(PARAM_TIME, workout.getTimeOfFinalRest())
//                .putExtra(PARAM_TASK, "Финальный отдых"));
    }

    public void onResetClick(View view) {
        stopService(new Intent(this, Timer.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, Timer.class));
    }
}
