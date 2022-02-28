package com.naqvi.foodmunch.Customer.CustomerHome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naqvi.foodmunch.Cafe.CafesList.CafeModel;
import com.naqvi.foodmunch.Customer.CafeProfile.CafeProfileActivity;
import com.naqvi.foodmunch.Helper.ImageUtil;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.TodaySpecialItemBinding;

import java.util.ArrayList;

public class TodaySpecial_RecyclerViewAdapter extends RecyclerView.Adapter<TodaySpecial_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<CafeModel> todaySpecialModelList = new ArrayList<>();
    private Context mContext;


    public TodaySpecial_RecyclerViewAdapter(Context mContext, ArrayList<CafeModel> todaySpecialModelList) {
        this.todaySpecialModelList = todaySpecialModelList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_special_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final CafeModel cafe = todaySpecialModelList.get(position);

        holder.binding.productName.setText(cafe.Name);
        Log.d("TAG2", "onBindViewHolder: " + cafe.Image);
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

        TodaySpecialItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TodaySpecialItemBinding.bind(itemView);
        }
    }

}
