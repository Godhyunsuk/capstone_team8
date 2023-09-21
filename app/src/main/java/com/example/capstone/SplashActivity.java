package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceStare) {
        super.onCreate(savedInstanceStare);
        setContentView(R.layout.splash_activity);

        //잠시 기달린 후 다음 페이지로 넘김
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }


    //이거 없애도 될듯? 없어도 잘 작동됨.
    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
