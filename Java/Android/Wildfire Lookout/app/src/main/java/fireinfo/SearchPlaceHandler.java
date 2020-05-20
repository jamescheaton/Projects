package com.example.hsmul.fireinfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.w3c.dom.Text;


public class SearchPlaceHandler extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    ExpandableListView expandableListView;
    private static final String TAG = "SearchPlaceHandler";
    private static final int AUTOCOMPLETE_REQUEST_CODE = 3;
    private Button searchButton;
    private List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
    private Place alertPlace;
    private String addressString;
    Geocoder geoCoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        geoCoder = new Geocoder(this);

        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyC7LUvDT0OB4i1moI69Op3Ig7gbU0ksu1Y" );

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
        setContentView(R.layout.search_place);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.search_map);
        mapFragment.getMapAsync(this);

        AutocompleteSupportFragment searchAutocomplete = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.search_bar);

        searchAutocomplete.setRetainInstance(true);

        searchAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                onMapSearch(place);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
                int statusCode = status.getStatusCode();
                System.out.println(statusCode);
                /*if (statusCode == 16){
                    resetMap();
                }*/
            }


        });


        searchButton = (Button) findViewById(R.id.invisibleButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .build(context);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        // Set the fields to specify which types of place data to return.
//        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
// Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

        expandableListView = (ExpandableListView) findViewById(R.id.expandable_view);
    }

    public void onMapSearch(Place place) {
        //EditText locationSearch = (EditText) findViewById(R.id.search_bar);
        //String location = locationSearch.getText().toString();
        //List<Address> addressList = null;

        if (place != null) {
            Geocoder geoCoder = new Geocoder(this);
            /*try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }*/
            //Address address = addressList.get(0);
            LatLng latLng = place.getLatLng();
            mMap.clear();
            Marker alert = mMap.addMarker(new MarkerOptions().position(latLng).title("Add Alert").snippet(place.getName()));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }
/*
        if (place.equals(null)){
            mMap.clear();
            LatLng position = new LatLng(33.7490, -84.3880);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(position));
        }*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                List<Address> addressList = null;
                try {
                    addressList = geoCoder.getFromLocation(latLng.latitude,latLng.longitude,1);


                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address address = addressList.get(0);
                addressString = address.getAddressLine(0);
                mMap.addMarker(new MarkerOptions().position(latLng).title("Add Alert").snippet("Custom Alert"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
        // Atlanta
        LatLng start = new LatLng(33.7490, -84.3880);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed
        //Place place = Autocomplete.getPlaceFromIntent(data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                alertPlace = place;
                addressString = place.getAddress();
                onMapSearch(place);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


    }

    public void resetMap(){
        mMap.clear();
        LatLng position = new LatLng(33.7490, -84.3880);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(position));
    }


    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("AlertAddress",addressString);
        setResult(RESULT_OK,intent);
        //TextView alertPos = findViewById(R.id.alert_address);
        //alertPos.setText(alertPlace.getAddress());
        finish();
    }
}
