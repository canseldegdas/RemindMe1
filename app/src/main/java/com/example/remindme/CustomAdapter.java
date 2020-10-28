package com.example.remindme;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList alarm_id, message, time, date;

    CustomAdapter(Activity activity, Context context, ArrayList alarm_id, ArrayList message, ArrayList time,
                  ArrayList date){
        this.activity = activity;
        this.context = context;
        this.alarm_id = alarm_id;
        this.message = message;
        this.time = time;
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.message_txt.setText(String.valueOf(message.get(position)));
        holder.time_txt.setText(String.valueOf(time.get(position)));
        holder.date_txt.setText(String.valueOf(date.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(alarm_id.get(position)));
                intent.putExtra("message", String.valueOf(message.get(position)));
                intent.putExtra("time", String.valueOf(time.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return alarm_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  message_txt, time_txt, date_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message_txt = itemView.findViewById(R.id.message_txt);
            time_txt = itemView.findViewById(R.id.time_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);

        }

    }

}
