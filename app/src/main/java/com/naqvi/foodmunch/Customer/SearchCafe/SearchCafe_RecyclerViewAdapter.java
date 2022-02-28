package com.naqvi.foodmunch.Customer.SearchCafe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naqvi.foodmunch.Cafe.CafesList.CafeModel;
import com.naqvi.foodmunch.Customer.CafeProfile.CafeProfileActivity;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.SearchCafeItemBinding;

import java.util.ArrayList;

public class SearchCafe_RecyclerViewAdapter extends RecyclerView.Adapter<SearchCafe_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<CafeModel> dataList = new ArrayList<>();
    private Context mContext;


    public SearchCafe_RecyclerViewAdapter(Context mContext, ArrayList<CafeModel> dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_cafe_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        CafeModel cafe = dataList.get(position);
        holder.binding.productName2.setText(cafe.Name);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CafeProfileActivity.class).putExtra("CafeId", cafe.Id));
            }
        });
//        Bitmap bm = ImageUtil.convertToImage(s.image);
//        holder.binding.image.setImageBitmap(bm);
//
//        //search
//        holder.binding.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SearchCafeItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SearchCafeItemBinding.bind(itemView);
        }
    }

}
