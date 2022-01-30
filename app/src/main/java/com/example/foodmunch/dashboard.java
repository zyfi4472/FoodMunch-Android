package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void menu(View view) {
        Intent i = new Intent(dashboard.this , addmenu.class);
        startActivity(i);
    }

    public void orders (View view) {
        Intent i3 = new Intent(dashboard.this, ordermanagement.class);
        startActivity(i3);
    }
    public void feedback1(View view) {
        Intent i4 = new Intent(dashboard.this , Feedback.class);
        startActivity(i4);
    }
}