package com.larsen.magnus.guessthenumber;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button btnEasy, btnMedium, btnHard;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ListView listView;
    String[] options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView = (ListView) findViewById(R.id.drawerList);
        options = getResources().getStringArray(R.array.options);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options));
        listView.setOnItemClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnHard = (Button) findViewById(R.id.btnHard);

        //OnClickListeners to the Levels
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startEasy = new Intent(MainActivity.this, GameActivity.class);
                startEasy.putExtra("theLevelSelected", theEasy);
                startActivity(startEasy);
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startMedium = new Intent(MainActivity.this, GameActivity.class);
                startMedium.putExtra("theLevelSelected", theMedium);
                startActivity(startMedium);
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startHard = new Intent(MainActivity.this, GameActivity.class);
                startHard.putExtra("theLevelSelected", theHard);
                startActivity(startHard);
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //Parameters: Name, Time, Attempts, SecretNum
        Level theEasy = new Level("Easy", 90000, 12, 1000);
        Level theMedium = new Level("Medium", 120000, 14, 10000);
        Level theHard = new Level("Hard", 180000, 16, 50000);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position) {
            case 0:
                Intent hallOfFame = new Intent(MainActivity.this, HallActivity.class);
                startActivity(hallOfFame);
                break;
            case 1:
                Intent aboutUs = new Intent(MainActivity.this, Aboutus.class);
                startActivity(aboutUs);
                break;
        }
    }
}