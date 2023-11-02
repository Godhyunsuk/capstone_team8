package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone.ConnectDB.SelectData;

public class AngelActivity extends AppCompatActivity {
    SelectData sd = new SelectData("angelinus","angelinus");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel);
        System.out.println(sd.COFFEE);

    }
}