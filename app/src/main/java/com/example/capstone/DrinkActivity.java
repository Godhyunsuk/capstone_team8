package com.example.capstone;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class DrinkActivity extends AppCompatActivity {
    Button back;
    ImageView drinkImg;
    TextView drinkName,drinkNa,drinkSugar,drinkProtein,drinkCaffein,drinkFat,drinkKcal,tvrmd1,tvrmd2,tvrmd3;
    private List<String[]> loadData() throws IOException, CsvException {
        AssetManager assetManager = DrinkActivity.this.getAssets();
        InputStream inputStream = assetManager.open("coffee.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, "UTF-8"));

        List<String[]> allContent = (List<String[]>) csvReader.readAll();
        return allContent;
    }

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
        List<String[]> csvList = null;
        String id = MegaActivity.id;
        String name = MegaActivity.name;

        Recommend_selected rs = new Recommend_selected();
        try {
            csvList = loadData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        DrinkRecommender dr = null;
        try {
            dr = new DrinkRecommender(csvList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rs.setDrinkRecommeder(dr, csvList);
        List<String> rlist = null;
        try {
            rlist = rs.list_out();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] data = csvList.get(0);
        double[] sData_info = rs.sDataInfo(id);
        String Fat = String.format("%.2f", sData_info[0]);
        String Sugar = String.format("%.2f", sData_info[1]);
        String Na = String.format("%.2f", sData_info[2]);
        String Protein = String.format("%.2f", sData_info[3]);
        String Caffeine = String.format("%.2f", sData_info[4]);
        String Kcal = String.format("%.2f", sData_info[5]);

        drinkName.setText(name);
        drinkFat.setText(Fat);
        drinkSugar.setText(Sugar);
        drinkNa.setText(Na);
        drinkProtein.setText(Protein);
        drinkCaffein.setText(Caffeine);
        drinkKcal.setText(Kcal);

        tvrmd1.setText(rlist.get(0)+", ");
        tvrmd2.setText(rlist.get(1)+", ");
        tvrmd3.setText(rlist.get(3));
        String img = "@drawable/"+id;
        int iResId = getResources().getIdentifier( img, "drawable", this.getPackageName() );
        drinkImg.setImageResource(iResId);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinkActivity.this, MegaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

}
