package com.webatron.rakesh.assigkmd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MonitorResult extends AppCompatActivity {

    SQLStorage sqlStorage;
    SQLNetwork sqlNetwork;
    SQLDevice sqlDevice;
    SQLBattery sqlBattery;
    SQLData data;
    ListView resultlist;
    SQLWeather sqlWeather;
    SharedPreferences monitor;
    ArrayAdapter adaptor;
    SharedPreferences.Editor editor;
    List<Resultdata> resultdataList;
    SQLiteDatabase wdatabase,ndatabase,ddatabase,bdatabase,sdatabase,database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_result);
        monitor = getSharedPreferences("Monitor",MODE_PRIVATE);

        resultlist = (ListView)findViewById(R.id.resultlist);
        sqlBattery = new SQLBattery(MonitorResult.this);
        sqlStorage = new SQLStorage(MonitorResult.this);
        sqlNetwork = new SQLNetwork(MonitorResult.this);
        sqlDevice = new SQLDevice(MonitorResult.this);
        sqlWeather = new SQLWeather(MonitorResult.this);

        data = new SQLData(MonitorResult.this);
        resultdataList = new ArrayList<>();
        wdatabase = sqlWeather.getReadableDatabase();
        ndatabase = sqlNetwork.getReadableDatabase();
        ddatabase = sqlDevice.getReadableDatabase();
        bdatabase = sqlBattery.getReadableDatabase();
        sdatabase = sqlStorage.getReadableDatabase();
        database = data.getReadableDatabase();

      //  if(monitor.getString("Status",null) != null){
            if(monitor.getString("Weather",null) != null){

                Cursor wpointer = database.rawQuery("select * from "+Weatherdatabase.Table_Name,null);
                Log.i("Key",""+wpointer);
                while(wpointer.moveToNext()){

                    String place = wpointer.getString(wpointer.getColumnIndex(Weatherdatabase.Col_Place));
                    String weather = wpointer.getString(wpointer.getColumnIndex(Weatherdatabase.Col_Weather));
                    String temp = wpointer.getString(wpointer.getColumnIndex(Weatherdatabase.Col_Temp));

                    Resultdata resultdata = new Resultdata("Weather",place,weather,temp);
                    resultdataList.add(resultdata);
                }
            }

            if(monitor.getString("Network",null)!=null){

                Cursor npointer = database.rawQuery("select * from "+DatabaseNetwork.Table_Name+"",null);
                while (npointer.moveToNext()){
                    String networkname = npointer.getString(npointer.getColumnIndex(DatabaseNetwork.Col_networkname));
                    String carierwifi = npointer.getString(npointer.getColumnIndex(DatabaseNetwork.Col_careerwifi));
                    String carier = npointer.getString(npointer.getColumnIndex(DatabaseNetwork.Col_careersim));

                    Resultdata resultdata = new Resultdata("Network",networkname,carierwifi,carier);

                    resultdataList.add(resultdata);
                }
            }

            if(monitor.getString("Device",null) != null){

                Cursor dpointer = database.rawQuery("select * from "+DatabaseDevice.Table_Name+"",null);
                while ((dpointer.moveToNext())){
                    String devicename = dpointer.getString(dpointer.getColumnIndex(DatabaseDevice.Col_Name));
                    String deviceversion = dpointer.getString(dpointer.getColumnIndex(DatabaseDevice.Col_Version));

                    Resultdata resultdata = new Resultdata("Device",devicename,deviceversion,"noresult");

                    resultdataList.add(resultdata);
                }
            }

            if(monitor.getString("Battery",null) != null){
                Cursor bpointer = database.rawQuery("select * from "+DatabaseBattery.Table_Name+"",null);
                while (bpointer.moveToNext()){
                    String batterystatus = bpointer.getString(bpointer.getColumnIndex(DatabaseBattery.Col_Status));
                    String batterylevel = bpointer.getString(bpointer.getColumnIndex(DatabaseBattery.Col_Level));

                    Resultdata resultdata = new Resultdata("Battery",batterystatus,batterylevel,"noresult");
                    resultdataList.add(resultdata);
                }
            }

            if(monitor.getString("Storage",null)!=null){
                Cursor spointer = database.rawQuery("select * from "+DatabaseStorage.Table_Name,null);
                while (spointer.moveToNext()){
                    String total = spointer.getString(spointer.getColumnIndex(DatabaseStorage.Col_RamTotal));
                    String avail = spointer.getString(spointer.getColumnIndex(DatabaseStorage.Col_RamAvail));
                    String used = spointer.getString(spointer.getColumnIndex(DatabaseStorage.Col_RamUsed));
                    Resultdata resultdata = new Resultdata("Storage","Total RAM: "+total,"AVAIL: "+avail,"USED: "+used);

                    resultdataList.add(resultdata);
                }
            }


            if(resultdataList.isEmpty()){

                Toast.makeText(MonitorResult.this,"Empty no monitored data available",Toast.LENGTH_SHORT).show();
            }else {

                adaptor = new ResultAdaptor(MonitorResult.this, R.layout.result_view, resultdataList);
                resultlist.setAdapter(adaptor);
            }
        //}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if(id == R.id.clearhistory){
            delete();
            Intent intent=getIntent();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(){
        SQLData database;
        SQLiteDatabase sqlit;
        database = new SQLData(getApplicationContext());

        sqlit = database.getWritableDatabase();

        sqlit.execSQL("delete from "+DatabaseStorage.Table_Name);
        sqlit.execSQL("delete from "+DatabaseNetwork.Table_Name);
        sqlit.execSQL("delete from "+DatabaseBattery.Table_Name);
        sqlit.execSQL("delete from "+DatabaseDevice.Table_Name);
        sqlit.execSQL("delete from "+Weatherdatabase.Table_Name);


    }
}
