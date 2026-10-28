package com.example.awake_mobdev_mco;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class myViewHolder extends RecyclerView.ViewHolder {

    MaterialSwitch switchBtn;
    private TextView time;
    private TextView days;

    public myViewHolder(@NonNull View itemView){
        super(itemView);
        this.time = itemView.findViewById(R.id.timeLbl);
        this.days = itemView.findViewById(R.id.daysLbl);
        switchBtn = itemView.findViewById(R.id.alarmSwitch);
    }

    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.alarmitemlayout,parent,false);
        switchBtn = itemView.findViewById(R.id.alarmSwitch);

        myViewHolder myviewholder = new myViewHolder(view);
        return  myviewholder;
    }


    public void setDays(String days) {
        this.days.setText(days);
    }

    public void setTime(String time) {
        this.time.setText(time);
    }
}
