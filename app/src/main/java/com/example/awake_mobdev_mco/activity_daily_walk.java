
package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_daily_walk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daily_walk);

        TextView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v ->{
            Intent intent = new Intent (activity_daily_walk.this, activity_today.class);
            startActivity(intent);
        });
    }
}
