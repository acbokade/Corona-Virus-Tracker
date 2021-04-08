package com.demo.coronvirus_tracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int latestTotalCases;
    private int DiffFromPrevDay;

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

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getDiffFromPrevDay() {
        return DiffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.DiffFromPrevDay = diffFromPrevDay;
    }

    @Override
    public String toString() {
        return "LocationStats{" + "state='" + state + '\'' + ", country='" + country + '\'' + ", latest totat cases="
                + latestTotalCases + '}';
    }
}
