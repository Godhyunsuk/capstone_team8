package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.capstone.ConnectDB.SelectData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        StorageReference storages = FirebaseStorage.getInstance().getReference().child("back");
        //음료 아이디와 파이어베이스에 저장할 사진 이름을 같게할거임.
        //이건 내가 따로 저장해둔 사진 이름들이고, 나중에 음료 아이디로 바꿔줄 예정.
        String[] args = {"4","13","15"};

        for(String i : args){
            LinearLayout layout = (LinearLayout) findViewById(R.id.q);
            ImageView iv = new ImageView(AngelActivity.this);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(iv);
            // ^^^^^^여기까진 사진이 제대로 나오는지 레이아웃으로 만들어본거임.

            StorageReference storage = storages.child(i+".PNG");
            storage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        //여기에서 정해진 ImageView에 값을 저장해서 보여줌.
                        Glide.with(AngelActivity.this).load(task.getResult()).into(iv);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        System.out.println(sd.CoffeeObject[0]);
    }
}