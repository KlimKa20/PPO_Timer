package by.bsuir.ppo_timer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
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
                + "TimeOfFinalRest text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
