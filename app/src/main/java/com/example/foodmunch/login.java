package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
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

public class login extends AppCompatActivity{

    EditText editTextemail, editTextpassword;
    Button btnlogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.login);
        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputEmail = editTextemail.getText().toString();
                String inputPassword = editTextpassword.getText().toString();

                if(inputEmail.equals("") | inputPassword.equals("")){
                    Toast.makeText(login.this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(login.this, "Login successful!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(login.this,home.class));
                            }
                            else{
                                Toast.makeText(login.this, "Login failed" + task.getException().getMessage() , Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
    }


    public void signup(View view) {
        Intent i = new Intent(login.this, register.class);
        startActivity(i);
    }

    public void home(View view) {
        Intent i = new Intent(login.this,  editProfile.class);
        startActivity(i);
    }
}

