package by.bsuir.ppo_timer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table workout ("
                + "id integer primary key autoincrement,"
                + "Name text,"
                + "TimeOfPreparation text,"
                + "TimeOfWork text,"
                + "TimeOfRest text,"
                + "CountOfCycles text,"
                + "CountOfSets text,"
                + "TimeOfRestBetweenSet text,"
                + "TimeOfFinalRest text"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 2 && newVersion == 3) {
            db.beginTransaction();
            try {


                db.execSQL("create temporary table people_tmp ("
                        + "id integer primary key autoincrement,"
                        + "Name text,"
                        + "TimeOfPreparation text,"
                        + "TimeOfWork text,"
                        + "TimeOfRest text,"
                        + "CountOfCycles text,"
                        + "CountOfSets text,"
                        + "TimeOfRestBetweenSet text,"
                        + "TimeOfFinalRest text,"
                        + "color integer"+");");

                db.execSQL("insert into people_tmp select id, Name, TimeOfPreparation, TimeOfWork,TimeOfRest,CountOfCycles,CountOfSets,TimeOfRestBetweenSet,TimeOfFinalRest,-16777216 from workout;");
                db.execSQL("drop table workout;");

                db.execSQL("create table workout ("
                        + "id integer primary key autoincrement,"
                        + "Name text,"
                        + "TimeOfPreparation text,"
                        + "TimeOfWork text,"
                        + "TimeOfRest text,"
                        + "CountOfCycles text,"
                        + "CountOfSets text,"
                        + "TimeOfRestBetweenSet text,"
                        + "TimeOfFinalRest text,"
                        + "color integer"+");");

                db.execSQL("insert into workout select id, Name, TimeOfPreparation, TimeOfWork,TimeOfRest,CountOfCycles,CountOfSets,TimeOfRestBetweenSet,TimeOfFinalRest,color from people_tmp;");
                db.execSQL("drop table people_tmp;");

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }
}
