package com.example.awake_mobdev_mco;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_shake_challenge extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private  Sensor accelerometer;

    private TextView testX, testY, testZ, timerView;

    private final float[] gravity = new float[3];

    private float smoothedIntensity = 0f;
    private float peakIntensity = 0f;

    private int timerSeconds; //fixed time for the user to shake

    private ProgressBar completionBar, intensityBar;

    private float requiredMagnitude = 60; // shake must be at least 60% magnitude for the timer to tick
    private static final float MAX_EXPECTED = 30f;
    private int TickMS = 100;

    final long challengeTime = 20000; //20 seconds in miliseconds
    private long remainingTime = challengeTime;

    private final Handler handler = new Handler(Looper.getMainLooper());


    private int currentMagnitude;

    private boolean isComplete = false;

    MyDbHelper dbHelper = new MyDbHelper(this);

    vibrator vibrator = new vibrator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_challenge);

        Button nextBtn = findViewById(R.id.testButton);
        timerSeconds = 20;
        testX = findViewById(R.id.testX);
        testY = findViewById(R.id.testY);
        testZ = findViewById(R.id.testZ);

        timerView = findViewById(R.id.timerText);
        completionBar = findViewById(R.id.completionBar);
        intensityBar = findViewById(R.id.intensityBar);



        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(activity_shake_challenge.this, activity_daily_walk.class);

            startActivity(intent);
        });


        //SENSOR SETUP
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


        completionBar.setMax((int)challengeTime); // the max of the completion bar is the max challenge time (20 seconds)
        intensityBar.setMax(100);
        updateTimerAndProgress();

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

        currentMagnitude = Math.min(100, Math.round(smoothedIntensity / MAX_EXPECTED*100));


        intensityBar.setProgress(currentMagnitude);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }

        handler.removeCallbacks(tick);
        handler.postDelayed(tick,TickMS);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

        handler.removeCallbacks(tick);

        currentMagnitude = 0;
    }


    private final Runnable tick = new Runnable() {
        @Override
        public void run() {

            if(isComplete) return;

            if(currentMagnitude >= requiredMagnitude){
                remainingTime = Math.max(0,remainingTime-TickMS); // reduce time
                updateTimerAndProgress();


                if(remainingTime == 0){
                    completeChallenge();
                    return;
                }
            }

            handler.postDelayed(this,TickMS);
        }
    };

    private void updateTimerAndProgress(){
        long secondsLeft = (remainingTime + 999) / 1000;

        timerView.setText(String.format(String.valueOf(secondsLeft)));

        completionBar.setProgress((int) (challengeTime - remainingTime));
    }

    private void completeChallenge(){
        isComplete = true;
        vibrator.vibrate(this);
        dbHelper.recordChallenge("SHAKE"); //RECORD TO DATABASE
        dbHelper.logChallengeCompletions();

            Intent intent = new Intent(activity_shake_challenge.this, activity_daily_walk.class);

            startActivity(intent);
    }
}
