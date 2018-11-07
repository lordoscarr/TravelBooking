package com.lordoscar.travelbooking.Models;

import java.util.Date;

public class Trip {
    private int id;
    private City origin;
    private City destination;

    public Trip(int id, City origin, City destination) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public City getOrigin() {
        return origin;
    }

    public City getDestination() {
        return destination;
    }

    public String toString(){
        return "Trip: " + origin + " ->" + destination;
    }
}
