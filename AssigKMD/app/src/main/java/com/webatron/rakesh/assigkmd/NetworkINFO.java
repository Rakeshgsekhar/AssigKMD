package com.webatron.rakesh.assigkmd;

/**
 * Created by rakesh on 25/1/18.
 */

public class NetworkINFO {
    String connecttiontype,carriername;

    public NetworkINFO(String connecttiontype, String carriername) {
        this.connecttiontype = connecttiontype;
        this.carriername = carriername;
    }

    public NetworkINFO() {
    }

    public String getConnecttiontype() {
        return connecttiontype;
    }

    public void setConnecttiontype(String connecttiontype) {
        this.connecttiontype = connecttiontype;
    }

    public String getCarriername() {
        return carriername;
    }

    public void setCarriername(String carriername) {
        this.carriername = carriername;
    }
}
