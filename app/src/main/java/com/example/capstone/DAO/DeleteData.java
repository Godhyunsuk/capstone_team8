package com.example.capstone.DAO;

import android.os.AsyncTask;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class DeleteData extends AsyncTask<String, Void, String> {

    public DeleteData(){
        this.execute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            // PHP 서버의 URL
            String serverUrl = "http://43.201.98.166/Clear_Data.php";
            // URL 설정 및 연결
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
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
}