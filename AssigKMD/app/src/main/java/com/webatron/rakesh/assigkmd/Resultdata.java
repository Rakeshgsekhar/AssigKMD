package com.webatron.rakesh.assigkmd;

/**
 * Created by rakesh on 28/1/18.
 */

public class Resultdata {
    String heading,data1,data2,data3;

    public Resultdata(String heading, String data1, String data2, String data3) {
        this.heading = heading;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }

    public Resultdata() {
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }
}
