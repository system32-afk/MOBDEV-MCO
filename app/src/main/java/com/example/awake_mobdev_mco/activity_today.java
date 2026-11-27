
package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activity_today extends AppCompatActivity {

    private TextView tvChallengesDone;
    private ProgressBar todayWalkProgress;

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

        //Connect Today screen views
        tvChallengesDone = findViewById(R.id.tvChallengesDone);
        todayWalkProgress = findViewById(R.id.todayWalkProgress);

        // MIND - Daily Math
        findViewById(R.id.cardMind).setOnClickListener(v -> {
            Intent intent = new Intent(
                    activity_today.this,
                    DailyMath.class
            );
            startActivity(intent);
        });

        // ENERGY - Daily Shake
        findViewById(R.id.cardEnergy).setOnClickListener(v -> {
            Intent intent = new Intent(
                    activity_today.this,
                    activity_shake_challenge.class
            );
            startActivity(intent);
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
        // Update saved challenge progress
        updateDailyChallenges();
    }
    private void updateDailyChallenges() {

        SharedPreferences preferences = getSharedPreferences(
                "AWAKE_DAILY_PROGRESS",
                MODE_PRIVATE
        );

        String today = new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.US
        ).format(new Date());

        String savedDate = preferences.getString(
                "walk_date", ""
        );

        int steps = 0;
        boolean walkCompleted = false;

        // Read today's saved step count
        if (today.equals(savedDate)) {
            steps = preferences.getInt("daily_steps", 0);
            walkCompleted = steps >= 100;
        }

        // Body Daily Walk progress bar
        todayWalkProgress.setMax(100);
        todayWalkProgress.setProgress(
                Math.min(steps, 100)
        );
        // math and shake completion not done yet
        int completedChallenges = walkCompleted ? 1 : 0;

        tvChallengesDone.setText(
                completedChallenges + " of 3 done"
        );
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh progress when returning to Today
        if (tvChallengesDone != null &&
                todayWalkProgress != null) {
            updateDailyChallenges();
        }
    }
}
