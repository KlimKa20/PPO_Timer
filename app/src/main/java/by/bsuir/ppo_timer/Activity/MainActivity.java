package by.bsuir.ppo_timer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.ppo_timer.Model.Workout;
import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;
import by.bsuir.ppo_timer.WorkoutAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBViewModel mViewModel;
    List<Workout> workouts = new ArrayList<>();
    ListView workoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(DBViewModel.class);
        ((Button)findViewById(R.id.NewWorkout)).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mViewModel.ReadFieldsFromDataBase(workouts);
        workoutList = (ListView) findViewById(R.id.Workoutlist);
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(this, R.layout.list_item, workouts);
        workoutList.setAdapter(workoutAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.NewWorkout:
                Intent intent = new Intent(this, WorkoutActivity.class);
                startActivity(intent);
                break;
        }
    }


}