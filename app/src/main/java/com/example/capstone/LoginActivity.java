package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
                            //토큰을 생성하기 위해 진행. Header 부분을 시작.
                            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 서명 암호화 알고리즘.

                            //토큰의 몬료기간을 설정하기 위해 Data객체를 처리.
                            long curTime = System.currentTimeMillis();

                            JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ","JWT");
                            jwtBuilder.setIssuer("What_Coffee")
                                    .setSubject("test@test.com")
                                    .setAudience("nick name")
                                    .signWith(key);
                            System.out.println("1111111111111111111111");
                            System.out.println(jwtBuilder.compact());

                            String jws = Jwts.builder().setHeaderParam("typ","JWT")// 토큰 유형
                                    .setIssuer("What_Coffee")// 발행자
                                    .setSubject("test@test.com") // 제목
                                    .setAudience("nick name") // 토큰을 사용할 수신자
                                    .setExpiration(new Date(curTime + 3600)) // 만료 시간
                                    .setIssuedAt(new Date(curTime)) // 발급 시간
                                    .signWith(key).compact(); // 생성
                            SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("token",jws);
                            editor.commit();
                            System.out.println("222222222222222222222222");
                            System.out.println(jws);
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