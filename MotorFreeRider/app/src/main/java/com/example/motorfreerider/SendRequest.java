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

public class SendRequest {
    GoogleSignInAccount account = GoogleSignInActivity.account;
    private String ID;
    private user user;
    public SendRequest(String id, user _user) {
        this.ID = id;
        this.user = _user;
    }

    private class HttpAsyncTask__SendRequest extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... urls) {
            return POST__SendRequest(urls[0], ID);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean result) {
            //     Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean POST__SendRequest(String url, String string) {
        InputStream inputStream = null;
        boolean result =false;
        String description = MapsActivity.description_str;
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            //string = string + Integer.toString(RegisterActivity.mYear) +Integer.toString(RegisterActivity.mMonth)+Integer.toString(RegisterActivity.mDay)+Integer.toString(RegisterActivity.mHour)+Integer.toString(RegisterActivity.mMinute);
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("Description", new String(description.getBytes(), "UTF-8"));
            jsonObject.accumulate("LastName", user.getLastName());
            jsonObject.accumulate("FirstName", user.getFirstName());
            jsonObject.accumulate("PhoneNumber", user.getMobileNumber());
            jsonObject.accumulate("Identify",user.getIdentity());
            jsonObject.accumulate("ID", user.getID());
            jsonObject.accumulate("Email", user.getEmail());
            jsonObject.accumulate("Score", user.getScore());
            jsonObject.accumulate("Count", user.getCount());
            jsonObject.accumulate("CarryPlace", MapsActivity.carryPlace);
            jsonObject.accumulate("FinishPlace",MapsActivity.finishPlace);
            jsonObject.accumulate("CarryPlaceLongitude", Double.toString(MapsActivity.carryPlace_longitude));
            jsonObject.accumulate("CarryPlaceLatitude", Double.toString(MapsActivity.carryPlace_latitude));
            jsonObject.accumulate("FinishPlaceLongitude", Double.toString(MapsActivity.finishPlace_longitude));
            jsonObject.accumulate("FinishPlaceLatitude", Double.toString(MapsActivity.finishPlace_latitude));
            jsonObject.accumulate("Owner ID", ID);
            string = string + jsonObject.toString() + "SendRequest";
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
            String str = EntityUtils.toString(entity);
            if(str.equals("true")){
                result =true;
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public Boolean SendRequest() throws ExecutionException, InterruptedException {
        return new SendRequest.HttpAsyncTask__SendRequest().execute("http://140.121.197.213:8080/Servlet/ReceiveFile").get();
    }
}
