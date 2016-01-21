package com.example.roman.socialmessaganger.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.commondata.CommonData;

public class UsersAdapter extends BaseAdapter {

    private Context context;

    public UsersAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return CommonData.getInstance().getUsers().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//get our xml for one Item
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        RelativeLayout relativeLayout = (RelativeLayout)
                inflater.inflate(R.layout.item_friend, parent, false);
//        end get

        TextView userName = (TextView) relativeLayout.findViewById(R.id.tv_item_friends_userName);
        TextView userStatus = (TextView) relativeLayout.findViewById(R.id.tv_item_friends_status);
//check user online status
        userName.setText(CommonData.getInstance().getUsers().get(position).getName());
         if (CommonData.getInstance().getUsers().get(position).isOnline()) {
             userStatus.setText("online");
         } else {
             userStatus.setText("Not online");
         }
//        end check status

        ImageView usersPhoto = (ImageView) relativeLayout.findViewById(R.id.iv_item_userPhoto);

//        check userPhoto;
        if (CommonData.getInstance().getUsers().get(position).getUserPhoto() == null) {
            usersPhoto.setImageResource(R.drawable.user);
        } else {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeFile(
                        CommonData.getInstance().getUsers().get(position).getUserPhoto().
                                getFile().getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            usersPhoto.setImageBitmap(bitmap);
        }
//        end check status
        return relativeLayout;
    }
}
