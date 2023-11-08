package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone.DAO.SelectData;

public class HollysActivity extends AppCompatActivity {
    SelectData sd = new SelectData("hollus","hollus");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hollys);
        System.out.println(sd.CoffeeObject);
    }
}