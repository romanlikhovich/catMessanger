package com.example.roman.socialmessaganger.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.commondata.CommonData;

public class UserWhomSendMessage extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_whom_send_message, container, false);
        TextView userName =
                (TextView) view.findViewById(R.id.tv_fragment_userWhomSendMessage_username);
        userName.setText(CommonData.getInstance().getUserWhomSendMessage().getName());
        ListView listView = (ListView) view.findViewById(R.id.lv_fragmentWhomSendMessage_messages);
        return view;
    }
}
