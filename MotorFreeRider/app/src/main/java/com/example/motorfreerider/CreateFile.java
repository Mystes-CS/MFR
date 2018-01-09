package com.example.motorfreerider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class CreateFile extends Activity {

    private user person;

    public  CreateFile(user user) {
        this.person = user;
        new HttpAsyncTask().execute("http://140.121.197.213:8080/Servlet/ReceiveFile");
    }

    public static String POST(String url, user person) {
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
            jsonObject.accumulate("LastName", new String(person.getLastName().getBytes(), "UTF-8"));
            jsonObject.accumulate("FirstName", new String(person.getFirstName().getBytes(), "UTF-8"));
            jsonObject.accumulate("PhoneNumber", person.getMobileNumber());
            jsonObject.accumulate("Identify",person.getIdentity());
            jsonObject.accumulate("ID", person.getID());
            jsonObject.accumulate("Email", person.getEmail());
            jsonObject.accumulate("Sex",person.getSex());
            jsonObject.accumulate("AccountType", person.getAccount());
            jsonObject.accumulate("Score", person.getScore());
            jsonObject.accumulate("Count", person.getCount());
            jsonObject.accumulate("CarType", person.getCarType());
            jsonObject.accumulate("CarNumber", person.getCarNumber());
            jsonObject.accumulate("Now",person.getNow());
            // 4. convert JSONObject to JSON to String
            json = person.getID()+jsonObject.toString()+"Create";
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);
            Log.w("result:",json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json, "UTF-8");
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

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST(urls[0], person);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    /*    private boolean validate(){
            if(etName.getText().toString().trim().equals(""))
                return false;
            else if(etCountry.getText().toString().trim().equals(""))
                return false;
            else if(etTwitter.getText().toString().trim().equals(""))
                return false;
            else
                return true;
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
}