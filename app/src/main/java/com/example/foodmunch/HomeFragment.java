package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {

    CardView card1;
    TextView notVerfiedMsg;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        notVerfiedMsg = v.findViewById(R.id.notVerified);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();

        if(!user.isEmailVerified()){
            notVerfiedMsg.setVisibility(getView().VISIBLE);
        }
        else if(user.isEmailVerified()){
            notVerfiedMsg.setVisibility(View.INVISIBLE);
        }
        
        card1 = v.findViewById(R.id.special);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), specialMenu.class);
                startActivity(i);
            }
        });

        return v;
    }

}
