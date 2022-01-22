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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;


public class verifyPhone extends AppCompatActivity
{

    String phonenumber, otpid, fullName, email, password;
    FirebaseAuth mAuth;
    Button b2;
    EditText t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        phonenumber  = getIntent().getStringExtra("mobile").toString();
        fullName  = getIntent().getStringExtra("fullName").toString();
        email  = getIntent().getStringExtra("email").toString();
        password  = getIntent().getStringExtra("pass").toString();


        b2 = findViewById(R.id.b2);
        t2 = findViewById(R.id.t2);
        mAuth = FirebaseAuth.getInstance();

        initiateotp();

       b2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(t2.getText().toString().isEmpty())
               {
                   Toast.makeText(getApplicationContext(),"Blank Field can not be processed!",Toast.LENGTH_LONG).show();
               }
               else if(t2.getText().toString().length()!=6)
               {
                   Toast.makeText(getApplicationContext(),"Invalid OTP!",Toast.LENGTH_LONG).show();
               }
               else
               {
                   PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid,t2.getText().toString());
                   signInWithPhoneAuthCredential(credential);
               }
           }
       });

    }

    private void initiateotp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                            {
                                otpid = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                            {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e)
                            {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            createuser();
                            startActivity(new Intent(verifyPhone.this,login.class));
                            finish();
                        }
                        else
                            {
                                Toast.makeText(getApplicationContext(),"Signin Code Error",Toast.LENGTH_LONG).show();
                            }
                    }
                });
    }

    private void createuser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            user user = new user(fullName, email, phonenumber, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(verifyPhone.this, "Verfication Email has been sent!", Toast.LENGTH_LONG).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(verifyPhone.this, "Email not sent!", Toast.LENGTH_LONG).show();


                                            }
                                        });

                                        Toast.makeText(verifyPhone.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();

                                    }
                                    else{
                                        Toast.makeText(verifyPhone.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else if(!task.isSuccessful()){
                            Toast.makeText(verifyPhone.this, "Registration unsuccessful!! Try again!"+task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}