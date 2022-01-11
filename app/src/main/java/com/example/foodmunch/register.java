package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextfullname, editTextemail, editTextphone, editTextpassword;
    private Button register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.signup);
        register.setOnClickListener(this);

        editTextfullname = (EditText) findViewById(R.id.fullName);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextphone = (EditText) findViewById(R.id.phone);
        editTextpassword = (EditText) findViewById(R.id.password);
    }

    public void login(View view) {
        Intent i = new Intent(register.this , login.class);
        startActivity(i);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.signup:
                register();
                break;

        }

    }

    private void register() {
        String fullName = editTextfullname.getText().toString().trim();
        String email = editTextemail.getText().toString().trim();
        String phone = editTextphone.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextfullname.setError("Full name is required");
            editTextfullname.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextemail.setError("Email is required");
            editTextemail.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            editTextphone.setError("Phone is required");
            editTextphone.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Please provide valid email");
            editTextemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextpassword.setError("Password is required");
            editTextpassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextpassword.setError("Minimum password length should be 6 characters");
            editTextpassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            user user = new user(fullName, email, phone, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(register.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();

                                    }
                                    else{
                                        Toast.makeText(register.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else if(!task.isSuccessful()){
                            Toast.makeText(register.this, "Registration unsuccessful!! Try again!"+task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}