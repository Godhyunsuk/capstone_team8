package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone.ConnectDB.SelectData;

public class ComposeActivity extends AppCompatActivity {
    SelectData sd = new SelectData("compose","compose");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        System.out.println(sd.CoffeeObject);
    }
}