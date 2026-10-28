package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class alarms_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_screen);

        LinearLayout badgeBtn = findViewById(R.id.navBadges);
        LinearLayout todayBtn = findViewById(R.id.navToday);

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
            Intent intent = new Intent(alarms_screen.this, activity_alarm_ringing.class);

            startActivity(intent);
        });
        alarms.setAdapter(adapter);



        //ON CLICK LISTENERS
        todayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(alarms_screen.this, TodayActivity.class);

            startActivity(intent);
        });

        badgeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(alarms_screen.this, activity_badges.class);

            startActivity(intent);
        });



    }
}
