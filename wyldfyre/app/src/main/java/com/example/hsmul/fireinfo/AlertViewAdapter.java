package com.example.hsmul.fireinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AlertViewAdapter extends ArrayAdapter<AlertView> {

    private Context mContext;
    int mResource;

    public AlertViewAdapter(Context context, int resource, ArrayList<AlertView> objects){
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).getName();
        String address = getItem(position).getAddress();
        String time = getItem(position).getTime();

        AlertView alert = new AlertView(name,address,time);
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        convertView = mInflator.inflate(mResource,parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvTime = (TextView) convertView.findViewById(R.id.textView3);

        tvName.setText(name);
        tvAddress.setText(address);
        tvTime.setText(time);

        return convertView;

    }
}
