package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.capstone.DAO.RegisterRequest;
import com.example.capstone.VO.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.net.MalformedURLException;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText etEmail, etPwd, etPwdChk, etNickname; //입력 필드
    private Button btnRegister; // 회원가입 버튼
    private Button btnPrevious; //이전 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("capstone");

        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        etPwdChk = findViewById(R.id.et_pwdchk);
        etNickname = findViewById(R.id.et_nickname);

        btnRegister = (Button) findViewById(R.id.btn_register);
        btnPrevious = (Button) findViewById(R.id.btn_previous);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //회원가입 처리
                String strEmail = etEmail.getText().toString();
                String strPwd = etPwd.getText().toString();
                String strPwdChk = etPwdChk.getText().toString();
                String strNickname = etNickname .getText().toString();
                String e_mailPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9.]+$"; // 이메일 형식 패턴

                if(!Pattern.matches(e_mailPattern , strEmail)){
                    Toast.makeText(RegisterActivity.this , "이메일 형식을 확인하세요" , Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer strbuP1 = new StringBuffer(strPwd);
                StringBuffer strbuP2 = new StringBuffer(strPwdChk);

                if(strPwd.equals("")) {
                    Toast.makeText(RegisterActivity.this , "비밀번호를 입력하세요" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strbuP1.length() < 8){ // 최소 비밀번호 사이즈를 맞추기 위해서
                    Toast.makeText(RegisterActivity.this, "비밀번호는 최소 8자리 이상입니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strPwdChk.equals("")) {
                    Toast.makeText(RegisterActivity.this , "비밀번호 확인을 입력하세요" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strPwd.equals(strPwdChk)) {
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                UserAccount account = new UserAccount();

                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);
                                account.setNickname(strNickname);

                                ContentValues value = new ContentValues();
                                value.put("emailId",firebaseUser.getEmail());
                                //mysql에 저장함.
                                RegisterRequest request = new RegisterRequest(value);
                                request.execute();

                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            else {
                                try {
                                    task.getResult();
                                }
                                catch(Exception e) {
                                    e.printStackTrace();
                                    Log.d("Fail_register_email",e.getMessage());
                                    Toast.makeText(RegisterActivity.this, "이미 존재하는 이메일입니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "비밀번호가 서로 다릅니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}