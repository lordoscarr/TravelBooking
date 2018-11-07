package com.lordoscar.travelbooking.Models;

public class Booking {
    private int id;
    private ScheduledTrip trip;
    private int seats;

    public Booking(int id, ScheduledTrip trip, int seats) {
        this.id = id;
        this.trip = trip;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public ScheduledTrip getTrip() {
        return trip;
    }

    public int getSeats() {
        return seats;
    }

    public String toString(){
        return "Booking: " + seats + " seats on " + trip;
    }
}
