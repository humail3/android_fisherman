package com.novahumail.fisherman;

public class MyPlaceModel {
    private String name;
    private String country;
    private String region;
    private String lake;


    public MyPlaceModel(String name, String country, String region, String lake) {
        this.name = name;
        this.country = country;
        this.region = region;
        this.lake = lake;
    }

    public MyPlaceModel(String name) {
        this.name = name;
    }

    // Default constructor required for Firebase
    public MyPlaceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLake() {
        return lake;
    }

    public void setLake(String lake) {
        this.lake = lake;
    }

}
