package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class activity_alarm_ringing extends AppCompatActivity {

    private Button btnStopAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringing);

        btnStopAlarm = findViewById(R.id.btnStopAlarm);
        btnStopAlarm.setOnClickListener(v -> {
            startActivity(new Intent(activity_alarm_ringing.this, activity_search_object.class));
        });
    }
}