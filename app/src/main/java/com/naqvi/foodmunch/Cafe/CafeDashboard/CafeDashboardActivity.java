package com.naqvi.foodmunch.Cafe.CafeDashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.naqvi.foodmunch.Cafe.CafeOwnerProfile.CafeOwnerProfileActivity;
import com.naqvi.foodmunch.Cafe.CafesList.CafeListActivity;
import com.naqvi.foodmunch.Common.LoginActivity;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.databinding.ActivityCafeDashboardBinding;

public class CafeDashboardActivity extends AppCompatActivity {

    ActivityCafeDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCafeDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.myCafesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CafeDashboardActivity.this, CafeListActivity.class));
            }
        });

        binding.myProfileButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CafeDashboardActivity.this, CafeOwnerProfileActivity.class));
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SharedReference sharedRef = new SharedReference(CafeDashboardActivity.this);
                sharedRef.removeUser();
                startActivity(new Intent(CafeDashboardActivity.this, LoginActivity.class));
            }
        });
    }
}