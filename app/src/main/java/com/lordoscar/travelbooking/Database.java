package com.lordoscar.travelbooking;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

            PreparedStatement stmt = connection.
                    prepareStatement("select * from dog");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Log.d("Connection", rs.getString("name") + ", " + rs.getString("breed") + ", " + rs.getString("birthyear"));
            }

        }catch (Exception ex){
            connection = null;
        }
    }
}
