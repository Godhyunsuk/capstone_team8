package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone.DAO.Check_and_Find;
import com.example.capstone.DAO.RegisterRequest;
import com.example.capstone.Unit.Session_Unit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText etEmail, etPwd, etPwdChk, etNickname; //입력 필드
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("capstone");

        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);

        //로그인 버튼 클릭시
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = etEmail.getText().toString();
                String strPwd = etPwd.getText().toString();
                String e_mailPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9.]+$";

                if(!Pattern.matches(e_mailPattern , strEmail)){
                    Toast.makeText(LoginActivity.this , "이메일 형식을 확인하세요" , Toast.LENGTH_SHORT).show();
                    return;
                }

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            Session_Unit JWS = new Session_Unit(strEmail);
                            SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();

                            Check_and_Find CAF = new Check_and_Find(strEmail);
                            try{
                                Thread.sleep(500);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                            editor.putString("Id",strEmail);
                            editor.commit();

                            HomeFragment.User= CAF.user;
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        //회원가입 버튼 클릭 시
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}