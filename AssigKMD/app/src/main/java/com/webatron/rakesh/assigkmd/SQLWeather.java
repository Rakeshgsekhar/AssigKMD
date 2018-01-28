package com.webatron.rakesh.assigkmd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rakesh on 26/1/18.
 */

public class SQLWeather extends SQLiteOpenHelper {

    public final static  String dname = "USERDATABASE";
    public final static int version = 1;
    //Data dt;


    public static final String createtable = "CREATE TABLE "+Weatherdatabase.Table_Name+"("+Weatherdatabase.Col_Place+" varchar(20),"+Weatherdatabase.Col_Weather+" varchar(25),"+Weatherdatabase.Col_Temp+" varchar(10))";

    public static final String DropTable = "DROP TABLE IF EXISTS "+Weatherdatabase.Table_Name+"";

    public SQLWeather(Context context) {

        super(context, dname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DropTable);
        onCreate(db);
    }
}
