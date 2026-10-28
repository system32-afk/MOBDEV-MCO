package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SetAlarm extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View cancelBtn = findViewById(R.id.cancelBtn);

        TextView hourTv = findViewById(R.id.hourTv);
        TextView minuteTv = findViewById(R.id.minuteTv);

        View minuteDownBtn = findViewById(R.id.minuteDownBtn);
        View hourUpBtn = findViewById(R.id.hourUpBtn);
        View hourDownBtn = findViewById(R.id.hourDownBtn);
        View minuteUpBtn2 = findViewById(R.id.minuteUpBtn2);

        View btnAm = findViewById(R.id.btnAm);
        View btnPm = findViewById(R.id.btnPm);
        View submitBtn = findViewById(R.id.saveAlarmBtn);

        View btnAudio = findViewById(R.id.btnAudio);
        View btnVibration = findViewById(R.id.btnVibration);
        View btnBoth = findViewById(R.id.btnBoth);

        AppCompatButton[] dayButtons = new AppCompatButton[] {
                findViewById(R.id.sundayBtn),
                findViewById(R.id.mondayBtn),
                findViewById(R.id.tuesdayBtn),
                findViewById(R.id.wednesdayBtn),
                findViewById(R.id.thursdayBtn),
                findViewById(R.id.fridayBtn),
                findViewById(R.id.saturdayBtn)
        };

        if (btnAm != null && btnPm != null) {
            btnAm.setSelected(true);
            btnPm.setSelected(false);

            btnAm.setOnClickListener(v -> {
                btnAm.setSelected(true);
                btnPm.setSelected(false);
            });

            btnPm.setOnClickListener(v -> {
                btnAm.setSelected(false);
                btnPm.setSelected(true);
            });
        }


        if (btnAudio != null && btnVibration != null && btnBoth != null) {
            btnAudio.setSelected(true);
            btnVibration.setSelected(false);
            btnBoth.setSelected(false);

            btnAudio.setOnClickListener(v -> {
                btnAudio.setSelected(true);
                btnVibration.setSelected(false);
                btnBoth.setSelected(false);
            });

            btnVibration.setOnClickListener(v -> {
                btnAudio.setSelected(false);
                btnVibration.setSelected(true);
                btnBoth.setSelected(false);
            });
            btnBoth.setOnClickListener(v -> {
                btnAudio.setSelected(false);
                btnVibration.setSelected(false);
                btnBoth.setSelected(true);
            });
        }

        for (AppCompatButton button : dayButtons) {
            button.setOnClickListener(v -> {
                v.setSelected(!v.isSelected());
            });
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAlarm.this, MainActivity.class);
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAlarm.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(SetAlarm.this, "Alarm Saved", Toast.LENGTH_SHORT).show();
            }
        });

        if (hourUpBtn !=null && hourTv !=null){
            hourUpBtn.setOnClickListener(v ->{
                int currentHour = Integer.parseInt(hourTv.getText().toString());
                currentHour++;
                if (currentHour > 12) {
                    currentHour = 1;
                }
                else {

                }
                if (currentHour < 10){
                    hourTv.setText("0" + currentHour);
                }
                else {
                    hourTv.setText(String.valueOf(currentHour));

                }
            });
        }
        if(hourDownBtn != null && hourTv != null){
            hourDownBtn.setOnClickListener(v ->{
                int currentHour = Integer.parseInt(hourTv.getText().toString());
                currentHour--;
                if (currentHour < 1) {
                    currentHour = 12;
                }
                else {

                }
                if (currentHour < 10){
                    hourTv.setText("0" + currentHour);
                }
                else {
                    hourTv.setText(String.valueOf(currentHour));
                }
            });
        }
        if(minuteUpBtn2 != null && minuteTv != null){
            minuteUpBtn2.setOnClickListener(v ->{
                int currentMinute = Integer.parseInt(minuteTv.getText().toString());
                currentMinute++;
                if (currentMinute > 60) {
                    currentMinute = 0;
                }
                else {

                }
                if (currentMinute < 10){
                    minuteTv.setText("0" + currentMinute);
                }
                else {
                    minuteTv.setText(String.valueOf(currentMinute));
                }
            });
        }

        if(minuteDownBtn != null && minuteTv != null){
            minuteDownBtn.setOnClickListener(v ->{
                int currentMinute = Integer.parseInt(minuteTv.getText().toString());
                currentMinute--;
                if (currentMinute < 1) {
                    currentMinute = 60;
                }
                else {

                }
                if (currentMinute < 10){
                    minuteTv.setText("0" + currentMinute);
                }
                else {
                    minuteTv.setText(String.valueOf(currentMinute));
                }
            });
        }
    }
}