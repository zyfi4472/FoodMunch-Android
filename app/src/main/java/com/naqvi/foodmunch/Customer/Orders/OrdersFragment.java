package com.naqvi.foodmunch.Customer.Orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.naqvi.foodmunch.Cafe.CafeCategories.CafeCategoriesActivity;
import com.naqvi.foodmunch.databinding.FragmentOrdersBinding;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;
    OrdersList_RecyclerViewAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");

        adapter = new OrdersList_RecyclerViewAdapter(getContext(), arrayList);
        binding.orderListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.orderListRecyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}