package com.lordoscar.travelbooking.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lordoscar.travelbooking.Helpers.Database;
import com.lordoscar.travelbooking.Helpers.TripAdapter;
import com.lordoscar.travelbooking.Models.Booking;
import com.lordoscar.travelbooking.Models.ScheduledTrip;
import com.lordoscar.travelbooking.Models.Trip;
import com.lordoscar.travelbooking.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Database database;
    private Button testButton;
    private int id = -1;
    private RegisterFragment registerFragment;
    private DetailFragment detailFragment;
    private ScheduleFragment scheduleFragment;
    private ListView tripListView;

    private Trip selectedTrip = null;
    private ScheduledTrip selectedScheduledTrip = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar filterToolbar = findViewById(R.id.filterToolbar);
        setSupportActionBar(filterToolbar);

        database = new Database();
        database.start();

        testButton = findViewById(R.id.testButton);
        tripListView = findViewById(R.id.tripListView);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getSharedPreferences("com.lordoscar.p1", Context.MODE_PRIVATE).edit().clear().commit();
                ArrayList<Trip> trips = database.getTrips();

                TripAdapter tripAdapter = new TripAdapter(MainActivity.this, R.layout.trip_list_item, trips);
                tripListView.setAdapter(tripAdapter);
                tripListView.setOnItemClickListener(tripAdapter);

                ArrayList<ScheduledTrip> scheduledTrips = database.getScheduledTrips(trips.get(0));

                for(ScheduledTrip trip : scheduledTrips){
                    Log.d("Scheduled trip: ", trip.toString());
                }
            }
        });



        Fragment fragment = null;

        if(fragment == null){
            SharedPreferences preferences = this.getSharedPreferences("com.lordoscar.p1", Context.MODE_PRIVATE);

            boolean firststart = preferences.getBoolean("firstTime", true);

            if(firststart){
                Log.d("First start", "First start!");
                FragmentManager fm = getSupportFragmentManager();
                registerFragment = new RegisterFragment();
                registerFragment.show(fm, "RegisterFragment");
            }else{
                //Get user id from shared preferences
                id = preferences.getInt("travelerId", -1);
                Log.d("Not first start", "Not first start! Traveler id: " + id);
            }
        }
    }

    public void openSchedule(Trip trip){
        selectedTrip = trip;
        FragmentManager fm = getSupportFragmentManager();
        scheduleFragment = new ScheduleFragment();
        scheduleFragment.show(fm, "ScheduleFragment");
    }

    public ArrayList<ScheduledTrip> getSchedule(){
        return database.getScheduledTrips(selectedTrip);
    }

    public void openDetail(ScheduledTrip scheduledTrip){
        selectedScheduledTrip = scheduledTrip;
        FragmentManager fm = getSupportFragmentManager();
        detailFragment = new DetailFragment();
        detailFragment.show(fm, "DetailFragment");
    }

    public ScheduledTrip getSelectedScheduledTrip(){
        return selectedScheduledTrip;
    }

    public int registerUser(String name, String address, String email, String phone){
        return database.registerTraveler(name,address,email,phone);
    }

    public boolean bookTrip(int seats){
        Booking booking = new Booking(id, selectedScheduledTrip, seats);
        return database.bookTrip(booking);
    }

    public void closeFragments(){
        try {
            scheduleFragment.dismiss();
            detailFragment.dismiss();
        }catch(Exception ex){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }
}
