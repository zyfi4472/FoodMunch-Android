package com.naqvi.foodmunch.Customer.SearchCafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naqvi.foodmunch.Cafe.CafesList.CafeModel;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.databinding.ActivitySearchCafeBinding;

import java.util.ArrayList;
import java.util.Locale;

public class SearchCafeActivity extends AppCompatActivity {

    ActivitySearchCafeBinding binding;
    private ArrayList<CafeModel> arrayList = new ArrayList<CafeModel>();
    SearchCafe_RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchCafeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");
//        arrayList.add("1");

        binding.searchCafeRecyclerView.setLayoutManager(new LinearLayoutManager(SearchCafeActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new SearchCafe_RecyclerViewAdapter(SearchCafeActivity.this, arrayList);
        binding.searchCafeRecyclerView.setAdapter(adapter);

        //  getCafeList();

        binding.etEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    getCafeList(s.toString());
                } else {
                    arrayList.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    void getCafeList(String query) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Cafe");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot listByOwner : snapshot.getChildren()) {
                    for (DataSnapshot postSnapshot : listByOwner.getChildren()) {
                        CafeModel cafe = new CafeModel();
                        cafe.Name = postSnapshot.child("CafeName").getValue().toString().toLowerCase();
                        cafe.isVerified = postSnapshot.child("isVerified").getValue().toString();
                        cafe.Id = postSnapshot.child("Id").getValue().toString().toLowerCase();

                        if (cafe.isVerified.equals("1") && cafe.Name.contains(query.toLowerCase())) {
                            arrayList.add(cafe);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}