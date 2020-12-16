package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PostviewActivity extends AppCompatActivity {
    String id;
    String age;
    String name;
    String password;
    String phone;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postview);
        Intent intent = new Intent();
        id = intent.getStringExtra("id");
        age = intent.getStringExtra("age");
        name = intent.getStringExtra("name");
        password = intent.getStringExtra("password");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("loc");
    }
}

