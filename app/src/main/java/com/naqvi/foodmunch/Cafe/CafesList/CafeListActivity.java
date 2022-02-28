package com.naqvi.foodmunch.Cafe.CafesList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naqvi.foodmunch.Cafe.CreateCafe.CreateCafeActivity;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.ActivityCafeListBinding;

import java.util.ArrayList;

public class CafeListActivity extends AppCompatActivity {
    ActivityCafeListBinding binding;
    SharedReference sharedRef;
    ArrayList<CafeModel> cafesList;
    LoadingDialog loadingDialog;
    CafeList_RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCafeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("My Cafes");

        sharedRef = new SharedReference(this);
        loadingDialog = new LoadingDialog(CafeListActivity.this);
        cafesList = new ArrayList<>();

        binding.cafeRecyclerView.setLayoutManager(new LinearLayoutManager(CafeListActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new CafeList_RecyclerViewAdapter(CafeListActivity.this, cafesList);
        binding.cafeRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCafeList();
    }

    void getCafeList() {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String userId = sharedRef.getUser().userId;
        DatabaseReference myRef = database.getReference("Cafe").child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cafesList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    CafeModel cafeModel = new CafeModel();
                    cafeModel.Id = postSnapshot.child("Id").getValue().toString();
                    cafeModel.Name = postSnapshot.child("CafeName").getValue().toString();
                    cafeModel.Description = postSnapshot.child("Description").getValue().toString();
                    cafeModel.Phone = postSnapshot.child("Phone").getValue().toString();
                    cafeModel.Image = postSnapshot.child("Image").getValue().toString();
                    cafeModel.isVerified = postSnapshot.child("isVerified").getValue().toString();
                    cafesList.add(cafeModel);
                }
                adapter.notifyDataSetChanged();
                loadingDialog.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismissDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.myCafesButton) {
            startActivity(new Intent(CafeListActivity.this, CreateCafeActivity.class));
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        // super.onBackPressed();
    }
}