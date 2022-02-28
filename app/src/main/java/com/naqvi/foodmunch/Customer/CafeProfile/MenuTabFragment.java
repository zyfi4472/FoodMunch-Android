package com.naqvi.foodmunch.Customer.CafeProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.naqvi.foodmunch.Cafe.ProductsList.ProductList_RecyclerViewAdapter;
import com.naqvi.foodmunch.Cafe.ProductsList.ProductModel;
import com.naqvi.foodmunch.Customer.Orders.OrdersList_RecyclerViewAdapter;
import com.naqvi.foodmunch.databinding.ActivityProductsListBinding;

import java.util.ArrayList;

public class MenuTabFragment extends Fragment {

    public MenuTabFragment(ArrayList<ProductModel> productsList) {
        arrayList = productsList;
    }


    private ActivityProductsListBinding binding;
    ProductList_RecyclerViewAdapter adapter;
    ArrayList<ProductModel> arrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = ActivityProductsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new ProductList_RecyclerViewAdapter(getContext(), arrayList);
        binding.productsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.productsRecyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}