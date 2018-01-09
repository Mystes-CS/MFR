package com.example.motorfreerider;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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
import java.util.concurrent.ExecutionException;

public class GetRequest {
    GoogleSignInAccount account = GoogleSignInActivity.account;
    public GetRequest() {
    }

    private class HttpAsyncTask_GetRequest extends AsyncTask<String, Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST_GetRequest(urls[0], account.getId());
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //     Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private String POST_GetRequest(String url, String string) {
        InputStream inputStream = null;
        String result = "Fail";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute)+"Register";
            string = string + "GetRequest";
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
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public String GetRequest() throws ExecutionException, InterruptedException {
        return new HttpAsyncTask_GetRequest().execute("http://140.121.197.213:8080/Servlet/ReceiveFile").get();
    }
}
