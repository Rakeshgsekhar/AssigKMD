package com.webatron.rakesh.assigkmd;

/**
 * Created by rakesh on 25/1/18.
 */

public class Main {

    private double temp,presssure,temp_min,temp_max;
    private int humidity;

    public Main(double temp, double presssure, double temp_min, double temp_max, int humidity) {
        this.temp = temp;
        this.presssure = presssure;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPresssure() {
        return presssure;
    }

    public void setPresssure(double presssure) {
        this.presssure = presssure;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
