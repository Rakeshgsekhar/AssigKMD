package com.webatron.rakesh.assigkmd;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AddMonitor extends AppCompatActivity {

    private RadioButton weather,storage,battery,network,device,hr,min;
    private ToggleButton monitor;
    private EditText duration;
    private String timeduration,timeinhrmin;
    private Button okey,close,clear;
    int time;
    Boolean flag=true;
    RadioGroup rgp;
    SharedPreferences monitordata;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitor);

        monitordata = getSharedPreferences("Monitor",MODE_PRIVATE);

        editor = monitordata.edit();
        weather = (RadioButton)findViewById(R.id.rweather);
        storage = (RadioButton)findViewById(R.id.rstorage);
        battery = (RadioButton)findViewById(R.id.rbattery);
        network = (RadioButton)findViewById(R.id.rnetwork);
        device = (RadioButton)findViewById(R.id.rdevice);
        duration = (EditText)findViewById(R.id.duration);
        monitor = (ToggleButton)findViewById(R.id.monitorstatus);
        okey = (Button)findViewById(R.id.confirm);
        rgp = (RadioGroup)findViewById(R.id.timestamp);
        clear = (Button)findViewById(R.id.clear);
        close = (Button)findViewById(R.id.close);



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weather.isChecked()){
                    weather.setChecked(false);
                }
                if(storage.isChecked()){
                    storage.setChecked(false);
                }
                if(network.isChecked()){
                    network.setChecked(false);
                }
                if(device.isChecked()){
                    device.setChecked(false);
                }
                if(battery.isChecked()){
                    battery.setChecked(false);
                }
            }
        });

        if(monitordata.getString("Status",null) == null){
            monitor.getTextOff();
        }

        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = rgp.getCheckedRadioButtonId();
                min = (RadioButton)findViewById(id);
                timeinhrmin = min.getText().toString();

                if(weather.isChecked()){
                    editor.putString("Weather",weather.getText().toString());
                }else {
                    editor.putString("Weather",null);
                }
                if(battery.isChecked()){
                    editor.putString("Battery",battery.getText().toString());
                }else {
                    editor.putString("Battery",null);
                }
                if(network.isChecked()){
                    editor.putString("Network",network.getText().toString());
                }else {
                    editor.putString("Network",null);
                }
                if(device.isChecked()){
                    editor.putString("Device",device.getText().toString());
                }else {
                    editor.putString("Device",null);
                }
                if(min.isChecked()){

                    if(timeinhrmin.equals("Min")){

                        timeduration = duration.getText().toString();
                        if((Integer.parseInt(timeduration)<5)){
                            flag = false;
                        }else {
                            flag=true;
                        }
                        time =Integer.parseInt(timeduration)*60000;
                        editor.putString("Duration",String.valueOf(time));
                    }else if(timeinhrmin.equals("Hr")){
                        timeduration = duration.getText().toString();
                        time = Integer.parseInt(timeduration)*3200000;
                        editor.putString("Duration",String.valueOf(time));

                    }
                }
                if(monitor.isChecked()){
                    editor.putString("Status","monitor");

                }
                if(storage.isChecked()){
                    editor.putString("Storage",storage.getText().toString());
                }else {
                    editor.putString("Storage",null);
                }


                if(flag ==true ){

                    editor.apply();

                    Toast.makeText(AddMonitor.this,monitordata.getString("Status",null),Toast.LENGTH_SHORT).show();

                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(System.currentTimeMillis());
                    cal.add(Calendar.SECOND,50);
                    Intent i = new Intent(AddMonitor.this,Monitor.class);
                    PendingIntent pintent = PendingIntent.getService(AddMonitor.this,0,i,0);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),(long)time,pintent);

                }
                else {

                    Toast.makeText(AddMonitor.this,"Monitoring time cannot be less than 5 Min",Toast.LENGTH_SHORT).show();

                }
                //startService(i);

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(AddMonitor.this);
                alert.setTitle("Stopping Service");
                alert.setMessage("Stopping the service will remove all monitoring");
                alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(AddMonitor.this,Monitor.class);
                        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),0,i,0);
                        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);

                        editor.putString("Status",null);
                        editor.apply();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog alertbuild = alert.create();
                alertbuild.show();

                //stopService(i);
            }
        });


    }

}
