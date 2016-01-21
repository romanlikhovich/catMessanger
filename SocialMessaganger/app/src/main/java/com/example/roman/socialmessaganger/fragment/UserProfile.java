package com.example.roman.socialmessaganger.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roman.socialmessaganger.R;
import com.parse.ParseUser;


public class UserProfile extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        ImageView userPhoto = (ImageView) view.findViewById(R.id.iv_fragment_userPtofile_userPhoto);
        TextView userNikcname = (TextView) view.findViewById(R.id.tv_fragment_userProfile_nickName);
        TextView userEmail = (TextView) view.findViewById(R.id.tv_fragment_userProfile_email);
        TextView userName = (TextView) view.findViewById(R.id.tv_fragment_userProfile_userName);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(ParseUser.getCurrentUser().getParseFile(
                    "userphoto").getFile().getPath());

            userPhoto.setImageBitmap(bitmap);
            userNikcname.setText(ParseUser.getCurrentUser().getUsername());
            userEmail.setText(ParseUser.getCurrentUser().getEmail());
            userName.setText(ParseUser.getCurrentUser().getString("name"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }

}
