package com.example.roman.socialmessaganger.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.commondata.CommonData;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Settings extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ImageView userPhoto = (ImageView) view.findViewById(R.id.iv_settingsUserPhoto);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(
                    ParseUser.getCurrentUser().getParseFile("userphoto").getFile().getPath());
            userPhoto.setImageBitmap(bitmap);
        } catch (ParseException e) {
            e.printStackTrace();
        }
           if (CommonData.getInstance().getUsersInfo() != 0 && CommonData.getInstance().getMessageInfo()!=0) {
               EditText userInfo = (EditText) view.findViewById(R.id.et_settings_userInfo);
               EditText messageInfo = (EditText) view.findViewById(R.id.et_settings_messageInfo);

               userInfo.setText("" + CommonData.getInstance().getUsersInfo());
               messageInfo.setText("" + CommonData.getInstance().getMessageInfo());
           }
        return view;
    }
}
