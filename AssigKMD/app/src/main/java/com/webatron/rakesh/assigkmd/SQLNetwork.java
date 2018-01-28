package com.webatron.rakesh.assigkmd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rakesh on 26/1/18.
 */

public class SQLNetwork extends SQLiteOpenHelper {


    public final static  String dname = "USERDATABASE";
    public final static int version = 1;
    //Data dt;


    public static final String createtable = "CREATE TABLE "+DatabaseNetwork.Table_Name+"("+DatabaseNetwork.Col_careersim+" varchar(20),"+DatabaseNetwork.Col_careerwifi+" varchar(25),"+DatabaseNetwork.Col_networkname+" varchar(10))";

    public static final String DropTable = "DROP TABLE IF EXISTS "+DatabaseNetwork.Table_Name+"";

    public SQLNetwork(Context context) {
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
