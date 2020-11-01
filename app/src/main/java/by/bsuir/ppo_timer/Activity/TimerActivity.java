package by.bsuir.ppo_timer.Activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
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
    public final static String BROADCAST_ACTION = "by.bsuir.ppo_timer";

    ListView lvSimple;
    Button action;
    ArrayList<String> work = new ArrayList();
    ArrayAdapter<String> adapter;
    boolean block = false;
    int element = 0;
    boolean check_last_sec = false;
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

        action = findViewById(R.id.buttonStart);
        lvSimple = findViewById(R.id.lvSimple);
        NameWorkout = findViewById(R.id.Nameworkout);
        TimeWorkout = findViewById(R.id.Timeworkout);

        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (!block) {
                    if (intent.getStringExtra(PARAM_PAUSE).equals("work")) {
                        String task = intent.getStringExtra(PARAM_NAME_ELEMENT);
                        String status = intent.getStringExtra(PARAM_TIME_ELEMENT);
                        if (status.equals("1")) {
                            workLastSec();
                        } else {
                            workInProgress(task);
                        }
                        NameWorkout.setText(task);
                        TimeWorkout.setText(status);
                    } else {
                        value_status_pause = intent.getStringExtra(PARAM_NAME_ELEMENT);
                        value_time_pause = intent.getStringExtra(PARAM_TIME_ELEMENT);
                        startPause(value_time_pause);
                    }
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(receiver, intentFilter);

        fillAdapter(workout);

        action.setOnClickListener(this::onStartClick);
        action.setText(getResources().getString(R.string.Start));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, work);
        lvSimple.setAdapter(adapter);
        lvSimple.setOnItemClickListener((parent, view, position, id) -> ChangeFieldListView(view, position));
        lvSimple.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                for (int i = 0; i < visibleItemCount; i++) {
                    lvSimple.getChildAt(i).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.itemrest));
                }
                if (element >= firstVisibleItem && element < firstVisibleItem + visibleItemCount) {
                    if (!check_last_sec)
                        lvSimple.getChildAt(element - firstVisibleItem).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                    else
                        lvSimple.getChildAt(element - 1 - firstVisibleItem).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                }
            }
        });
    }

    public void workInProgress(String task){
        if (check_last_sec) {
            if (element != 0 && element - 1 - lvSimple.getFirstVisiblePosition() < 14 && element - 1 - lvSimple.getFirstVisiblePosition() >= 0 && element != 0)
                lvSimple.getChildAt(element - lvSimple.getFirstVisiblePosition() - 1).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.itemrest));
            if (element - lvSimple.getFirstVisiblePosition() < 14 && element - 1 - lvSimple.getFirstVisiblePosition() >= 0)
                lvSimple.getChildAt(element - lvSimple.getFirstVisiblePosition()).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
            check_last_sec = false;
        }
        value_status_pause = "";
        if (task.equals(getResources().getString(R.string.Finish))) {
            action.setOnClickListener(TimerActivity.this::onStartClick);
            action.setText(getResources().getString(R.string.Start));
        }
    }

    public void workLastSec(){
        element++;
        check_last_sec = true;
        if (element < adapter.getCount()) {
            String[] words = adapter.getItem(element).split(" : ");
            if (words.length == 2)
                AddNewService(words[1], "0");
            else
                AddNewService(words[1], words[2]);
        }
    }

    public void startPause(String status){

        if (status.equals("1")) {
            if (!check_last_sec)
                element--;
            else
                check_last_sec = false;
            String[] words = adapter.getItem(element).split(" : ");
            value_status_pause = words[1];
        }
        value_element_pause = element;
    }
    public void fillAdapter(Workout workout) {
        int number = 1;
        int set = workout.getCountOfSets();
        if (workout.getTimeOfPreparation() != 0)
            work.add(StringForTimer(number++, getResources().getString(R.string.Preparation), workout.getTimeOfPreparation()));
        while (set > 0) {
            int cycle = workout.getCountOfCycles();
            while (cycle > 0) {
                work.add(StringForTimer(number++, getResources().getString(R.string.Work), workout.getTimeOfWork()));
                work.add(StringForTimer(number++, getResources().getString(R.string.Rest), workout.getTimeOfRest()));
                cycle--;
            }
            set--;
            if (set != 0) {
                if (workout.getTimeOfRestBetweenSet() != 0)
                    work.add(StringForTimer(number++, getResources().getString(R.string.TimeOfRestBetweenSet), workout.getTimeOfRestBetweenSet()));
            }
        }
        if (workout.getTimeOfFinalRest() != 0)
            work.add(StringForTimer(number++, getResources().getString(R.string.TimeOfFinalRest), workout.getTimeOfFinalRest()));
        work.add(number + " : " + getResources().getString(R.string.Finish));

    }

    public String StringForTimer(int number, String name, Integer time) {
        return number + " : " + name + " : " + time;
    }

    public void ChangeFieldListView(View view, int position) {
        for (int i = 0; i < lvSimple.getLastVisiblePosition() - lvSimple.getFirstVisiblePosition() + 1; i++) {
            lvSimple.getChildAt(i).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.itemrest));
        }
        element = position;
        view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
        stopService(new Intent(this, Timer.class));
//        change_bool = true;
        String[] words = adapter.getItem(position).split(" : ");

        if (!block) {
            if (words.length == 2) {
                action.setText(getResources().getString(R.string.Start));
                action.setOnClickListener(this::onStartClick);
                AddNewService(words[1], "0");
            } else {
                action.setOnClickListener(this::onResetClick);
                action.setText(getResources().getString(R.string.Pause));
                AddNewService(words[1], words[2]);
            }
        }

    }

    public void AddNewService(String name, String time) {
        startService(new Intent(this, Timer.class).putExtra(PARAM_START_TIME, time)
                .putExtra(PARAM_NAME_ELEMENT, name));
    }

    public void onStartClick(View view) {
        if (!block) {
            if (value_status_pause.isEmpty() || value_status_pause.equals(getResources().getString(R.string.Finish))) {
                element = 0;
                check_last_sec = false;
                stopService(new Intent(this, Timer.class));
                AddNewService(getResources().getString(R.string.Preparation), String.valueOf(workout.getTimeOfPreparation()));
                lvSimple.getChildAt(lvSimple.getLastVisiblePosition()-lvSimple.getFirstVisiblePosition()).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.itemrest));
                if (element >= lvSimple.getFirstVisiblePosition() && element <= lvSimple.getLastVisiblePosition())
                    lvSimple.getChildAt(element).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
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
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                TimerActivity.this);
        quitDialog.setTitle(getResources().getString(R.string.Exit));
        quitDialog.setPositiveButton(getResources().getString(R.string.Yes), (dialog, which) -> {
            block = true;
            stopService(new Intent(this, Timer.class));
            finish();
        });
        quitDialog.setNegativeButton(getResources().getString(R.string.No), (dialog, which) -> {

        });
        quitDialog.show();
    }
}
