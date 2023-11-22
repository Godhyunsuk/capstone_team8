package com.example.capstone.DAO;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import com.example.capstone.VO.mysql_User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Map;

public class Check_and_Find extends AsyncTask<String, Void, String> {
    String value;
    String jsonString;
    public mysql_User user = new mysql_User();
    public Check_and_Find(String value){
        this.value = value;
        this.execute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            // PHP 서버의 URL
            String serverUrl = "http://43.201.98.166/Check_and_Find.php?emailId="+value;
            StringBuffer sbParams = new StringBuffer();

            // URL 설정 및 연결
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");
            System.out.println(url);
            // 응답 코드 확인
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line= reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                Log.d("values", sb.toString().trim());
                jsonString = sb.toString().trim();
                doParse();
                return "SUCCESS";
            } else {
                if(connection !=null){
                    connection.disconnect();
                }
                // 요청 실패
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
    }

    private void doParse() throws JSONException{
        JSONObject jsonObject = new JSONObject(jsonString);
        Gson gson = new Gson();
        user = gson.fromJson(String.valueOf(jsonObject),mysql_User.class);
        if((user.getEmailId())==null){
            user.setEmailId("");
            user.setLike_List(new ArrayList<>());
        }else{
            String inputs = user.getEmailId().replaceAll("[\\[\\]\" ]","");
            user.setEmailId(inputs);
            String[] arg = user.getEmailId().split(",");
            ArrayList<String> args = new ArrayList<>();
            for(String qwe : arg){
                args.add(qwe);
            }
            user.setLike_List(args);
            if(args.get(0) == ""){
                args.remove(0);
            }
        }

    }
}
