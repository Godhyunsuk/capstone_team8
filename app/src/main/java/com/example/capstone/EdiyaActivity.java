package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone.ConnectDB.SelectData;

public class EdiyaActivity extends AppCompatActivity {
    SelectData sd = new SelectData("ediya","ediya");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ediya);
        System.out.println(sd.COFFEE);
    }
}