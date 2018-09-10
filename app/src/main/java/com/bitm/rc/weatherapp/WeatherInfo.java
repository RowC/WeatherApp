package com.bitm.rc.weatherapp;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherInfo extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... prms) {

        try {
            URL url = new URL(prms[0]);
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader streamReader=new InputStreamReader(inputStream);
            int data= streamReader.read();
            String apiDetails="";
            char current;
            while (data !=-1){
                current =(char) data;
                apiDetails+=current;
                data= streamReader.read();
            }
            return apiDetails;
        }catch (Exception exp){
            exp.printStackTrace();
        }
        return null;
    }
}
