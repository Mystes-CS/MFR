package com.example.motorfreerider;


import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class addMarker {
    GoogleSignInAccount account = GoogleSignInActivity.account;
    GoogleMap mMap;
    public addMarker() {
    }

    private class HttpAsyncTask_addMarker extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST_addMarker(urls[0], account.getId());
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //     Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private String POST_addMarker(String url, String string) {
        InputStream inputStream = null;
        String result = "Fail";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute);
           /* JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("Year", Integer.toString(RegisterActivity.mYear));
            jsonObject.accumulate("Month", Integer.toString(RegisterActivity.mMonth));
            jsonObject.accumulate("Date", Integer.toString(RegisterActivity.mDay));
            jsonObject.accumulate("Hour", Integer.toString(RegisterActivity.mHour));
            jsonObject.accumulate("Minute", Integer.toString(RegisterActivity.mMinute));
            jsonObject.accumulate("Start", new String(RegisterActivity.start.getBytes(), "UTF-8"));
            jsonObject.accumulate("Latitude",Double.toString(RegisterActivity.latitude));
            jsonObject.accumulate("Longitude",Double.toString(RegisterActivity.longitude));*/
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute)+"Register";
            //string = string + jsonObject.toString() + "Register";
            string += "addMarker";
            StringEntity se = new StringEntity(string, "UTF-8");
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");
            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "string");
            httpPost.setHeader("Content-type", "string; charset=utf-8");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
           // Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
           // Type type = new TypeToken<ArrayList<JsonObject>>() {
            //}.getType();
            //ArrayList<JsonObject> temp = gson.fromJson(result, type);
            //Log.w("temp:",result);
            //JSONObject jsnobject = new JSONObject(result);
            /*JSONArray jsonArray = jsnobject.;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                Log.w("result:",explrObject.get("start").toString());
            }*/
            //ArrayList<JSONObject> temp =
          /*  JSONObject JOB = new JSONObject(result);
            driver.setFirstName(JOB.optString("FirstName"));
            driver.setLastName(JOB.optString("LastName"));
            driver.setStat(JOB.optString("Stat"));
            driver.setLatitude(latitude);
            driver.setLongitude(longitude);*/
           // Log.w("result:",result);
            //   Log.w("result",stringFromServ);
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public String mark() throws ExecutionException, InterruptedException {
        return new HttpAsyncTask_addMarker().execute("http://140.121.197.213:8080/Servlet/ReceiveFile").get();
    }
}
