package com.example.motorfreerider;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class GetForEvery {
    String identify;
    GoogleSignInAccount account = GoogleSignInActivity.account;

    public GetForEvery(String identify) {
        this.identify = identify;
    }

    private class HttpAsyncTask_getForEvery extends AsyncTask<String, Void, user> {
        @Override
        protected user doInBackground(String... urls) {
            return POST_getForEvery(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(user result) {
            //     Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private user POST_getForEvery(String url) {
        InputStream inputStream = null;
        String result = "Fail",string;
        user _user = new user();
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute)+"Register";
            string ="GetForEvery";
            Log.w("result111", string);
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
            Log.w("result111", result);
            JSONArray JOB = new JSONArray(result);
            JSONObject jsonObject = new JSONObject();
            if (identify.equals("Owner")) {
                jsonObject = (JSONObject) JOB.get(0);
                _user.setFirstName(jsonObject.optString("FirstName"));
                _user.setLastName(jsonObject.optString("LastName"));
                _user.setScore(jsonObject.optString("Score"));
                _user.setCount(jsonObject.optString("Count"));
                _user.setIdentity(jsonObject.optString("Identify"));
                _user.setID(jsonObject.optString("ID"));
                _user.setMobileNumber(jsonObject.optString("PhoneNumber"));
                _user.setEmail(jsonObject.optString("Email"));
                _user.setCarryPlace(jsonObject.optString("CarryPlace"));
                _user.setCarryPlaceLatitude(jsonObject.optString("CarryPlaceLatitude"));
                _user.setCarryPlaceLongitude(jsonObject.optString("CarryPlaceLongitude"));
                _user.setFinishPlace(jsonObject.optString("FinishPlace"));
                _user.setFinishPlaceLatitude(jsonObject.optString("FinishPlaceLatitude"));
                _user.setFinishPlaceLongitude(jsonObject.optString("FinishPlaceLongitude"));
                _user.setDescription(jsonObject.optString("Description"));
                _user.setOwnerID(jsonObject.optString("OwnerID"));
            } else if (identify.equals("Passenger")) {
                jsonObject = (JSONObject) JOB.get(1);
                _user.setFirstName(jsonObject.optString("FirstName"));
                _user.setLastName(jsonObject.optString("LastName"));
                _user.setScore(jsonObject.optString("Score"));
                _user.setCount(jsonObject.optString("Count"));
                _user.setIdentity(jsonObject.optString("Identify"));
                _user.setID(jsonObject.optString("ID"));
                _user.setMobileNumber(jsonObject.optString("PhoneNumber"));
                _user.setCarryPlaceLongitude(jsonObject.optString("longitude"));
                _user.setCarryPlaceLatitude(jsonObject.optString("latitude"));
                _user.setStart(jsonObject.optString("start"));
                String time = String.format("%s/%s/%s %s:%s", jsonObject.optString("Year"), jsonObject.optString("Month"), jsonObject.optString("Date"), jsonObject.optString("Hour"), jsonObject.optString("Minute"));
                _user.setTime(time);//5
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _user;
    }

    public user getForEvery() throws ExecutionException, InterruptedException {
        return new HttpAsyncTask_getForEvery().execute("http://140.121.197.213:8080/Servlet/ReceiveFile").get();
    }
}
