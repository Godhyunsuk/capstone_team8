package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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

                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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