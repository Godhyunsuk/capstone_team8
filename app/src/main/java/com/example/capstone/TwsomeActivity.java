package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone.DAO.SelectData;

public class TwsomeActivity extends AppCompatActivity {
    SelectData sd = new SelectData("twosome","twosome");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twsome);
        System.out.println(sd.CoffeeObject);
    }
}