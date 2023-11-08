package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import com.example.capstone.VO.Coffee_Object;

import java.util.*;

public class DrinkActivity extends AppCompatActivity {
    Button back;
    ImageView drinkImg,ivRmd1,ivRmd2,ivRmd3,ivRmd4;
    TextView drinkName,drinkNa,drinkSugar,drinkProtein,drinkCaffein,drinkFat,drinkKcal,tvrmd1,tvrmd2,tvrmd3,tvrmd4;
    DrinkRecommender dr;
    Recommend_selected rs;
    List<String> rsList;
    static Coffee_Object[] CoffeeObject;
    static Map<String,double[]> data = new HashMap<>();
    static String name;
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

        for(Coffee_Object c : CoffeeObject){
            System.out.println(c.getKcal());
            System.out.println(c.getProtein());
            double[] s = {c.getKcal(),c.getFat(),c.getProtein(),c.getNa(),c.getSuger(),c.getCaff()};
            data.put(c.getBname(),s);
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
                Intent intent = new Intent(DrinkActivity.this, BackActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
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

        String img = "@drawable/back";
        int iResId = getResources().getIdentifier( img, "drawable", this.getPackageName() );
        drinkImg.setImageResource(iResId);

        tvrmd1.setText(rsList.get(0));
        tvrmd2.setText(rsList.get(1));
        tvrmd3.setText(rsList.get(2));
        tvrmd4.setText(rsList.get(3));

        ivRmd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = tvrmd1.getText().toString();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        ivRmd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = tvrmd2.getText().toString();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        ivRmd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = tvrmd3.getText().toString();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        ivRmd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = tvrmd4.getText().toString();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

}
