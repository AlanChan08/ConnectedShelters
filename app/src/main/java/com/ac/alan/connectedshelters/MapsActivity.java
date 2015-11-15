package com.ac.alan.connectedshelters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import android.content.IntentSender;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    double shelterX;
    double shelterY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "*******************Location services connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            handleNewLocation(location);
        };
    }

    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .title("I am here!");
//        mMap.addMarker(options);
        Log.d(TAG, location.toString());
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();


            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        shelterX = getIntent().getExtras().getDouble(Tag.TAG_SHELTER_X);
        shelterY = getIntent().getExtras().getDouble(Tag.TAG_SHELTER_Y);
        Log.d("***********Transfert", shelterX + " " + shelterY);
        mMap.addMarker(new MarkerOptions().position(new LatLng(shelterX, shelterY)).title("Destination"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(shelterX, shelterY), 13.0f));


    }

}
