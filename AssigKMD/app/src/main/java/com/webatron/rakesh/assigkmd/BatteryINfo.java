package com.webatron.rakesh.assigkmd;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BatteryINfo extends AppCompatActivity {


    private ImageView batteryimg;
    private TextView betteryinfo;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_info);

        batteryimg = (ImageView)findViewById(R.id.batteryimg);
        betteryinfo = (TextView)findViewById(R.id.bateryinfotext);
        final Intent battery = registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int status = battery.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;



        runnable = new Runnable() {
            @Override
            public void run() {

                int level = (int)batterylevel();
                betteryinfo.setText("BATTERY : "+level+" %");
                if(level>80){
                    batteryimg.setImageResource(R.drawable.ic_battery_full_black_24dp);
                }
                else if(level>50 && level <=80){
                    batteryimg.setImageResource(R.drawable.ic_battery_80_black_24dp);
                }else if(level<=50 && level>0){
                    batteryimg.setImageResource(R.drawable.ic_battery_20_black_24dp);
                }
                handler.postDelayed(runnable,5000);
            }
        };


        handler = new Handler();
        handler.postDelayed(runnable,0);


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BatteryINfo.this,Home.class);
        startActivity(intent);
        finish();
    }
}
