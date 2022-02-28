package com.naqvi.foodmunch.Cafe.ProductsList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naqvi.foodmunch.Cafe.CafeCategories.CafeCategoriesActivity;
import com.naqvi.foodmunch.Cafe.CafesList.CafeModel;
import com.naqvi.foodmunch.Helper.ImageUtil;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.CafeListItemBinding;
import com.naqvi.foodmunch.databinding.ProductListItemBinding;

import java.util.ArrayList;

public class ProductList_RecyclerViewAdapter extends RecyclerView.Adapter<ProductList_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<ProductModel> productsList = new ArrayList<>();
    private Context mContext;


    public ProductList_RecyclerViewAdapter(Context mContext, ArrayList<ProductModel> productsList) {
        this.productsList = productsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final ProductModel product = productsList.get(position);

        holder.binding.tvTitle.setText(product.Title);
        holder.binding.tvDescription.setText(product.Description);
        holder.binding.tvPrice.setText("Rs : " + product.Price);
        if (!product.Image.isEmpty()) {
            holder.binding.image.setImageBitmap(ImageUtil.convertToImage(product.Image));
        }

//        holder.binding.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, CafeCategoriesActivity.class).putExtra("CafeName", cafe.Name));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ProductListItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProductListItemBinding.bind(itemView);
        }
    }

}
