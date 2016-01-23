package com.example.roman.socialmessaganger.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.adapter.MessagesAdapter;
import com.example.roman.socialmessaganger.commondata.CommonData;
import com.example.roman.socialmessaganger.commondata.MyMessage;
import com.example.roman.socialmessaganger.other.UpdateActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserWhomSendMessage extends Fragment{
    private  ListView listView;
    private MessagesAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_whom_send_message, container, false);
        TextView userName =
                (TextView) view.findViewById(R.id.tv_fragment_userWhomSendMessage_username);
        userName.setText(CommonData.getInstance().getUserWhomSendMessage().getName());
        listView = (ListView) view.findViewById(R.id.lv_fragmentWhomSendMessage_messages);
        adapter = new MessagesAdapter(getActivity().getApplicationContext());
        CommonData.getInstance().getAllMessage().clear();

        ParseQuery query1 = new ParseQuery("Messages");
        query1.whereEqualTo("to", ParseUser.getCurrentUser().getString("name"));
        query1.whereEqualTo("from", CommonData.getInstance().getUserWhomSendMessage().getName());

        ParseQuery query2 = new ParseQuery("Messages");
        query2.whereEqualTo("from", ParseUser.getCurrentUser().getString("name"));
        query2.whereEqualTo("to", CommonData.getInstance().getUserWhomSendMessage().getName());

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);
        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);

        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        MyMessage message = new MyMessage(
                                list.get(i).getObjectId(),
                                list.get(i).getString("message"),
                                list.get(i).getString("from"),
                                list.get(i).getString("to"));
                        CommonData.getInstance().getAllMessage().add(message);
                    }
                    listView.setAdapter(adapter);
//                    System.out.println("Message " + adapter.getCount());
                }
            }
        });




        return view;
    }
}
