package com.kostarkia.storingdataloginsqlite.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kostarkia.storingdataloginsqlite.R;

public class ProfileActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView textUsername = findViewById(R.id.textUsername);

        SharedPreferences preferences = this.getSharedPreferences("com.kostarkia.storingdataloginsqlite", MODE_PRIVATE);

        String getUser = preferences.getString("userName", null);

        textUsername.setText(getUser);

    }


}