package com.webatron.rakesh.assigkmd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rakesh on 26/1/18.
 */

public class SQLStorage extends SQLiteOpenHelper {

    public final static  String dname = "USERDATABASE";
    public final static int version = 1;
    //Data dt;


    public static final String createtable = "CREATE TABLE "+DatabaseStorage.Table_Name+"("+DatabaseStorage.Col_RamTotal+" varchar(20),"+DatabaseStorage.Col_RamAvail+" varchar(25),"+DatabaseStorage.Col_RamUsed+" varchar(10))";

    public static final String DropTable = "DROP TABLE IF EXISTS "+DatabaseStorage.Table_Name+"";

    public SQLStorage(Context context) {
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
