package com.example.capstone.DAO;

import android.content.ContentValues;
import android.os.AsyncTask;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Insert_Json extends AsyncTask<String, Void, String> {
    ContentValues values;
    public Insert_Json(ContentValues values){
        this.values = values;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            // PHP 서버의 URL
            String serverUrl = "http://43.201.98.166/Update_User_Like_List.php";
            StringBuffer sbParams = new StringBuffer();
            if(values == null){
                sbParams.append("");
            }else{
                boolean isAnd= false;
                String key;
                String value;

                for(Map.Entry<String,Object> parameter : values.valueSet()){
                    key = parameter.getKey();
                    value = parameter.getValue().toString();
                    System.out.println(key);
                    System.out.println(value);
                    if(isAnd){
                        sbParams.append("&");
                    }
                    sbParams.append(key).append("=").append(value);

                    if(!isAnd){
                        if(values.size() >=2){
                            isAnd=true;
                        }
                    }
                }
            }
            // URL 설정 및 연결
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");
            String strParams = sbParams.toString();
            // POST 데이터를 서버로 보냄
            OutputStream os = connection.getOutputStream();
            os.write(strParams.getBytes("UTF-8"));
            os.flush();
            os.close();
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
