package com.lordoscar.travelbooking.Models;

public class City {
    private int id;
    private String name;
    private String country;
    private String stationAddress;

    public City(int id, String name, String country, String stationAddress) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.stationAddress = stationAddress;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public String toString(){
        return name + ", " + country;
    }
}
