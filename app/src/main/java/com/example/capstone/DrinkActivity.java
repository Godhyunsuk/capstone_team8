package com.example.capstone;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.capstone.DAO.CheckNO;
import com.example.capstone.DAO.CheckOK;
import com.example.capstone.DAO.DrinkData;
import com.example.capstone.VO.Coffee_Object;
import com.example.capstone.VO.mysql_User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DrinkActivity extends AppCompatActivity {
    Button back;
    ImageView drinkImg,ivRmd1,ivRmd2,ivRmd3,ivRmd4;
    TextView drinkName,drinkNa,drinkSugar,drinkProtein,drinkCaffein,drinkFat,drinkKcal,tvrmd1,tvrmd2,tvrmd3,tvrmd4;
    DrinkRecommender dr;
    Recommend_selected rs;
    List<String> rsList;
    CheckBox favorite;
    static mysql_User User = HomeFragment.User;
    static String id;
    ImageView[] ivRmds;
    TextView[] tvrmds;
    static Coffee_Object[] CoffeeObject;
    Map<String,double[]> data;
    static String name;
    CheckOK OK;
    CheckNO NO;
    static String IMG_URL = "http://43.201.18.155/test/";
    static String extend_name = ".png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        back = (Button)findViewById(R.id.backDrink);
        drinkImg = (ImageView) findViewById(R.id.drinkImg);
        drinkName = (TextView) findViewById(R.id.drinkName);
        drinkFat = (TextView) findViewById(R.id.drinkFat);
        drinkSugar = (TextView) findViewById(R.id.drinkSugar);
        drinkNa = (TextView) findViewById(R.id.drinkNa);
        drinkProtein = (TextView) findViewById(R.id.drinkProtein);
        drinkCaffein = (TextView) findViewById(R.id.drinkCaffein);
        drinkKcal = (TextView) findViewById(R.id.drinkKcal);
        tvrmd1 = (TextView) findViewById(R.id.tvRcmd1);
        tvrmd2 = (TextView) findViewById(R.id.tvRcmd2);
        tvrmd3 = (TextView) findViewById(R.id.tvRcmd3);
        tvrmd4 = (TextView) findViewById(R.id.tvRcmd4);
        ivRmd1 = (ImageView) findViewById(R.id.ivRcmd1);
        ivRmd2 = (ImageView) findViewById(R.id.ivRcmd2);
        ivRmd3 = (ImageView) findViewById(R.id.ivRcmd3);
        ivRmd4 = (ImageView) findViewById(R.id.ivRcmd4);
        ivRmds = new ImageView[]{ivRmd1, ivRmd2, ivRmd3, ivRmd4};
        tvrmds = new TextView[]{tvrmd1, tvrmd2, tvrmd3, tvrmd4};
        favorite = (CheckBox) findViewById(R.id.favorite);
        Map<String,String> findId = new HashMap<>();
        data = new HashMap<>();
        Log.d("DrinkActivity",User.getEmailId());
        System.out.println("Drink");
        System.out.println(HomeFragment.User.getLike_List());
        for(Coffee_Object c : CoffeeObject){
            double[] s = {c.getKcal(),c.getFat(),c.getProtein(),c.getNa(),c.getSuger(),c.getCaff()};
            data.put(c.getBname(),s);
            findId.put(c.getBname(),c.getD_id());
        }

        double[] values = data.get(name);
        try {
            dr = new DrinkRecommender(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            rs = new Recommend_selected(dr,data,name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rsList = rs.list_out();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String kcal = String.format("%.2f", values[0]);
        String fat = String.format("%.2f", values[1]);
        String protein = String.format("%.2f", values[2]);
        String na = String.format("%.2f", values[3]);
        String sugar = String.format("%.2f", values[4]);
        String caff = String.format("%.2f", values[5]);

        drinkName.setText(name);
        drinkFat.setText(fat);
        drinkSugar.setText(sugar);
        drinkNa.setText(na);
        drinkProtein.setText(protein);
        drinkCaffein.setText(caff);
        drinkKcal.setText(kcal);

        try {
            URL uri = new URL(IMG_URL+id+extend_name);
            Glide.with(DrinkActivity.this).load(uri).into(drinkImg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        for (int i=0;i<ivRmds.length;i++){
            TextView tvrmd = tvrmds[i];
            tvrmd.setText(rsList.get(i));
            try {
                URL uri = new URL(IMG_URL+findId.get(rsList.get(i))+extend_name);
                Glide.with(DrinkActivity.this).load(uri).into(ivRmds[i]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            int finalI = i;
            ivRmds[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent intent = new Intent(DrinkActivity.this, DrinkActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    name=tvrmds[finalI].getText().toString();
                    id=findId.get(rsList.get(finalI));
                }
            });
        }

        if(HomeFragment.User.getLike_List().contains(id)){
            favorite.setChecked(true);
        }
        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(!HomeFragment.User.getLike_List().contains(id)){
                        ContentValues values =new ContentValues();
                        values.put("d_id",id);
                        //OK = new CheckOK(values);
                        //OK.execute();
                        HomeFragment.User.addLike_List(id);

                    }
                }else{
                    if(HomeFragment.User.getLike_List().contains(id)){
                        ContentValues values =new ContentValues();
                        values.put("d_id",id);
                        //NO = new CheckNO(values);
                        //NO.execute();
                        HomeFragment.User.removeLike_List(id);
                    }
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
