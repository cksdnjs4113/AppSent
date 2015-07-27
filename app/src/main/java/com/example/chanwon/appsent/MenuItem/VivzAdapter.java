package com.example.chanwon.appsent.MenuItem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chanwon.appsent.Activity.OverviewSentiment;
import com.example.chanwon.appsent.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by CHANWON on 7/27/2015.
 */
public class VivzAdapter extends RecyclerView.Adapter<VivzAdapter.MyViewHolder> {
    private LayoutInflater inflator;
    List<Information> data = Collections.emptyList();
    private Context context;
    private VivzAdapter.ClickListner clickListner;

    public VivzAdapter(Context context, List<Information> data) {
        this.context = context;
        inflator = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            title.setOnClickListener(this);
            icon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
           context.startActivity(new Intent(context, OverviewSentiment.class));
            if(clickListner!=null){
                clickListner.itemClicked(v, getPosition());
            }
        }
    }
    public interface ClickListner{
        public void itemClicked(View view, int position);
    }
    public void setClickListner(VivzAdapter.ClickListner clickListner){
        this.clickListner = clickListner;
    }
}
