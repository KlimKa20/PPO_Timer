package by.bsuir.ppo_timer.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.ViewModel.CreateWorkoutViewModel;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;

import static by.bsuir.ppo_timer.Model.FieldType.COUNTOFCYCLE;
import static by.bsuir.ppo_timer.Model.FieldType.COUNTOFSETS;
import static by.bsuir.ppo_timer.Model.FieldType.TIMEOFFINALREST;
import static by.bsuir.ppo_timer.Model.FieldType.TIMEOFPREPARATION;
import static by.bsuir.ppo_timer.Model.FieldType.TIMEOFREST;
import static by.bsuir.ppo_timer.Model.FieldType.TIMEOFRESTBETWEENSET;
import static by.bsuir.ppo_timer.Model.FieldType.TIMEOFWORK;

public class WorkoutActivity extends AppCompatActivity {

    CreateWorkoutViewModel createWorkoutViewModel;
    DBViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        mViewModel = ViewModelProviders.of(this).get(DBViewModel.class);
        createWorkoutViewModel = ViewModelProviders.of(this).get(CreateWorkoutViewModel.class);

        ((EditText) findViewById(R.id.NameTextViewEditText)).setOnEditorActionListener((v, actionId, event) -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                createWorkoutViewModel.setName(((EditText) findViewById(R.id.NameTextViewEditText)).getText().toString());
                return true;
            }
            return false;
        });

        ((Button) findViewById(R.id.PreparationButtonMinus)).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFPREPARATION));
        ((Button) findViewById(R.id.WorkButtonMinus)).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFWORK));
        ((Button) findViewById(R.id.RestButtonMinus)).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFREST));
        ((Button) findViewById(R.id.CycleButtonMinus)).setOnClickListener(item -> createWorkoutViewModel.decrement(COUNTOFCYCLE));
        ((Button) findViewById(R.id.SetButtonMinus)).setOnClickListener(item -> createWorkoutViewModel.decrement(COUNTOFSETS));
        ((Button) findViewById(R.id.TimeOfRestBetweenSetButtonMinus)).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFRESTBETWEENSET));
        ((Button) findViewById(R.id.TimeOfFinalRestButtonMinus)).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFFINALREST));

        ((Button) findViewById(R.id.PreparationButtonPlus)).setOnClickListener(item -> createWorkoutViewModel.increment(TIMEOFPREPARATION));
        ((Button) findViewById(R.id.WorkButtonPlus)).setOnClickListener(item -> createWorkoutViewModel.increment(TIMEOFWORK));
        ((Button) findViewById(R.id.RestButtonPlus)).setOnClickListener(item -> createWorkoutViewModel.increment(TIMEOFREST));
        ((Button) findViewById(R.id.CycleButtonPlus)).setOnClickListener(item -> createWorkoutViewModel.increment(COUNTOFCYCLE));
        ((Button) findViewById(R.id.SetButtonPlus)).setOnClickListener(item -> createWorkoutViewModel.increment(COUNTOFSETS));
        ((Button) findViewById(R.id.TimeOfRestBetweenSetButtonPlus)).setOnClickListener(item -> createWorkoutViewModel.increment(TIMEOFRESTBETWEENSET));
        ((Button) findViewById(R.id.TimeOfFinalRestButtonPlus)).setOnClickListener(item -> createWorkoutViewModel.increment(TIMEOFFINALREST));

        createWorkoutViewModel.GetValue(TIMEOFPREPARATION).observe(this, value -> ((TextView) findViewById(R.id.PreparationTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(TIMEOFWORK).observe(this, value -> ((TextView) findViewById(R.id.WorkTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(TIMEOFREST).observe(this, value -> ((TextView) findViewById(R.id.RestTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(COUNTOFCYCLE).observe(this, value -> ((TextView) findViewById(R.id.CycleTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(COUNTOFSETS).observe(this, value -> ((TextView) findViewById(R.id.SetTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(TIMEOFRESTBETWEENSET).observe(this, value -> ((TextView) findViewById(R.id.TimeOfRestBetweenSetTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(TIMEOFFINALREST).observe(this, value -> ((TextView) findViewById(R.id.TimeOfFinalRestTextViewEditText)).setText(value));

        ((Button) findViewById(R.id.CreateWorkoutButton)).setOnClickListener(item -> {
            mViewModel.AddFieldToDataBase(createWorkoutViewModel.getObject());
            super.finish();
        });
    }

}