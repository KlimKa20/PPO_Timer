package by.bsuir.ppo_timer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WorkoutViewModel mViewModel;
    List<Workout> workouts = new ArrayList<>();

    ListView workoutList;

    private void setInitialData() {
        mViewModel.ReadFieldsFromDataBase(workouts);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        setInitialData();

        workoutList = (ListView) findViewById(R.id.Workoutlist);
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(this, R.layout.list_item, workouts);
        workoutList.setAdapter(workoutAdapter);


    }
}