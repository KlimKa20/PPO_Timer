package by.bsuir.ppo_timer.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.ViewModel.CreateWorkoutViewModel;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;

public class WorkoutActivity extends AppCompatActivity  implements View.OnClickListener {

    CreateWorkoutViewModel createWorkoutViewModel;
    DBViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        mViewModel = ViewModelProviders.of(this).get(DBViewModel.class);
        createWorkoutViewModel = ViewModelProviders.of(this).get(CreateWorkoutViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CreateWorkoutButton:
                String ee = ((TextView)findViewById(R.id.NameTextViewEditText)).getText().toString();
                String ee1 = ((TextView)findViewById(R.id.PreparationTextViewEditText)).getText().toString();
                String ee2 = ((TextView)findViewById(R.id.WorkTextViewEditText)).getText().toString();
                String ee3 = ((TextView)findViewById(R.id.RestTextViewEditText)).getText().toString();
                String ee4 = ((TextView)findViewById(R.id.CycleTextViewEditText)).getText().toString();
                String ee5 = ((TextView)findViewById(R.id.SetTextViewEditText)).getText().toString();
                String ee6 = ((TextView)findViewById(R.id.TimeOfRestBetweenSetTextViewEditText)).getText().toString();
                String ee7 = ((TextView)findViewById(R.id.TimeOfFinalRestTextViewEditText)).getText().toString();
                mViewModel.AddFieldToDataBase(ee,ee1,ee2,ee3,ee4,ee5,ee6,ee7);
                break;
        }
    }
}