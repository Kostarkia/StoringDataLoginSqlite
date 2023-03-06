package com.kostarkia.storingdataloginsqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kostarkia.storingdataloginsqlite.R;
import com.kostarkia.storingdataloginsqlite.database.MyDatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUserName, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private SharedPreferences prefences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = findViewById(R.id.editTextFirstName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        prefences = this.getSharedPreferences("com.kostarkia.storingdataloginsqlite", MODE_PRIVATE);

        String getUser = prefences.getString("userName", null);//son giriş yapan kişinin userName ini çeker
        editTextUserName.setText(getUser);

        buttonLogin.setOnClickListener(view -> login(editTextUserName.getText().toString(), editTextPassword.getText().toString()));

        buttonRegister.setOnClickListener(view -> register());
    }

    public void login(String userName, String password) {

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {//userName ve password un boş olup olmadığını kontrol eder

            MyDatabaseHelper db = new MyDatabaseHelper(LoginActivity.this);
            boolean isUserExits = db.checkUser(userName, password);

            if (isUserExits) {
                Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();

                prefences.edit().putString("userName", userName).apply(); //Son giriş yapan kullanıcının Kullanıcı adını saklar

                editTextUserName.setText("");
                editTextPassword.setText("");

                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.unsuccessful), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.userandpasswordisnotnull), Toast.LENGTH_SHORT).show();
        }
    }

    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class); // Kayıt ol ekranına yönlendirir
        startActivity(intent);
    }
}