package com.example.awake_mobdev_mco;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_shake_challenge extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private  Sensor accelerometer;

    private TextView testX, testY, testZ;

    private final float[] gravity = new float[3];

    private float smoothedIntensity = 0f;
    private float peakIntensity = 0f;

    private static final float MAX_EXPECTED = 30f; // magnitude that counts as 100%


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_challenge);

        Button nextBtn = findViewById(R.id.testButton);

        testX = findViewById(R.id.testX);
        testY = findViewById(R.id.testY);
        testZ = findViewById(R.id.testZ);

        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(activity_shake_challenge.this, activity_daily_walk.class);

            startActivity(intent);
        });


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        for (Sensor s : sensorManager.getSensorList(Sensor.TYPE_ALL)) {
            android.util.Log.d("SENSORS", s.getName() + " | type=" + s.getType());
        }

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (accelerometer == null) {
            testX.setText("No accelerometer available");
        }

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        // Remove gravity
        final float alpha = 0.8f;
        for (int i = 0; i < 3; i++) {
            gravity[i] = alpha * gravity[i] + (1 - alpha) * event.values[i];
        }
        float x = event.values[0] - gravity[0];
        float y = event.values[1] - gravity[1];
        float z = event.values[2] - gravity[2];

        // Raw intensity (m/s²)
        float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

        // Smooth it so the display doesn't flicker (exponential moving average)
        smoothedIntensity = 0.7f * smoothedIntensity + 0.3f * magnitude;

        // Track the strongest shake so far
        peakIntensity = Math.max(peakIntensity, magnitude);

        // Normalize to a 0-100 score
        int percent = Math.min(100, Math.round(smoothedIntensity / MAX_EXPECTED * 100));

        testX.setText(String.format("Intensity: %.1f m/s²", smoothedIntensity));
        testY.setText("Score: " + percent + "%");
        testZ.setText(String.format("Peak: %.1f m/s²", peakIntensity));
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
