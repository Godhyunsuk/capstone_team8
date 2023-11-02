package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.ConnectDB.SelectData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BackActivity extends AppCompatActivity {
    // SelectData 객체를 먼저 생성하여 초기화합니다.
    //COFFEE가 MAP<String,double[]>형태라 변수 잘 만들어서 쓰면 될듯
    String[] backName;
    int[] backImage;
    GridView gridview;
    SelectData sd = new SelectData("back","back");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //비동기화라서 데이터를 불러올때까지 잠시 기달려야함.
        //안그럼 데이터 불러오기전에 실행되서 값이 안생김.
        //우선 1초로 둿는데 더 빨리 풀리게 해도 될듯? 잘 모름.
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        backName = new String[(sd.COFFEE).size()];
        backImage = new int[(sd.COFFEE).size()];

        Iterator<String> keys = sd.COFFEE.keySet().iterator();

        int count = 0;
        while(keys.hasNext()) {
            backName[count] = keys.next();
            count++;
        }

        for(int i=0; i<backName.length; i++){
            backImage[i] = R.drawable.back;
        }
        gridview = findViewById(R.id.gridView1);
        BackAdapter backAdapter = new BackAdapter(BackActivity.this, backName, backImage);
        gridview.setAdapter(backAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(BackActivity.this, DrinkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                DrinkActivity.name=backName[position];
                DrinkActivity.data = new HashMap<String,double[]>(sd.COFFEE);
            }
        });
    }

    public class BackAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        String[] arrBackName;
        int[] arrBackImage;

        public BackAdapter(Context context, String[] arrBackName, int[] arrBackImage) {
            this.context = context;
            this.arrBackName = arrBackName;
            this.arrBackImage = arrBackImage;
        }

        @Override
        public int getCount() {
            return arrBackName.length;
        }

        @Override
        public Object getItem(int position) {
            return arrBackName[position];
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
                view = inflater.inflate(R.layout.back_layout, null);
            }

            ImageView backImage = view.findViewById(R.id.backImage);
            TextView backName = view.findViewById(R.id.backName);

            backImage.setImageResource(arrBackImage[position]);
            backName.setText(arrBackName[position]);

            return view;
        }
    }
}