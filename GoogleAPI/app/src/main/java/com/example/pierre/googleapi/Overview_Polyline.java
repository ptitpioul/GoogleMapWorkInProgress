package com.example.pierre.googleapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Overview_Polyline {

    @SerializedName("points")
    @Expose
    private String points;

    /**
     *
     * @return
     * The location
     */
    public String getPoints() {
        return points;
    }

    /**
     *
     * @param points
     * The location
     */
    public void setLocation(String points) {
        this.points = points;
    }

}
