package com.naqvi.foodmunch.Cafe.CafesList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naqvi.foodmunch.Cafe.AddProduct.AddProductActivity;
import com.naqvi.foodmunch.Cafe.CafeCategories.CafeCategoriesActivity;
import com.naqvi.foodmunch.Cafe.CafeProfile.CafeProfileActivity;
import com.naqvi.foodmunch.Helper.ImageUtil;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.CafeListItemBinding;

import java.util.ArrayList;

public class CafeList_RecyclerViewAdapter extends RecyclerView.Adapter<CafeList_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<CafeModel> cafesList = new ArrayList<CafeModel>();
    private Context mContext;


    public CafeList_RecyclerViewAdapter(Context mContext, ArrayList<CafeModel> cafesList) {
        this.cafesList = cafesList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafe_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final CafeModel cafe = cafesList.get(position);

        holder.binding.tvName.setText(cafe.Name);
        holder.binding.tvDescription.setText(cafe.Description);
        if (!cafe.Image.isEmpty()) {
            Bitmap bitmap = ImageUtil.convertToImage(cafe.Image);
            holder.binding.image.setImageBitmap(bitmap);
        }
        if (cafe.isVerified.equals("0")) {
            holder.binding.tvVerified.setTextColor(Color.parseColor("#C80000"));
            holder.binding.tvVerified.setText("Unverified");
        }

        holder.binding.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CafeProfileActivity.class).putExtra("CafeId", cafe.Id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cafesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CafeListItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CafeListItemBinding.bind(itemView);
        }
    }

}
