package com.example.aleksandrabramovski.database;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    final static String LOG_TAG = "mylogs";
    Context contex;
    ArrayList<BatteryDetails> battery;
    RVClickListener listener;
    
    public Context getContext(){
        return contex;
    }

    public RecyclerAdapter(Context context, ArrayList<BatteryDetails> battery, RVClickListener listener){
        this.contex = context;
        this.battery = battery;
        this.listener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batteryitem, parent, false);
        final RecyclerViewHolder holder = new RecyclerViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.boxNumber.setText(battery.get(position).getBoxNumber());
        holder.serNum1.setText(battery.get(position).getSerNum1());
        holder.dOfManuf.setText(battery.get(position).getDateOfManuf());
        holder.condition.setText(battery.get(position).getCondition());
        GradientDrawable circleColor = (GradientDrawable)holder.condition.getBackground();
        int colorCondition = getConditionColor(holder.condition.getText().toString());
        circleColor.setColor(colorCondition);
    }

    @Override
    public int getItemCount() {
        return battery.size();
    }

    public int getConditionColor(String condition){
        int color = 0;
        if (condition == null){
            color = R.color.defaultColor;
        }else{
            switch (condition){
                case "New":
                    color = R.color.newBattColor;
                    break;
                case "Used":
                    color = R.color.usedBattColor;
                    break;
            }
        }
        return ContextCompat.getColor(getContext(), color);
    }
}