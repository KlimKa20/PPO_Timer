package by.bsuir.ppo_timer.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
    Boolean block = false;
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
                if (!block) {
                    if (intent.getStringExtra(PARAM_PAUSE).equals("work")) {
                        String task = intent.getStringExtra(PARAM_NAME_ELEMENT);
                        String status = intent.getStringExtra(PARAM_TIME_ELEMENT);
                        if (status.equals("1")) {
                            element++;
                            check_one_bool = true;
                            if (element < adapter.getCount()) {
                                String[] words = adapter.getItem(element).split(" : ");
                                if (words.length == 1)
                                    AddNewService(words[0], "0");
                                else
                                    AddNewService(words[0], words[1]);
                                NameWorkout.setText(task);
                                TimeWorkout.setText(status);
                            }
                        } else {
                            if (check_one_bool) {
//                                if (element != 0)
//                                    for (int i = 0; i < adapter.getCount() && i < 14; i++) {
//                                        if (work.get(element) == lvSimple.getItemAtPosition(i)){
//                                            lvSimple.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.itemrest));
//                                            break;
//                                        }
//
//                                    }
//                                for (int i = 0; i < adapter.getCount() && i < 14; i++) {
//                                    if (work.get(element) == lvSimple.getItemAtPosition(i)){
//                                        lvSimple.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.green));
//                                        break;
//                                    }
//                                }
                                if (element != 0)
                                    lvSimple.getChildAt(element - 1).setBackgroundColor(getResources().getColor(R.color.itemrest));
                                lvSimple.getChildAt(element).setBackgroundColor(getResources().getColor(R.color.green));
                                check_one_bool = false;
                            }
                            value_status_pause = "";
                            if (task.equals(getResources().getString(R.string.Finish))) {
                                action.setOnClickListener(TimerActivity.this::onStartClick);
                                action.setText(getResources().getString(R.string.Start));
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
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(receiver, intentFilter);

        int set = Integer.parseInt(workout.getCountOfSets());
        work.add(getResources().getString(R.string.Preparation) + " : " + workout.getTimeOfPreparation());
        while (set > 0) {
            int cycle = Integer.parseInt(workout.getCountOfCycles());
            while (cycle > 0) {
                work.add(getResources().getString(R.string.Work) + " : " + workout.getTimeOfWork());
                work.add(getResources().getString(R.string.Rest) + " : " + workout.getTimeOfRest());
                cycle--;
            }
            set--;
            if (set != 0) {
                work.add(getResources().getString(R.string.TimeOfRestBetweenSet) + " : " + workout.getTimeOfRestBetweenSet());
            }
        }
        work.add(getResources().getString(R.string.TimeOfFinalRest) + " : " + workout.getTimeOfFinalRest());
        work.add(getResources().getString(R.string.Finish));

        action = findViewById(R.id.buttonStart);
        action.setOnClickListener(this::onStartClick);
        action.setText(getResources().getString(R.string.Start));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, work);
        lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(adapter);
        lvSimple.setOnItemClickListener((parent, view, position, id) -> ChangeFieldListView(view,position));
    }

    public void ChangeFieldListView(View view,int position) {
        for (int i = 0; i < adapter.getCount() && i < 14; i++) {
            lvSimple.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.itemrest));
        }
        element = position;
        view.setBackgroundColor(getResources().getColor(R.color.green));
        stopService(new Intent(this, Timer.class));

//        for (int i = 0; i < adapter.getCount(); i++) {
//            lvSimple.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.itemrest));
//        }
        change_bool = true;
//        lvSimple.getChildAt(element).setBackgroundColor(getResources().getColor(R.color.green));
        String[] words = adapter.getItem(position).split(" : ");
        action.setOnClickListener(this::onResetClick);
        action.setText(getResources().getString(R.string.Pause));
        if (!block) {
            if (words.length == 1) {
                action.setText(getResources().getString(R.string.Start));
                action.setOnClickListener(this::onStartClick);
                AddNewService(words[0], "0");
            } else
                AddNewService(words[0], words[1]);
        }

    }

    public void AddNewService(String name, String time) {
        startService(new Intent(this, Timer.class).putExtra(PARAM_START_TIME, time)
                .putExtra(PARAM_NAME_ELEMENT, name));
    }

    public void onStartClick(View view) {
        if (!block) {
            if (value_status_pause.isEmpty() || value_status_pause.equals(getResources().getString(R.string.Finish))) {
                String time_work = workout.getTimeOfPreparation();
                element = 0;
                check_one_bool = false;
                AddNewService(getResources().getString(R.string.Preparation), time_work);
//                lvSimple.getChildAt(adapter.getCount() - 1).setBackgroundColor(getResources().getColor(R.color.itemrest)); вопрос
                lvSimple.getChildAt(element).setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                element = value_element_pause;
                AddNewService(value_status_pause, value_time_pause);
            }
        }
        action.setOnClickListener(this::onResetClick);
        action.setText(getResources().getString(R.string.Pause));
    }

    public void onResetClick(View view) {
        action.setOnClickListener(this::onStartClick);
        action.setText(getResources().getString(R.string.Start));
        stopService(new Intent(this, Timer.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, Timer.class));
    }


    @Override
    public void onBackPressed() {
        block = true;
        stopService(new Intent(this, Timer.class));
        super.onBackPressed();
    }
}
