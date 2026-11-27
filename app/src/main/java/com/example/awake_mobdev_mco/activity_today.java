
package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_today extends AppCompatActivity {

    private TextView tvChallengesDone;
    private TextView tvMathStatus;
    private TextView tvShakeStatus;
    private TextView tvWalkStatus;
    private TextView tvStreak;

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
        tvMathStatus = findViewById(R.id.tvMathStatus);
        tvShakeStatus = findViewById(R.id.tvShakeStatus);
        tvWalkStatus = findViewById(R.id.tvWalkStatus);
        tvStreak = findViewById(R.id.tvStreak);

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
        updateDailyChallenges();
    }
    private void updateDailyChallenges() {

        String today = new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.US
        ).format(new Date());

        MyDbHelper dbHelper = MyDbHelper.getInstance(this);

        boolean mathCompleted = dbHelper.isChallengeCompleted("MATH", today);
        boolean shakeCompleted = dbHelper.isChallengeCompleted("SHAKE", today);
        boolean walkCompleted = dbHelper.isChallengeCompleted("WALK", today);

        tvMathStatus.setText(mathCompleted ? "Completed" : "Not yet completed");
        tvShakeStatus.setText(shakeCompleted ? "Completed" : "Not yet completed");
        tvWalkStatus.setText(walkCompleted ? "Completed" : "Not yet completed");

        tvMathStatus.setTextColor(android.graphics.Color.parseColor(
                mathCompleted ? "#388A61" : "#55587A"
        ));

        tvShakeStatus.setTextColor(android.graphics.Color.parseColor(
                shakeCompleted ? "#388A61" : "#55587A"
        ));

        tvWalkStatus.setTextColor(android.graphics.Color.parseColor(
                walkCompleted ? "#388A61" : "#55587A"
        ));

        int completedChallenges = 0;

        if (mathCompleted) completedChallenges++;
        if (shakeCompleted) completedChallenges++;
        if (walkCompleted) completedChallenges++;

        tvChallengesDone.setText(completedChallenges + " of 3 done");
        updateStreak();
        updateWeeklyProgress();
    }
    private void updateStreak() {
        MyDbHelper dbHelper = MyDbHelper.getInstance(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Calendar calendar = Calendar.getInstance();
        int streak = 0;

        String today = dateFormat.format(calendar.getTime());

        if (dbHelper.getCompletedChallengeCount(today) != 3) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        while (dbHelper.getCompletedChallengeCount(
                dateFormat.format(calendar.getTime())) == 3) {
            streak++;
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        tvStreak.setText("🔥 " + streak + "-day streak");
        TextView tvBadgeProgress = findViewById(R.id.tvBadgeProgress);

        int remainingDays = Math.max(0, 7 - streak);

        if (remainingDays == 0) {
            tvBadgeProgress.setText("7-day streak reached!");
        } else if (remainingDays == 1) {
            tvBadgeProgress.setText("1 more day to earn it!");
        } else {
            tvBadgeProgress.setText(remainingDays + " more days to earn it!");
        }
    }
    private void updateWeeklyProgress() {
        MyDbHelper dbHelper = MyDbHelper.getInstance(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        int[] dayIds = {
                R.id.tvMonday,
                R.id.tvTuesday,
                R.id.tvWednesday,
                R.id.tvThursday,
                R.id.tvFriday,
                R.id.tvSaturday,
                R.id.tvSunday
        };

        String[] dayLabels = {"M", "T", "W", "T", "F", "S", "S"};

        Calendar calendar = Calendar.getInstance();

        int offset = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7;
        calendar.add(Calendar.DAY_OF_YEAR, -offset);

        for (int i = 0; i < 7; i++) {
            TextView dayView = findViewById(dayIds[i]);
            String date = dateFormat.format(calendar.getTime());

            boolean completed = dbHelper.getCompletedChallengeCount(date) == 3;

            dayView.setText((completed ? "●" : "○") + "\n" + dayLabels[i]);
            dayView.setTextColor(android.graphics.Color.parseColor(
                    completed ? "#FF7A2F" : "#C4C7EA"
            ));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Refresh progress when returning to Today
        if (tvChallengesDone != null &&
                tvMathStatus != null &&
                tvShakeStatus != null &&
                tvWalkStatus != null &&
                tvStreak != null) {
            updateDailyChallenges();
        }
    }
}