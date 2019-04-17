package com.example.hsmul.fireinfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_friends, null);
        ListView mListView = view.findViewById(R.id.friendListView);

        Friend friend1 = new Friend("Hajo","Safe","Alert Configured");
        Friend friend2 = new Friend("Noah","Safe","Alert Not Configured!");


        ArrayList<Friend> friendList = new ArrayList<>();
        friendList.add(friend1);
        friendList.add(friend2);


        FriendViewAdapter adapter = new FriendViewAdapter(getActivity(), R.layout.friend_list_view, friendList);
        mListView.setAdapter(adapter);
        return view;
    }
}
