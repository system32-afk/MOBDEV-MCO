
package com.example.awake_mobdev_mco;

import android.Manifest;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activity_daily_walk extends AppCompatActivity
        implements SensorEventListener {

    private static final int STEP_GOAL = 100;
    private static final int PERMISSION_REQUEST_CODE = 102;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private SharedPreferences preferences;
    private TextView tvSteps;
    private TextView tvRemaining;
    private TextView tvWalkStatus;
    private TextView milestone1000;
    private TextView milestone3000;
    private TextView milestone6000;
    private ProgressBar walkProgress;
    private int dailySteps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daily_walk);

        TextView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(
                    activity_daily_walk.this,
                    activity_today.class
            );
            startActivity(intent);
            finish();
        });

        // Connect the Daily Walk views
        tvSteps = findViewById(R.id.tvSteps);
        tvRemaining = findViewById(R.id.tvRemaining);
        tvWalkStatus = findViewById(R.id.tvWalkStatus);
        walkProgress = findViewById(R.id.walkProgress);

        milestone1000 = findViewById(R.id.milestone1000);
        milestone3000 = findViewById(R.id.milestone3000);
        milestone6000 = findViewById(R.id.milestone6000);

        // Save step progress on the device
        preferences = getSharedPreferences(
                "AWAKE_DAILY_PROGRESS",
                MODE_PRIVATE
        );

        sensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            stepCounterSensor = sensorManager.getDefaultSensor(
                    Sensor.TYPE_STEP_COUNTER
            );
        }

        loadDailyProgress();
        updateWalkUI();

        if (stepCounterSensor == null) {
            Toast.makeText(
                    this,
                    "Step counter sensor is not available on this device.",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            checkActivityPermission();
        }
    }
    private String getTodayDate() {
        return new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.US
        ).format(new Date());
    }

    // Load saved steps and reset when the date changes
    private void loadDailyProgress() {
        String today = getTodayDate();
        String savedDate = preferences.getString("walk_date", "");

        if (!today.equals(savedDate)) {
            dailySteps = 0;

            preferences.edit()
                    .putString("walk_date", today)
                    .putInt("daily_steps", 0)
                    .putInt("step_baseline", -1)
                    .putInt("last_sensor_steps", -1)
                    .putBoolean("walk_completed", false)
                    .apply();
        } else {
            dailySteps = preferences.getInt("daily_steps", 0);
        }
        preferences.edit()
                .putBoolean("walk_completed", dailySteps >= STEP_GOAL)
                .apply();
    }
    private void checkActivityPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.ACTIVITY_RECOGNITION
                        },
                        PERMISSION_REQUEST_CODE
                );
                return;
            }
        }

        startStepCounter();
    }
    private void startStepCounter() {
        if (stepCounterSensor != null &&
                sensorManager != null) {

            sensorManager.registerListener(
                    this,
                    stepCounterSensor,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_STEP_COUNTER) {
            return;
        }

        int totalSensorSteps = (int) event.values[0];

        if (!getTodayDate().equals(
                preferences.getString("walk_date", "")
        )) {
            loadDailyProgress();
        }

        int lastSensorSteps = preferences.getInt(
                "last_sensor_steps", -1
        );

        if (lastSensorSteps >= 0) {
            if (totalSensorSteps >= lastSensorSteps) {
                dailySteps += totalSensorSteps - lastSensorSteps;
            }
        }

        preferences.edit()
                .putString("walk_date", getTodayDate())
                .putInt("daily_steps", dailySteps)
                .putInt("last_sensor_steps", totalSensorSteps)
                .putBoolean(
                        "walk_completed",
                        dailySteps >= STEP_GOAL
                )
                .apply();

        updateWalkUI();
    }
    // steps, progress bar, milestones, and status
    private void updateWalkUI() {
        NumberFormat format = NumberFormat.getIntegerInstance();

        tvSteps.setText(format.format(dailySteps));

        walkProgress.setMax(STEP_GOAL);
        walkProgress.setProgress(
                Math.min(dailySteps, STEP_GOAL)
        );

        int remaining = Math.max(
                0,
                STEP_GOAL - dailySteps
        );

        tvRemaining.setText(
                format.format(remaining) + " steps remaining"
        );

        milestone1000.setText(
                dailySteps >= 25
                        ? "✓  25 steps completed"
                        : "○  25 steps"
        );
        milestone3000.setText(
                dailySteps >= 50
                        ? "✓  50 steps completed"
                        : "○  50 steps"
        );
        // UPDATED:100 steps
        milestone6000.setText(
                dailySteps >= STEP_GOAL
                        ? "✓  100 steps completed"
                        : "○  100 steps — Daily goal"
        );

        int completedColor = ContextCompat.getColor(
                this, android.R.color.holo_orange_light
        );

        int pendingColor = android.graphics.Color.parseColor(
                "#C4C7EA"
        );

        milestone1000.setTextColor(
                dailySteps >= 25 ? completedColor : pendingColor
        );

        milestone3000.setTextColor(
                dailySteps >= 50 ? completedColor : pendingColor
        );

        milestone6000.setTextColor(
                dailySteps >= STEP_GOAL ? completedColor : pendingColor
        );

        if (dailySteps >= STEP_GOAL) {
            tvRemaining.setText("Daily goal reached!");
            tvWalkStatus.setText("Challenge completed!");
        } else {
            tvWalkStatus.setText("Challenge in progress");
        }
    }
    @Override
    public void onAccuracyChanged(
            Sensor sensor, int accuracy
    ) {
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (preferences != null) {
            loadDailyProgress();
            updateWalkUI();
        }

        if (stepCounterSensor != null) {
            checkActivityPermission();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Stop listening when this screen is not visible
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults
        );

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED) {

                startStepCounter();
            } else {
                Toast.makeText(
                        this,
                        "Physical activity permission is required.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }
}
