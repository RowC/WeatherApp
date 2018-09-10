package com.bitm.rc.weatherapp;

public class Weather {
    private String city;
    private String temp;
    private String tempMin;
    private String tempMax;
    private String tempDate;
    private String tempDays;

    public Weather() {
    }

    public Weather(String tempMin, String tempMax, String tempDate,String tempDays) {

        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.tempDate = tempDate;
        this.tempDays = tempDays;
    }
    /*public Weather(String city, String temp, String tempMin, String tempMax, String tempDate) {
        this.city = city;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.tempDate = tempDate;
    }*/

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempDate() {
        return tempDate;
    }

    public void setTempDate(String tempDate) {
        this.tempDate = tempDate;
    }

    public String getTempDays() {
        return tempDays;
    }

    public void setTempDays(String tempDays) {
        this.tempDays = tempDays;
    }
}
