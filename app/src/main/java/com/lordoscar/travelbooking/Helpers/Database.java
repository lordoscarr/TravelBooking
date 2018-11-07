package com.lordoscar.travelbooking.Helpers;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.postgresql.replication.PGReplicationConnectionImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

    private class GetCities extends AsyncTask<String, Void, ArrayList<String>> {
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
}
