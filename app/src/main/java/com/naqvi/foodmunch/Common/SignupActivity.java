package com.naqvi.foodmunch.Common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naqvi.foodmunch.R;
import com.naqvi.foodmunch.databinding.ActivitySignupBinding;


import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    String userType = "Select User Type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        binding.etUserType.setAdapter(new ArrayAdapter(this, R.layout.dropdown_item, getResources().getStringArray(R.array.userTypeDropDown)));

        binding.etUserType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.equals("Select User Type")) {
                    binding.etUserTypeLayout.setError("Please select User Type");
                } else {
                    binding.etUserTypeLayout.setError(null);
                }
                userType = s.toString();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpButton(
                        binding.etName.getEditText().getText().toString(),
                        binding.etEmail.getEditText().getText().toString(),
                        binding.etPhoneNo.getEditText().getText().toString(),
                        binding.etPassword.getEditText().getText().toString(),
                        binding.etConfirmPassword.getEditText().getText().toString()
                );
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.etName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    binding.etName.setError("Enter your Name");
                } else {
                    binding.etName.setError(null);
                }
            }
        });

        binding.etName.getEditText().addTextChangedListener(new TextWatcher() {
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

        binding.etPhoneNo.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    binding.etPhoneNo.setError("Enter Mobile Number");
                } else {
                    binding.etPhoneNo.setError(null);
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

        binding.etConfirmPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    binding.etPassword.setError("Enter confirm Password");
                } else {
                    binding.etPassword.setError(null);
                }
            }
        });

    }


    void signUpButton(String name, String email, String phoneNo, String password, String confirmPassword) {
        if (!name.isEmpty() && !email.isEmpty() && !phoneNo.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !userType.equals("Select User Type")) {
            if (password.equals(confirmPassword) && password.length() >= 8) {
                Log.d("TAG", "signUpButton: verified");
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                FirebaseUser user = mAuth.getCurrentUser();

                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("FullName", binding.etName.getEditText().getText().toString());
                                userInfo.put("Email", email);
                                userInfo.put("Phone", binding.etPhoneNo.getEditText().getText().toString());
                                userInfo.put("Password", password);
                                userInfo.put("isCustomer", userType.equals("Customer") ? "1" : "0");
                                userInfo.put("isVerified", userType.equals("Customer") ? "1" : "0");

                                // insert data
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users").child(user.getUid());
                                myRef.setValue(userInfo);

                                Toast.makeText(SignupActivity.this, "Registration successfully!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));


                                // Email verification
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SignupActivity.this, "Verfication Email has been sent!", Toast.LENGTH_LONG).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignupActivity.this, "Email not sent!", Toast.LENGTH_LONG).show();
                                    }
                                });

                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, "Registration Failed!" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                if (password.length() < 8) {
                    Toast.makeText(this, "Password should be at least 8 characters", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Password didn\'t match", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}

