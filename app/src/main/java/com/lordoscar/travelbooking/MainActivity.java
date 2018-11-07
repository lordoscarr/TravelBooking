package com.lordoscar.travelbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Database database;
    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar filterToolbar = findViewById(R.id.filterToolbar);
        setSupportActionBar(filterToolbar);

        testButton = findViewById(R.id.testButton);

        database = new Database();
        database.start();

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = database.registerTraveler("Joakim Andersson", "Lopargatan 70C", "test3@hotmail.com", "0418-25883");
                Log.d("REGISTERED USER", "Id is " + x);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }
}
