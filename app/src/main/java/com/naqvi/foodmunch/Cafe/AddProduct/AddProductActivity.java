package com.naqvi.foodmunch.Cafe.AddProduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.databinding.ActivityAddProductBinding;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    ActivityAddProductBinding binding;
    LoadingDialog loadingDialog;
    SharedReference sharedRef;
    String CafeName;
    String Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CafeName = getIntent().getStringExtra("CafeName");
        Category = getIntent().getStringExtra("Category");
        getSupportActionBar().setTitle(CafeName);

        loadingDialog = new LoadingDialog(this);
        sharedRef = new SharedReference(this);

        binding.addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenu(
                        binding.etProductTitle.getEditText().getText().toString(),
                        binding.etProductPrice.getEditText().getText().toString(),
                        binding.etProductDescription.getEditText().getText().toString()
                );
            }
        });
    }

    void addMenu(String title, String price, String description) {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String userId = sharedRef.getUser().userId;
        DatabaseReference myRef = database.getReference("Menu").child(userId).child(CafeName.replaceAll(" ", "_")).child(Category.replaceAll(" ", "_")).child(title.replaceAll(" ", "_"));

        Map<String, Object> menuItem = new HashMap<>();
        menuItem.put("Id", title.replaceAll(" ", "_"));
        menuItem.put("Title", title);
        menuItem.put("Price", price);
        menuItem.put("Description", description);
        menuItem.put("Image", "");
        myRef.setValue(menuItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loadingDialog.dismissDialog();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG1", "onFailure: " + e.getMessage());
                loadingDialog.dismissDialog();
            }
        });
    }

}