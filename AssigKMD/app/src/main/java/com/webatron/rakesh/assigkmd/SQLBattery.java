package com.webatron.rakesh.assigkmd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rakesh on 26/1/18.
 */

public class SQLBattery extends SQLiteOpenHelper {

    public final static  String dname = "USERDATABASE";
    public final static int version = 1;
    //Data dt;


    public static final String createtable = "CREATE TABLE "+DatabaseBattery.Table_Name+"("+DatabaseBattery.Col_Status+" varchar(20),"+DatabaseBattery.Col_Level+" varchar(25))";

    public static final String DropTable = "DROP TABLE IF EXISTS "+DatabaseBattery.Table_Name+"";

    public SQLBattery(Context context) {
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
