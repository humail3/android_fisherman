package com.novahumail.fisherman;

public class MyShotModel {
    private String name;
    private String pressure;
    private String temperature;
    private String montage;
    private String bait;
    private String wind;
    private String place;

    // Default constructor required for Firebase
    public MyShotModel() {
    }

    public MyShotModel(String name, String pressure, String temperature, String montage, String bait, String wind, String place) {
        this.name = name;
        this.pressure = pressure;
        this.temperature = temperature;
        this.montage = montage;
        this.bait = bait;
        this.wind = wind;
        this.place = place;
    }

    public MyShotModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getMontage() {
        return montage;
    }

    public void setMontage(String montage) {
        this.montage = montage;
    }

    public String getBait() {
        return bait;
    }

    public void setBait(String bait) {
        this.bait = bait;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
