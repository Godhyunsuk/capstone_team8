package com.example.capstone.DAO;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import com.example.capstone.VO.Coffee_Object;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class DrinkData {
    private String jsonString;
    public Coffee_Object[] CoffeeObject;

    //Gson gson = new Gson();
    public DrinkData(ContentValues values){
        JsonParse jsonParse = new JsonParse(values);      // AsyncTask 생성
        jsonParse.execute();     // AsyncTask 실행
    }



    public class JsonParse extends AsyncTask<String, Void, String> {
        String TAG;
        String url = "http://43.201.18.155/All_Brand.php";
        ContentValues values;
        public JsonParse(){}
        public JsonParse(ContentValues values){
            this.values = values;
        }

        @Override
        protected String doInBackground(String... strings) {    // execute의 매개변수를 받아와서 사용
            try {
                StringBuffer sbParams = new StringBuffer();
                if(values == null){
                    sbParams.append("");
                }else{
                    boolean isAnd = false;
                    String key;
                    String value;

                    for(Map.Entry<String,Object> parameter : values.valueSet()) {
                        key = parameter.getKey();
                        value = parameter.getValue().toString();
                        if(key.equals("brand_query")){
                            TAG = value;
                        }

                        if (isAnd) {
                            sbParams.append("&");
                        }
                        sbParams.append(key).append("=").append(value);
                        if (!isAnd) {
                            if (values.size() >= 2) {
                                isAnd = true;
                            }
                        }
                    }

                }

                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Accept-Charset","UTF-8");
                httpURLConnection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");
                String strParams = sbParams.toString();
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(strParams.getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseStatusCode = httpURLConnection.getResponseCode();

                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while((line= reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                    Log.d("values", sb.toString().trim());
                    jsonString = sb.toString().trim();
                    doParse(TAG);

                    if(httpURLConnection !=null){
                        httpURLConnection.disconnect();
                    }
                    return "success";
                }
                else {
                    if(httpURLConnection !=null){
                        httpURLConnection.disconnect();
                    }
                    // 요청 실패
                    return "Error: " + responseStatusCode;
                }
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                String errorString = e.toString();
                return errorString;
            }
        }

        @Override
        protected void onPostExecute(String fromdoInBackgroundString) { // doInBackgroundString에서 return한 값을 받음
            super.onPostExecute(fromdoInBackgroundString);
            Log.d(TAG,"SUCCESS");
        }
    }

    private void doParse(String TAG) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(TAG);
        Gson gson = new Gson();
        Coffee_Object[] coffs = new Coffee_Object[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            Coffee_Object cof = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), Coffee_Object.class);
            coffs[i] = cof;
        }
        CoffeeObject = coffs;
    }
}
