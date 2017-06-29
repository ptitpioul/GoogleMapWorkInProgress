package com.example.pierre.googleapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierre on 09/06/2017.
 */

public class Direction {


    @SerializedName("routes")
    @Expose
    private List<Routes> routes = new ArrayList<>();



    /**
     *
     * @return
     * The routes
     */
    public List<Routes> getRoutes() {
        return routes;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Routes> results) {
        this.routes = results;
    }


}
