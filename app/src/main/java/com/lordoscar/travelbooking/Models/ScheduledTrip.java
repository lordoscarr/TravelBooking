package com.lordoscar.travelbooking.Models;

import java.sql.Date;

public class ScheduledTrip {
    private int id;
    private Date departure;
    private Date arrival;
    private int seats;
    private int freeSeats;
    private int price;
    private Trip trip;

    public ScheduledTrip(int id, Date departure, Date arrival, int seats, int freeSeats, int price, Trip trip) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.seats = seats;
        this.freeSeats = freeSeats;
        this.price = price;
        this.trip = trip;
    }

    public int getId() {
        return id;
    }

    public Date getDeparture() {
        return departure;
    }

    public Date getArrival() {
        return arrival;
    }

    public int getSeats() {
        return seats;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public int getPrice() {
        return price;
    }

    public Trip getTrip() {
        return trip;
    }

    public String toString(){
        return trip + " which arrives on " + arrival.toString();
    }
}
