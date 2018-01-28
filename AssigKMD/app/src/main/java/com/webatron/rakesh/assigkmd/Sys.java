package com.webatron.rakesh.assigkmd;

/**
 * Created by rakesh on 25/1/18.
 */

public class Sys {
    private int type,id;
    private double message,sunrise,sunset;
    private String country;

    public Sys(int type, int id, double message, double sunrise, double sunset, String country) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.country = country;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
