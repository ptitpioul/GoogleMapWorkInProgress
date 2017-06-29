package com.example.pierre.googleapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Routes {

    @SerializedName("overview_polyline")
    @Expose
    private Overview_Polyline overview_polyline;


    /**
     *
     * @return
     * The overview_polyline
     */
    public Overview_Polyline getOverview_polyline() {
        return overview_polyline;
    }

    /**
     *
     * @param overview_polyline
     * The overview_polyline
     */
    public void setOverview_polyline(Overview_Polyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }



}
