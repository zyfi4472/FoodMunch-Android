package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //Splash screen.
        new Handler().postDelayed(() ->
        {
            //For checking i the user is already logged in or not.

            FirebaseUser mUser = mAuth.getCurrentUser();
                if(mUser!=null)
                {
                    //user already logged in.
                    startActivity(new Intent(getApplicationContext(),home.class));
                    finish();
                }
                else
                {
                    //if no user is logged in.
                    startActivity(new Intent(getApplicationContext(),login.class));
                    finish();
                }

        }, 3000);

    }


}

