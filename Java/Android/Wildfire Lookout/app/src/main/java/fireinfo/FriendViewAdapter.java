package com.example.hsmul.fireinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendViewAdapter extends ArrayAdapter<Friend> {

    private Context mContext;
    int mResource;

    public FriendViewAdapter(Context context, int resource, ArrayList<Friend> objects){
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).getName();
        String status = getItem(position).getStatus();
        String alertsConfigured = getItem(position).getAlertsConfigured();

        Friend friend = new Friend(name,status,alertsConfigured);
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        convertView = mInflator.inflate(mResource,parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.friendTextView1);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.friendTextView2);
        TextView tvAlerts = (TextView) convertView.findViewById(R.id.friendTextView3);

        tvName.setText(name);
        tvStatus.setText(status);
        tvAlerts.setText(alertsConfigured);

        return convertView;

    }
}
