package com.naqvi.foodmunch.Customer.CustomerHome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.naqvi.foodmunch.Cafe.CafesList.CafeModel;
import com.naqvi.foodmunch.Customer.SearchCafe.SearchCafeActivity;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.databinding.FragmentCustomerHomeBinding;

import java.util.ArrayList;

public class CustomerHomeFragment extends Fragment {

    private FragmentCustomerHomeBinding binding;
    FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    ArrayList<CafeModel> todaySpecialsList;
    LoadingDialog loadingDialog;
    TodaySpecial_RecyclerViewAdapter adapter1;
    CafeList_RecyclerViewAdapter adapter2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCustomerHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadingDialog = new LoadingDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        todaySpecialsList = new ArrayList<>();

        binding.tvTodaySpecial.setVisibility(View.INVISIBLE);
        binding.tvRestaurants.setVisibility(View.INVISIBLE);

        binding.todaySpecialRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        adapter1 = new TodaySpecial_RecyclerViewAdapter(getContext(), todaySpecialsList);
        adapter2 = new CafeList_RecyclerViewAdapter(getContext(), todaySpecialsList);
        binding.todaySpecialRecyclerView.setAdapter(adapter1);
        binding.restaurantsRecyclerView.setAdapter(adapter2);
        // getTodaySpecial();
        getCafeList();

        binding.searchParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), SearchCafeActivity.class));
            }
        });
        return root;
    }

    void getCafeList() {
        loadingDialog.startLoadingDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Cafe");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAG1", "" + snapshot);
                for (DataSnapshot listByOwner : snapshot.getChildren()) {
                    for (DataSnapshot postSnapshot : listByOwner.getChildren()) {
                        if (postSnapshot.child("isVerified").getValue().toString().equals("1")) {
                            CafeModel cafeModel = new CafeModel();
                            cafeModel.Id = postSnapshot.child("Id").getValue().toString();
                            cafeModel.Name = postSnapshot.child("CafeName").getValue().toString();
                            cafeModel.Description = postSnapshot.child("Description").getValue().toString();
                            cafeModel.Phone = postSnapshot.child("Phone").getValue().toString();
                            cafeModel.Image = postSnapshot.child("Image").getValue().toString();
                            cafeModel.isVerified = postSnapshot.child("isVerified").getValue().toString();

                            todaySpecialsList.add(cafeModel);
                        }
                    }
                }
                if (todaySpecialsList.size() > 0) {
                    binding.tvTodaySpecial.setVisibility(View.VISIBLE);
                    binding.tvRestaurants.setVisibility(View.VISIBLE);
                } else {
                    binding.tvRestaurants.setText("No Cafe Found");
                    binding.tvRestaurants.setVisibility(View.VISIBLE);
                }
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                loadingDialog.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismissDialog();
            }
        });
    }

//    void getTodaySpecial() {
//        loadingDialog.startLoadingDialog();
//        fStore.collection("Restaurants").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (QueryDocumentSnapshot item : queryDocumentSnapshots) {
//                    Log.d("TAG", "onSuccess: " + item.getData());
//                    String id = item.getData().get("id").toString();
//                    String name = item.getData().get("name").toString();
//                    String image = item.getData().get("image").toString();
//                    todaySpecialsList.add(new TodaySpecialModel(id, name, image));
//                }
//
//
//                loadingDialog.dismissDialog();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                loadingDialog.dismissDialog();
//            }
//        });
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}