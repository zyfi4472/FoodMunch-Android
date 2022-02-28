package com.naqvi.foodmunch.Customer.Orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.OrderHistoryItemBinding;
import com.naqvi.foodmunch.databinding.SearchCafeItemBinding;

import java.util.ArrayList;

public class OrdersList_RecyclerViewAdapter extends RecyclerView.Adapter<OrdersList_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> dataList = new ArrayList<String>();
    private Context mContext;


    public OrdersList_RecyclerViewAdapter(Context mContext, ArrayList<String> todaySpecialList) {
        this.dataList = todaySpecialList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        OrderHistoryItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = OrderHistoryItemBinding.bind(itemView);
        }
    }

}
