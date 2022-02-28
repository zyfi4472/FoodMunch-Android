package com.naqvi.foodmunch.Cafe.CafeCategories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.ActivityCafeCategoriesBinding;
import com.naqvi.foodmunch.databinding.AddCategoryDialogBinding;

import java.util.ArrayList;

public class CafeCategoriesActivity extends AppCompatActivity {

    ActivityCafeCategoriesBinding binding;
    AddCategoryDialogBinding dialogBinding;
    ArrayList<String> arrayList = new ArrayList<>();
    SharedReference sharedRef;
    LoadingDialog loadingDialog;
    String CafeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCafeCategoriesBinding.inflate(getLayoutInflater());
        dialogBinding = AddCategoryDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CafeId = getIntent().getStringExtra("CafeId");
        getSupportActionBar().setTitle("Menu Categories");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedRef = new SharedReference(CafeCategoriesActivity.this);
        loadingDialog = new LoadingDialog(CafeCategoriesActivity.this);
        binding.cafeCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(CafeCategoriesActivity.this, LinearLayoutManager.VERTICAL, false));
        getCafeCategories();
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
            showDialog();
        }
        return true;
    }

    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CafeCategoriesActivity.this);
        View v = LayoutInflater.from(CafeCategoriesActivity.this).inflate(R.layout.add_category_dialog, null);
        builder.setView(v);
        builder.setCancelable(false);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String categoryTxt = ((TextInputLayout) v.findViewById(R.id.etCategory)).getEditText().getText().toString();
                addCategory(categoryTxt);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();
    }

    void addCategory(String categoryTxt) {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = sharedRef.getUser().userId;
        Log.d("TAG1", "addCafe: " + userId);
        DatabaseReference myRef = database.getReference("Menu").child(userId).child(CafeId).child(categoryTxt.replaceAll(" ", "_"));
        myRef.setValue("").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loadingDialog.dismissDialog();
                getCafeCategories();
                Toast.makeText(CafeCategoriesActivity.this, "Category Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.dismissDialog();
            }
        });
    }


    void getCafeCategories() {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String userId = sharedRef.getUser().userId;
        DatabaseReference myRef = database.getReference("Menu").child(userId).child(CafeId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    arrayList.add(data.getKey());
                }
                CafeCategories_RecyclerViewAdapter adapter = new CafeCategories_RecyclerViewAdapter(CafeCategoriesActivity.this, arrayList, CafeId);
                binding.cafeCategoryRecyclerView.setAdapter(adapter);

                loadingDialog.dismissDialog();

                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismissDialog();
            }
        });
    }
}