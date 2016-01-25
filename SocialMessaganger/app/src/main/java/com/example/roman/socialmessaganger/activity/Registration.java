package com.example.roman.socialmessaganger.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.roman.socialmessaganger.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Registration extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 1;
    private ImageView userPhoto;
    private EditText username;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private byte[] photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userPhoto = (ImageView) findViewById(R.id.iv_registrationAvatar);
        username = (EditText) findViewById(R.id.et_registrationUserName);
        name = (EditText) findViewById(R.id.et_registrationName);
        email = (EditText) findViewById(R.id.et_registrationEmail);
        password = (EditText) findViewById(R.id.et_registrationPassword);
        confirmPassword = (EditText) findViewById(R.id.et_registrationConfirmPassword);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user);
        photo = getBytesFromBitmap(bitmap);
    }

    //    for home button arrow
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    //    events to click menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            buttons arrow home
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //    registration button
    public void registration(View view) {

        if (password.getText().toString().equals(confirmPassword.getText().toString())) {
            final ParseUser user = new ParseUser();
            user.setUsername(username.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user.put("name", name.getText().toString());
            final ParseFile photoFile = new ParseFile("photo.jpg", photo);
            photoFile.saveInBackground();
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        user.put("userphoto", photoFile);
                        user.signUpInBackground();
                        Toast.makeText(getApplicationContext(), "Sign up success",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registration.this, UserMain.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sign up failed",
                                Toast.LENGTH_SHORT).show();
                        password.setText("");
                        confirmPassword.setText("");
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Passwords are different",
                    Toast.LENGTH_SHORT).show();
            password.setText("");
            confirmPassword.setText("");

        }
    }

    public void loadPhoto(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
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
}
