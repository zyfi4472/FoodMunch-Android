package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullNameTxtView = (TextView) findViewById(R.id.currentName);
        final TextView emailTxtView = (TextView) findViewById(R.id.currentEmail);
        final TextView phoneTxtView = (TextView) findViewById(R.id.currentPhone);
        final Button update = (Button) findViewById(R.id.btnupdate);



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user u = snapshot.getValue(user.class);

                if(u != null){
                    String fullName = u.fullName;
                    String email = u.email;
                    String phone = u.phone;

                    fullNameTxtView.setText(fullName);
                    emailTxtView.setText(email);
                    phoneTxtView.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(userProfile.this,"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userProfile.this,editProfile.class);
                i.putExtra("fullName",fullNameTxtView.getText().toString());
                i.putExtra("email",emailTxtView.getText().toString());
                i.putExtra("phone",phoneTxtView.getText().toString());
                startActivity(i);
            }
        });


    }


}