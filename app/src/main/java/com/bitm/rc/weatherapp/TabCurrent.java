package com.bitm.rc.weatherapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONObject;

public class TabCurrent extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    TextView textViewCity, textViewTemp, textViewTemp_min, textViewTemp_max, textViewDate, textViewError;
    MaterialSearchBar materialSearchBar;
    Button btnC, btnF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_current, container, false);
        materialSearchBar = rootView.findViewById(R.id.search_bar_current);
        textViewCity = rootView.findViewById(R.id.textViewCity);
        textViewTemp = rootView.findViewById(R.id.textViewTemp);
        textViewTemp_min = rootView.findViewById(R.id.textViewTempMin);
        textViewTemp_max = rootView.findViewById(R.id.textViewTempMax);
        textViewDate = rootView.findViewById(R.id.textViewDate);
        textViewError = rootView.findViewById(R.id.textViewError);
        btnC = rootView.findViewById(R.id.btnC);
        btnF = rootView.findViewById(R.id.btnF);

        getCurrentWeather();
        materialSearchBar.setOnSearchActionListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmpSt = textViewTemp.getText().toString();
                String tmpMnSt = textViewTemp_min.getText().toString();
                String tmpMXSt = textViewTemp_max.getText().toString();
                String tmpS = tmpSt.split(" ")[0];
                String tmpMnS = tmpMnSt.split(" ")[0];
                String tmpMxS = tmpMXSt.split(" ")[0];

                String c = tmpSt.split(" ")[1];
                String cc = "\u2103";
                if (c.equals(cc)) {
                    Double tmp = (!tmpS.equals("")) ? Double.parseDouble(tmpS) : 0.0;
                    Double tmpMn = (!tmpMnS.equals("")) ? Double.parseDouble(tmpMnS) : 0.0;
                    Double tmpMx = (!tmpMxS.equals("")) ? Double.parseDouble(tmpMxS) : 0.0;

                    Double tmpF = (((tmp * 9 / 5) + 32));
                    Double tmpMnF = (((tmpMn * 9 / 5) + 32));
                    Double tmpMxF = (((tmpMx * 9 / 5) + 32));
                    textViewTemp.setText((tmpF) + " \u2109");
                    textViewTemp_min.setText((tmpMnF) + " \u2109");
                    textViewTemp_max.setText((tmpMxF) + " \u2109");
                }


            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmpSt = textViewTemp.getText().toString();
                String tmpMnSt = textViewTemp_min.getText().toString();
                String tmpMXSt = textViewTemp_max.getText().toString();
                String tmpS = tmpSt.split(" ")[0];
                String tmpMnS = tmpMnSt.split(" ")[0];
                String tmpMxS = tmpMXSt.split(" ")[0];
                String f = tmpSt.split(" ")[1];
                if (f.equals("\u2109")) {
                    Double tmp = (!tmpS.equals("")) ? Double.parseDouble(tmpS) : 0.0;
                    Double tmpMn = (!tmpMnS.equals("")) ? Double.parseDouble(tmpMnS) : 0.0;
                    Double tmpMx = (!tmpMxS.equals("")) ? Double.parseDouble(tmpMxS) : 0.0;
                    Double tmpC = (tmp - 32) * 5 / 9;
                    Double tmpMnC = (tmpMn - 32) * 5 / 9;
                    Double tmpMxC = (tmpMx - 32) * 5 / 9;
                    textViewTemp.setText((tmpC) + " \u2103");
                    textViewTemp_min.setText((tmpMnC) + " \u2103");
                    textViewTemp_max.setText((tmpMxC) + " \u2103");
                }
            }
        });
    }

    public void getCurrentWeather() {
        WeatherInfo weatherInfo = new WeatherInfo();
        try {
            String weatherDetails = weatherInfo.execute("https://api.openweathermap.org/data/2.5/forecast?q=dhaka&units=metric&APPID=1c476c4624d1f7efb6f54ed81e801e39").get();
            JSONObject jsonObject = new JSONObject(weatherDetails);
            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            JSONObject jsonObjectList = jsonArray.getJSONObject(0);
            JSONObject jsonObject1 = jsonObjectList.getJSONObject("main");
            String city = jsonObjectCity.getString("name").toString();
            String temp = jsonObject1.getString("temp").toString();
            String minTemp = jsonObject1.getString("temp_min").toString();
            String maxTemp = jsonObject1.getString("temp_max").toString();
            String currentDate = jsonObjectList.get("dt_txt").toString();
            textViewCity.setText((city));
            textViewTemp.setText((temp) + " \u2103");
            textViewTemp_min.setText((minTemp) + " \u2103");
            textViewTemp_max.setText((maxTemp) + " \u2103");
            textViewDate.setText((currentDate));
        } catch (Exception e) {

        }
    }

    public void getCurrentWeather(String cityName) {
        WeatherInfo weatherInfo = new WeatherInfo();
        try {
            String weatherDetails = weatherInfo.execute("https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&units=metric&APPID=1c476c4624d1f7efb6f54ed81e801e39").get();

//            if(jsonObject.getString("cod")=="404"){
            if (weatherDetails == null) {
                textViewCity.setText((""));
                textViewTemp.setText("");
                textViewTemp_min.setText("");
                textViewTemp_max.setText("");
                textViewDate.setText("");
                textViewError.setText("");
                textViewError.setText("Sorry City not found");
                Toast.makeText(this.getContext(), "Sorry ", Toast.LENGTH_LONG).show();
            } else {
                JSONObject jsonObject = new JSONObject(weatherDetails);
                JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                JSONObject jsonObjectList = jsonArray.getJSONObject(0);
                JSONObject jsonObject1 = jsonObjectList.getJSONObject("main");
                String city = jsonObjectCity.getString("name").toString();
                String temp = jsonObject1.getString("temp").toString();
                String minTemp = jsonObject1.getString("temp_min").toString();
                String maxTemp = jsonObject1.getString("temp_max").toString();
                String currentDate = jsonObjectList.get("dt_txt").toString();
                textViewCity.setText((city));
                textViewTemp.setText((temp) + " \u2103");
                textViewTemp_min.setText((minTemp) + " \u2103");
                textViewTemp_max.setText((maxTemp) + " \u2103");
                textViewDate.setText((currentDate));
                textViewError.setText("");
            }
        } catch (Exception e) {

        }
    }


    @Override
    public void onSearchStateChanged(boolean b) {
        String state = b ? "enabled" : "disabled";
        Toast.makeText(this.getContext(), "Search " + state, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        getCurrentWeather(text.toString());
//        Toast.makeText(this.getContext(), "Searching " + text.toString(), Toast.LENGTH_LONG).show();

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
