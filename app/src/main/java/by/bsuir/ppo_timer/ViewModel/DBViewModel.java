package by.bsuir.ppo_timer.ViewModel;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.ppo_timer.DBHelper;
import by.bsuir.ppo_timer.Model.Workout;

public class DBViewModel extends AndroidViewModel {

    private DBHelper dbHelper;

    public DBViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new DBHelper(getApplication());
    }

    public void AddFieldToDataBase(Workout workout) {
        ContentValues cv = new ContentValues();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("Name", workout.getName());
        cv.put("TimeOfPreparation", workout.getTimeOfPreparation());
        cv.put("TimeOfWork", workout.getTimeOfWork());
        cv.put("TimeOfRest", workout.getTimeOfRest());
        cv.put("CountOfCycles", workout.getCountOfCycles());
        cv.put("CountOfSets", workout.getCountOfSets());
        cv.put("TimeOfRestBetweenSet", workout.getTimeOfRestBetweenSet());
        cv.put("TimeOfFinalRest", workout.getTimeOfFinalRest());
        db.insert("workout", null, cv);
    }

    public void ReadFieldsFromDataBase(List<Workout> workouts) {
        workouts.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("workout", null, null, null, null, null, null);
        if (c.moveToFirst()) {

            int IdColIndex = c.getColumnIndex("id");
            int NameColIndex = c.getColumnIndex("Name");
            int TimeOfPreparationIndex = c.getColumnIndex("TimeOfPreparation");
            int TimeOfWorkIndex = c.getColumnIndex("TimeOfWork");
            int TimeOfRestIndex = c.getColumnIndex("TimeOfRest");
            int CountOfCyclesIndex = c.getColumnIndex("CountOfCycles");
            int CountOfSetsIndex = c.getColumnIndex("CountOfSets");
            int TimeOfRestBetweenSetIndex = c.getColumnIndex("TimeOfRestBetweenSet");
            int TimeOfFinalRestIndex = c.getColumnIndex("TimeOfFinalRest");

            do {
                workouts.add(new Workout(c.getInt(IdColIndex), c.getString(NameColIndex), c.getString(TimeOfPreparationIndex),
                        c.getString(TimeOfWorkIndex), c.getString(TimeOfRestIndex),
                        c.getString(CountOfCyclesIndex), c.getString(CountOfSetsIndex),
                        c.getString(TimeOfRestBetweenSetIndex), c.getString(TimeOfFinalRestIndex)));

            } while (c.moveToNext());
        }
        c.close();
    }
    public void DeleteFieldFromDataBase(int id,List<Workout> workouts) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("workout", "id = " + id, null);
        ReadFieldsFromDataBase(workouts);
    }
}
