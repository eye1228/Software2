package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    String id;
    String age;
    String name;
    String password;
    String phone;
    String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = new Intent(this.getIntent());
        id = intent.getStringExtra("id");
        age = intent.getStringExtra("age");
        name = intent.getStringExtra("name");
        password = intent.getStringExtra("password");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("loc");
        System.out.println(id + "/"+age+"/"+name+"/"+password+"/"+phone);

    }
    public void postingClick(View v){
        Intent intent = new Intent(getApplicationContext(),PostingActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("age",age);
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        intent.putExtra("phone",phone);
        intent.putExtra("loc",address);
        startActivity(intent);
    }
    public void calendarClick(View v){
        Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("age",age);
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        intent.putExtra("phone",phone);
        intent.putExtra("loc",address);
        startActivity(intent);
    }
    public void postviewClick(View v){
        Intent intent = new Intent(getApplicationContext(),PostviewActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("age",age);
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        intent.putExtra("phone",phone);
        intent.putExtra("loc",address);
        startActivity(intent);
    }
    public void locationClick(View v){
        Intent intent = new Intent(getApplicationContext(), locationActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("age",age);
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        intent.putExtra("phone",phone);
        intent.putExtra("loc",address);
        startActivity(intent);
    }
}
