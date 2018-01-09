package com.example.motorfreerider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView sex,name,identify,score;
    private user us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sex = (TextView)findViewById(R.id.profileSex);
        name = (TextView)findViewById(R.id.profileName);
        identify =(TextView)findViewById(R.id.profileIdentify);
        score = (TextView)findViewById(R.id.profileScore);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setTitle("個人資訊");
        GetProfile getProfile = new GetProfile();
        try {
             us = getProfile.GetProfile();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String temp;
        if(us.getSex().equals("Male")){
            temp = "男性";
        }
        else{
            temp ="女性";
        }
        sex.setText(temp);
        if(us.getIdentity().equals("owner")){
            temp = "車主";
        }
        else{
            temp ="乘客";
        }
        String Name = us.getLastName() + us.getFirstName();
        name.setText(Name);
        identify.setText(temp);
        score.setText(us.getScore());
    }
}
