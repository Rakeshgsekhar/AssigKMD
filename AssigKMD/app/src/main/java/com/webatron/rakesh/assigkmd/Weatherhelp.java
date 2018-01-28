package com.webatron.rakesh.assigkmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rakesh on 25/1/18.
 */

public class Weatherhelp {
    static String stream = null;
    public Weatherhelp(){

    }

    public String getdata(String urlstring){
        try{
            URL url = new URL(urlstring);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            if(httpURLConnection.getResponseCode()==200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder builder =new StringBuilder();
                String line;
                while ((line = reader.readLine()) !=null){

                    builder.append(line);
                    stream = builder.toString();
                    httpURLConnection.disconnect();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stream;
    }
}
