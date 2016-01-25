package com.example.roman.socialmessaganger.other;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.commondata.CommonData;
import com.example.roman.socialmessaganger.commondata.User;
import com.example.roman.socialmessaganger.fragment.UserWhomSendMessage;
import com.parse.ParseException;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;


public class MyItemizedOverlay extends ItemizedIconOverlay<OverlayItem> {

	protected Context mContext;

	public MyItemizedOverlay(final Context context,
							 final List<OverlayItem> aList) {
		super(context, aList, new OnItemGestureListener<OverlayItem>() {
			@Override
			public boolean onItemSingleTapUp(final int index,
					final OverlayItem item) {
				return false;
			}

			@Override
			public boolean onItemLongPress(final int index,
					final OverlayItem item) {
				return false;
			}
		});
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	public boolean addItem(OverlayItem item) {
		// TODO Auto-generated method stub
		return super.addItem(item);
	}
	@Override
	protected boolean onSingleTapUpHelper(final int index,
			final OverlayItem item, final MapView mapView) {
		final User user = CommonData.getInstance().findUserById(item.getTitle());
		if (user != null) {
			Toast toast = Toast.makeText(mContext, user.getName(),Toast.LENGTH_LONG);
			LinearLayout toastImage = (LinearLayout) toast.getView();
			ImageView imageView = new ImageView(mContext);
			Button button = new Button(mContext);
			button.setText(user.isOnline() ? "online" : "offline");
			button.setTextSize(15.0f);
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeFile(user.getUserPhoto().getFile().getPath());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (bitmap == null) {
				imageView.setImageResource(R.drawable.user);
			}else {
				imageView.setImageBitmap(bitmap);
			}
			toastImage.addView(imageView, 150, 150);
			toastImage.addView(button,250,100);
			toast.show();
		} else {
			Toast.makeText(mContext, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
		}


		return true;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e, MapView mapView) {
//		float x = e.getRawX();
//		float y = e.getRawY();
//		GeoPoint gp = (GeoPoint) mapView.getProjection().fromPixels((int) x, (int) y);
//		 Toast.makeText(mContext, "" + gp.getLatitude() + " " + gp.getLongitude(),
//				 Toast.LENGTH_SHORT).show();
		return super.onSingleTapUp(e, mapView);
	}


}