package com.webatron.rakesh.assigkmd;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StorageInfo extends AppCompatActivity {

    private ListView storagelist;

    ArrayAdapter adaptor;

    List<StorageDetails> details;
    long availableram,totalram,usedram,availableinternal,totalinternal,usedinternal,availableext,totalext,usedext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_info);

        storagelist = (ListView)findViewById(R.id.storagelist);

        details = new ArrayList<>();


        /*** Finding Ram Storage Available in your Device  **/
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        availableram = memoryInfo.availMem;

        totalram = memoryInfo.totalMem;

        usedram = totalram-availableram;

        StorageDetails ram = new StorageDetails("RAM",Size(totalram),Size(usedram),Size(availableram));

        details.add(ram);
        boolean availability = android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        /*** Finding Internal Storage Available in your Device  **/

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long temp = stat.getBlockSize();
        long tempavailable = stat.getAvailableBlocks();
        availableinternal = temp*tempavailable;

        totalinternal = stat.getBlockCount()*temp;

        usedinternal = totalinternal-availableinternal;

        StorageDetails internal = new StorageDetails("Internal Memory",Size(totalinternal),Size(usedinternal),Size(availableinternal));
        details.add(internal);


        /*** Finding External Storage Available in your Device  **/

        if(availability){
            File pathexternal = Environment.getExternalStorageDirectory();
            StatFs statexternal = new StatFs(pathexternal.getPath());
            long tempexternal = statexternal.getBlockSize();
            availableext = tempexternal*statexternal.getAvailableBlocks();
            totalext = tempexternal*statexternal.getBlockCount();
        }else {
            availableext =0;
            totalext =0;
        }

        usedext = totalext-availableext;

        StorageDetails external = new StorageDetails("External Storage",Size(totalext),Size(usedext),Size(availableext));
        details.add(external);


        adaptor = new StorageAdaptor(StorageInfo.this,R.layout.storageview,details);
        storagelist.setAdapter(adaptor);


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(StorageInfo.this,Home.class);
        startActivity(intent);
        finish();
    }
}
