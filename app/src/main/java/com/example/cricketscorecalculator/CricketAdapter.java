package com.example.cricketscorecalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CricketAdapter extends RecyclerView.Adapter<CricketAdapter.ViewHolder> {

    Context context;
    private List<Cricket> datalist;

    public CricketAdapter(Context context,List<Cricket> datalist)
    {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cricket data = datalist.get(position);
        holder.textViewscore.setText(data.getScore()+"-"+data.getOut());
        holder.textViewover.setText(data.getOver()+"");
        holder.textViewaction.setText(data.getAction());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewscore;
        public TextView textViewover;
        public TextView textViewaction;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewscore = itemView.findViewById(R.id.runstb);
            textViewover = itemView.findViewById(R.id.overtb);
            textViewaction = itemView.findViewById(R.id.runRatetb);
        }
    }



}
