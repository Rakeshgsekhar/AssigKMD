package com.webatron.rakesh.assigkmd;

import java.util.List;

/**
 * Created by rakesh on 25/1/18.
 */

public class OpenWeatherMap {

    private Coord coord;
    private List<Weather> weather;
    String base;
    private Main main;
    private Sys sys;
    private Rain rain;
    Clouds clouds;
    int date;
    int id,cod;
    String name;

    public OpenWeatherMap() {


    }

    public OpenWeatherMap(Coord coord, List<Weather> weather, String base, Main main, Sys sys, Rain rain, Clouds clouds, int date, int id, int cod, String name) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.sys = sys;
        this.rain = rain;
        this.clouds = clouds;
        this.date = date;
        this.id = id;
        this.cod = cod;
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
