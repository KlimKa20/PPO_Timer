package by.bsuir.ppo_timer.ViewModel;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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
        cv.put("color", workout.getColor());
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
            int color = c.getColumnIndex("color");

            do {
                workouts.add(new Workout(c.getInt(IdColIndex), c.getString(NameColIndex), c.getString(TimeOfPreparationIndex),
                        c.getString(TimeOfWorkIndex), c.getString(TimeOfRestIndex),
                        c.getString(CountOfCyclesIndex), c.getString(CountOfSetsIndex),
                        c.getString(TimeOfRestBetweenSetIndex), c.getString(TimeOfFinalRestIndex), c.getInt(color)));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void DeleteFieldFromDataBase(int id, List<Workout> workouts) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("workout", "id = " + id, null);
        ReadFieldsFromDataBase(workouts);
    }

    public Workout FindById(int id) {
        Workout workout = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("workout", null, "Id = ?", new String[]{Integer.toString(id)}, null, null, null);

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
            int color = c.getColumnIndex("color");
            workout = new Workout(c.getInt(IdColIndex), c.getString(NameColIndex), c.getString(TimeOfPreparationIndex),
                    c.getString(TimeOfWorkIndex), c.getString(TimeOfRestIndex),
                    c.getString(CountOfCyclesIndex), c.getString(CountOfSetsIndex),
                    c.getString(TimeOfRestBetweenSetIndex), c.getString(TimeOfFinalRestIndex), c.getInt(color));
        }
        c.close();
        return workout;
    }

    public void UpdateField(Workout workout) {
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
        cv.put("color", workout.getColor());
        db.update("workout", cv, "id = ?",
                new String[]{Integer.toString(workout.getId())});
    }

    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete("workout", null, null);
    }
}
