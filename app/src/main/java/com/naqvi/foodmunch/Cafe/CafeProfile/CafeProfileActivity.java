package com.naqvi.foodmunch.Cafe.CafeProfile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naqvi.foodmunch.Cafe.CafeCategories.CafeCategoriesActivity;
import com.naqvi.foodmunch.Cafe.CafesList.CafeModel;
import com.naqvi.foodmunch.Cafe.ProductsList.ProductModel;
import com.naqvi.foodmunch.Customer.CafeProfile.CafeMenu_ViewPagerAdapter;
import com.naqvi.foodmunch.Customer.CafeProfile.MenuTabFragment;
import com.naqvi.foodmunch.Helper.ImageUtil;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.ActivityCafeProfileBinding;

import java.util.ArrayList;

public class CafeProfileActivity extends AppCompatActivity {

    ActivityCafeProfileBinding binding;
    String CafeId;
    LoadingDialog loadingDialog;
    SharedReference sharedRef;
    ArrayList<String> categoriesList = new ArrayList<>();
    ArrayList<ProductModel> productsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCafeProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CafeId = getIntent().getStringExtra("CafeId");

        sharedRef = new SharedReference(this);
        loadingDialog = new LoadingDialog(this);

        binding.tvCafeName.setText(CafeId.replace("_", " "));
        binding.tvPhone.setText("03455447318");

        binding.profileTabLayout.setupWithViewPager(binding.profileViewPager);
        binding.profileViewPager.setOffscreenPageLimit(3);

        getCafeDetails();

    }

    void getCafeDetails() {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = sharedRef.getUser().userId;
        DatabaseReference myRef = database.getReference("Cafe").child(userId).child(CafeId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CafeModel cafeModel = new CafeModel();
                cafeModel.Id = snapshot.child("Id").getValue().toString();
                cafeModel.Name = snapshot.child("CafeName").getValue().toString();
                cafeModel.Description = snapshot.child("Description").getValue().toString();
                cafeModel.Phone = snapshot.child("Phone").getValue().toString();
                cafeModel.Image = snapshot.child("Image").getValue().toString();
                cafeModel.isVerified = snapshot.child("isVerified").getValue().toString();

                binding.tvCafeName.setText(cafeModel.Name);
                binding.tvPhone.setText(cafeModel.Phone);
                if (!cafeModel.Image.isEmpty()) {
                    binding.image.setImageBitmap(ImageUtil.convertToImage(cafeModel.Image));
                }

                loadingDialog.dismissDialog();
                getCafeMenu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismissDialog();
            }
        });
    }

    void getCafeMenu() {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = sharedRef.getUser().userId;
        DatabaseReference myRef = database.getReference("Menu").child(userId).child(CafeId);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.myCafesButton) {
            startActivity(new Intent(CafeProfileActivity.this, CafeCategoriesActivity.class).putExtra("CafeId", CafeId));
        }
        return true;
    }
}