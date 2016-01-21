package com.example.roman.socialmessaganger.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.adapter.UsersAdapter;

public class Friends extends Fragment {
    private UsersAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        adapter = new UsersAdapter(getActivity());

        ListView friendsList = (ListView) view.findViewById(R.id.lv_fragment_friends_userFriends);
        friendsList.setAdapter(adapter);
        return view;
    }

}
