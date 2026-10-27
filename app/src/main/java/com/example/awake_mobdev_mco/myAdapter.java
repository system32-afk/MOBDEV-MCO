package com.example.awake_mobdev_mco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myViewHolder> {
    private ArrayList<alarm> data;
    private final Context context;

    public myAdapter(Context context, ArrayList data){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarmitemlayout, parent, false);
        return new myViewHolder(view);
    }

    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.setDays(data.get(position).getDays().toString());
        holder.setTime(data.get(position).getTime());
    }

    public int getItemCount() {
        return data.size();
    }
}
