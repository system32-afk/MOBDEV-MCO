package com.example.awake_mobdev_mco;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class activity_badges extends AppCompatActivity {

    private GridLayout badgeGrid;
    private TextView chipAll, chipStreaks, chipSteps, chipShake, chipMind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);

        badgeGrid = findViewById(R.id.badgeGrid);
        chipAll = findViewById(R.id.chipAll);
        chipStreaks = findViewById(R.id.chipStreaks);
        chipSteps = findViewById(R.id.chipSteps);
        chipShake = findViewById(R.id.chipShake);
        chipMind = findViewById(R.id.chipMind);

        // Top navbar: filter chips
        chipAll.setOnClickListener(v -> applyFilter("all"));
        chipStreaks.setOnClickListener(v -> applyFilter("streaks"));
        chipSteps.setOnClickListener(v -> applyFilter("steps"));
        chipShake.setOnClickListener(v -> applyFilter("shake"));
        chipMind.setOnClickListener(v -> applyFilter("mind"));

        // Bottom navbar
        findViewById(R.id.navToday).setOnClickListener(v -> {
            startActivity(new Intent(this, activity_today.class));
            finish();
        });
        findViewById(R.id.navAlarms).setOnClickListener(v -> {
            startActivity(new Intent(this, alarms_screen.class));
            finish();
        });
        findViewById(R.id.navBadges).setOnClickListener(v -> recreate());

        applyFilter("all");
    }

    private void applyFilter(String filter) {
        // Show only cards whose android:tag matches the filter ("all" shows everything)
        for (int i = 0; i < badgeGrid.getChildCount(); i++) {
            View card = badgeGrid.getChildAt(i);
            boolean show = filter.equals("all") || filter.equals(card.getTag());
            card.setVisibility(show ? View.VISIBLE : View.GONE);
        }

        // Highlight the selected chip
        styleChip(chipAll, filter.equals("all"));
        styleChip(chipStreaks, filter.equals("streaks"));
        styleChip(chipSteps, filter.equals("steps"));
        styleChip(chipShake, filter.equals("shake"));
        styleChip(chipMind, filter.equals("mind"));
    }

    private void styleChip(TextView chip, boolean selected) {
        chip.setBackgroundResource(selected ? R.drawable.shape_pill_ink : R.drawable.shape_pill_white);
        chip.setTextColor(ContextCompat.getColor(this, selected ? R.color.card_white : R.color.ink));
        chip.setTypeface(null, selected ? Typeface.BOLD : Typeface.NORMAL);
    }
}