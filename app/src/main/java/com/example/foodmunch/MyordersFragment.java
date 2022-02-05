package com.example.foodmunch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyordersFragment extends Fragment {

    String[] menulist = {"\t\t\tCafe 1 : Burger\n\n" + "RS 200","\t\t\tCafe 2 : Biryani\n\n" + "RS 150","\t\t\tCafe 3 : Fries\n\n" + "RS 180","\t\t\tCafe 4 : Pizza\n\n" + "RS 400"};
    int[] menuimages = {R.drawable.burger, R.drawable.biryani, R.drawable.fries, R.drawable.pizza};
    ListView l1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_myorders, container , false);

        l1 = v.findViewById(R.id.specialmenu);
        CustomBaseAdapterMyorders c1 =new CustomBaseAdapterMyorders(getContext(),menulist,menuimages);
        l1.setAdapter(c1);

        return v;
    }
}
