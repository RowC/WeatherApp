package com.bitm.rc.weatherapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class TabForecast  extends Fragment implements MaterialSearchBar.OnSearchActionListener{
    private RecyclerView recyclerView;
    private ForecastListAdapter adapter;
    MaterialSearchBar materialSearchBar;
    TextView textViewError,textViewCity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_forecast, container, false);
        recyclerView = rootView.findViewById(R.id.weather_recycler_view);
        materialSearchBar= rootView.findViewById(R.id.search_bar);
        textViewCity = rootView.findViewById(R.id.textViewCity);
        textViewError = rootView.findViewById(R.id.textViewError);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        adapter = new ForecastListAdapter(getForecastWeather(), rootView.getContext());
        recyclerView.setAdapter(adapter);
        getForecastWeather();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        materialSearchBar.setOnSearchActionListener(this);
    }

    public List<Weather> getForecastWeather(){
        WeatherInfo weatherInfo = new WeatherInfo();
        ArrayList<Weather> list = new ArrayList<>();
        try {

            String weatherDetails = weatherInfo.execute("https://api.openweathermap.org/data/2.5/forecast?q=dhaka&units=metric&APPID=1c476c4624d1f7efb6f54ed81e801e39").get();
            JSONObject jsonObject = new JSONObject(weatherDetails);
            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
            JSONArray jsonArray = jsonObject.getJSONArray("list");

            textViewCity.setText(jsonObjectCity.getString("name"));
            for(int i=0;i<jsonArray.length();i++){
                if(i%8==0){
                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                    JSONObject jsonObject1 = jsonObjectList.getJSONObject("main");
                    String temp = jsonObject1.getString("temp").toString();
                    String minTemp = jsonObject1.getString("temp_min").toString();
                    String maxTemp = jsonObject1.getString("temp_max").toString();
                    String currentDate = jsonObjectList.get("dt_txt").toString();
                    Weather weather = new Weather(temp,minTemp,maxTemp,currentDate);
                    list.add(weather);
                }
            }
        }catch (Exception e){

        }
        return list;
    }

    public List<Weather> getForecastWeather(String cityName){
        WeatherInfo weatherInfo = new WeatherInfo();
        ArrayList<Weather> list = new ArrayList<>();
        try {
            String weatherDetails = weatherInfo.execute("https://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&units=metric&APPID=1c476c4624d1f7efb6f54ed81e801e39").get();
            if(weatherDetails==null){
                list.clear();
                textViewError.setText("Sorry City not found");
                textViewCity.setText("");
                Toast.makeText(this.getContext(),"Sorry ",Toast.LENGTH_LONG).show();
            }else {
                textViewError.setText("");
                JSONObject jsonObject = new JSONObject(weatherDetails);
                JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                textViewCity.setText(jsonObjectCity.getString("name"));
                for(int i=0;i<jsonArray.length();i++){
                    if(i%8==0){
                        JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                        JSONObject jsonObject1 = jsonObjectList.getJSONObject("main");
                        String temp = jsonObject1.getString("temp").toString();
                        String minTemp = jsonObject1.getString("temp_min").toString();
                        String maxTemp = jsonObject1.getString("temp_max").toString();
                        String currentDate = jsonObjectList.get("dt_txt").toString();
                        Weather weather = new Weather(temp,minTemp,maxTemp,currentDate);
                        list.add(weather);
                    }
                }
            }
        }catch (Exception e){

        }
        return list;
    }

    @Override
    public void onSearchStateChanged(boolean b) {
        String state = b ? "enabled" : "disabled";
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        adapter = new ForecastListAdapter(getForecastWeather(text.toString()), this.getContext());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                Toast.makeText(this.getContext(), "BUTTON NAVIGATION", Toast.LENGTH_LONG).show();
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                Toast.makeText(this.getContext(), "BUTTON SPEECH", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
