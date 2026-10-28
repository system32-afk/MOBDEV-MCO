
package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_today extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_today);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // MIND - Daily Math
        findViewById(R.id.cardMind).setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "Mind challenge clicked",
                    Toast.LENGTH_SHORT
            ).show();
        });
        // ENERGY - Daily Shake
        findViewById(R.id.cardEnergy).setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "Energy challenge clicked",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // BODY - Daily Walk
        findViewById(R.id.cardWalk).setOnClickListener(v -> {
            Intent intent = new Intent(
                    activity_today.this,
                    activity_daily_walk.class
            );
            startActivity(intent);
        });

        findViewById(R.id.navToday).setOnClickListener(v -> {
            // No action needed
        });
        // ALARMS
        findViewById(R.id.navAlarms).setOnClickListener(v -> {
            Intent intent = new Intent(
                    activity_today.this,
                    activity_alarms_screen.class
            );
            startActivity(intent);
        });
        // BADGES
        findViewById(R.id.navBadges).setOnClickListener(v -> {
            Intent intent = new Intent(
                    activity_today.this,
                    activity_badges.class
            );
            startActivity(intent);
        });
    }
}
