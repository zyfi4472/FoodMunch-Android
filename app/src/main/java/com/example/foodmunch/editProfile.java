package com.example.foodmunch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class editProfile extends AppCompatActivity {

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        final EditText fullNameTxtView = (EditText) findViewById(R.id.editName);
        final EditText emailTxtView = (EditText) findViewById(R.id.editEmail);
        final EditText phoneTxtView = (EditText) findViewById(R.id.editPhone);
        final Button saveBtn = (Button) findViewById(R.id.btnSave);



        String fullName = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");

        fullNameTxtView.setText(fullName);
        emailTxtView.setText(email);
        phoneTxtView.setText(phone);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = fullNameTxtView.getText().toString();
                String email = emailTxtView.getText().toString();
                String phone = phoneTxtView.getText().toString();

                updateData(fullName,email,phone);
            }
        });


    }

    private void updateData(String fullName, String email, String phone) {

        HashMap edited = new HashMap();
        edited.put("fullName",fullName);
        edited.put("email",email);
        edited.put("phone",phone);

        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef.updateChildren(edited).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){

                    Toast.makeText(editProfile.this,"Successfully updated!!", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(editProfile.this,"Failed to update!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}