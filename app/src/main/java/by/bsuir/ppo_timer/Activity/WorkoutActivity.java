package by.bsuir.ppo_timer.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import by.bsuir.ppo_timer.Model.Workout;
import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.ViewModel.CreateWorkoutViewModel;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar;
import codes.side.andcolorpicker.model.IntegerHSLColor;

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
    HSLColorPickerSeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_workout);

        mViewModel = ViewModelProviders.of(this).get(DBViewModel.class);
        createWorkoutViewModel = ViewModelProviders.of(this).get(CreateWorkoutViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        seekBar = (HSLColorPickerSeekBar) findViewById(R.id.hueSeekBar);

        ((EditText) findViewById(R.id.NameTextViewEditText)).setOnEditorActionListener((v, actionId, event) -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                createWorkoutViewModel.setName(((EditText) findViewById(R.id.NameTextViewEditText)).getText().toString());
                actionBar.setTitle(((EditText) findViewById(R.id.NameTextViewEditText)).getText().toString());
                return true;
            }
            return false;
        });

        Intent intent = getIntent();

        findViewById(R.id.PreparationButtonMinus).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFPREPARATION));
        findViewById(R.id.WorkButtonMinus).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFWORK));
        findViewById(R.id.RestButtonMinus).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFREST));
        findViewById(R.id.CycleButtonMinus).setOnClickListener(item -> createWorkoutViewModel.decrement(COUNTOFCYCLE));
        findViewById(R.id.SetButtonMinus).setOnClickListener(item -> createWorkoutViewModel.decrement(COUNTOFSETS));
        findViewById(R.id.TimeOfRestBetweenSetButtonMinus).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFRESTBETWEENSET));
        findViewById(R.id.TimeOfFinalRestButtonMinus).setOnClickListener(item -> createWorkoutViewModel.decrement(TIMEOFFINALREST));

        findViewById(R.id.PreparationButtonPlus).setOnClickListener(item -> createWorkoutViewModel.increment(TIMEOFPREPARATION));
        findViewById(R.id.WorkButtonPlus).setOnClickListener(item -> createWorkoutViewModel.increment(TIMEOFWORK));
        findViewById(R.id.RestButtonPlus).setOnClickListener(item -> createWorkoutViewModel.increment(TIMEOFREST));
        findViewById(R.id.CycleButtonPlus).setOnClickListener(item -> createWorkoutViewModel.increment(COUNTOFCYCLE));
        findViewById(R.id.SetButtonPlus).setOnClickListener(item -> createWorkoutViewModel.increment(COUNTOFSETS));
        findViewById(R.id.TimeOfRestBetweenSetButtonPlus).setOnClickListener(item -> createWorkoutViewModel.increment(TIMEOFRESTBETWEENSET));
        findViewById(R.id.TimeOfFinalRestButtonPlus).setOnClickListener(item -> {
            createWorkoutViewModel.increment(TIMEOFFINALREST);
            createWorkoutViewModel.setColor(seekBar.getProgress());
        });

        createWorkoutViewModel.GetValue(TIMEOFPREPARATION).observe(this, value -> ((TextView) findViewById(R.id.PreparationTextViewEditText)).setText(String.valueOf(value)));
        createWorkoutViewModel.GetValue(TIMEOFWORK).observe(this, value -> ((TextView) findViewById(R.id.WorkTextViewEditText)).setText(String.valueOf(value)));
        createWorkoutViewModel.GetValue(TIMEOFREST).observe(this, value -> ((TextView) findViewById(R.id.RestTextViewEditText)).setText(String.valueOf(value)));
        createWorkoutViewModel.GetValue(COUNTOFCYCLE).observe(this, value -> ((TextView) findViewById(R.id.CycleTextViewEditText)).setText(String.valueOf(value)));
        createWorkoutViewModel.GetValue(COUNTOFSETS).observe(this, value -> ((TextView) findViewById(R.id.SetTextViewEditText)).setText(String.valueOf(value)));
        createWorkoutViewModel.GetValue(TIMEOFRESTBETWEENSET).observe(this, value -> ((TextView) findViewById(R.id.TimeOfRestBetweenSetTextViewEditText)).setText(String.valueOf(value)));
        createWorkoutViewModel.GetValue(TIMEOFFINALREST).observe(this, value -> ((TextView) findViewById(R.id.TimeOfFinalRestTextViewEditText)).setText(String.valueOf(value)));

        int status = intent.getIntExtra("actionObj", 0);

        if (status != -1) {
            ((Button)findViewById(R.id.CreateWorkoutButton)).setText(getResources().getString(R.string.Update));
            Workout workout = mViewModel.FindById(status);
            createWorkoutViewModel.Initialize(workout.getName(), workout.getTimeOfPreparation(), workout.getTimeOfWork(), workout.getTimeOfRest(),
                    workout.getCountOfCycles(), workout.getCountOfSets(), workout.getTimeOfRestBetweenSet(),
                    workout.getTimeOfFinalRest(), workout.getColor());
            seekBar.setPickedColor(convertToIntegerHSLColor(workout.getColor()));
            actionBar.setBackgroundDrawable(new ColorDrawable(workout.getColor()));
            ((EditText) findViewById(R.id.NameTextViewEditText)).setText(workout.getName());
            actionBar.setTitle(workout.getName());
        }

        findViewById(R.id.CreateWorkoutButton).setOnClickListener(item -> {
            IntegerHSLColor ii = seekBar.getPickedColor();
            int c = Color.HSVToColor(new float[]{ii.getFloatH(), ii.getFloatL(), ii.getFloatS()});
            createWorkoutViewModel.setColor(c);
            if (status == -1) {
                mViewModel.AddFieldToDataBase(createNewObject(status));
            } else {
                mViewModel.UpdateField(createNewObject(status));
            }
            super.finish();
        });
        super.onCreate(savedInstanceState);
    }


    private Workout createNewObject(int id) {
        return new Workout(id, createWorkoutViewModel.getName().getValue(), createWorkoutViewModel.GetValue(TIMEOFPREPARATION).getValue(),
                createWorkoutViewModel.GetValue(TIMEOFWORK).getValue(), createWorkoutViewModel.GetValue(TIMEOFREST).getValue(),
                createWorkoutViewModel.GetValue(COUNTOFCYCLE).getValue(), createWorkoutViewModel.GetValue(COUNTOFSETS).getValue(),
                createWorkoutViewModel.GetValue(TIMEOFRESTBETWEENSET).getValue(), createWorkoutViewModel.GetValue(TIMEOFFINALREST).getValue(),
                createWorkoutViewModel.getColor().getValue());
    }

    private IntegerHSLColor convertToIntegerHSLColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        IntegerHSLColor integerHSLColor = new IntegerHSLColor();
        integerHSLColor.setFloatH(hsv[0]);
        integerHSLColor.setFloatL(hsv[1]);
        integerHSLColor.setFloatS(hsv[2]);
        return integerHSLColor;
    }

}