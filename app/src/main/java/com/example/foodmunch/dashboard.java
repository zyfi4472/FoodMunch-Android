package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class dashboard extends AppCompatActivity {

    FirebaseAuth mAuth;

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


    public void logout(View view) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(),cafeLogin.class));
        Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_LONG).show();
        finish();
    }
}