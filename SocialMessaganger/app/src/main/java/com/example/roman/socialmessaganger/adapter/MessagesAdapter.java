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
import com.parse.ParseException;
import com.parse.ParseUser;

public class MessagesAdapter extends BaseAdapter {
    private Context context;

    public MessagesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return  CommonData.getInstance().getAllMessage().size();
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
        RelativeLayout relativeLayout;
        if (convertView != null) {
            relativeLayout = (RelativeLayout) convertView;
        }
        else {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            relativeLayout = (RelativeLayout)
                    inflater.inflate(R.layout.item_messages, parent, false);
        }

            TextView whom = (TextView) relativeLayout.findViewById(R.id.tv_itemSendMessageWho);
            TextView message = (TextView)
                    relativeLayout.findViewById(R.id.tv_itemSendMessageTextMessage);

            ImageView photo = (ImageView) relativeLayout.findViewById(R.id.iv_itemSendMessagePhoto);

            Bitmap bitmap = null;
            try {
                if (CommonData.getInstance().getAllMessage().get(position).getFrom().equals(CommonData.getInstance().getUserWhomSendMessage().getName())) {
                    bitmap = BitmapFactory.decodeFile(CommonData.getInstance().getUserWhomSendMessage().getUserPhoto().getFile().getPath());
                } else {
                    bitmap = BitmapFactory.decodeFile(ParseUser.getCurrentUser().getParseFile("userphoto").getFile().getPath());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (bitmap == null) {
                photo.setImageResource(R.drawable.user);
            } else {
                photo.setImageBitmap(bitmap);
            }

            whom.setText(CommonData.getInstance().getAllMessage().get(position).getFrom());
            message.setText(CommonData.getInstance().getAllMessage().get(position).getMessage());

        return relativeLayout;
    }
}
