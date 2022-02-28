package com.naqvi.foodmunch.Cafe.CafeCategories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naqvi.foodmunch.Cafe.AddProduct.AddProductActivity;
import com.naqvi.foodmunch.Cafe.ProductsList.ProductsListActivity;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.CafeCategoriesItemBinding;

import java.util.ArrayList;

public class CafeCategories_RecyclerViewAdapter extends RecyclerView.Adapter<CafeCategories_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> categoriesList = new ArrayList<>();
    private Context mContext;
    String CafeId;


    public CafeCategories_RecyclerViewAdapter(Context mContext, ArrayList<String> categoriesList, String CafeId) {
        this.categoriesList = categoriesList;
        this.mContext = mContext;
        this.CafeId = CafeId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafe_categories_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        String category = categoriesList.get(position);
        holder.binding.tvCategory.setText(category);


        holder.binding.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ProductsListActivity.class).putExtra("CafeId", CafeId).putExtra("Category", category));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CafeCategoriesItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CafeCategoriesItemBinding.bind(itemView);
        }
    }

}
