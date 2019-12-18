package com.janfranco.instacloneparse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.usernameText);
        t2 = findViewById(R.id.passwordText);

        ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser != null) {
            Intent intent = new Intent(SignUpActivity.this, FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void login(View view) {
        ParseUser.logInInBackground(t1.getText().toString(), t2.getText().toString(),
                new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null)
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                            Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(), "Welcome, " + user.getUsername(),
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this,
                            FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void signUp(View view) {
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(t1.getText().toString());
        parseUser.setPassword(t2.getText().toString());
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null)
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                            Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(), "Successfully signed up!",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this,
                            FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
