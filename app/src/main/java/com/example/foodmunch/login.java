package com.example.foodmunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signup(View view) {
        Intent i = new Intent(login.this , register.class);
        startActivity(i);
    }

    public void home(View view) {
        Intent i2 = new Intent(login.this , home.class);
        startActivity(i2);
    }
}