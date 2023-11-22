package com.example.capstone;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.DAO.DeleteData;
import com.example.capstone.DAO.Insert_Json;
import com.example.capstone.VO.mysql_User;
import com.google.firebase.auth.FirebaseAuth;

public class MyinfoFragment extends Fragment {
    Button logout;
    String user_id;
    String Like_List;
    SharedPreferences pref;
    ContentValues values;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo,container,false);
        pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        user_id = pref.getString("Id","");
        Like_List = pref.getString("LL","");
        values = new ContentValues();

        logout = (Button) view.findViewById(R.id.buttonlogin);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                values.put("emailId",user_id);
                values.put("Like_List",Like_List);
                Insert_Json JSON = new Insert_Json(values);
                JSON.execute();
                // 사용자 로그아웃
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        return view;
    }

}