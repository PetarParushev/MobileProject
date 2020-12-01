package com.example.mobilegameballcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordEditText1;
    Button createAccountButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordEditText1 = findViewById(R.id.passwordEditText1);
        createAccountButton = findViewById(R.id.createAccountButton);
        DB = new DBHelper(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String repass = passwordEditText1.getText().toString();

                if(email.equals("") || password.equals("") || repass.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if(password.equals(repass)){
                        Boolean checkEmail = DB.checkEmail(email);
                        if(checkEmail == false) {
                            Boolean insert = DB.insertData(email, password);
                            if(insert == true) {
                                Toast.makeText(RegistrationActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent
                                        (RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegistrationActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }

                emailEditText.getText().clear();
                passwordEditText.getText().clear();
                passwordEditText1.getText().clear();
            }
        });
    }
}
