package com.naqvi.foodmunch.Common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naqvi.foodmunch.Cafe.CafeDashboard.CafeDashboardActivity;
import com.naqvi.foodmunch.Customer.MainActivity;
import com.naqvi.foodmunch.Helper.LoadingDialog;
import com.naqvi.foodmunch.Helper.SharedReference;
import com.naqvi.foodmunch.databinding.ActivityLoginBinding;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    SharedReference sharedRef;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        loadingDialog = new LoadingDialog(this);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        sharedRef = new SharedReference(LoginActivity.this);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(
                        binding.etEmail.getEditText().getText().toString(),
                        binding.etPassword.getEditText().getText().toString()
                );
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });

        binding.etEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    binding.etEmail.setError("Enter your Email");
                } else {
                    binding.etEmail.setError(null);
                }
            }
        });

        binding.etPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    binding.etPassword.setError("Enter Password");
                } else {
                    binding.etPassword.setError(null);
                }
            }
        });
    }

    void login(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            loadingDialog.startLoadingDialog();
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(authResult.getUser().getUid());
                    usersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                usersRef.removeEventListener(this);
                                loadingDialog.dismissDialog();

                                sharedRef.saveUser(new UserModel(authResult.getUser().getUid(), snapshot.child("isCustomer").getValue().toString()));

                                if (snapshot.child("isCustomer").getValue().toString().equals("1"))
                                    //Customer Login
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                else {
                                    //Cafe Login
                                    startActivity(new Intent(LoginActivity.this, CafeDashboardActivity.class));
                                }
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            loadingDialog.dismissDialog();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismissDialog();
                    Toast.makeText(LoginActivity.this, "Login failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            DynamicToast.makeWarning(LoginActivity.this, "Default toast").show();
        }
    }

}
