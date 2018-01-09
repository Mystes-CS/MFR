package com.example.motorfreerider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public class chooseIdentity extends AppCompatActivity implements View.OnClickListener {
    GoogleSignInAccount account = GoogleSignInActivity.account;
    private Button button;
    private EditText firstName, lastName, phoneNum,CarType,CarNumber;
    private RadioButton owner, passenger, male, female;
    private RadioGroup groupRadio, sex;
    private String Identity = "", Sex = "";
    public static user _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_identity);
        _user = new user();
        button = findViewById(R.id.first_send);
        button.setOnClickListener(this);
        CarType = (EditText)findViewById(R.id.CarType);
        CarNumber = (EditText)findViewById(R.id.CarNumber);
        lastName = (EditText) findViewById(R.id.lastName);
        firstName = (EditText) findViewById(R.id.firstName);
        phoneNum = (EditText) findViewById(R.id.MobileNumber);
        groupRadio = (RadioGroup) findViewById(R.id.group_radio_identity);
        sex = (RadioGroup) findViewById(R.id.group_radio_Sex);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        owner = (RadioButton) findViewById(R.id.owner);
        passenger = (RadioButton) findViewById(R.id.passenger);
        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.owner:
                        break;
                    case R.id.passenger:
                        break;
                }
            }
        });
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        break;
                    case R.id.female:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_send:
                String temp = prepareData();
                Log.w("幹",temp);
                if (temp.equals("true")) {
                    CreateFile createFile = new CreateFile(_user);
                    Intent intent = new Intent(chooseIdentity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(chooseIdentity.this, "有資料沒有填完喔", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public String prepareData() {
        if (firstName.getText().toString().equals("") || lastName.getText().toString().equals("") || phoneNum.getText().toString().equals("") || Identity.equals("") || Sex.equals("")) {
            return "false";
        }
        if(Identity.equals("owner")){
            if(CarNumber.getText().toString().equals("")||CarType.getText().toString().equals("")){
                return "false";
            }
        }
        _user.setFirstName(firstName.getText().toString() + "");
        _user.setLastName(lastName.getText().toString() + "");
        _user.setMobileNumber(phoneNum.getText().toString() + "");
        _user.setID(account.getId() + "");
        _user.setCarNumber(CarNumber.getText().toString());
        _user.setCarType(CarType.getText().toString());
        _user.setEmail(account.getEmail() + "");
        _user.setIdentity(Identity);
        _user.setSex(Sex);
        _user.setAccount(account.getAccount());
        _user.setCount("0");
        _user.setScore("0");
        _user.setNow("NoCar");
        return "true";
    }

    public void onSelect(View view) {
        switch (view.getId()) {
            case R.id.owner:
                Identity = "owner";
                findViewById(R.id.CarALL).setVisibility(View.VISIBLE);
                findViewById(R.id.CarNumberALL).setVisibility(View.VISIBLE);
                break;
            case R.id.passenger:
                Identity = "passenger";
                findViewById(R.id.CarALL).setVisibility(View.INVISIBLE);
                findViewById(R.id.CarNumberALL).setVisibility(View.INVISIBLE);
                break;
            case R.id.male:
                Sex = "Male";
                break;
            case R.id.female:
                Sex = "Female";
                break;
        }
    }
}
