package com.webatron.rakesh.assigkmd;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NetworkDetails extends AppCompatActivity {

    private String connection,carrier;
    private ListView network;
    ArrayAdapter adaptor;
    List<NetworkINFO> infos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_details);

        network = (ListView)findViewById(R.id.networklist);

        infos = new ArrayList<>();
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected()){
            connection = "-";
            NetworkINFO net = new NetworkINFO("no Connection available","-");
            infos.add(net);
        }else if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            connection="WIFI";
            NetworkINFO net = new NetworkINFO(connection,"UNKNOWN");
            infos.add(net);

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

            NetworkINFO net = new NetworkINFO(connection,carrier);
            infos.add(net);
        }

        adaptor = new NetworkAdaptor(NetworkDetails.this,R.layout.networkview,infos);
        network.setAdapter(adaptor);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NetworkDetails.this,Home.class);
        startActivity(intent);
        finish();
    }
}
