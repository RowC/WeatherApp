package com.bitm.rc.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ViewHolder> {
    List<Weather> weatherInfoLists;
    Context context;
//    BookInfoManager bookInfoManager;
//    SalesInfoManager salesInfoManager;

    public ForecastListAdapter(List<Weather> weatherInfoLists, Context context) {
        this.weatherInfoLists = weatherInfoLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(weatherInfoLists.get(position).getTempMax()==null){
            Toast.makeText(context,"Sorry!",Toast.LENGTH_LONG).show();
        }else {
            Weather weather = weatherInfoLists.get(position);
            holder.textViewTemp_minF.setText("Min: "+weather.getTempMax().toString() + " \u2103");
            holder.textViewTemp_maxF.setText("Max: "+weather.getTempMin().toString() + " \u2103");
            holder.textViewDateF.setText("Date: "+weather.getTempDate().toString());
        }

    }


    @Override
    public int getItemCount() {
        return weatherInfoLists.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewCity, textViewTemp, textViewTemp_min, textViewTemp_max, textViewDate;

        TextView textViewTempF, textViewTemp_minF, textViewTemp_maxF, textViewDateF;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            textViewCityF = itemView.findViewById(R.id.textViewCity);
//            textViewTempF = itemView.findViewById(R.id.textViewTempF);
            textViewTemp_minF = itemView.findViewById(R.id.textViewTempMinF);
            textViewTemp_maxF = itemView.findViewById(R.id.textViewTempMaxF);
            textViewDateF = itemView.findViewById(R.id.textViewDateF);
        }


    }








}
