package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.capstone.DAO.SelectData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.util.ArrayList;

public class AngelActivity extends AppCompatActivity {
    SelectData sd = new SelectData("angelinus","angelinus");
    ImageView load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel);

        load = (ImageView)findViewById(R.id.imageView4);
        StorageReference storages = FirebaseStorage.getInstance().getReference().child("angelinus");
        //음료 아이디와 파이어베이스에 저장할 사진 이름을 같게할거임.
        //이건 내가 따로 저장해둔 사진 이름들이고, 나중에 음료 아이디로 바꿔줄 예정.
        String[] args = {"AG001","AG002","AG003"};

        for(String i : args){
            StorageReference storage = storages.child(i+".png");
            storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    System.out.println(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }

    }
}