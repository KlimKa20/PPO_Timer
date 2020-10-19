package by.bsuir.ppo_timer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import by.bsuir.ppo_timer.Model.Workout;
import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;
import by.bsuir.ppo_timer.WorkoutAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBViewModel mViewModel;
    List<Workout> workouts = new ArrayList<>();
    ListView workoutList;
    WorkoutAdapter workoutAdapter;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(DBViewModel.class);
        workoutList = findViewById(R.id.Workoutlist);
        findViewById(R.id.NewWorkout).setOnClickListener(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String listValue = sp.getString("test_lang", "не выбрано");
        if (listValue.equals("English") || listValue.equals("Английский"))
        {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, null);
        }
        else {
            Locale locale = new Locale("ru");
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, null);
        }
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mViewModel.ReadFieldsFromDataBase(workouts);
        workoutAdapter = new WorkoutAdapter(this, R.layout.list_item, workouts);
        workoutList.setAdapter(workoutAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.NewWorkout:
                intent = new Intent(this, WorkoutActivity.class).putExtra("actionObj","-1");
                startActivity(intent);
                break;
            case R.id.buttonStart:
                int index = (int) v.getTag();
                intent = new Intent(this, TimerActivity.class);
                intent.putExtra("idWorkout",index);
                startActivity(intent);
                break;
            case R.id.buttonDelete:
                mViewModel.DeleteFieldFromDataBase((int) v.getTag(), workouts);
                workoutAdapter = new WorkoutAdapter(this, R.layout.list_item, workouts);
                workoutList.setAdapter(workoutAdapter);
                break;
            case R.id.buttonEdit:
                intent = new Intent(this, WorkoutActivity.class).putExtra("actionObj",String.valueOf(v.getTag()));
                startActivity(intent);
                break;
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        mi.setIntent(new Intent(this, SettingActivity.class));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        super.onRestart();
    }
}