package com.naqvi.foodmunch.Customer.Account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.naqvi.foodmunch.Customer.Orders.OrdersList_RecyclerViewAdapter;
import com.naqvi.foodmunch.databinding.FragmentFeedbackBinding;
import com.naqvi.foodmunch.databinding.FragmentMyaccountBinding;

import java.util.ArrayList;

public class MyaccountFragment extends Fragment {
    private FragmentMyaccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyaccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
