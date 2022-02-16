package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Splash screen.
        new Handler().postDelayed(() ->
        {
            //For checking if the user is already logged in or not.
            FirebaseUser mUser = mAuth.getCurrentUser();
            if(mUser!=null)
            {
                //user already logged in.
                checkUserAccessLevel(mUser.getUid());
               // startActivity(new Intent(getApplicationContext(),home.class));
                finish();
            }
            else
            {
                //if no user is logged in.
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();
            }

        }, 3000);

    }
    private void checkUserAccessLevel(String uid)
    {
        DocumentReference df = fStore.collection("Users").document(uid);
        // Extract data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                Log.d("TAG","onSuccess : " + documentSnapshot.getData());
                // identify the user access level
                if(documentSnapshot.getString("isCustomer") != null)
                {
                    startActivity(new Intent(getApplicationContext(),home.class));
                    finish();
                }
                if(documentSnapshot.getString("isCafeOwner") != null)
                {
                    startActivity(new Intent(getApplicationContext(),dashboard.class));
                    finish();
                }
            }
        });

    }

}

