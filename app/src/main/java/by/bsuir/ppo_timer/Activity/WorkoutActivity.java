package by.bsuir.ppo_timer.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.divyanshu.colorseekbar.ColorSeekBar;

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
//    ColorSeekBar colorSeekBar;
    HSLColorPickerSeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setContentView(R.layout.activity_workout);
        mViewModel = ViewModelProviders.of(this).get(DBViewModel.class);
        createWorkoutViewModel = ViewModelProviders.of(this).get(CreateWorkoutViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        seekBar = (HSLColorPickerSeekBar) findViewById(R.id.hueSeekBar);
//        colorSeekBar = findViewById(R.id.color_seekBar);
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
        findViewById(R.id.TimeOfFinalRestButtonPlus).setOnClickListener(item -> {createWorkoutViewModel.increment(TIMEOFFINALREST);createWorkoutViewModel.setColor(seekBar.getProgress());});
//        colorSeekBar.setOnColorChangeListener(i -> createWorkoutViewModel.setColor(i));
//        seekBar.setOnClickListener(v -> {
//            IntegerHSLColor ii = seekBar.getPickedColor();
//            int c = Color.HSVToColor(new float[]{ii.getFloatH(),ii.getFloatL(),ii.getFloatS()});
//            createWorkoutViewModel.setColor(c);
//        });

        createWorkoutViewModel.GetValue(TIMEOFPREPARATION).observe(this, value -> ((TextView) findViewById(R.id.PreparationTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(TIMEOFWORK).observe(this, value -> ((TextView) findViewById(R.id.WorkTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(TIMEOFREST).observe(this, value -> ((TextView) findViewById(R.id.RestTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(COUNTOFCYCLE).observe(this, value -> ((TextView) findViewById(R.id.CycleTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(COUNTOFSETS).observe(this, value -> ((TextView) findViewById(R.id.SetTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(TIMEOFRESTBETWEENSET).observe(this, value -> ((TextView) findViewById(R.id.TimeOfRestBetweenSetTextViewEditText)).setText(value));
        createWorkoutViewModel.GetValue(TIMEOFFINALREST).observe(this, value -> ((TextView) findViewById(R.id.TimeOfFinalRestTextViewEditText)).setText(value));
//        createWorkoutViewModel.getColor().observe(this, value -> actionBar.setBackgroundDrawable(new ColorDrawable(value)));

        int status = intent.getIntExtra("actionObj",0);
        if (status != -1) {
            Workout workout = mViewModel.FindById(status);
            createWorkoutViewModel.Initialize(workout);
            createWorkoutViewModel.setName(workout.getName());
            float[] hsv = new float[3];
            Color.colorToHSV(workout.getColor(), hsv);
            IntegerHSLColor integerHSLColor = new IntegerHSLColor();
            integerHSLColor.setFloatH(hsv[0]);
            integerHSLColor.setFloatL(hsv[1]);
            integerHSLColor.setFloatS(hsv[2]);
            seekBar.setPickedColor(integerHSLColor);
            actionBar.setBackgroundDrawable(new ColorDrawable(workout.getColor()));
            ((EditText) findViewById(R.id.NameTextViewEditText)).setText(workout.getName());
            actionBar.setTitle(workout.getName());
        }

        findViewById(R.id.CreateWorkoutButton).setOnClickListener(item -> {
            IntegerHSLColor ii = seekBar.getPickedColor();
            int c = Color.HSVToColor(new float[]{ii.getFloatH(),ii.getFloatL(),ii.getFloatS()});
            createWorkoutViewModel.setColor(c);
            if (status == -1) {
                mViewModel.AddFieldToDataBase(createWorkoutViewModel.getObject(0));
            } else {
                mViewModel.UpdateField(createWorkoutViewModel.getObject(status));
            }
            super.finish();
        });
        super.onCreate(savedInstanceState);
    }

}