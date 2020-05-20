package com.example.hsmul.fireinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class NewAlertFragment extends Fragment {
    private Button searchOnMapButton;
    private Button saveAlertButton;
    private Switch textAlertSwitch;
    private TextView nameTextView;
    private TextView alertAddress;
    private static final int GET_ALERT_ADDRESS = 2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_alert_fragment, null);
        nameTextView = view.findViewById(R.id.alert_title);
        alertAddress = view.findViewById(R.id.alert_address);
        textAlertSwitch = (Switch) view.findViewById(R.id.text_switch);
        Switch callSwitch = view.findViewById(R.id.call_switch);
        Switch textSwitch = view.findViewById(R.id.text_switch);
        //textAlertSwitch.setOnCheckedChangeListener({
             //   ExpandableRelativeLayout expandableLayout
        //});
        searchOnMapButton = view.findViewById(R.id.select_on_map_button);
        searchOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchPlaceHandler.class);
                startActivityForResult(intent,GET_ALERT_ADDRESS);
            }
        });

        saveAlertButton = view.findViewById(R.id.done_button);
        saveAlertButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String alertName = nameTextView.getText().toString();

                //TextView addressTextView = v.findViewById(R.id.alert_address);
                String alertAddressString = alertAddress.getText().toString();
                //AlertView newAlert = new AlertView(alertName,alertAddress,"TestTime");
                AlertsFragment alertView = new AlertsFragment();
                //alertView.addAlertToList(newAlert);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                searchOnMapButton.setVisibility(View.GONE);
                //Fragment newAlert = new NewAlertFragment();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                ArrayList<String> phoneNums = new ArrayList<String>();
                phoneNums.add("4047044235");
                ArrayList<String> textNums = new ArrayList<String>();
                textNums.add("4047044235");
                ArrayList<String> emailList = new ArrayList<String>();
                //emailList.add("james.heaton@gatech.edu");

                GeoPoint coords = new GeoPoint(33.7690, -84.4880);


                UserAlertSettings newAlert = new UserAlertSettings(false,true,false,true,textNums,emailList, phoneNums, coords, FirebaseAuth.getInstance().getUid(),"Demo Alert",db,"");
                newAlert.addToDB();
                saveAlertButton.setVisibility(View.GONE);

                fr.replace(R.id.new_alert,alertView);
                fr.addToBackStack(null);
                fr.commit();

}
        });

                callSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
        InputPhoneFragment phoneDialog = new InputPhoneFragment();
        phoneDialog.show(getFragmentManager(), "Phone Dialog");
        }
        }
        });

        textSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        InputPhoneFragment phoneDialog = new InputPhoneFragment();
        phoneDialog.show(getFragmentManager(),"Text Dialog");
        }
        });


        return view;
        }

public void setAddress(String text){
        TextView textView = (TextView) getView().findViewById(R.id.alert_address);
        textView.setText(text);
        }





        }
