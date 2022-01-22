package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class register extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextfullname, editTextemail, editTextphone, editTextpassword;
    private Button register;
    CountryCodePicker ccp;

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
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(editTextphone);
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

    private void register()
    {
        String fullName = editTextfullname.getText().toString().trim();
        String email = editTextemail.getText().toString().trim();
        String phone = editTextphone.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if(fullName.isEmpty())
        {
            editTextfullname.setError("Full name is required");
            editTextfullname.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            editTextemail.setError("Email is required");
            editTextemail.requestFocus();
            return;
        }
        if(phone.isEmpty())
        {
            editTextphone.setError("Phone is required");
            editTextphone.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextemail.setError("Please provide valid email");
            editTextemail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editTextpassword.setError("Password is required");
            editTextpassword.requestFocus();
            return;
        }
        if(password.length() < 6)
        {
            editTextpassword.setError("Minimum password length should be 6 characters");
            editTextpassword.requestFocus();
            return;
        }

        //Open OTP verification activity.

        Intent i = new Intent(register.this,verifyPhone.class);
        i.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
        i.putExtra("fullName",editTextfullname.getText().toString());
        i.putExtra("email",editTextemail.getText().toString());
        i.putExtra("pass",editTextpassword.getText().toString());
        startActivity(i);

    }
}