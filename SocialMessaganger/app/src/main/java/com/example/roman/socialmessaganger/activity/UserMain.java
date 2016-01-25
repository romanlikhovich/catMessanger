package com.example.roman.socialmessaganger.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.commondata.CommonData;
import com.example.roman.socialmessaganger.binder.MyBinder;
import com.example.roman.socialmessaganger.commondata.MyMessage;
import com.example.roman.socialmessaganger.commondata.User;
import com.example.roman.socialmessaganger.fragment.CreateMessage;
import com.example.roman.socialmessaganger.fragment.Messages;
import com.example.roman.socialmessaganger.fragment.Settings;
import com.example.roman.socialmessaganger.other.MyItemizedOverlay;
import com.example.roman.socialmessaganger.service.MyService;
import com.example.roman.socialmessaganger.other.UpdateActivity;
import com.example.roman.socialmessaganger.fragment.Friends;
import com.example.roman.socialmessaganger.fragment.UserProfile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ServiceConnection, UpdateActivity {
    private ImageView userPhoto;
    private MapView mapView;
    private MyItemizedOverlay myItemizedOverlay;
    private ProgressDialog dialog;
    private ArrayList<OverlayItem> items;
    private LocationManager locationManager;
    private ParseUser user;
    private MyService service;
    private boolean isAddFragment;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    private static final int GALLERY_REQUEST = 1;
    private byte[] photo;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activity_with_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        user = ParseUser.getCurrentUser();
        //        user photo to header menu
        View header = LayoutInflater.from(this).inflate(
                R.layout.nav_header_user_activity_with_menu, null);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(user.getParseFile("userphoto").getFile().getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap == null) {
            ((ImageView) header.findViewById(
                    R.id.iv_header_userPhoto)).setImageResource(R.drawable.user);
        } else {
            ((ImageView) header.findViewById(R.id.iv_header_userPhoto)).setImageBitmap(bitmap);
        }
        navigationView.addHeaderView(header);
        ((TextView) header.findViewById(R.id.tv_header_userName)).setText(user.getString("name"));
        ((TextView) header.findViewById(R.id.tv_header_userEmail)).setText(user.getEmail());
        navigationView.removeHeaderView(navigationView.getHeaderView(0));
//        end userPhoto

        settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.getController().setZoom(13);
        items = new ArrayList<>();

        isAddFragment = false;

//        run ProccessDialog
        dialog = new ProgressDialog(UserMain.this);
        dialog.setTitle("Loading from data base");
        dialog.setMessage("Please wait");
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_activity_with_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentTransaction = getFragmentManager().beginTransaction();
//        if (id == R.id.userProfile) {
//            fragment = new UserProfile();
//            if (!isAddFragment) {
//                fragmentTransaction.add(R.id.frgmCont, fragment);
//                isAddFragment = true;
//            } else {
//                fragmentTransaction.replace(R.id.frgmCont, fragment);
//            }
//            mapView.setBuiltInZoomControls(false);
//
//        } else if (id == R.id.userFriends) {
//            fragment = new Friends();
//            if (!isAddFragment) {
//                fragmentTransaction.add(R.id.frgmCont, fragment);
//                isAddFragment = true;
//            } else {
//                fragmentTransaction.replace(R.id.frgmCont, fragment);
//            }
//            mapView.setBuiltInZoomControls(false);

//        } else
        if (id == R.id.userMessages) {
            fragment = new CreateMessage();
            if (!isAddFragment) {
                fragmentTransaction.add(R.id.frgmCont, fragment);
                isAddFragment = true;
            } else {
                fragmentTransaction.replace(R.id.frgmCont, fragment);
            }
            mapView.setBuiltInZoomControls(false);

        } else if (id == R.id.userSettings) {
            fragment = new Settings();
            if (!isAddFragment) {
                fragmentTransaction.add(R.id.frgmCont, fragment);
                isAddFragment = true;
            } else {
                fragmentTransaction.replace(R.id.frgmCont, fragment);
            }
            mapView.setBuiltInZoomControls(false);

        } else if (id == R.id.userLogout) {
            ParseUser.logOut();
            startActivity(new Intent(this, Login.class));
            finish();
        } else if (id == R.id.menuUserMao) {
            fragmentTransaction.remove(fragment);
            if (CommonData.getInstance().getFragment() != null) {
                fragmentTransaction.remove(CommonData.getInstance().getFragment());
            }
        }
        mapView.setBuiltInZoomControls(true);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        String usersInfo;
        String messagesInfo;
        if (settings.contains("messagesInfo") && settings.contains("usersInfo")) {
            messagesInfo = settings.getString("messagesInfo", "");
            usersInfo = settings.getString("usersInfo", "");
            CommonData.getInstance().setMessageInfo(Integer.parseInt(messagesInfo));
            CommonData.getInstance().setUsersInfo(Integer.parseInt(usersInfo));
        }
        Intent serviceIntent = new Intent(getApplicationContext(), MyService.class);
        startService(serviceIntent);
        bindService(serviceIntent, this, 0);
        user.put("online", true);
        user.saveInBackground();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent serviceIntent = new Intent(getApplicationContext(), MyService.class);
        stopService(serviceIntent);
        unbindService(this);
        user.put("online", false);
        user.saveInBackground();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(UserMain.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(UserMain.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 10, locationListener);
    }


    //    location listener
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                user.put("location", new ParseGeoPoint(location.getLatitude(),
                        location.getLongitude()));
                user.saveInBackground();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.service = ((MyBinder) service).getService();
        this.service.setActivity(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void updateActivity(boolean isUpdate) {
        if (isUpdate) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    items.clear();
                    mapView.getOverlays().clear();
                    mapView.getController().setZoom(12);
                    GeoPoint thisUserPoint = null;
                    for (int i = 0; i < CommonData.getInstance().getUsers().size(); i++) {
                        if (!ParseUser.getCurrentUser().getObjectId().equals(
                                CommonData.getInstance().getUsers().get(i).getId()
                        )) {
                            GeoPoint userPoint =
                                    CommonData.getInstance().getUsers().get(i).getLocation();
                            OverlayItem userItem = new OverlayItem(
                                    CommonData.getInstance().getUsers().get(i).getId(),
                                    "", userPoint);
                            items.add(userItem);
                        } else {
                            thisUserPoint =
                                    CommonData.getInstance().getUsers().get(i).getLocation();
                            OverlayItem userItem = new OverlayItem(
                                    "YOU", "", thisUserPoint);
                            items.add(userItem);
                        }
                    }
                    myItemizedOverlay = new MyItemizedOverlay(getApplicationContext(), items);
                    mapView.getController().setCenter(thisUserPoint);
                    mapView.getOverlays().add(myItemizedOverlay);
                    mapView.invalidate();
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void updateFragment(boolean isUpdate) {

    }

    //    button create message
    public void createMessage(View view) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragment = new CreateMessage();
        if (!isAddFragment) {
            fragmentTransaction.add(R.id.frgmCont, fragment);
            isAddFragment = true;
        } else {
            fragmentTransaction.replace(R.id.frgmCont, fragment);
        }
        fragmentTransaction.commit();
    }

    //    button back
    public void back(View view) {
    }

    //    button send
    public void send(View view) {
        final EditText message = (EditText)
                findViewById(R.id.et_fragment_userWhomSendMessage_textMessage);
        ParseObject newMessage = new ParseObject("Messages");
        newMessage.put("from", ParseUser.getCurrentUser().getString("name"));
        newMessage.put("to", CommonData.getInstance().getUserWhomSendMessage().getName());
        newMessage.put("message", message.getText().toString());
        newMessage.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    message.setText("");
                } else {
                    Toast.makeText(UserMain.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void changePhoto(View view) {
        userPhoto = (ImageView) findViewById(R.id.iv_settingsUserPhoto);
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        photo = getBytesFromBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    userPhoto.setImageBitmap(bitmap);
                }
        }
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public void save(View view) {
        if (photo != null) {
            final ParseFile photoFile = new ParseFile("photo.jpg", photo);
            photoFile.saveInBackground();

            ParseUser user = ParseUser.getCurrentUser();
            user.put("userphoto", photoFile);
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        CommonData.getInstance().getUsers().clear();
                        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                        userQuery.findInBackground(
                                new FindCallback<ParseUser>() {
                                    @Override
                                    public void done(List<ParseUser> list, ParseException e) {
                                        if (e == null) {
                                            for (ParseUser users : list) {
                                                CommonData.getInstance().getUsers().add(
                                                        new User(users.getObjectId(),
                                                                users.getUsername(),
                                                                users.getString("name"),
                                                                users.getEmail(),
                                                                users.getBoolean("online"),
                                                                new GeoPoint(
                                                                        users.getParseGeoPoint(
                                                                                "location").getLatitude(),
                                                                        users.getParseGeoPoint(
                                                                                "location").getLongitude()),
                                                                users.getParseFile("userphoto")));
                                            }

                                            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                                            //        user photo to header menu
                                            View header = LayoutInflater.from(UserMain.this).inflate(
                                                    R.layout.nav_header_user_activity_with_menu, null);
                                            Bitmap bitmap = null;
                                            try {
                                                bitmap = BitmapFactory.decodeFile(ParseUser.getCurrentUser().getParseFile("userphoto").getFile().getPath());
                                            } catch (Exception ee) {
                                                ee.printStackTrace();
                                            }
                                            if (bitmap == null) {
                                                ((ImageView) header.findViewById(
                                                        R.id.iv_header_userPhoto)).setImageResource(R.drawable.user);
                                            } else {
                                                ((ImageView) header.findViewById(R.id.iv_header_userPhoto)).setImageBitmap(bitmap);
                                            }
                                            navigationView.addHeaderView(header);
                                            ((TextView) header.findViewById(R.id.tv_header_userName)).setText(ParseUser.getCurrentUser().getString("name"));
                                            ((TextView) header.findViewById(R.id.tv_header_userEmail)).setText(ParseUser.getCurrentUser().getEmail());
                                            navigationView.removeHeaderView(navigationView.getHeaderView(0));
                                        }
                                    }
                                }
                        );
                    }
                }
            });
        }

        EditText usersInfo = (EditText) findViewById(R.id.et_settings_userInfo);
        EditText messagesInfo = (EditText) findViewById(R.id.et_settings_messageInfo);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("messagesInfo", messagesInfo.getText().toString());
        editor.putString("usersInfo", usersInfo.getText().toString());
        CommonData.getInstance().setMessageInfo(Integer.parseInt(messagesInfo.getText().toString()));
        CommonData.getInstance().setUsersInfo(Integer.parseInt(usersInfo.getText().toString()));
        editor.apply();

        Toast.makeText(UserMain.this, "Settings are saved", Toast.LENGTH_SHORT).show();
    }
}
