package com.example.hsmul.fireinfo;


import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.Map;

public class FirePoint {
    private Timestamp dateTime;
    //private double brightTI4;
    //private double brightTI5;
    private String confidence;
//    private boolean daynight;
//    private double frp;
//    private double scan;
//    private double track;
    private String type;
    private GeoPoint location;
    private String docID;

    FirePoint(Map<String, Object> data, String ID){
        dateTime = (Timestamp) data.get("D");
        //brightTI4 = (double) data.get("4");
//        brightTI5 = (double) data.get("5");
        confidence = (String) data.get("C");
//        daynight = (boolean) data.get("N");
//        frp = (double) data.get("R");
//        scan = (double) data.get("S");
//        track = (double) data.get("TR");
        type = (String) data.get("String");
        location = (GeoPoint) data.get("L");
        docID = ID;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    //public double getBrightTI4() { return brightTI4; }

   // public double getBrightTI5() { return brightTI5; }
/*
    public double getFrp() {
        return frp;
    }

    public double getScan() {
        return scan;
    }

    public double getTrack() {
        return track;
    }
*/
    public String getConfidence() {
        return confidence;
    }

    public String getDocID() {
        return docID;
    }

    public String getType() {
        return type;
    }
}
