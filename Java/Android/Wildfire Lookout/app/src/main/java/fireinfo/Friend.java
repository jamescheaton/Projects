package com.example.hsmul.fireinfo;

public class Friend {

    private String name;
    private String status;
    private String alertsConfigured;

    public Friend(String name, String status, String alertsConfigured){
        this.name = name;
        this.status = status;
        this.alertsConfigured = alertsConfigured;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlertsConfigured() {
        return alertsConfigured;
    }

    public void setAlertsConfigured(String alertsConfigured) {
        this.alertsConfigured = alertsConfigured;
    }
}
