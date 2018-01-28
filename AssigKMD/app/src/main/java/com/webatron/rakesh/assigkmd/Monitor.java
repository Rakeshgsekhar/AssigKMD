package com.webatron.rakesh.assigkmd;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by rakesh on 25/1/18.
 */

public class Monitor extends Service {

    SharedPreferences monitor;
    SharedPreferences.Editor editor;
    String status,weather,battery,network,storage,device,duration;
    String weatherplace,weatherdata,weathertemp,batterydata;
    LocationManager locationManager;
    String provider;
    static double lat, lon;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    int MY_PERMISSION = 0;
    long availableram,totalram,usedram;
    String connection,carrier;
    String release,name,version;
    SQLiteDatabase wsqlit,nsqlit,bsqlit,dsqlit,ssqlit;
    String statusbat;
    SQLData database;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {



        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this,"Started",Toast.LENGTH_SHORT).show();
        monitor = getSharedPreferences("Monitor",MODE_PRIVATE);
        status = monitor.getString("Status",null);
        weather = monitor.getString("Weather",null);
        battery = monitor.getString("Battery",null);
        storage = monitor.getString("Storage",null);
        network = monitor.getString("Network",null);
        device = monitor.getString("Device",null);
        duration = monitor.getString("Duration",null);



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(status!=null) {

            database = new SQLData(getApplicationContext());

            if (weather != null) {

                weatherinfo();

            } else {

            }

            if (storage != null) {

                storageinfo();
            } else {

            }

            if (network != null) {

                networkinfo();
            } else {

            }

            if (device != null) {

                deviceinfo();
            } else {

            }

            if (battery != null) {

                batteryinfo();
            } else {

            }
        }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"Service stopped",Toast.LENGTH_SHORT).show();



    }



    public void batteryinfo(){


        Intent battery = registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int status = battery.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
        if(isCharging){
            statusbat = "Charging";
        }else {
            statusbat = "Not Charging";
        }
        int level = (int)batterylevel();
        batterydata =""+level+" %";

       // bdatabase = new SQLBattery(getApplicationContext());
        bsqlit = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseBattery.Col_Status,statusbat);
        values.put(DatabaseBattery.Col_Level,batterydata);

        Long id = bsqlit.insert(DatabaseBattery.Table_Name,null,values);
        bsqlit.close();

    }
    public void weatherinfo(){


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        Location location = locationManager.getLastKnownLocation(provider);

        if(location == null){
            Toast.makeText(this,"NO location available",Toast.LENGTH_SHORT).show();
        }

        lat = location.getLatitude();
        lon = location.getLongitude();
        new GetWeather().execute(WeatherApp.apiRequest(String.valueOf(lat),String.valueOf(lon)));

    }

    public void networkinfo(){

        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected()){
            connection = "-";
        }else if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            connection="WIFI";

        }else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            int connectiontype = networkInfo.getSubtype();
            TelephonyManager manager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
            carrier = manager.getNetworkOperatorName();
            if(connectiontype == TelephonyManager.NETWORK_TYPE_CDMA){
                connection = "2G";

            }else if(connectiontype == TelephonyManager.NETWORK_TYPE_UMTS){
                connection = "3G";
            }else if(connectiontype == TelephonyManager.NETWORK_TYPE_LTE){
                connection = "4G";
            }

        }
       // ndatabase = new SQLNetwork(getApplicationContext());
        nsqlit = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseNetwork.Col_networkname,connection);
        values.put(DatabaseNetwork.Col_careerwifi,"Unknown");
        values.put(DatabaseNetwork.Col_careersim,"carrier");

        long id = nsqlit.insert(DatabaseNetwork.Table_Name,null,values);
        nsqlit.close();


    }

    public void storageinfo(){


        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        availableram = memoryInfo.availMem;

        totalram = memoryInfo.totalMem;

        usedram = totalram-availableram;

        //sdatabase = new SQLStorage(getApplicationContext());
        ssqlit =database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseStorage.Col_RamTotal,totalram);
        values.put(DatabaseStorage.Col_RamAvail,availableram);
        values.put(DatabaseStorage.Col_RamUsed,usedram);

        long id = ssqlit.insert(DatabaseStorage.Table_Name,null,values);
        ssqlit.close();

    }

    public void deviceinfo(){

        name = Build.MANUFACTURER;
        release = Build.MODEL;
        version = Build.VERSION.RELEASE;

        //ddatabase = new SQLDevice(getApplicationContext());
        dsqlit = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseDevice.Col_Name,name);
        values.put(DatabaseDevice.Col_Version,version);

        Long id = dsqlit.insert(DatabaseDevice.Table_Name,null,values);
        dsqlit.close();

    }

    public class GetWeather extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String stream = null;
            String urlString = strings[0];

            Weatherhelp http = new Weatherhelp();
            stream = http.getdata(urlString);


            return stream;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.contains("Error: City not found")){
               // prg.dismiss();
                return;
            }

            Gson gson = new Gson();
            Type type = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s,type);


            weatherplace = String.format("%s,%s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry());
            weatherdata = String.format("%s",openWeatherMap.getWeather().get(0).getDescription());
            Log.i("String",s);
            weathertemp = String.format("%s",openWeatherMap.getMain().getTemp());

//            wdatabase = new SQLWeather(getApplicationContext());

            wsqlit = database.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(Weatherdatabase.Col_Place,weatherplace);
            values.put(Weatherdatabase.Col_Weather,weatherdata);
            values.put(Weatherdatabase.Col_Temp,weathertemp);
            Long id = wsqlit.insert(Weatherdatabase.Table_Name,null,values);
            Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_SHORT).show();
            wsqlit.close();




        }
    }



    public float batterylevel(){

        Intent battery = registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = battery.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale = battery.getIntExtra(BatteryManager.EXTRA_SCALE,-1);

        if(level == -1 || scale ==-1){
            return 50.0f;
        }

        return (((float) level/(float)scale)*100.0f);
    }


    public String Size(long size){
        String sizeidentifier = null;

        if(size >=1024){
            sizeidentifier = " KB";
            size/=1024;
            if(size >=1024){
                sizeidentifier = " MB";
                size/=1024;

            }

        }
        StringBuilder result = new StringBuilder(Long.toString(size));
        if(sizeidentifier != null){
            result.append(sizeidentifier);
        }
        return result.toString();
    }

}
