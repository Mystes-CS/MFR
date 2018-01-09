package com.example.motorfreerider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RequestList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GetRequest getRequest;
    JSONArray jsonArr;
    List<user> userList;
    private MyAdapter adapter;
    private Button btnYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("hello","hello");
        setContentView(R.layout.activity_request_list);
        ListView listView = (ListView) findViewById(R.id.list);
        userList = new ArrayList<user>();
        //ListAdapter listAdapter = new ArrayAdapter<>(this,R.layout.activity_request_list,userList);
        getRequest = new GetRequest();
        try {
            String result = getRequest.GetRequest();
            Log.w("Request",result);
            jsonArr = new JSONArray(result);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                user _user = new user();
                _user.setFirstName(jsonObj.get("FirstName").toString());
                _user.setScore(jsonObj.get("Score").toString());
                _user.setCount(jsonObj.get("Count").toString());
                _user.setLastName(jsonObj.get("LastName").toString());
                _user.setFinishPlace(jsonObj.get("FinishPlace").toString());
                _user.setFinishPlaceLatitude(jsonObj.get("FinishPlaceLatitude").toString());
                _user.setFinishPlaceLongitude(jsonObj.get("FinishPlaceLongitude").toString());
                _user.setCarryPlace(jsonObj.get("CarryPlace").toString());
                _user.setCarryPlaceLatitude(jsonObj.get("CarryPlaceLatitude").toString());
                _user.setCarryPlaceLongitude(jsonObj.get("CarryPlaceLongitude").toString());
                _user.setDescription(jsonObj.get("Description").toString());
                _user.setIdentity(jsonObj.get("Identify").toString());
               // String time = String.format("%s/%s/%s  %s : %s", jsonObj.get("Year").toString(), jsonObj.get("Month").toString(), jsonObj.get("Date").toString(), jsonObj.get("Hour").toString(), jsonObj.get("Minute").toString());
                //_user.setTime(time);
                _user.setOwnerID(jsonObj.get("Owner ID").toString());
                _user.setID(jsonObj.get("ID").toString());
                _user.setMobileNumber(jsonObj.get("PhoneNumber").toString());
                _user.setEmail(jsonObj.get("Email").toString());
                userList.add(_user);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new MyAdapter(this, userList);
        adapter.getPassengerID();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "FUCK", Toast.LENGTH_SHORT).show();
    }
}
