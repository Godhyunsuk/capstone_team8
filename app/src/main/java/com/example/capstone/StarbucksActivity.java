package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone.DAO.SelectData;

public class StarbucksActivity extends AppCompatActivity {
    SelectData sd = new SelectData("starbucks","starbucks");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starbucks);
        System.out.println(sd.CoffeeObject);
    }
}