package com.example.hsmul.fireinfo;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlertsFragment extends Fragment {

    private Button newAlertButton;
    private NewAlertFragment newAlert;
    private ListView mListView;
    private ArrayList<AlertView> alertList;
    private Switch callSwitch;
    private FirebaseFirestore db;
    private FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_alerts, null);
        mListView = view.findViewById(R.id.listView);

        AlertView alert1 = new AlertView("My Alert 1","Atlanta,GA","04-10-2019");
        //AlertView alert2 = new AlertView("My Alert 2","Los Angeles, CA","04-09-2019");
        //AlertView alert3 = new AlertView("My Alert 3","New York, NY","04-08-2019");
        //AlertView alert4 = new AlertView("My Alert 4","Birmingham,AL","04-07-2019");

        final ArrayList<AlertView> alertList = new ArrayList<>();
        alertList.add(alert1);
        db = FirebaseFirestore.getInstance();

        //AlertViewAdapter adapter = new AlertViewAdapter(getActivity(), R.layout.alerts_list_view, alertList);
        //mListView.setAdapter(adapter);

        newAlert = new NewAlertFragment();

        newAlertButton = view.findViewById(R.id.add_alert_button);
        newAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                //Fragment newAlert = new NewAlertFragment();
                fr.replace(R.id.map,newAlert);
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        String UID = "vMD75rDFfJboSzgBq26ddHZxf6n1";//user.getUid();
        Log.d("UID",user.getUid());
        CollectionReference ref = db.collection("watchLocations");

        ref.whereEqualTo("userId", UID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("Test","Beginning Search");
                        if (task.isSuccessful()) {
                            Log.d("Test","Successful");
                            String alertName;
                            String alertAddress;
                            String alertTime;
                            AlertView currentAlert;
                            Geocoder geoCoder = new Geocoder(getActivity());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Test","Looping");
                                UserAlertSettings alert = new UserAlertSettings(document.getData(), document.getId());
                                alertName = alert.getName();
                                List<Address> addressList = null;
                                try {
                                    addressList = geoCoder.getFromLocation(alert.getCoordinates().getLatitude(),alert.getCoordinates().getLongitude(),1);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Address address = addressList.get(0);
                                alertAddress = address.getAddressLine(0);
                                currentAlert = new AlertView(alertName,alertAddress,"TEST");
                                alertList.add(currentAlert);
                                Log.d("Test","Added");


                            }
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                        AlertViewAdapter adapter = new AlertViewAdapter(getActivity(), R.layout.alerts_list_view, alertList);
                        mListView.setAdapter(adapter);
                    }
                });

        AlertViewAdapter adapter = new AlertViewAdapter(getActivity(), R.layout.alerts_list_view, alertList);
        Log.d("Alert",alertList.get(0).toString());
        //Log.d("Alert",alertList.get(1).toString());
        mListView.setAdapter(adapter);
        return view;
    }

    public void setNewAlertText(String address){
        newAlert.setAddress(address);
    }

    public void addAlertToList(AlertView newAlert){
        alertList.add(newAlert);
    }




}
