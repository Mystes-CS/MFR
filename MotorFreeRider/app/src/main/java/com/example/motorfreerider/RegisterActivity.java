package com.example.motorfreerider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    GoogleSignInAccount account = GoogleSignInActivity.account;
    public static String start;
    private Button btDate, btTime, send;
    private TextView tvDate, tvTime;
    public static  String content;
    public static int mYear, mMonth, mDay, mHour, mMinute;
    public static double latitude, longitude;
    EditText forPassenger;
    user _user = new user();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                start = place.getName().toString();
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                //  Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                // Log.i(TAG, "An error occurred: " + status);
            }
        });
        _user = new user();
        forPassenger = (EditText)findViewById(R.id.for_passenger);
        send = (Button) findViewById(R.id.buttonSend);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        btDate = (Button) findViewById(R.id.btDate);
        btTime = (Button) findViewById(R.id.btTime);
        send.setOnClickListener(this);
        btDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    public void showDatePickerDialog() {
        // 設定初始日期
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // 跳出日期選擇器
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // 完成選擇，顯示日期
                        String _date = String.format("%04d/%02d/%02d", year, monthOfYear + 1, dayOfMonth);
                        mYear = year;
                        mMonth = monthOfYear + 1;
                        mDay = dayOfMonth;
                        tvDate.setText(_date);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void showTimePickerDialog() {
        // 設定初始時間
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // 跳出時間選擇器
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // 完成選擇，顯示時間
                        String _time = String.format("%02d : %02d", hourOfDay, minute);
                        mHour = hourOfDay;
                        mMinute = minute;
                        tvTime.setText(_time);
                    }
                }, mHour, mMinute, true);
        tpd.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSend) {
            content = forPassenger.getText().toString();
            Register register = new Register();
            try {
                driver _driver = register.Register();
                if (_driver.getStat().equals("Success")) {
                    Toast.makeText(RegisterActivity.this, "成功 ! 已將您的乘車資訊刊登在共乘資訊中", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MapsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "失敗 請重新刊登", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
