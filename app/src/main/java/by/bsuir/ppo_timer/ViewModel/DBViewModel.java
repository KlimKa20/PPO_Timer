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

    private final DBHelper dbHelper;
    final private String idColumn = "id";
    final private String NameColumn = "Name";
    final private String TimeOfPreparationColumn = "TimeOfPreparation";
    final private String TimeOfWorkColumn = "TimeOfWork";
    final private String TimeOfRestColumn = "TimeOfRest";
    final private String CountOfCyclesColumn = "CountOfCycles";
    final private String CountOfSetsColumn = "CountOfSets";
    final private String TimeOfRestBetweenSetColumn = "TimeOfRestBetweenSet";
    final private String TimeOfFinalRestColumn = "TimeOfFinalRest";
    final private String colorColumn = "color";
    final private String table = "workout";

    public DBViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new DBHelper(getApplication());
    }

    public void AddFieldToDataBase(Workout workout) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put(NameColumn, workout.getName());
        cv.put(TimeOfPreparationColumn, workout.getTimeOfPreparation());
        cv.put(TimeOfWorkColumn, workout.getTimeOfWork());
        cv.put(TimeOfRestColumn, workout.getTimeOfRest());
        cv.put(CountOfCyclesColumn, workout.getCountOfCycles());
        cv.put(CountOfSetsColumn, workout.getCountOfSets());
        cv.put(TimeOfRestBetweenSetColumn, workout.getTimeOfRestBetweenSet());
        cv.put(TimeOfFinalRestColumn, workout.getTimeOfFinalRest());
        cv.put(colorColumn, workout.getColor());
        db.insert(table, null, cv);
    }

    public void ReadFieldsFromDataBase(List<Workout> workouts) {
        workouts.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(table, null, null, null, null, null, null);
        if (c.moveToFirst()) {

            int IdColIndex = c.getColumnIndex(idColumn);
            int NameColIndex = c.getColumnIndex(NameColumn);
            int TimeOfPreparationIndex = c.getColumnIndex(TimeOfPreparationColumn);
            int TimeOfWorkIndex = c.getColumnIndex(TimeOfWorkColumn);
            int TimeOfRestIndex = c.getColumnIndex(TimeOfRestColumn);
            int CountOfCyclesIndex = c.getColumnIndex(CountOfCyclesColumn);
            int CountOfSetsIndex = c.getColumnIndex(CountOfSetsColumn);
            int TimeOfRestBetweenSetIndex = c.getColumnIndex(TimeOfRestBetweenSetColumn);
            int TimeOfFinalRestIndex = c.getColumnIndex(TimeOfFinalRestColumn);
            int color = c.getColumnIndex(colorColumn);

            do {
                workouts.add(new Workout(c.getInt(IdColIndex), c.getString(NameColIndex), c.getInt(TimeOfPreparationIndex),
                        c.getInt(TimeOfWorkIndex), c.getInt(TimeOfRestIndex),
                        c.getInt(CountOfCyclesIndex), c.getInt(CountOfSetsIndex),
                        c.getInt(TimeOfRestBetweenSetIndex), c.getInt(TimeOfFinalRestIndex), c.getInt(color)));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void DeleteFieldFromDataBase(int id, List<Workout> workouts) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(table, "id = " + id, null);
        ReadFieldsFromDataBase(workouts);
    }

    public Workout FindById(int id) {
        Workout workout = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(table, null, "Id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (c.moveToFirst()) {
            int IdColIndex = c.getColumnIndex(idColumn);
            int NameColIndex = c.getColumnIndex(NameColumn);
            int TimeOfPreparationIndex = c.getColumnIndex(TimeOfPreparationColumn);
            int TimeOfWorkIndex = c.getColumnIndex(TimeOfWorkColumn);
            int TimeOfRestIndex = c.getColumnIndex(TimeOfRestColumn);
            int CountOfCyclesIndex = c.getColumnIndex(CountOfCyclesColumn);
            int CountOfSetsIndex = c.getColumnIndex(CountOfSetsColumn);
            int TimeOfRestBetweenSetIndex = c.getColumnIndex(TimeOfRestBetweenSetColumn);
            int TimeOfFinalRestIndex = c.getColumnIndex(TimeOfFinalRestColumn);
            int color = c.getColumnIndex(colorColumn);
            workout = new Workout(c.getInt(IdColIndex), c.getString(NameColIndex), c.getInt(TimeOfPreparationIndex),
                    c.getInt(TimeOfWorkIndex), c.getInt(TimeOfRestIndex),
                    c.getInt(CountOfCyclesIndex), c.getInt(CountOfSetsIndex),
                    c.getInt(TimeOfRestBetweenSetIndex), c.getInt(TimeOfFinalRestIndex), c.getInt(color));
        }
        c.close();
        return workout;
    }

    public void UpdateField(Workout workout) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put(NameColumn, workout.getName());
        cv.put(TimeOfPreparationColumn, workout.getTimeOfPreparation());
        cv.put(TimeOfWorkColumn, workout.getTimeOfWork());
        cv.put(TimeOfRestColumn, workout.getTimeOfRest());
        cv.put(CountOfCyclesColumn, workout.getCountOfCycles());
        cv.put(CountOfSetsColumn, workout.getCountOfSets());
        cv.put(TimeOfRestBetweenSetColumn, workout.getTimeOfRestBetweenSet());
        cv.put(TimeOfFinalRestColumn, workout.getTimeOfFinalRest());
        cv.put(colorColumn, workout.getColor());
        db.update(table, cv, "id = ?",
                new String[]{Integer.toString(workout.getId())});
    }

    public void removeAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(table, null, null);
    }
}
