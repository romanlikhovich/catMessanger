package com.example.roman.socialmessaganger.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roman.socialmessaganger.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

//    button for login
    public void signIn(View view) {
        final EditText username = (EditText) findViewById(R.id.et_nickName);
        final EditText password = (EditText) findViewById(R.id.et_password);

        ParseUser.logInInBackground(username.getText().toString(),
                password.getText().toString(),new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e == null) {
                    Toast.makeText(Login.this, "Sign in success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, UserMain.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }
        });
    }

//    button for registration
    public void signUp(View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }
}
