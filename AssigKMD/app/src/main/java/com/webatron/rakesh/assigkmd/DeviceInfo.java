package com.webatron.rakesh.assigkmd;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DeviceInfo extends AppCompatActivity {

    String release,name,version;
    private TextView devicename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        devicename = (TextView)findViewById(R.id.devicename);

        name = Build.MANUFACTURER;

        release = Build.MODEL;
        version = Build.VERSION.RELEASE;

        devicename.setText(name+"-"+release+" version : "+version);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DeviceInfo.this,Home.class);
        startActivity(intent);
        finish();
    }
}
