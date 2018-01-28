package com.webatron.rakesh.assigkmd;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rakesh on 25/1/18.
 */

public class WeatherApp {
    public static String API_KEY="82f09ca3989dbe7255fd3019e9cfec84";

    public static String API_URL="http://api.openweathermap.org/data/2.5/weather";

    @NonNull
    public static String apiRequest(String latitude, String longitude){
        StringBuilder stringBuilder = new StringBuilder(API_URL);
        stringBuilder.append(String.format("?lat=%s&lon=%s&APPID=%s&units=metric",latitude,longitude,API_KEY));
        return stringBuilder.toString();
    }

    public static String DateTime(double time){
        DateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)time*1000);
        return format.format(date);
    }


}
