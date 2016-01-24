package com.example.roman.socialmessaganger.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.adapter.MessagesAdapter;
import com.example.roman.socialmessaganger.commondata.CommonData;
import com.example.roman.socialmessaganger.commondata.MyMessage;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserWhomSendMessage extends Fragment implements Runnable {
    private ListView listView;
    private MessagesAdapter adapter;
    private View view;
    private Thread myThread;
    private boolean isRunThread;
    AlertDialog.Builder ad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_whom_send_message, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ad = new AlertDialog.Builder(this.getActivity());
        isRunThread = true;
        TextView userName =
                (TextView) view.findViewById(R.id.tv_fragment_userWhomSendMessage_username);
        userName.setText(CommonData.getInstance().getUserWhomSendMessage().getName());
        listView = (ListView) view.findViewById(R.id.lv_fragmentWhomSendMessage_messages);
        adapter = new MessagesAdapter(getActivity().getApplicationContext());
        myThread = new Thread(this);
        myThread.start();

        listView.setLongClickable(true);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createDeleteMessageDialog(position);
                ad.show();
                return true;
            }
        });
    }

    @Override
    public void run() {
        while (isRunThread) {
            SystemClock.sleep(1000);

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
            mainQuery.orderByDescending("createdAt");

            mainQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {
                        CommonData.getInstance().getAllMessage().clear();
                        for (int i = 0; i < list.size(); i++) {
                            MyMessage message = new MyMessage(
                                    list.get(i).getObjectId(),
                                    list.get(i).getString("message"),
                                    list.get(i).getString("from"),
                                    list.get(i).getString("to"));
                            CommonData.getInstance().getAllMessage().add(message);
                        }
                        if (getActivity() == null) {
                            return;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(adapter);
                                listView.invalidate();
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isRunThread = false;
    }

    public void createDeleteMessageDialog(final int position) {
        String title = "Delete message";
        String message = "Are you sure?";
        String button1String = "Yes";
        String button2String = "No";

        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                ParseObject parseObject = ParseObject.createWithoutData("Messages",
                        CommonData.getInstance().getAllMessage().get(position).getId());
                parseObject.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            CommonData.getInstance().getAllMessage().remove(position);
                            listView.invalidate();
                            Toast.makeText(getActivity(), "Message are delete ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {

            }
        });
    }
}
