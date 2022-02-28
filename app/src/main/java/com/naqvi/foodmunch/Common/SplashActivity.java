package com.naqvi.foodmunch.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.naqvi.foodmunch.Cafe.CafeDashboard.CafeDashboardActivity;
import com.naqvi.foodmunch.Customer.MainActivity;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.R;

public class SplashActivity extends AppCompatActivity {
    int SPLASH_TIME_OUT = 3000;
    SharedReference sharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        sharedRef = new SharedReference(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserModel userModel = sharedRef.getUser();

                if (!userModel.userId.equals("No Value")) {
                    if (userModel.userType.equals("1")) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, CafeDashboardActivity.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}