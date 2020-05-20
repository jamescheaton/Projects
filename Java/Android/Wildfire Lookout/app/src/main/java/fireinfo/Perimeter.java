package com.example.hsmul.fireinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class Perimeter {
    private double acres;
    private String agency;
    private String comments;
    private String complexName;
    private Timestamp dateTime;
    private double epochTime;
    private String fireCode;
    private String fireName;
    private GeoPoint location;
    private ArrayList<GeoPoint> points;
    private String uniqueFireID;
    private String unitID;

    Perimeter(Map<String, Object> data){
        acres = (double) data.get("Acres");
        agency = (String) data.get("Agency");
        comments = (String) data.get("Comments");
        complexName = (String) data.get("ComplexName");
        dateTime = (Timestamp) data.get("DateTime");
        epochTime = (double) data.get("EpochTime");
        fireCode = (String) data.get("FireCode");
        fireName = (String) data.get("FireName");
        location = (GeoPoint) data.get("Location");
        points = new ArrayList<GeoPoint>((Collection) data.get("Points"));
        uniqueFireID = (String) data.get("UniqueFireIdentification");
        unitID = (String) data.get("UnitIdentification");
    }

    public double getAcres() {
        return acres;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public ArrayList<GeoPoint> getPoints() {
        return points;
    }

    public double getEpochTime() {
        return epochTime;
    }

    public String getAgency() {
        return agency;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public String getComments() {
        return comments;
    }

    public String getComplexName() {
        return complexName;
    }

    public String getFireCode() {
        return fireCode;
    }

    public String getFireName() {
        return fireName;
    }

    public String getUniqueFireID() {
        return uniqueFireID;
    }

    public String getUnitID() {
        return unitID;
    }
}
