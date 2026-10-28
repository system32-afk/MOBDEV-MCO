package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class activity_alarms_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_screen);

        LinearLayout badgeBtn = findViewById(R.id.navBadges);
        LinearLayout todayBtn = findViewById(R.id.navToday);
        FloatingActionButton addAlarm = findViewById(R.id.addAlarmBtn);

        //DUMMY DATA
        ArrayList<alarm> alarmItem = new ArrayList<>();
        ArrayList<String> days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday"));
        alarmItem.add(new alarm("7:30", days, "AM"));
        alarmItem.add(new alarm("7:35", days, "AM"));



        RecyclerView alarms = findViewById(R.id.alarmList);
        myAdapter adapter = new myAdapter(getApplicationContext(),alarmItem);

        alarms.setLayoutManager(new LinearLayoutManager(this));
        alarms.setAdapter(adapter);

        adapter.setOnItemClickListener((item, position) -> {
            Intent intent = new Intent(activity_alarms_screen.this, activity_alarm_ringing.class);

            startActivity(intent);
        });
        alarms.setAdapter(adapter);


        addAlarm.setOnClickListener(v -> {
            Intent intent = new Intent(activity_alarms_screen.this, SetAlarm.class);
            startActivity(intent);
        });

        //ON CLICK LISTENERS FOR NAVIGATION BAR
        todayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(activity_alarms_screen.this, activity_today.class);
            startActivity(intent);
        });

        badgeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(activity_alarms_screen.this, activity_badges.class);
            startActivity(intent);
        });



    }
}
