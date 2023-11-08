package com.example.capstone;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.DAO.SelectData;
import com.example.capstone.VO.Coffee_Object;


public class MegaActivity extends AppCompatActivity {
    //유선종이 추가
    SelectData sd = new SelectData("mega","mega");
    Button backBtn;
    ImageView MG001,MG002,MG003;
    TextView drinkName1,drinkName2,drinkName3;
    static String id = "";
    static String name="";


    protected void onCreate(Bundle savedInstanceState) {
        //유선종이 추가
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Coffee_Object[] C = sd.CoffeeObject;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mega);
        for(Coffee_Object c : C){
            System.out.println(c);
        }
        backBtn = (Button) findViewById(R.id.back);
        MG001 = (ImageView) findViewById(R.id.MG001);
        MG002 = (ImageView) findViewById(R.id.MG002);
        MG003 = (ImageView) findViewById(R.id.MG003);
        drinkName1 = (TextView) findViewById(R.id.drink_name1);
        drinkName2 = (TextView) findViewById(R.id.drink_name2);
        drinkName3 = (TextView) findViewById(R.id.drink_name3);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MegaActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
        MG001.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MegaActivity.this, DrinkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                id = "mg004";
                name = (String) drinkName1.getText();
            }
        });
        MG002.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MegaActivity.this, DrinkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                id = "mg005";
                name = (String) drinkName2.getText();
            }
        });
        MG003.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MegaActivity.this, DrinkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                id = "mg006";
                name = (String) drinkName3.getText();
            }
        });
    }

}