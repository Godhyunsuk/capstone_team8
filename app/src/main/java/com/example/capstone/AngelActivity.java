package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstone.DAO.SelectData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.apache.commons.lang3.ArrayUtils;

import java.net.URI;
import java.util.ArrayList;

public class AngelActivity extends AppCompatActivity {
    SelectData sd = new SelectData("angelinus","angelinus");
    String[] args = {"AG001","AG002","AG003"};
    int[] a = {1,2,3};
    StorageReference storages = FirebaseStorage.getInstance().getReference().child("angelinus");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel);

        GridView gridView= (GridView)findViewById(R.id.gridView1);


        //음료 아이디와 파이어베이스에 저장할 사진 이름을 같게할거임.
        //이건 내가 따로 저장해둔 사진 이름들이고, 나중에 음료 아이디로 바꿔줄 예정.
        MenuAdapter menuAdapter = new MenuAdapter(AngelActivity.this,args,a);
        gridView.setAdapter(menuAdapter);


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
            StorageReference storage = storages.child(arrCoffeeName[position]+".png");
            storage.getDownloadUrl().addOnSuccessListener(uri ->  {
                Glide.with(context).load(uri).into(backImage);
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            backName.setText(arrCoffeeName[position]);

            return view;
        }

    }
}