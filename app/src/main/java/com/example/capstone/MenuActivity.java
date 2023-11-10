package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.capstone.DAO.DrinkData;
import com.example.capstone.VO.Coffee_Object;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {
    // SelectData 객체를 먼저 생성하여 초기화합니다.
    //COFFEE가 MAP<String,double[]>형태라 변수 잘 만들어서 쓰면 될듯
    String[] menuName;
    String[] menuId;
    int[] menuImage;
    GridView gridview;
    static String brand;
    static String brand_Code;
    Button backBtn;
    ContentValues values = new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //비동기화라서 데이터를 불러올때까지 잠시 기달려야함.
        //안그럼 데이터 불러오기전에 실행되서 값이 안생김.
        //우선 1초로 둿는데 더 빨리 풀리게 해도 될듯? 잘 모름.

        values.put("brand_query", brand);
        values.put("brand_where", brand_Code);
        DrinkData dd = new DrinkData(values);

        try{
            Thread.sleep(50);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        String layout = "@layout/activity_"+brand;
        int lResId = getResources().getIdentifier( layout, "layout", this.getPackageName() );
        super.onCreate(savedInstanceState);
        setContentView(lResId);
        backBtn = findViewById(R.id.back);
        menuName = new String[(dd.CoffeeObject).length];
        menuId = new String[dd.CoffeeObject.length];
        menuImage = new int[(dd.CoffeeObject).length];
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        int count = 0;
        for(Coffee_Object c : dd.CoffeeObject) {
            menuName[count] = c.getBname();
            menuId[count] = c.getD_id();
            count++;
        }
        //db에서 이미지 가져올시 수정될 코드
        String img = "@drawable/"+brand;
        int iResId = getResources().getIdentifier( img, "drawable", this.getPackageName() );

        for(int i=0; i<menuName.length; i++){
            menuImage[i] =iResId;
        }
        gridview = findViewById(R.id.gridView1);
        MenuAdapter menuAdapter = new MenuAdapter(MenuActivity.this, menuName, menuImage);
        gridview.setAdapter(menuAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MenuActivity.this, DrinkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                DrinkActivity.name=menuName[position];
                DrinkActivity.id=menuId[position];
                DrinkActivity.CoffeeObject = dd.CoffeeObject;
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

            if(inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            if(view == null) {
                view = inflater.inflate(R.layout.menu_layout, null);
            }

            ImageView backImage = view.findViewById(R.id.menuImage);
            TextView backName = view.findViewById(R.id.menuName);

            backImage.setImageResource(arrCoffeeImage[position]);
            backName.setText(arrCoffeeName[position]);

            return view;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}