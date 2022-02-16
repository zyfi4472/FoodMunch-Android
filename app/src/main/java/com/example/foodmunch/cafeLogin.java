package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class cafeLogin extends AppCompatActivity{

    EditText editTextemail, editTextpassword;
    Button btnlogin;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_login);

        btnlogin = findViewById(R.id.login);
        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputEmail = editTextemail.getText().toString();
                String inputPassword = editTextpassword.getText().toString();

                if(inputEmail.equals("") | inputPassword.equals("")){
                    Toast.makeText(cafeLogin.this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
                }
                else {

                    mAuth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(cafeLogin.this, "Login failed" + e.getMessage() , Toast.LENGTH_LONG).show();
                        }
                    });


                   /* mAuth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),dashboard.class));
                                Toast.makeText(cafeLogin.this, "Login successful!", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(cafeLogin.this, "Login failed" + Objects.requireNonNull(task.getException()).getMessage() , Toast.LENGTH_LONG).show();
                            }
                        }
                    });*/

                }
            }
        });
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
                if(documentSnapshot.getString("isCafeOwner") != null)
                {
                    startActivity(new Intent(getApplicationContext(),dashboard.class));
                    Toast.makeText(cafeLogin.this, "Login successful!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    Toast.makeText(cafeLogin.this, "Login Failed!! \n You are not registered as cafe owner!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void signup(View view) {
        Intent i = new Intent(cafeLogin.this, cafeRegister.class);
        startActivity(i);
    }


    public void customerLogin(View view) {
        Intent i = new Intent(cafeLogin.this, login.class);
        startActivity(i);
    }
}

