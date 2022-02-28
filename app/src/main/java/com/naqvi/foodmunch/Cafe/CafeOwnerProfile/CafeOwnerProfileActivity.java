package com.naqvi.foodmunch.Cafe.CafeOwnerProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naqvi.foodmunch.Cafe.CafesList.CafeModel;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.ActivityCafeOwnerProfileBinding;
import com.naqvi.foodmunch.databinding.ActivitySearchCafeBinding;

public class CafeOwnerProfileActivity extends AppCompatActivity {

    ActivityCafeOwnerProfileBinding binding;
    LoadingDialog loadingDialog;
    SharedReference sharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCafeOwnerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedRef = new SharedReference(CafeOwnerProfileActivity.this);
        loadingDialog = new LoadingDialog(CafeOwnerProfileActivity.this);

        getProfileData();
    }

    void getProfileData() {
        loadingDialog.startLoadingDialog();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = sharedRef.getUser().userId;

        DatabaseReference myRef = database.getReference("Users").child(userId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadingDialog.dismissDialog();
                try {
                    binding.tvEmail.setText(snapshot.child("Email").getValue().toString());
                    binding.etName.getEditText().setText(snapshot.child("FullName").getValue().toString());
                    binding.etPhoneNo.getEditText().setText(snapshot.child("Phone").getValue().toString());
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismissDialog();
            }
        });

    }

}