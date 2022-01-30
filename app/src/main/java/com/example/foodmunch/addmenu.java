package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmenu);





    }
    public void confirm(View view) {
        Toast.makeText(addmenu.this, "Food item added successfully", Toast.LENGTH_LONG).show();

    }

    public void cancel(View view) {
        startActivity(new Intent(getApplicationContext(), dashboard.class));

    }
}