package com.example.awake_mobdev_mco;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myViewHolder> {
    private ArrayList<alarm> data;
    private final Context context;

    private OnItemClickListener listener;
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

        holder.switchBtn.setOnCheckedChangeListener(null);
        holder.switchBtn.setChecked(data.get(position).isEnabled());
        applySwitchColors(holder, data.get(position).isEnabled());

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) {
                listener.onItemClick(data.get(pos), pos);
            }
        });
    }

    public int getItemCount() {
        return data.size();
    }



    private void applySwitchColors(myViewHolder holder, boolean isOn) {
        int track = ContextCompat.getColor(context, isOn ? R.color.orange : R.color.switch_off_track);
        holder.switchBtn.setTrackTintList(ColorStateList.valueOf(track));
    }
    //DELETE THIS. THIS IS FOR MCO2 ONLY FOR GOING TO THE CHALLENGES
    public interface OnItemClickListener {
        void onItemClick(alarm item, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
