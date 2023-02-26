package com.example.foofgeek;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foofgeek.Helper.DBHelper;

public class LoginActivity extends AppCompatActivity {
    // EditText userName, userPassword


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Show backbutton
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        final EditText etRegAcc = findViewById(R.id.etRegAcc);
        final EditText etRegPass = findViewById(R.id.etRegPass);
        final Button buttonRegLogin = findViewById(R.id.bRegLogin);
        final TextView registerLink = findViewById(R.id.tvRegLink);

        buttonRegLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etRegAcc.getText().toString();
                final String password = etRegPass.getText().toString();
                DBHelper dbHelper = new DBHelper(v.getContext(), "Recipes.db", null, 1);
                if (dbHelper.user_Login(username, RegistrationActivity.sha256(password))) {
                    Toast.makeText(v.getContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("userID", username);
                    editor.commit();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1500);
                } else {
                    Toast.makeText(v.getContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //registration page access
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
