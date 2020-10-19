package by.bsuir.ppo_timer.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import by.bsuir.ppo_timer.Model.Workout;
import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.Service.Timer;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;

public class TimerActivity extends AppCompatActivity {

    private TextView NameWorkout;
    private TextView TimeWorkout;
    private Workout workout;
    private DBViewModel dbViewModel;
    BroadcastReceiver receiver;

    public final static String PARAM_START_TIME = "start_time";
    public final static String PARAM_NAME_ELEMENT = "name";
    public final static String PARAM_TIME_ELEMENT = "time";
    public final static String PARAM_PAUSE = "pause";
    public final static String PARAM_STOP = "stop";
    public final static String BROADCAST_ACTION = "by.bsuir.ppo_timer";

    ListView lvSimple;
    Button action;
    ArrayList<String> work = new ArrayList();
    ArrayAdapter<String> adapter;
    int element = 0;
    Boolean check_one_bool = false;
    Boolean change_bool = false;
    String value_status_pause = "";
    String value_time_pause = "";
    int value_element_pause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent intent = getIntent();
        dbViewModel = ViewModelProviders.of(this).get(DBViewModel.class);
        workout = dbViewModel.FindById(intent.getIntExtra("idWorkout", 0));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(workout.getName());
        actionBar.setBackgroundDrawable(new ColorDrawable(workout.getColor()));
        NameWorkout = findViewById(R.id.Nameworkout);
        TimeWorkout = findViewById(R.id.Timeworkout);
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent.getStringExtra(PARAM_PAUSE).equals("work")) {
                    String task = intent.getStringExtra(PARAM_NAME_ELEMENT);
                    String status = intent.getStringExtra(PARAM_TIME_ELEMENT);
                    if (status.equals("1")) {
                        element++;
                        check_one_bool = true;
                        if (element < adapter.getCount()) {
                            String[] words = adapter.getItem(element).split(" : ");
                            if (words.length==1)
                                AddNewService(words[0], "0");

                            else
                                AddNewService(words[0], words[1]);
                            NameWorkout.setText(task);
                            TimeWorkout.setText(status);
                        }
                    } else {
                        if (check_one_bool) {
                            lvSimple.getChildAt(element - 1).setBackgroundColor( getResources().getColor(R.color.itemrest) );
                            lvSimple.getChildAt(element).setBackgroundColor(Color.parseColor("#ff1dcc4c"));
                            check_one_bool = false;
                        }
                        value_status_pause = "";
                        if (task.equals("Финиш")) {
                            action.setOnClickListener(TimerActivity.this::onStartClick);
                            action.setText("Start");
                        }
                        NameWorkout.setText(task);
                        TimeWorkout.setText(status);
                    }
                } else {
                    String status = intent.getStringExtra(PARAM_TIME_ELEMENT);
                    value_status_pause = intent.getStringExtra(PARAM_NAME_ELEMENT);
                    value_time_pause = intent.getStringExtra(PARAM_TIME_ELEMENT);
                    if (status.equals("1")) {
                        if (!change_bool)
                            element--;
                        else
                            change_bool = false;
                        String[] words = adapter.getItem(element).split(" : ");
                        value_status_pause = words[0];
                    }
                    value_element_pause = element;
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(receiver, intentFilter);

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
        work.add("Финиш");

        action = findViewById(R.id.buttonStart);
        action.setOnClickListener(this::onStartClick);
        action.setText("Start");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, work);
        lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(adapter);
        lvSimple.setOnItemClickListener((parent, view, position, id) -> ChangeFieldListView(position));
    }

    public void ChangeFieldListView(int position) {
        element = position;
        stopService(new Intent(this, Timer.class).putExtra(PARAM_STOP, "stop"));
        for (int i = 0; i < adapter.getCount(); i++) {
            lvSimple.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.itemrest) );
        }
        change_bool = true;
        check_one_bool = false;
        lvSimple.getChildAt(element).setBackgroundColor(Color.parseColor("#ff1dcc4c"));
        String[] words = adapter.getItem(position).split(" : ");
        action.setOnClickListener(this::onResetClick);
        action.setText("Pause");
        if (words.length==1){
            action.setText("Start");
            action.setOnClickListener(this::onStartClick);
            AddNewService(words[0], "0");
        }
        else
            AddNewService(words[0], words[1]);
    }

    public void AddNewService(String name, String time) {
        startService(new Intent(this, Timer.class).putExtra(PARAM_START_TIME, time)
                .putExtra(PARAM_NAME_ELEMENT, name));
    }

    public void onStartClick(View view) {
        if (value_status_pause.isEmpty() || value_status_pause.equals("Финиш")) {
            String time_work = workout.getTimeOfPreparation();
            element = 0;
            AddNewService("Подготовка", time_work);
            lvSimple.getChildAt(adapter.getCount()-1).setBackgroundColor(getResources().getColor(R.color.itemrest) );
            lvSimple.getChildAt(element).setBackgroundColor(Color.parseColor("#ff1dcc4c"));
        } else {
            element = value_element_pause;
            AddNewService(value_status_pause, value_time_pause);
        }
        action.setOnClickListener(this::onResetClick);
        action.setText("Pause");
    }

    public void onResetClick(View view) {
        action.setOnClickListener(this::onStartClick);
        action.setText("Start");
        stopService(new Intent(this, Timer.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, Timer.class));
    }
}
