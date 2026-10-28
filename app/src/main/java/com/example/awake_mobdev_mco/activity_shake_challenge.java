package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class activity_shake_challenge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_challenge);

        Button nextBtn = findViewById(R.id.testButton);

        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(activity_shake_challenge.this, activity_daily_walk.class);

            startActivity(intent);
        });

    }
}
