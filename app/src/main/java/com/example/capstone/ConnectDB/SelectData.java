package com.example.capstone.ConnectDB;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SelectData {
    private String jsonString;
    public Map<String,double[]> COFFEE = new HashMap<>();
    //Gson gson = new Gson();
    public SelectData(String File, String Brand){
        JsonParse jsonParse = new JsonParse(Brand);      // AsyncTask 생성
        jsonParse.execute("http://43.201.98.166/"+File+".php");     // AsyncTask 실행
    }

    public class JsonParse extends AsyncTask<String, Void, String> {
        String TAG;
        public JsonParse(){}
        public JsonParse(String TAG){
            this.TAG = TAG;
        }

        @Override
        protected String doInBackground(String... strings) {    // execute의 매개변수를 받아와서 사용
            String url = strings[0];
            System.out.println(url);
            try {
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG, sb.toString().trim());

                jsonString = sb.toString().trim();        // 받아온 JSON의 공백을 제거
                doParse(TAG);
                return "success";
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                String errorString = e.toString();
                return errorString;
            }
        }

        //이게 마지막에 실행되다 보니깐 다 실행된 뒤에 호출됨.
        //요너석이 문제였던 거임.
        @Override
        protected void onPostExecute(String fromdoInBackgroundString) { // doInBackgroundString에서 return한 값을 받음
            super.onPostExecute(fromdoInBackgroundString);
            Log.d(TAG,COFFEE.toString());
        }
    }

    private void doParse(String TAG) {
        Map<String,double[]> R_Map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String NAME = item.getString("bname");
                double[] LIST = new double[6];
                LIST[0] = item.getDouble("kcal");
                LIST[1] = item.getDouble("fat");
                LIST[2] = item.getDouble("protein");
                LIST[3] = item.getDouble("Na");
                LIST[4] = item.getDouble("suger");
                LIST[5] = item.getDouble("Caff");
                R_Map.put(NAME,LIST);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        COFFEE = R_Map;
    }
}
