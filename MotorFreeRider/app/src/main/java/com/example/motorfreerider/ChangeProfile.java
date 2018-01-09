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
import org.json.JSONObject;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class ChangeProfile {
    private String Passenger, Owner, Attr;
    GoogleSignInAccount account = GoogleSignInActivity.account;

    public ChangeProfile(String Attr, String Passenger, String Owner) {
        this.Owner = Owner;
        this.Passenger = Passenger;
        this.Attr = Attr;
    }

    private class HttpAsyncTask_Change extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST_Change(urls[0], Attr, Owner, Passenger);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //     Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private String POST_Change(String url, String Attr, String Owner, String Passenger) {
        InputStream inputStream = null;
        String result = "Fail";
        try {
            String string="";
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute)+"Register";
            if (Attr.equals("Identify")) {
                string = account.getId() + Attr + "Change";
            }else if(Attr.equals("Car")){
                string = account.getId() + Attr + "Change";
            }
            else if (Attr.equals("multipleAgree")) {
                string = Owner + Passenger + Attr + "Change";
            }

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

            //   Log.w("result",stringFromServ);
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    private class HttpAsyncTask_addCar extends AsyncTask<String, Void, String> {
        private String CarType,CarNumber;
        public  HttpAsyncTask_addCar(String CarType,String CarNumber){
            this.CarNumber=CarNumber;
            this.CarType=CarType;
        }
        @Override
        protected String doInBackground(String... urls) {
            return POST_addCar(urls[0], CarNumber,CarType);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //     Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }
    private String POST_addCar(String url, String CarNumber, String CarType) {
        InputStream inputStream = null;
        String result = "Fail";
        try {
            String string="";
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute)+"Register";
            JSONObject jsonObject =new JSONObject();
            jsonObject.accumulate("CarNumber",CarNumber);
            jsonObject.accumulate("CarType",CarType);
            string = account.getId()+ jsonObject.toString()+ "addCar";


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

            //   Log.w("result",stringFromServ);
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    public String Change() throws ExecutionException, InterruptedException {
        return new HttpAsyncTask_Change().execute("http://140.121.197.213:8080/Servlet/ReceiveFile").get();
    }
    public String addCar(String CarType,String CarNumber) throws ExecutionException, InterruptedException {
        return new HttpAsyncTask_addCar(CarType,CarNumber).execute("http://140.121.197.213:8080/Servlet/ReceiveFile").get();
    }
}
