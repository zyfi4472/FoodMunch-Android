package com.naqvi.foodmunch.Customer.CustomerHome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naqvi.foodmunch.Cafe.CafesList.CafeModel;
import com.naqvi.foodmunch.Customer.CafeProfile.CafeProfileActivity;
import com.naqvi.foodmunch.Helper.ImageUtil;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.HomeCafeListItemBinding;

import java.util.ArrayList;

public class CafeList_RecyclerViewAdapter extends RecyclerView.Adapter<CafeList_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<CafeModel> todaySpecialModelList = new ArrayList<>();
    private Context mContext;


    public CafeList_RecyclerViewAdapter(Context mContext, ArrayList<CafeModel> todaySpecialModelList) {
        this.todaySpecialModelList = todaySpecialModelList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cafe_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final CafeModel cafe = todaySpecialModelList.get(position);
        holder.binding.productName.setText(cafe.Name);
        if (!cafe.Image.isEmpty()) {
            Bitmap bm = ImageUtil.convertToImage(cafe.Image);
            holder.binding.image.setImageBitmap(bm);
        }

        //search
        holder.binding.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CafeProfileActivity.class).putExtra("CafeId", cafe.Id));
            }
        });

    }

    @Override
    public int getItemCount() {
        return todaySpecialModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        HomeCafeListItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = HomeCafeListItemBinding.bind(itemView);
        }
    }

}
