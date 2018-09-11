package com.bitm.rc.weatherapp;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class TabCurrent extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    TextView textViewCity, textViewTemp, textViewTempDes, textViewHumidity, textViewTemp_min,
            textViewTemp_max, textViewDate, textViewDay, textViewError, textViewLMin, textViewLMax, textViewHum;
    ImageView imgViewIcon;
    MaterialSearchBar materialSearchBar;
    Button btnC, btnF;
    LinearLayout minL, maxL, humL;
    private static DecimalFormat dcml = new DecimalFormat(".##");

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_current, container, false);
        materialSearchBar = rootView.findViewById(R.id.search_bar_current);
        textViewCity = rootView.findViewById(R.id.textViewCity);
        textViewTemp = rootView.findViewById(R.id.textViewTemp);
        textViewTempDes = rootView.findViewById(R.id.textViewTempDes);
        textViewHumidity = rootView.findViewById(R.id.textViewHumidity);
        imgViewIcon = rootView.findViewById(R.id.imgViewTempIcon);
        textViewTemp_min = rootView.findViewById(R.id.textViewTempMin);
        textViewTemp_max = rootView.findViewById(R.id.textViewTempMax);
        textViewDate = rootView.findViewById(R.id.textViewDate);
        textViewDay = rootView.findViewById(R.id.textViewDay);
        textViewError = rootView.findViewById(R.id.textViewError);
        btnC = rootView.findViewById(R.id.btnC);
        btnF = rootView.findViewById(R.id.btnF);
        minL = rootView.findViewById(R.id.TempMinL);
        maxL = rootView.findViewById(R.id.TempMaxL);
        humL = rootView.findViewById(R.id.TempHmL);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Toast.makeText(getContext(), " Last location is " + location.getLatitude(), Toast.LENGTH_LONG).show();
                            getCurrentWeather(location);

                        } else {
                            Toast.makeText(getContext(), "Sorry location not found", Toast.LENGTH_LONG).show();
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


        btnC.setVisibility(View.GONE);
        btnF.setVisibility(View.GONE);


        textViewTempDes.setVisibility(View.GONE);
        minL.setVisibility(View.GONE);
        maxL.setVisibility(View.GONE);
        humL.setVisibility(View.GONE);

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

                    textViewTemp.setText(dcml.format(tmpF) + " \u2109");
                    textViewTemp_min.setText(dcml.format(tmpMnF) + " \u2109");
                    textViewTemp_max.setText(dcml.format(tmpMxF) + " \u2109");
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
                    textViewTemp.setText(dcml.format(tmpC) + " \u2103");
                    textViewTemp_min.setText(dcml.format(tmpMnC) + " \u2103");
                    textViewTemp_max.setText(dcml.format(tmpMxC) + " \u2103");
                }
            }
        });
    }

    //    public void getCurrentWeather() {
    public void getCurrentWeather(Location location) {
        WeatherInfo weatherInfo = new WeatherInfo();

//        1c476c4624d1f7efb6f54ed81e801e39
        try {
            Double lat = location.getLatitude();
            Double lon = location.getLongitude();
            String weatherDetails = weatherInfo.execute("https://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&units=metric&appid=1c476c4624d1f7efb6f54ed81e801e39").get();
            btnC.setVisibility(View.VISIBLE);
            btnF.setVisibility(View.VISIBLE);
            textViewTempDes.setVisibility(View.VISIBLE);
            minL.setVisibility(View.VISIBLE);
            maxL.setVisibility(View.VISIBLE);
            humL.setVisibility(View.VISIBLE);

            JSONObject jsonObject = new JSONObject(weatherDetails);
            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            JSONObject jsonObjectList = jsonArray.getJSONObject(0);
            JSONObject jsonObject1 = jsonObjectList.getJSONObject("main");
            String city = jsonObjectCity.getString("name").toString();
            String temp = jsonObject1.getString("temp").toString();
            String minTemp = jsonObject1.getString("temp_min").toString();
            String maxTemp = jsonObject1.getString("temp_max").toString();
            String humidity = jsonObject1.getString("humidity").toString();

            JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
            JSONObject weatherJson1 = jsonArrayWeather.getJSONObject(0);
            String weatherJson = weatherJson1.getString("main");
            String description = weatherJson1.getString("description");
            String icon = weatherJson1.getString("icon");

            String currentDate = jsonObjectList.get("dt_txt").toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate = format.parse(currentDate);
            String dayOfTheWeek = (String) DateFormat.format("EEEE", newDate); // Thursday
            String day = (String) DateFormat.format("dd", newDate); // 20
            String monthNumber = (String) DateFormat.format("MM", newDate); // 06
            String year = (String) DateFormat.format("yyyy", newDate); // 2013
            textViewCity.setText((city));
            textViewDate.setText((day + "/" + monthNumber + "/" + year));
            textViewDay.setText((dayOfTheWeek));
            textViewTemp.setText((temp) + " \u2103");
            textViewTemp_min.setText((minTemp) + " \u2103");
            textViewTemp_max.setText((maxTemp) + " \u2103");
            textViewHumidity.setText((humidity));
            textViewTempDes.setText((weatherJson + "\n" + description));
            String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
            File f = new File(iconUrl);
//            String iconUrl = "http://openweathermap.org/img/w/10d.png";
//            Picasso.with(getContext()).load(iconUrl).into(imgViewIcon);

//            Picasso.with(getActivity()).load(f).into(imgViewIcon);

            Picasso.get().load(f).into(imgViewIcon);
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
                textViewDay.setText("");
                btnC.setVisibility(View.GONE);
                btnF.setVisibility(View.GONE);


                textViewTempDes.setVisibility(View.GONE);
                minL.setVisibility(View.GONE);
                maxL.setVisibility(View.GONE);
                humL.setVisibility(View.GONE);

                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText("Sorry City Not Found");
                Toast.makeText(this.getContext(), "Sorry ", Toast.LENGTH_LONG).show();
            } else {

                btnC.setVisibility(View.VISIBLE);
                btnF.setVisibility(View.VISIBLE);
                textViewHumidity.setVisibility(View.VISIBLE);
                textViewTempDes.setVisibility(View.VISIBLE);
                minL.setVisibility(View.VISIBLE);
                maxL.setVisibility(View.VISIBLE);
                humL.setVisibility(View.VISIBLE);

                textViewError.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(weatherDetails);
                JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                JSONObject jsonObjectList = jsonArray.getJSONObject(0);
                JSONObject jsonObject1 = jsonObjectList.getJSONObject("main");

                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                JSONObject weatherJson1 = jsonArrayWeather.getJSONObject(0);
                String weatherJson = weatherJson1.getString("main");
                String description = weatherJson1.getString("description");
                String icon = weatherJson1.getString("icon");


                String city = jsonObjectCity.getString("name").toString();
                String temp = jsonObject1.getString("temp").toString();
                String minTemp = jsonObject1.getString("temp_min").toString();
                String maxTemp = jsonObject1.getString("temp_max").toString();
                String humidity = jsonObject1.getString("humidity").toString();
                String currentDate = jsonObjectList.get("dt_txt").toString();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date newDate = format.parse(currentDate);
                String dayOfTheWeek = (String) DateFormat.format("EEEE", newDate); // Thursday
                String day = (String) DateFormat.format("dd", newDate); // 20
                String monthNumber = (String) DateFormat.format("MM", newDate); // 06
                String year = (String) DateFormat.format("yyyy", newDate); // 2013
                textViewCity.setText((city));
                textViewDate.setText((day + "/" + monthNumber + "/" + year));
                textViewDay.setText((dayOfTheWeek));
                textViewTemp.setText((temp) + " \u2103");
                textViewTemp_min.setText((minTemp) + " \u2103");
                textViewTemp_max.setText((maxTemp) + " \u2103");

                textViewHumidity.setText((humidity));
                textViewTempDes.setText((weatherJson + "\n" + description));
                String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
                File f = new File(iconUrl);
                Picasso.get().load(f).into(imgViewIcon);
            }
        } catch (Exception e) {

        }
    }


    @Override
    public void onSearchStateChanged(boolean b) {
        String state = b ? "enabled" : "disabled";
//        Toast.makeText(this.getContext(), "Search " + state, Toast.LENGTH_LONG).show();
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
