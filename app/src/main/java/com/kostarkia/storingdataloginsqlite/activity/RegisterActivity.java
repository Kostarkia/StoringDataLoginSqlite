package com.kostarkia.storingdataloginsqlite.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kostarkia.storingdataloginsqlite.R;
import com.kostarkia.storingdataloginsqlite.database.MyDatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextSurName, editTextPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextSurName = findViewById(R.id.editTextSurName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(view -> register());
    }

    public void register() {
        String firstName = editTextFirstName.getText().toString();
        String surName = editTextSurName.getText().toString();
        String password = editTextPassword.getText().toString();

        MyDatabaseHelper myDB = new MyDatabaseHelper(RegisterActivity.this);
        boolean isUserExits = myDB.checkRegister(firstName);

        if (editTextFirstName.getText().toString().length() == 0 || editTextSurName.getText().toString().length() == 0 || editTextPassword.getText().toString().length() == 0) {
            {
                Toast.makeText(RegisterActivity.this, getString(R.string.stringisnotnull), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (!isUserExits) {
                myDB.addUser(firstName.trim(), surName.trim(), password.trim());

                editTextFirstName.setText("");
                editTextSurName.setText("");
                editTextPassword.setText("");
            }
        }


    }


}