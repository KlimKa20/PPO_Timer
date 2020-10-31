package by.bsuir.ppo_timer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table workout ("
                + "id integer primary key autoincrement,"
                + "Name text,"
                + "TimeOfPreparation integer,"
                + "TimeOfWork integer,"
                + "TimeOfRest integer,"
                + "CountOfCycles integer,"
                + "CountOfSets integer,"
                + "TimeOfRestBetweenSet integer,"
                + "TimeOfFinalRest integer,"
                + "color integer" + ");");
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
                        + "color integer" + ");");

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
                        + "color integer" + ");");

                db.execSQL("insert into workout select id, Name, TimeOfPreparation, TimeOfWork,TimeOfRest,CountOfCycles,CountOfSets,TimeOfRestBetweenSet,TimeOfFinalRest,color from people_tmp;");
                db.execSQL("drop table people_tmp;");

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
        if (oldVersion == 3 && newVersion == 4) {
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
                        + "color integer" + ");");

                db.execSQL("insert into people_tmp select id, Name, TimeOfPreparation, TimeOfWork,TimeOfRest,CountOfCycles,CountOfSets,TimeOfRestBetweenSet,TimeOfFinalRest, color from workout;");
                db.execSQL("drop table workout;");

                db.execSQL("create table workout ("
                        + "id integer primary key autoincrement,"
                        + "Name text,"
                        + "TimeOfPreparation integer,"
                        + "TimeOfWork integer,"
                        + "TimeOfRest integer,"
                        + "CountOfCycles integer,"
                        + "CountOfSets integer,"
                        + "TimeOfRestBetweenSet integer,"
                        + "TimeOfFinalRest integer,"
                        + "color integer" + ");");

                db.execSQL("insert into workout select id, Name, cast( TimeOfPreparation as integer), cast( TimeOfWork as integer),cast( TimeOfRest as integer),cast( CountOfCycles as integer),cast( CountOfSets as integer),cast( TimeOfRestBetweenSet as integer),cast( TimeOfFinalRest as integer),color from people_tmp;");
                db.execSQL("drop table people_tmp;");

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }
}
