package by.bsuir.ppo_timer;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {

    DBHelper dbHelper;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new DBHelper(getApplication());
    }

    public void AddFieldToDataBase(String Name, int TimeOfPreparation,
                                   int TimeOfWork, int TimeOfRest,
                                   int CountOfCycles, int CountOfSets,
                                   int TimeOfRestBetweenSet, int TimeOfFinalRest) {
        ContentValues cv = new ContentValues();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("Name", Name);
        cv.put("TimeOfPreparation", TimeOfPreparation);
        cv.put("TimeOfWork", TimeOfWork);
        cv.put("TimeOfRest", TimeOfRest);
        cv.put("CountOfCycles", CountOfCycles);
        cv.put("CountOfSets", CountOfSets);
        cv.put("TimeOfRestBetweenSet", TimeOfRestBetweenSet);
        cv.put("TimeOfFinalRest", TimeOfFinalRest);
        db.insert("workout", null, cv);
    }

    public void ReadFieldsFromDataBase(List<Workout> workouts) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("workout", null, null, null, null, null, null);
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int NameColIndex = c.getColumnIndex("Name");
            int TimeOfPreparationIndex = c.getColumnIndex("TimeOfPreparation");
            int TimeOfWorkIndex = c.getColumnIndex("TimeOfWork");
            int TimeOfRestIndex = c.getColumnIndex("TimeOfRest");
            int CountOfCyclesIndex = c.getColumnIndex("CountOfCycles");
            int CountOfSetsIndex = c.getColumnIndex("CountOfSets");
            int TimeOfRestBetweenSetIndex = c.getColumnIndex("TimeOfRestBetweenSet");
            int TimeOfFinalRestIndex = c.getColumnIndex("TimeOfFinalRest");

            do {
                String nn = c.getString(NameColIndex)
                        + c.getString(TimeOfPreparationIndex)
                        + c.getString(TimeOfWorkIndex)
                        + c.getString(TimeOfRestIndex)
                        + c.getString(CountOfCyclesIndex)
                        + c.getString(CountOfSetsIndex)
                        + c.getString(TimeOfRestBetweenSetIndex)
                        + c.getInt(TimeOfFinalRestIndex);
                workouts.add(new Workout(c.getString(NameColIndex), c.getInt(TimeOfPreparationIndex),
                        c.getInt(TimeOfWorkIndex), c.getInt(TimeOfRestIndex),
                        c.getInt(CountOfCyclesIndex), c.getInt(CountOfSetsIndex),
                        c.getInt(TimeOfRestBetweenSetIndex), c.getInt(TimeOfFinalRestIndex)));

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else {
            workouts.add(new Workout("start", 10, 10, 10, 10, 10, 10, 10));
        }
        c.close();
    }
}
