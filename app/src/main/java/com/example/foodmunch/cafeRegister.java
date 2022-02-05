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

public class cafeRegister extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextcafeName, editTextownername, editTextoEmail, editTextoPhone, editTextoPassword;
    private Button register;
    CountryCodePicker ccp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_register);

        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.signup);
        register.setOnClickListener(this);


        editTextownername = (EditText) findViewById(R.id.ownerName);
        editTextcafeName = (EditText) findViewById(R.id.cafeName);
        editTextoEmail = (EditText) findViewById(R.id.oEmail);
        editTextoPhone = (EditText) findViewById(R.id.oPhone);
        editTextoPassword = (EditText) findViewById(R.id.oPassword);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(editTextoPhone);
    }
    public void login(View view) {
        Intent i = new Intent(cafeRegister.this , cafeLogin.class);
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
        String ownerName = editTextownername.getText().toString().trim();
        String cafeName = editTextcafeName.getText().toString().trim();
        String oEmail = editTextoEmail.getText().toString().trim();
        String oPhone = editTextoPhone.getText().toString().trim();
        String oPassword = editTextoPassword.getText().toString().trim();

        if(ownerName.isEmpty())
        {
            editTextownername.setError("Owner name is required");
            editTextownername.requestFocus();
            return;
        }
        if(ownerName.length()>30)
        {
            editTextownername.setError("Owner name length must be less than 30 characters");
            editTextownername.requestFocus();
            return;
        }
        if(cafeName.isEmpty())
        {
            editTextcafeName.setError("Cafe name is required");
            editTextcafeName.requestFocus();
            return;
        }
        if(oEmail.isEmpty())
        {
            editTextoEmail.setError("Email is required");
            editTextoEmail.requestFocus();
            return;
        }
        if(oPhone.isEmpty())
        {
            editTextoPhone.setError("Phone is required");
            editTextoPhone.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(oEmail).matches())
        {
            editTextoEmail.setError("Please provide valid email");
            editTextoEmail.requestFocus();
            return;
        }
        if(oPassword.isEmpty())
        {
            editTextoPassword.setError("Password is required");
            editTextoPassword.requestFocus();
            return;
        }
        if(oPassword.length() < 6)
        {
            editTextoPassword.setError("Minimum password length should be 6 characters");
            editTextoPassword.requestFocus();
            return;
        }

        //Open OTP verification activity.

        Intent i = new Intent(cafeRegister.this,ownerVerifyPhone.class);
        i.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
        i.putExtra("ownerName",editTextownername.getText().toString());
        i.putExtra("cafeName",editTextcafeName.getText().toString());
        i.putExtra("email",editTextoEmail.getText().toString());
        i.putExtra("pass",editTextoPassword.getText().toString());
        startActivity(i);

    }

}