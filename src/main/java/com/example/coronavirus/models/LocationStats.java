package com.example.coronavirus.models;

public class LocationStats {

    private String state;
    private String country;
    private int latestTotalcases;
    private int diffFromLastDay;
    private int deaths;

    public int getDiffFromLastDay() {
        return diffFromLastDay;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setDiffFromLastDay(int diffFromLastDay) {
        this.diffFromLastDay = diffFromLastDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalcases() {
        return latestTotalcases;
    }

    public void setLatestTotalcases(int latestTotalcases) {
        this.latestTotalcases = latestTotalcases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalcases=" + latestTotalcases +
                ", diffFromLastDay=" + diffFromLastDay +
                ", deaths=" + deaths +
                '}';
    }
}
