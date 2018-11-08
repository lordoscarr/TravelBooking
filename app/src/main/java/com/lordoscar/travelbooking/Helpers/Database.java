package com.lordoscar.travelbooking.Helpers;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.lordoscar.travelbooking.Models.Booking;
import com.lordoscar.travelbooking.Models.City;
import com.lordoscar.travelbooking.Models.ScheduledTrip;
import com.lordoscar.travelbooking.Models.Trip;

import org.postgresql.replication.PGReplicationConnectionImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class Database extends Thread{

    private Connection connection = null;
    private static final String SQL_NAME = "ah7379";
    private static final String SQL_USER = "ah7379";
    private static final String SQL_PASSWORD = "iif75bul";

    @Override
    public void run() {
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/" + SQL_NAME +"?user=" + SQL_USER + "&password=" + SQL_PASSWORD);
            Log.d("Connection", connection.getCatalog());

        }catch (Exception ex){
            ex.printStackTrace();
            connection = null;
        }
    }

    public ArrayList<String> getDrivers(){
        try {
            return new GetDrivers().execute().get();
        }catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public int registerTraveler(String name, String address, String email, String phone){
        try {
            int id = new RegisterUser().execute(name, address, email, phone).get();
            Log.d("REGISTER ID TO RETURN", id + "");
            return id;
        }catch (Exception ex){
            return -1;
        }
    }

    private class RegisterUser extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            int id = -1;
            String name = params[0];
            String address = params[1];
            String email = params[2];
            String phone = params[3];

            try{
                PreparedStatement stmt = connection.
                        prepareStatement("insert into traveler (name, address, email, phone) values ('" + name + "', '"+ address + "', '" + email + "', '" + phone + "');");

                stmt.executeUpdate();

                stmt = connection.
                        prepareStatement("select * from traveler where (email = '" + email + "');");
                ResultSet rs = stmt.executeQuery();

                if(rs.next()){
                    Log.d("RETRIEVED NEW USER", "ID: " + rs.getString("id") + ", Name: " + rs.getString("name"));
                    id = Integer.parseInt(rs.getString("id"));
                }

            }catch (Exception ex){
                if (ex.getMessage().contains("duplicate") && ex.getMessage().contains("unique_email")){
                    id = -1;
                }
                ex.printStackTrace();
            }
            return id;
        }
    }

    private class GetDrivers extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> drivers = new ArrayList<>();
            try{
                PreparedStatement stmt = connection.
                        prepareStatement("select * from driver");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    drivers.add("Driver: " + rs.getString("name") + ", " + rs.getString("phone"));
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return drivers;
        }
    }

    public HashMap<Integer, City> getCities(){
        try {
            return new GetCities().execute().get();
        }catch (Exception ex){
            ex.printStackTrace();
            return new HashMap<Integer, City>();
        }
    }

    private class GetCities extends AsyncTask<String, Void, HashMap<Integer, City>> {
        @Override
        protected HashMap<Integer, City> doInBackground(String... params) {
            HashMap<Integer, City> cities = new HashMap<>();
            try{
                PreparedStatement stmt = connection.
                        prepareStatement("select * from city");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    City city = new City(Integer.parseInt(rs.getString("id")), rs.getString("name"), rs.getString("country"), rs.getString("stationaddress"));
                    cities.put(city.getId(), city);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return cities;
        }
    }

    public ArrayList<Trip> getTrips(){
        try {
            return new GetTrips().execute(getCities()).get();
        }catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<Trip>();
        }
    }

    private class GetTrips extends AsyncTask<HashMap<Integer, City>, Void, ArrayList<Trip>> {
        @Override
        protected ArrayList<Trip> doInBackground(HashMap<Integer, City>... params) {
            HashMap<Integer, City> cities = params[0];
            ArrayList<Trip> trips = new ArrayList<>();
            try{
                PreparedStatement stmt = connection.
                        prepareStatement("select * from trip");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    int id = Integer.parseInt(rs.getString("id"));
                    int origin = Integer.parseInt(rs.getString("origin"));
                    int destination = Integer.parseInt(rs.getString("destination"));
                    Trip trip = new Trip(id, cities.get(origin), cities.get(destination));
                    Log.d("TRIP", trip.toString());
                    trips.add(trip);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return trips;
        }
    }

    public ArrayList<ScheduledTrip> getScheduledTrips(Trip trip){
        try {
            return new GetScheduledTrips().execute(trip).get();
        }catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    private class GetScheduledTrips extends AsyncTask<Trip, Void, ArrayList<ScheduledTrip>> {
        @Override
        protected ArrayList<ScheduledTrip> doInBackground(Trip... params) {
            Trip trip = params[0];
            ArrayList<ScheduledTrip> scheduledTrips = new ArrayList<>();
            try{
                PreparedStatement stmt = connection.
                        prepareStatement("select * from scheduledTrip where (trip = " + "'" + trip.getId() + "');");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    int id = Integer.parseInt(rs.getString("id"));
                    Timestamp departure = rs.getTimestamp("departure");
                    Timestamp arrival = rs.getTimestamp("arrival");
                    int seats = Integer.parseInt(rs.getString("seats"));
                    int freeSeats = Integer.parseInt(rs.getString("freeseats"));
                    int price = Integer.parseInt(rs.getString("price"));

                    ScheduledTrip scheduledTrip = new ScheduledTrip(id, departure, arrival, seats, freeSeats, price, trip);

                    Log.d("SCHEDULED TRIP", trip.toString());
                    scheduledTrips.add(scheduledTrip);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return scheduledTrips;
        }
    }

    public boolean bookTrip(Booking booking){
        try {
            return new BookTrip().execute(booking).get();
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    private class BookTrip extends AsyncTask<Booking, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Booking... params) {
            try{
                Booking booking = params[0];
                //Update amount of free seats
                int freeSeats = booking.getTrip().getFreeSeats() - booking.getSeats();

                PreparedStatement stmt = connection.prepareStatement("update scheduledtrip set freeseats = " + freeSeats +
                        " where (id = " + booking.getTrip().getId() + ");");
                stmt.executeUpdate();

                //Add booking to table
                stmt = connection.prepareStatement("insert into booking (traveler, scheduledtrip, seats) " +
                        "values (" + booking.getId() + ", " + booking.getTrip().getId() + ", " + booking.getSeats() + ");");
                stmt.executeUpdate();
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }
    }
}
