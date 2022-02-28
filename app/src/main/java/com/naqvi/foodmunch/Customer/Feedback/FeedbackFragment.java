package com.naqvi.foodmunch.Customer.Feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.naqvi.foodmunch.Customer.Orders.OrdersList_RecyclerViewAdapter;
import com.naqvi.foodmunch.databinding.FragmentFeedbackBinding;

import java.util.ArrayList;

public class FeedbackFragment extends Fragment {
    private FragmentFeedbackBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFeedbackBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
