package com.example.awake_mobdev_mco;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.alarms_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        //DUMMY DATA
        ArrayList<alarm> alarmItem = new ArrayList<>();
        ArrayList<String> days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday"));

        alarmItem.add(new alarm("7:30", days, "AM"));
        alarmItem.add(new alarm("7:35", days, "AM"));

        RecyclerView alarms = findViewById(R.id.alarmList);


        alarms.setLayoutManager(new LinearLayoutManager(this));
        alarms.setAdapter(new myAdapter(getApplicationContext(),alarmItem));
    }
}