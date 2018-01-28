package com.webatron.rakesh.assigkmd;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Home extends AppCompatActivity implements LocationListener {

    private Button battery, device, network, storage,monitor;

    TextView place, weather;

    LocationManager locationManager;
    String provider;
    static double lat, lon;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    int MY_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        battery = (Button) findViewById(R.id.battery);
        device = (Button) findViewById(R.id.device);
        network = (Button) findViewById(R.id.network);
        storage = (Button) findViewById(R.id.storage);
        monitor = (Button) findViewById(R.id.monitorvalues);

        place = (TextView) findViewById(R.id.weatherPlace);
        weather = (TextView) findViewById(R.id.weather);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Home.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }


        Location location = locationManager.getLastKnownLocation(provider);

        if(location == null){
            Toast.makeText(Home.this,"NO location available",Toast.LENGTH_SHORT).show();
        }

        /**** **/
        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, BatteryINfo.class);
                startActivity(intent);
                finish();
            }
        });


        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, DeviceInfo.class);
                startActivity(intent);
                finish();
            }
        });


        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, NetworkDetails.class);
                startActivity(intent);
                finish();
            }
        });


        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, StorageInfo.class);
                startActivity(intent);
                finish();
            }
        });

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,MonitorResult.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home.this,AddMonitor.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.monitordetails){
            Intent intent = new Intent(Home.this,MonitorResult.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Home.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lon = location.getLongitude();
        new GetWeather().execute(WeatherApp.apiRequest(String.valueOf(lat),String.valueOf(lon)));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private class GetWeather extends AsyncTask<String,Void,String>{


        ProgressDialog prg = new ProgressDialog(Home.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prg.setMessage("Please WAIT.. . !");
            prg.show();

        }

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

            prg.dismiss();
            if(s.contains("Error: City not found")){
                prg.dismiss();
                return;
            }

            Gson gson = new Gson();
            Type type = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s,type);
            prg.dismiss();

            place.setText(String.format("%s,%s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
            weather.setText(String.format("%s",openWeatherMap.getWeather().get(0).getDescription()));
            Log.i("String",s);

        }
    }

}
