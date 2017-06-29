package com.example.pierre.googleapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pierre on 09/06/2017.
 */



public interface GoogleDirectionsApiRequest {

    String BASE_URL = "https://maps.googleapis.com";



    @GET("/maps/api/directions/json")
    Call<Direction> getDirection(@Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode, @Query("key") String key);
}
