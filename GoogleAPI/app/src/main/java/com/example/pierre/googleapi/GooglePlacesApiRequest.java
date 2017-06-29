package com.example.pierre.googleapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pierre on 09/06/2017.
 */



public interface GooglePlacesApiRequest {

    String BASE_URL = "https://maps.googleapis.com";

    @GET("/maps/api/place/nearbysearch/json")
    Call<Shop> getNearbyPlaces(@Query("type") String types, @Query("location") String location, @Query("radius") Integer radius, @Query("key") String key);
}
