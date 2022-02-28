package com.naqvi.foodmunch.Cafe.ProductsList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naqvi.foodmunch.Cafe.AddProduct.AddProductActivity;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.ActivityProductsListBinding;

import java.util.ArrayList;

public class ProductsListActivity extends AppCompatActivity {

    ActivityProductsListBinding binding;
    LoadingDialog loadingDialog;
    SharedReference sharedRef;
    String CafeId;
    String Category;
    ArrayList<ProductModel> productsList;
    ProductList_RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Products");

        CafeId = getIntent().getStringExtra("CafeId");
        Category = getIntent().getStringExtra("Category");
        loadingDialog = new LoadingDialog(ProductsListActivity.this);
        sharedRef = new SharedReference(ProductsListActivity.this);
        productsList = new ArrayList<>();


        binding.productsRecyclerView.setLayoutManager(new LinearLayoutManager(ProductsListActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new ProductList_RecyclerViewAdapter(ProductsListActivity.this, productsList);
        binding.productsRecyclerView.setAdapter(adapter);

        //    getProducts();
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
            startActivity(new Intent(ProductsListActivity.this, AddProductActivity.class).putExtra("CafeName", CafeId).putExtra("Category", Category));
        }
        return true;
    }

    void getProducts() {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        productsList.clear();

        String userId = sharedRef.getUser().userId;
        DatabaseReference myRef = database.getReference("Menu").child(userId).child(CafeId).child(Category.replaceAll(" ", "_"));

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ProductModel product = new ProductModel();
                    product.Id = postSnapshot.child("Id").getValue().toString();
                    product.Title = postSnapshot.child("Title").getValue().toString();
                    product.Description = postSnapshot.child("Description").getValue().toString();
                    product.Price = postSnapshot.child("Price").getValue().toString();
                    product.Image = postSnapshot.child("Image").getValue().toString();
                    productsList.add(product);
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
    protected void onResume() {
        super.onResume();
        getProducts();
    }
}