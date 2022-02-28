package com.naqvi.foodmunch.Customer.CafeProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naqvi.foodmunch.Cafe.CafesList.CafeModel;
import com.naqvi.foodmunch.Cafe.ProductsList.ProductModel;
import com.naqvi.foodmunch.Helper.ImageUtil;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.databinding.ActivityCafeProfileBinding;

import java.util.ArrayList;

public class CafeProfileActivity extends AppCompatActivity {

    ActivityCafeProfileBinding binding;
    String CafeId;
    String cafeOwnerId;
    LoadingDialog loadingDialog;
    ArrayList<String> categoriesList = new ArrayList<>();
    ArrayList<ProductModel> productsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = new LoadingDialog(this);
        binding = ActivityCafeProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CafeId = getIntent().getStringExtra("CafeId");

        binding.tvPhone.setText("");

        binding.profileTabLayout.setupWithViewPager(binding.profileViewPager);
        binding.profileViewPager.setOffscreenPageLimit(3);

        getCafeDetails();
    }

    void getCafeDetails() {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Cafe");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                forLoop:
                for (DataSnapshot listByOwner : snapshot.getChildren()) {
                    for (DataSnapshot postSnapshot : listByOwner.getChildren()) {
                        if (postSnapshot.getKey().equals(CafeId)) {
                            cafeOwnerId = postSnapshot.getRef().getParent().getKey();
                            CafeModel cafeModel = new CafeModel();
                            cafeModel.Id = postSnapshot.child("Id").getValue().toString();
                            cafeModel.Name = postSnapshot.child("CafeName").getValue().toString();
                            cafeModel.Description = postSnapshot.child("Description").getValue().toString();
                            cafeModel.Phone = postSnapshot.child("Phone").getValue().toString();
                            cafeModel.Image = postSnapshot.child("Image").getValue().toString();
                            cafeModel.isVerified = postSnapshot.child("isVerified").getValue().toString();

                            binding.tvCafeName.setText(cafeModel.Name);
                            binding.tvPhone.setText(cafeModel.Phone);
                            if (!cafeModel.Image.isEmpty()) {
                                binding.image.setImageBitmap(ImageUtil.convertToImage(cafeModel.Image));
                            }

                            loadingDialog.dismissDialog();
                            getCafeMenu(cafeModel.Id);
                            break forLoop;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismissDialog();
            }
        });
    }

    void getCafeMenu(String CafeId) {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Menu").child(cafeOwnerId).child(CafeId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadingDialog.dismissDialog();
                for (DataSnapshot listByOwner : snapshot.getChildren()) {
                    for (DataSnapshot postSnapshot : listByOwner.getChildren()) {

                        try {
                            String parentId = postSnapshot.getRef().getParent().getKey().replace("_", " ");
                            if (!categoriesList.contains(parentId)) {
                                categoriesList.add(parentId);
                            }

                            ProductModel product = new ProductModel();
                            product.Id = postSnapshot.child("Id").getValue().toString();
                            product.ParentId = parentId;
                            product.Title = postSnapshot.child("Title").getValue().toString();
                            product.Description = postSnapshot.child("Description").getValue().toString();
                            product.Price = postSnapshot.child("Price").getValue().toString();
                            product.Image = postSnapshot.child("Image").getValue().toString();

                            productsList.add(product);
                        } catch (Exception e) {
                          //  Toast.makeText(CafeProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                setViewPager();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismissDialog();
            }
        });
    }

    void setViewPager() {
        if (categoriesList.size() > 0) {
            binding.appBarLayout.setVisibility(View.VISIBLE);
            CafeMenu_ViewPagerAdapter viewPagerAdaptor = new CafeMenu_ViewPagerAdapter(getSupportFragmentManager(), 3);
            for (String s : categoriesList) {
                ArrayList<ProductModel> list = new ArrayList<>();
                for (int i = 0; i < productsList.size(); i++) {
                    try {
                        if (productsList.get(i).ParentId.equals(s.replace("_", " "))) {
                            list.add(productsList.get(i));
                        }
                    } catch (Exception e) {

                    }
                }
                viewPagerAdaptor.addFragment(new MenuTabFragment(list), s);
            }
            binding.profileViewPager.setAdapter(viewPagerAdaptor);
        } else {
            binding.appBarLayout.setVisibility(View.INVISIBLE);
        }
    }
}