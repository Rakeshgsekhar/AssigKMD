package com.webatron.rakesh.assigkmd;

/**
 * Created by rakesh on 25/1/18.
 */

public class StorageDetails {

    private String tittle;
    private String totalspace;
    private String usedspace;
    private String freespace;

    public StorageDetails(String tittle, String totalspace, String usedspace, String freespace) {
        this.tittle = tittle;
        this.totalspace = totalspace;
        this.usedspace = usedspace;
        this.freespace = freespace;
    }

    public StorageDetails() {

    }

    public String getTittle() {

        return tittle;
    }

    public void setTittle(String tittle) {

        this.tittle = tittle;
    }

    public String getTotalspace() {

        return totalspace;
    }

    public void setTotalspace(String totalspace) {

        this.totalspace = totalspace;
    }

    public String getUsedspace() {

        return usedspace;
    }

    public void setUsedspace(String usedspace) {

        this.usedspace = usedspace;
    }

    public String getFreespace() {

        return freespace;
    }

    public void setFreespace(String freespace) {

        this.freespace = freespace;
    }
}
