package com.webatron.rakesh.assigkmd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rakesh on 28/1/18.
 */

public class SQLData extends SQLiteOpenHelper {
    public final static  String dname = "USERDATABASE.db";
    public final static int version = 1;

    public static final String createtabledevice = "CREATE TABLE "+DatabaseDevice.Table_Name+"("+DatabaseDevice.Col_Name+" varchar(20),"+DatabaseDevice.Col_Version+" varchar(25))";

    public static final String DropTabledevice = "DROP TABLE IF EXISTS "+DatabaseDevice.Table_Name+"";

    public static final String createtablenetwork = "CREATE TABLE "+DatabaseNetwork.Table_Name+"("+DatabaseNetwork.Col_careersim+" varchar(20),"+DatabaseNetwork.Col_careerwifi+" varchar(25),"+DatabaseNetwork.Col_networkname+" varchar(10))";

    public static final String DropTablenetwork = "DROP TABLE IF EXISTS "+DatabaseNetwork.Table_Name+"";

    public static final String createtablestorage = "CREATE TABLE "+DatabaseStorage.Table_Name+"("+DatabaseStorage.Col_RamTotal+" varchar(20),"+DatabaseStorage.Col_RamAvail+" varchar(25),"+DatabaseStorage.Col_RamUsed+" varchar(10))";

    public static final String DropTablestorage = "DROP TABLE IF EXISTS "+DatabaseStorage.Table_Name+"";

    public static final String createtableweather = "CREATE TABLE "+Weatherdatabase.Table_Name+"("+Weatherdatabase.Col_Place+" varchar(20),"+Weatherdatabase.Col_Weather+" varchar(25),"+Weatherdatabase.Col_Temp+" varchar(10))";

    public static final String DropTableweather = "DROP TABLE IF EXISTS "+Weatherdatabase.Table_Name+"";

    public static final String createtablebattery = "CREATE TABLE "+DatabaseBattery.Table_Name+"("+DatabaseBattery.Col_Status+" varchar(20),"+DatabaseBattery.Col_Level+" varchar(25))";

    public static final String DropTablebattery = "DROP TABLE IF EXISTS "+DatabaseBattery.Table_Name+"";


    public SQLData(Context context) {
        super(context, dname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createtablebattery);
        db.execSQL(createtabledevice);
        db.execSQL(createtablenetwork);
        db.execSQL(createtablestorage);
        db.execSQL(createtableweather);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DropTablebattery);
        db.execSQL(DropTabledevice);
        db.execSQL(DropTablenetwork);
        db.execSQL(DropTablestorage);
        db.execSQL(DropTableweather);

        onCreate(db);


    }

}
