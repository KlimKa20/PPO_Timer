package by.bsuir.ppo_timer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.ppo_timer.Model.Workout;
import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;
import by.bsuir.ppo_timer.WorkoutAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_ACCESS_TYPE = 200;
    DBViewModel mViewModel;
    List<Workout> workouts = new ArrayList<>();
    ListView workoutList;
    WorkoutAdapter workoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(DBViewModel.class);
        workoutList = findViewById(R.id.Workoutlist);
        findViewById(R.id.NewWorkout).setOnClickListener(this);
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
                intent = new Intent(this, WorkoutActivity.class).putExtra("actionObj", -1);
                startActivity(intent);
                break;
            case R.id.buttonStart:
                int index = (int) v.getTag();
                intent = new Intent(this, TimerActivity.class);
                intent.putExtra("idWorkout", index);
                startActivity(intent);
                break;
            case R.id.buttonDelete:
                mViewModel.DeleteFieldFromDataBase((int) v.getTag(), workouts);
                workoutAdapter = new WorkoutAdapter(this, R.layout.list_item, workouts);
                workoutList.setAdapter(workoutAdapter);
                break;
            case R.id.buttonEdit:
                intent = new Intent(this, WorkoutActivity.class).putExtra("actionObj", (int)v.getTag());
                startActivity(intent);
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, getResources().getString(R.string.Preferences));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivityForResult(intent,REQUEST_ACCESS_TYPE);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_ACCESS_TYPE){
            if(resultCode==RESULT_OK){
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}