package com.example.roman.socialmessaganger.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.adapter.UsersAdapter;
import com.example.roman.socialmessaganger.commondata.CommonData;

public class CreateMessage extends Fragment {
    private UsersAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_message, container, false);
        adapter = new UsersAdapter(getActivity());
        ListView friendsList = (ListView) view.findViewById(R.id.lv_fragment_createMessage);
        friendsList.setAdapter(adapter);
        friendsList.invalidate();
        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonData.getInstance().setUserWhomSendMessage(
                        CommonData.getInstance().getUsers().get(position));

                FragmentTransaction trans = getActivity().getFragmentManager().beginTransaction().replace(
                       R.id.frgmCont, new UserWhomSendMessage());
                trans.commit();
            }
        });
        return view;
    }
}
