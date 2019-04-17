package com.example.hsmul.fireinfo;

import java.util.Map;

public class FireObject {
    private double maxLat;
    private double minLat;
    private double maxLon;
    private double minLon;
    private String Latest;
    private String DocID;

    FireObject(Map<String, Object> data, String ID){
        maxLat = (Double) data.get("MaxLat");
        minLat = (Double) data.get("MinLat");
        maxLon = (Double) data.get("MaxLon");
        minLon = (Double) data.get("MinLon");
        Latest = (String) data.get("Latest");
        DocID = ID;
    }

    FireObject(double maxLat, double minLat, double maxLon, double minLon, String Latest, String ID){
        this.maxLat = maxLat;
        this. minLat = minLat;
        this.maxLon = maxLon;
        this.minLon = minLon;
        this.Latest = Latest;
        DocID = ID;
    }

    double getMaxLat(){
        return maxLat;
    }

    double getMinLat(){
        return minLat;
    }

    double getMaxLon(){
        return minLon;
    }

    double getMinLon(){
        return minLon;
    }

    String getLatest(){
        return Latest;
    }

    String getDocID(){
        return DocID;
    }

    boolean isEqual(FireObject other){
        if (maxLon == other.getMaxLon() && minLon == other.getMinLon()
                && maxLat == other.getMaxLat() && minLat == other.getMinLat()
                && Latest.equals(other.getLatest())){
            return true;
        }
        return false;
    }
}
