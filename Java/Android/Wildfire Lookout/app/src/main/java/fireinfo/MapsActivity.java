package com.example.hsmul.fireinfo;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import static java.lang.Boolean.TRUE;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, BottomNavigationView.OnNavigationItemSelectedListener, InputPhoneFragment.NoticeDialogListener {

    private static final float INITIAL_ZOOM = 5.0f;
    private static final int GET_ALERT_ADDRESS = 2;
    private static final int SET_ALERT_ADDRESS = 3;
    private GoogleMap mMap;
    private LatLng userLoc;
    private FirebaseFirestore db;
    ArrayList<FireObject> fires;

    private Button searchOnMapButton;
    private AlertsFragment alertFragment;
    private FriendFragment friendFragment;
    private ResourcesFragment resourcesFragment;
    private Fragment currentFragment;

    private boolean isMapShowing;

    String[] states = {"TX", "CA", "FL"};
            /*{"AK",
            "AL",
            "AR",
            "AS",
            "AZ",
            "CA",
            "CO",
            "CT",
            "DC",
            "DE",
            "FL",
            "GA",
            "GU",
            "HI",
            "IA",
            "ID",
            "IL",
            "IN",
            "KS",
            "KY",
            "LA",
            "MA",
            "MD",
            "ME",
            "MI",
            "MN",
            "MO",
            "MS",
            "MT",
            "NC",
            "ND",
            "NE",
            "NH",
            "NJ",
            "NM",
            "NV",
            "NY",
            "OH",
            "OK",
            "OR",
            "PA",
            "PR",
            "RI",
            "SC",
            "SD",
            "TN",
            "TX",
            "UT",
            "VA",
            "VI",
            "VT",
            "WA",
            "WI",
            "WV",
            "WY"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = FirebaseFirestore.getInstance();
        fires = new ArrayList<FireObject>();
        loadFireMetaData();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        alertFragment = new AlertsFragment();
        friendFragment = new FriendFragment();
        resourcesFragment = new ResourcesFragment();

        isMapShowing = true;


        //View view = inflater.inflate(R.layout.search_place,null);
        /*searchOnMapButton = findViewById(R.id.select_on_map_button);
        searchOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, SearchPlaceHandler.class);
                startActivityForResult(intent,2);
            }
        });*/
    }

    void getFirePoints() {
        CollectionReference ref = db.collection("fp");
        //for (String state : states) {
            ref.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> data = document.getData();
                                    ArrayList<Map<String, Object>> points = (ArrayList<Map<String, Object>>) data.get("points");
                                    for (Map<String, Object> point : points){
                                        FirePoint fp = new FirePoint(point, document.getId());
                                        GeoPoint loc = fp.getLocation();
                                        LatLng locLatLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                                        mMap.addMarker(new MarkerOptions().position(locLatLng).title("Fire"));
                                    }
                                }
                            } else {
                                Log.d("Error", "Error getting documents: ", task.getException());
                            }
                        }});

        //}
    }


    boolean getFirePerimeters() {
 //       if (fires.isEmpty()){
   //         return false;
     //   }
 //       for (FireObject fire : fires) {
            DocumentReference ref = db.collection("fires/2019-AZCRD-000050/perimeters/").document("1550502000.0");
            ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            System.out.println("new perimeter");
                            Perimeter perimeter = new Perimeter((Map<String, Object>) document.getData());
                            ArrayList<LatLng> points = new ArrayList<LatLng>();
                            for (GeoPoint point : perimeter.getPoints()) {
                                points.add(new LatLng(point.getLatitude(), point.getLongitude()));
                            }
                            if (!points.isEmpty()) {
                                Polygon polygon = mMap.addPolygon(new PolygonOptions()
                                        .addAll(points)
                                        .strokeColor(Color.RED)
                                        .fillColor(Color.rgb(255, 159, 0))
                                        .visible(TRUE));
                            }
                        } else {
                            Log.d("Error", "No such document");
                        }
                    } else {
                        Log.d("Error", "get failed with ", task.getException());
                    }
                }
            });
      //      fires.remove(fire);
     //   }
        return true;
    }

    void loadFireMetaData() {
        System.out.println("entered metadata");
        db.collection("fires").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("adding metadata");
                                FireObject fire = new FireObject((Map<String, Object>) document.getData(), document.getId());
                                fires.add(fire);
                            }


                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        userLoc = new LatLng(33.783, -84.408);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLoc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(INITIAL_ZOOM));
/*        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true); */
        loadFireMetaData();
        getFirePoints();
        getFirePerimeters();
    }

    private final GoogleMap.OnCameraChangeListener mOnCameraChangeListener =
            new GoogleMap.OnCameraChangeListener() {

                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    getFirePerimeters();
                }
            };

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            //Fragment fragment_map = getSupportFragmentManager().findFragmentById(R.id.map);
            //if(fragment_map != null) {
            //fr.remove(fragment_map);
            //}
            //fr.add(R.id.container, fragment);
            fr.replace(R.id.map,fragment);
            fr.addToBackStack(null);

            fr.commit();

            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        switch(menuItem.getItemId()){
            case R.id.navigation_alerts:
                fragment = alertFragment;
                //configureNewAlertButton();
                break;
            case R.id.navigation_resources:
                fragment = resourcesFragment;
                break;
            case R.id.navigation_friends:
                fragment = friendFragment;
                break;
            case R.id.navigation_me:
                //fragment = new MeFragment();
                break;
            case R.id.navigation_map:


                //fragment = null;
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // if (requestCode == GET_ALERT_ADDRESS) {
            if (resultCode == RESULT_OK) {
                //Fragment alertFragment = new AlertsFragment();
                String address = data.getStringExtra("AlertAddress");
                alertFragment.setNewAlertText(address);

            }


        //}
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new InputPhoneFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

    }



/*
    public void configureNewAlertButton(){
        Button newAlertButton = (Button) findViewById(R.id.add_alert_button);
        newAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new NewAlertFragment());
                return;
            }
        });
    }*/
}
