package com.example.motorfreerider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import android.widget.Toast;
import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class client extends Activity {
    GoogleSignInAccount account = GoogleSignInActivity.account;
    user person;

  /*  public static String POST(String url, user person) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("ID", person.getID());
            jsonObject.accumulate("Email", person.getEmail());
            jsonObject.accumulate("AccountType", person.getAccount());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            person = new user();
            person.setID(account.getId());
            person.setEmail(account.getEmail());
            person.setAccount(account.getAccount().type.toString());

            return POST(urls[0], person);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }*/
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    private class HttpAsyncTask_First extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... urls) {
             return POST_First(urls[0],account.getId());
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean result) {
       //     Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }
    public static boolean POST_First(String url, String string) {
        InputStream inputStream = null;
        boolean result =true;
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            StringEntity se = new StringEntity(string);
            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "string");
            httpPost.setHeader("Content-type", "string");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            HttpEntity entity = httpResponse.getEntity();
            String stringFromServ  = EntityUtils.toString(entity);
         //   Log.w("result",stringFromServ);
            if(stringFromServ != null)
                if(stringFromServ.equals("true")){
                    result=true;
                }
            else
                result = false;

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        // 11. return result
        return result;
    }
    private class HttpAsyncTask_Check extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST_Check(urls[0],account.getId()+"Check");
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //     Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }
    public static String POST_Check(String url, String string) {
        InputStream inputStream = null;
        String result ="";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            StringEntity se = new StringEntity(string);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "string");
            httpPost.setHeader("Content-type", "string");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            HttpEntity entity = httpResponse.getEntity();
            String stringFromServ  = EntityUtils.toString(entity);
            //   Log.w("result",stringFromServ);
            if(stringFromServ != null)
                result = stringFromServ;
            else{
                result=null;
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    private class HttpAsyncTask_InCar extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST_Check(urls[0],account.getId()+"InCar");
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //     Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }
    public static String POST_InCar(String url, String string) {
        InputStream inputStream = null;
        String result ="";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            StringEntity se = new StringEntity(string);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "string");
            httpPost.setHeader("Content-type", "string");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            HttpEntity entity = httpResponse.getEntity();
            String stringFromServ  = EntityUtils.toString(entity);
            //   Log.w("result",stringFromServ);
            if(stringFromServ != null)
                result = stringFromServ;
            else{
                result=null;
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
     public boolean firstLogin() throws ExecutionException, InterruptedException {
       return new HttpAsyncTask_First().execute("http://140.121.197.213:8080/Servlet/ReceiveFile").get();
    }
    public String  checkIdentify() throws ExecutionException, InterruptedException {
        return new HttpAsyncTask_Check().execute("http://140.121.197.213:8080/Servlet/ReceiveFile").get();
    }
    public String  checkInCar() throws ExecutionException, InterruptedException {
        return new HttpAsyncTask_InCar().execute("http://140.121.197.213:8080/Servlet/ReceiveFile").get();
    }
}