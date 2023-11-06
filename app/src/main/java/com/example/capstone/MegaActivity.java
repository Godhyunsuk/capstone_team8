package com.example.capstone;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.ConnectDB.SelectData;
import com.example.capstone.VO.Coffee_Object;


public class MegaActivity extends AppCompatActivity {

    String[] coffeeName;
    int[] coffeeImage;
    GridView gridview;
    SelectData sd = new SelectData("mega","mega");

    protected void onCreate(Bundle savedInstanceState) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mega);

        coffeeName = new String[(sd.CoffeeObject).length];
        coffeeImage = new int[(sd.CoffeeObject).length];

        int count = 0;
        for (Coffee_Object c : sd.CoffeeObject) {
            coffeeName[count] = c.getBname();
            count++;
        }

        for (int i = 0; i < coffeeImage.length; i++) {
            coffeeImage[i] = R.drawable.mega;
        }

        gridview = findViewById(R.id.gridView1);
        MegaActivity.MenuAdapter menuAdapter = new MegaActivity.MenuAdapter(MegaActivity.this, coffeeName, coffeeImage);
        gridview.setAdapter(menuAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

        public class MenuAdapter extends BaseAdapter {

            Context context;
            LayoutInflater inflater;
            String[] arrCoffeeName;
            int[] arrCoffeeImage;

            public MenuAdapter(Context context, String[] arrCoffeeName, int[] arrCoffeeImage) {
                this.context = context;
                this.arrCoffeeName = arrCoffeeName;
                this.arrCoffeeImage = arrCoffeeImage;
            }

            @Override
            public int getCount() {
                return arrCoffeeName.length;
            }

            @Override
            public Object getItem(int position) {
                return arrCoffeeName[position];
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {

                if (inflater == null) {
                    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }

                if (view == null) {
                    view = inflater.inflate(R.layout.menu_layout, null);
                }

                ImageView backImage = view.findViewById(R.id.menuImage);
                TextView backName = view.findViewById(R.id.menuName);

                backImage.setImageResource(arrCoffeeImage[position]);
                backName.setText(arrCoffeeName[position]);

                return view;
            }
        }
    }

