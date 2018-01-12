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

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RequestListForPassenger extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GetRequest getRequest;
    JSONArray jsonArr;
    List<user> userList;
    GoogleSignInAccount account = GoogleSignInActivity.account;
    private PassengerAdapter adapter;
    private Button btnYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list_for_passenger);
        ListView listView = (ListView) findViewById(R.id.list_for_passenger);
        userList = new ArrayList<user>();
        //ListAdapter listAdapter = new ArrayAdapter<>(this,R.layout.activity_request_list,userList);
        ALL_ISend getISend = new ALL_ISend();
        try {
            String result = getISend.All_ISend();
            jsonArr = new JSONArray(result);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                user _user = new user();
                if (account.getId().equals(jsonObj.get("ID").toString())) {
                    _user.setID(jsonObj.get("Owner ID").toString());
                    GetRegister getRegister = new GetRegister(_user.getID());
                    result = getRegister.GetRegister();
                    Log.w("List",result);
                    JSONObject jsonOBJ = new JSONObject(result);
                    String Time = String.format("%s/%s/%s %s:%s", jsonOBJ.get("Year").toString(), jsonOBJ.get("Month").toString(), jsonOBJ.get("Date").toString(), jsonOBJ.get("Hour").toString(),jsonOBJ.get("Minute").toString());
                    _user.setStart(jsonOBJ.get("start").toString());
                    _user.setScore(jsonOBJ.get("Score").toString());
                    _user.setPassengerID(jsonObj.get("ID").toString());
                    _user.setDescription(jsonOBJ.get("Description").toString());
                    _user.setMobileNumber(jsonOBJ.getString("PhoneNumber"));
                    _user.setFirstName(jsonOBJ.get("FirstName").toString());
                    _user.setLastName(jsonOBJ.get("LastName").toString());
                    _user.setTime(Time);
                  //  Log.w("List",_user.getDescription());
                    userList.add(_user);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new PassengerAdapter(this, userList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
