package com.example.hsmul.fireinfo;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserAlertSettings {

    private boolean pushNotificationsEnabled;
    private boolean textsEnabled;
    private boolean callsEnabled;
    private boolean emailsEnabled;
    private ArrayList<String> textNumbers;
    private ArrayList<String> callNumbers;
    private ArrayList<String> emails;
    private GeoPoint coordinates;
    private String userId;
    private String name;
    private FirebaseFirestore db;
    private String id;

    public UserAlertSettings(FirebaseFirestore db){
        this.pushNotificationsEnabled = false;
        this.textsEnabled = false;
        this.callsEnabled = false;
        this.emailsEnabled = false;
        this.textNumbers = new ArrayList<String>();
        this.callNumbers = new ArrayList<String>();
        this.emails = new ArrayList<String>();
        this.coordinates = new GeoPoint(0,0);
        this.userId = "";
        this.name = "";
        this.db = db;
        this.id = "";

    }

    public UserAlertSettings(boolean pushNotificationsEnabled, boolean textsEnabled, boolean emailsEnabled,
                             boolean callsEnabled, ArrayList<String> textNumbers, ArrayList<String> emails, ArrayList<String> callNumbers,
                             GeoPoint coordinates, String userId, String name, FirebaseFirestore db, String id){
        this.pushNotificationsEnabled = pushNotificationsEnabled;
        this.textsEnabled = textsEnabled;
        this.callsEnabled = callsEnabled;
        this.emailsEnabled = emailsEnabled;
        this.textNumbers = textNumbers;
        this.callNumbers = callNumbers;
        this.emails = emails;
        this.coordinates = coordinates;
        this.userId = userId;
        this.name = name;
        this.db = db;
        this.id = id;

    }


 /*   public UserAlertSettings(AlertSettings settings, FirebaseFirestore db, String id){
>>>>>>> Stashed changes:app/src/main/java/com/example/hsmul/fireinfo/AlertSettings.java
        this.pushNotificationsEnabled = settings.isPushNotificationsEnabled();
        this.textsEnabled = settings.isTextsEnabled();
        this.callsEnabled = settings.isCallsEnabled();
        this.emailsEnabled = settings.isEmailsEnabled();
        this.textNumbers = settings.getTextNumbers();
        this.callNumbers = settings.getCallNumbers();
        this.emails = settings.getEmails();
        this.coordinates = settings.getCoordinates();
        this.userId = settings.getUserId();
        this.name = settings.getName();
        this.db = db;
        this.id = id;
    }*/

    public UserAlertSettings(Map<String, Object> data, String ID){
        Map<String, Object> alertSettingsData = (Map<String, Object>) data.get("d");
        pushNotificationsEnabled = (boolean) alertSettingsData.get("pushNotificationsEnabled");
        textsEnabled = (boolean) alertSettingsData.get("textsEnabled");
        callsEnabled = (boolean) alertSettingsData.get("callsEnabled");
        emailsEnabled = (boolean) alertSettingsData.get("emailsEnabled");
        textNumbers = new ArrayList<String>((Collection) alertSettingsData.get("textNumbers"));
        callNumbers = new ArrayList<String>((Collection) alertSettingsData.get("callNumbers"));
        emails = new ArrayList<String>((Collection) alertSettingsData.get("emails"));
        coordinates = (GeoPoint) alertSettingsData.get("coordinates");
        userId = (String) alertSettingsData.get("userID");
        name = (String) alertSettingsData.get("name");
        this.id = ID;
    }

//    public void saveToFirebase(AlertTableViewController sender){

//    }

    public boolean isPushNotificationsEnabled() {
        return pushNotificationsEnabled;
    }
    public boolean isTextsEnabled() {
        return textsEnabled;
    }

    public boolean isCallsEnabled() {
        return callsEnabled;
    }

    public boolean isEmailsEnabled() {
        return emailsEnabled;
    }

    public ArrayList<String> getTextNumbers() {
        return textNumbers;
    }

    public ArrayList<String> getCallNumbers() {
        return callNumbers;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public GeoPoint getCoordinates() {
        return coordinates;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public String getId() {
        return id;
    }

    public void addToDB() {
        HashMap<String, Object> datamap = new HashMap<String, Object>();
        datamap.put("textsEnabled", textsEnabled);
        datamap.put("callsEnabled", callsEnabled);
        datamap.put("emailsEnabled", emailsEnabled);
        datamap.put("textNumbers", textNumbers);
        datamap.put("callNumbers", callNumbers);
        datamap.put("emails", emails);
        datamap.put("userId", userId);
        datamap.put("pushNotificationsEnabled", pushNotificationsEnabled);
        datamap.put("name", name);
        datamap.put("coordinates", coordinates);

        HashMap<String, Object> watchPoint = new HashMap<>();
        watchPoint.put("d", datamap);
        watchPoint.put("g", "");
        watchPoint.put("l", coordinates);
        watchPoint.put("userId", userId);

        db.collection("watchLocations").document(userId + "_" + name).set(watchPoint);
    }
}
