package com.example.pierre.googleapi;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {


    GoogleApiClient mGoogleApiCLient;
    Location mLastLocation;

    public static int notificationCountCart = 0;

    String type;
    private GoogleMap mMap;
    ImageButton wish;
    ImageButton payment;
    int REQUEST_CODE_AUTOCOMPLETE = 1;
    int REQUEST_PLACE_PICKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        type = getIntent().getStringExtra("type");

        wish =(ImageButton)findViewById(R.id.wish);
        wish.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    Intent intent = new Intent(MapsActivity.this, WishListActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }}
        });

        payment =(ImageButton)findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    Intent intent = new Intent(MapsActivity.this, PaymentActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        buildGoogleApiCLient();
        if (mGoogleApiCLient != null)
            mGoogleApiCLient.connect();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMarkerClickListener(this);


        // Add a marker in Sydney and move the camera
        //  LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
/*
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
*/


   /*     PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        Intent intent = null;
        try {
            intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
**/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(place.getLatLng())
                        .title(place.toString()))
                        .showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));


            }
        } else if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                mMap.addMarker(new MarkerOptions()
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(place.getLatLng())
                        .title(place.getName().toString()))
                        .showInfoWindow();


                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            }

        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiCLient);
      //  Toast.makeText(this, String.valueOf(mLastLocation.getLatitude()) + "," + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
        Log.d("COORDONNEES", String.valueOf(mLastLocation.getLatitude()) + "," + String.valueOf(mLastLocation.getLongitude()));

        LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.addMarker(new MarkerOptions()
               // .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng)
                .title("FROM"))
                .showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

   /*     LatLng sydney = new LatLng(-34, 151);
         mMap.addMarker(new MarkerOptions()
                 .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                 .position(sydney).title("Marker in Sydney"));
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GooglePlacesApiRequest.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GooglePlacesApiRequest service = retrofit.create(GooglePlacesApiRequest.class);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(GoogleDirectionsApiRequest.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GoogleDirectionsApiRequest service2 = retrofit.create(GoogleDirectionsApiRequest.class);

        // String.valueOf(mLastLocation.getLatitude()) + "," + String.valueOf(mLastLocation.getLongitude()) "-34.0,151.0"

        service.getNearbyPlaces(type, String.valueOf(mLastLocation.getLatitude()) + "," + String.valueOf(mLastLocation.getLongitude()), 1000, "AIzaSyCuY6qRBXhnPSZ96rzWAs3TtsjKriIpg7U").enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {


                Log.d("MESSAGE RESPONSE GOOGLE", response.toString());
                String size = String.valueOf(response.body().getResults().size());

                Toast.makeText(MapsActivity.this, size, Toast.LENGTH_SHORT).show();

                try {
                 //   mMap.clear();
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                        Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                        String placeName = response.body().getResults().get(i).getName();
                        String vicinity = response.body().getResults().get(i).getVicinity();
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(lat, lng);
                        // Position of Marker on Map
                        markerOptions.position(latLng);
                        // Adding Title to the Marker
                        markerOptions.title(placeName + " : " + vicinity);
                        // Adding Marker to the Camera.
                        Marker m = mMap.addMarker(markerOptions);
                        // Adding colour to the marker
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        Log.d("COORDONATES", String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));


                        service2.getDirection(String.valueOf(mLastLocation.getLatitude()) + "," + String.valueOf(mLastLocation.getLongitude()),
                                String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude),
                                "walking", "AIzaSyCuY6qRBXhnPSZ96rzWAs3TtsjKriIpg7U").enqueue(new Callback<Direction>() {
                            @Override
                            public void onResponse(Call<Direction> call, Response<Direction> response) {


                                Log.d("MESSAGE RESPONSE GOOGLE", response.toString());
                                if (response.body().getRoutes().size() > 0) {
                                    String points = String.valueOf(response.body().getRoutes().get(0).getOverview_polyline().getPoints());
                                    List<LatLng> location = PolyUtil.decode(points);


                                    PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
                                    for (int z = 0; z < location.size(); z++) {
                                        LatLng point = location.get(z);
                                        options.add(point);
                                    }
                                   mMap.addPolyline(options);


                                 //   Toast.makeText(MapsActivity.this, points, Toast.LENGTH_SHORT).show();
                                }


                             //   Toast.makeText(MapsActivity.this, "HALELUHIA", Toast.LENGTH_SHORT).show();


                                try {
                                    //   mMap.clear();
                                    // This loop will go through all the results and add marker on each location.

                      /*  Polyline line = mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), latLng)
                                .width(5)
                                .color(Color.RED));


                        PolyUtil.decode();

                        */

                                        // move map camera
                                        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                        //  mMap.animateCamera(CameraUpdateFactory.zoomTo(13));


                                } catch (Exception e) {
                                    Log.d("onResponse", "There is an error");
                                    e.printStackTrace();
                                }



                            }

                            @Override
                            public void onFailure(Call<Direction> call, Throwable t) {
                                Toast.makeText(MapsActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                                t.printStackTrace();
                            }
                        });
                      /*  Polyline line = mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), latLng)
                                .width(5)
                                .color(Color.RED));


                        PolyUtil.decode();

                        */

                        // move map camera
                      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                      //  mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiCLient() {
        mGoogleApiCLient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, StoreProductsActivity.class);
        startActivity(intent);
        return false;
    }
}