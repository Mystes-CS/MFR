package com.example.motorfreerider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    //  private GoogleApiClient mGoogleApiClient = GoogleSignInActivity.googleApiClient;
    private Activity mActivity;
    public static GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Button together;
    private Button search;
    private Button drive;
    private Button drivePhase_owner, drivePhase_passenger;
    private Toolbar toolbar;
    private NavigationView navigation_view;
    private static Boolean isExit = false;
    private static Boolean hasTask = false;
    private Timer tExit = new Timer();
    private TimerTask task;
    private String Identify, Where;
    private final int MarkerNumber = 10;
    public static String carryPlace = "", finishPlace = "";
    GoogleSignInAccount account = GoogleSignInActivity.account;
    GoogleApiClient googleApiClient = GoogleSignInActivity.googleApiClient;
    user _user = chooseIdentity._user;
    public static String description_str = "";
    public static Double carryPlace_latitude, carryPlace_longitude, finishPlace_latitude, finishPlace_longitude;
    private Marker obj[];
    addMarker _addMarker;
    Dialog myDialog, sendDialog, inputDialog, ShowForEvery, CarDialog;
    List<Marker> markerList = new ArrayList<Marker>();
    JSONArray jsonArr;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        obj = new Marker[MarkerNumber];
        removeMarkers();
        _addMarker = new addMarker();
        String result = null;
        try {
            result = _addMarker.mark();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!result.equals("empty")) {
            try {
                jsonArr = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = jsonArr.getJSONObject(i);
                    String latitude = jsonObj.get("latitude").toString();
                    String longitude = jsonObj.get("longitude").toString();
                    obj[i] = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                            .title(jsonObj.get("LastName").toString() + " " + jsonObj.get("FirstName").toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                obj[i].setTag(i);
                markerList.add(obj[i]);
            }
        }
        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setOnMarkerDragListener(new LinkMarkerLongClickListener(markerList) {
                @Override
                public void onLongClickListener(Marker marker) throws JSONException {
                    //ToastUtils.showToast(Mission1Activity.this, marker.getTitle());
                    Log.w("ID:", jsonArr.getJSONObject((int) marker.getTag()).toString());
                    if (Identify.equals("passenger")) {
                        ShowPopup(jsonArr.getJSONObject((int) marker.getTag()));
                    }
                }
            });
            // mMap.setOnMarkerClickListener(this);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        myDialog = new Dialog(this);
        ShowForEvery = new Dialog(this);
        CarDialog = new Dialog(this);
        sendDialog = new Dialog(this);
        inputDialog = new Dialog(this);
        CarDialog.setContentView(R.layout.car);
        inputDialog.setContentView(R.layout.input_carry_place);
        sendDialog.setContentView(R.layout.send_text);
        myDialog.setContentView(R.layout.custompopup);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        String str = intent.getStringExtra("refuse");
        if (str != null && str.equals("OK")) {
            Toast.makeText(MapsActivity.this, "成功取消請求了", Toast.LENGTH_SHORT).show();
        }
        task = new TimerTask() {
            @Override
            public void run() {
                isExit = false;
                hasTask = true;
            }
        };
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.activity_main2_drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        navigation_view = (NavigationView) findViewById(R.id.left_drawer);
        // 為navigatin_view設置點擊事件
        View header = navigation_view.getHeaderView(0);
        TextView proName = (TextView) header.findViewById(R.id.txtNamePro);
        GetProfile getProfile = new GetProfile();
        user temp = new user();
        try {
            temp = getProfile.GetProfile();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String Name = temp.getLastName() + temp.getFirstName();
        proName.setText(Name);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // 點選時收起選單
                mDrawerLayout.closeDrawer(GravityCompat.START);

                // 取得選項id
                int id = item.getItemId();
                // 依照id判斷點了哪個項目並做相應事件
                if (id == R.id.nav_map) {
                    // 按下「首頁」要做的事
                    Toast.makeText(MapsActivity.this, "回到首頁", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    // onCreate(null);
                    return true;
                } else if (id == R.id.nav_profile) {
                    Toast.makeText(MapsActivity.this, "個人資訊", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_information) {
                    client _client = new client();
                    String ident = "";
                    try {
                        ident = _client.checkIdentify();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (ident.equals("owner")) {
                        Toast.makeText(MapsActivity.this, "共乘資訊", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MapsActivity.this, RequestList.class);
                        startActivity(intent);
                        return true;
                    } else if (ident.equals("passenger")) {
                        Toast.makeText(MapsActivity.this, "共乘資訊", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MapsActivity.this, RequestListForPassenger.class);
                        startActivity(intent);
                        return true;
                    }
                } else if (id == R.id.nav_setting) {
                    Toast.makeText(MapsActivity.this, "設定", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_change) {
                    Toast.makeText(MapsActivity.this, "變更身分", Toast.LENGTH_SHORT).show();
                    ChangeProfile changeProfile = new ChangeProfile("Identify", null, null);
                    try {
                        String stat = changeProfile.Change();
                        if (stat.equals("true")) {
                            Toast.makeText(MapsActivity.this, "更改完成", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
                            startActivity(intent);
                        } else if (stat.equals("NoCar")) {
                            addCar();
                        } else {
                            Toast.makeText(MapsActivity.this, "變更失敗,可能是因為正在共乘中", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                } else if (id == R.id.nav_contact) {
                    Toast.makeText(MapsActivity.this, "聯絡我們", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_signOut) {
                    Toast.makeText(MapsActivity.this, "登出", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MapsActivity.this, GoogleSignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        together = (Button) findViewById(R.id.together_button);
        search = (Button) findViewById(R.id.sear_button);
        drive = (Button) findViewById(R.id.wantToDrive);
        drivePhase_owner = (Button) findViewById(R.id.DuringDriveOwner);
        drivePhase_owner.setOnClickListener(this);
        drivePhase_passenger = (Button) findViewById(R.id.DuringDrivePassenger);
        drivePhase_passenger.setOnClickListener(this);
        together.setOnClickListener(this);
        search.setOnClickListener(this);
        drive.setOnClickListener(this);
        getLocationPermission();
        client _client = new client();
        try {
            Identify = _client.checkIdentify();
            Where = _client.checkInCar();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Where.equals("InCar")) {
            if (Identify.equals("owner")) {
                findViewById(R.id.twoButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.oneButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.DuringDrivebtn_Owner).setVisibility(View.VISIBLE);
                findViewById(R.id.DuringDrivebtn_Passenger).setVisibility(View.INVISIBLE);
            } else if (Identify.equals("passwnger")) {
                findViewById(R.id.twoButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.oneButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.DuringDrivebtn_Owner).setVisibility(View.INVISIBLE);
                findViewById(R.id.DuringDrivebtn_Passenger).setVisibility(View.VISIBLE);
            }
        } else if (Identify.equals("owner")) {
            findViewById(R.id.DuringDrivebtn_Owner).setVisibility(View.INVISIBLE);
            findViewById(R.id.DuringDrivebtn_Passenger).setVisibility(View.INVISIBLE);
            findViewById(R.id.twoButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.oneButton).setVisibility(View.VISIBLE);
        } else if (Identify.equals("passenger")) {
            findViewById(R.id.DuringDrivebtn_Owner).setVisibility(View.INVISIBLE);
            findViewById(R.id.DuringDrivebtn_Passenger).setVisibility(View.INVISIBLE);
            findViewById(R.id.twoButton).setVisibility(View.VISIBLE);
            findViewById(R.id.oneButton).setVisibility(View.INVISIBLE);
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            if (currentLocation != null) {
                                LatLng currPoint = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                                moveCamera(currPoint, DEFAULT_ZOOM);
                                // mMap.addMarker(new MarkerOptions().position(currPoint).title("目前位置"));
                            }
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.together_button:
                //     intent = new Intent(MapsActivity.this,SearchActivity.class);
                //     startActivity(intent);
                break;
            case R.id.sear_button:
                intent = new Intent(MapsActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.wantToDrive:
                intent = new Intent(MapsActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.DuringDriveOwner:
                GetForEvery getForEvery = new GetForEvery("Owner");
                ShowForEvery.setContentView(R.layout.driving_profile_for_owner);
                try {
                    user us = getForEvery.getForEvery();
                    ShowForEvery("Owner", us);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.DuringDrivePassenger:
                GetForEvery getForEveryP = new GetForEvery("Passenger");
                ShowForEvery.setContentView(R.layout.driving_profile_for_passenger);
                try {
                    user us = getForEveryP.getForEvery();
                    ShowForEvery("Passenger", us);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                Toast.makeText(this, "再按一次返回鍵退出應用程式", Toast.LENGTH_SHORT).show();
                if (!hasTask) {
                    tExit.schedule(task, 2000);
                }
            } else {
                finish();
                System.exit(0);
            }
        }
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
       /* Integer clickCount = (Integer) marker.getTag();


        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }*/
        myDialog.show();
        return false;
    }

    public void addCar() throws ExecutionException, InterruptedException {
        final EditText carNumber, carType;
        TextView txtclose;
        Button send;
        txtclose = (TextView) CarDialog.findViewById(R.id.txt_close3);
        carType = (EditText) CarDialog.findViewById(R.id.CarType_2);
        carNumber = (EditText) CarDialog.findViewById(R.id.CarNumber_2);
        send = (Button) CarDialog.findViewById(R.id.btn_Carsend);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarDialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeProfile changeProfile = new ChangeProfile("Car", null, null);
                try {
                    if(carType.getText().toString().equals("")||carNumber.getText().toString().equals("")) {
                        Toast.makeText(MapsActivity.this, "有資料沒填喔", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        CarDialog.dismiss();
                        String stat = changeProfile.addCar(carType.getText().toString(), carNumber.getText().toString());
                        if (stat.equals("OK")) {
                            Toast.makeText(MapsActivity.this, "新增成功", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        CarDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CarDialog.show();
    }

    public void ShowPopup(final JSONObject jsonObj) throws JSONException {
        TextView txtclose, name, time, place, score;
        String Score = jsonObj.get("Score").toString();
        String Count = jsonObj.get("Count").toString();
        String Place = jsonObj.get("start").toString();
        String Name = jsonObj.get("LastName").toString() + " " + jsonObj.get("FirstName").toString();
        String Date = jsonObj.get("Year").toString() + "/" + jsonObj.get("Month").toString() + "/" + jsonObj.get("Date").toString();
        String Time = Date + "\n  " + jsonObj.get("Hour").toString() + "：" + jsonObj.get("Minute").toString();
        float ScoreNum = Float.valueOf(Score);
        float CountNum = Float.valueOf(Count);
        Button btnRequest, btnExit;
        score = (TextView) myDialog.findViewById(R.id.text_score);
        place = (TextView) myDialog.findViewById(R.id.text_place);
        name = (TextView) myDialog.findViewById(R.id.text_name);
        time = (TextView) myDialog.findViewById(R.id.text_time);
        name.setText(Name);
        time.setText(Time);
        place.setText(Place);
        if (CountNum == 0) {
            score.setText("N/A");
        } else {
            score.setText(String.format("%f", (float) ScoreNum / CountNum));
        }
        txtclose = (TextView) myDialog.findViewById(R.id.txt_close_pop);
        btnExit = (Button) myDialog.findViewById(R.id.btn_exit);
        btnRequest = (Button) myDialog.findViewById(R.id.btn_request);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                inputCarryPlace(jsonObj);
                //SendText(jsonObj);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void ShowForEvery(final String ident, user us) throws ExecutionException, InterruptedException {
        final TextView txtclose, txtCarryPlace, txtEndPlace, txtPhone, txtName, txtTime, txtStart;
        Button btnEnd;
        txtclose = (TextView) ShowForEvery.findViewById(R.id.txt_close_Passenger);
        txtName = (TextView) ShowForEvery.findViewById(R.id.text_PassengerName);
        btnEnd = (Button) ShowForEvery.findViewById(R.id.btn_OwnerEnd);
        txtTime = (TextView) ShowForEvery.findViewById(R.id.text_time);
        txtStart = (TextView) ShowForEvery.findViewById(R.id.text_start);
        txtCarryPlace = (TextView) ShowForEvery.findViewById(R.id.text_Carry);
        txtEndPlace = (TextView) ShowForEvery.findViewById(R.id.text_finish);
        txtPhone = (TextView) ShowForEvery.findViewById(R.id.text_PassengerMobile);
        String Name = us.getLastName() + us.getFirstName();
        if (ident.equals("Owner")) {
            txtCarryPlace.setText(us.getCarryPlace());
            txtEndPlace.setText(us.getFinishPlace());
        } else if (ident.equals("Passenger")) {
            txtTime.setText(us.getTime());
            txtStart.setText(us.getStart());
        }
        txtName.setText(Name);
        txtPhone.setText(us.getMobileNumber());
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowForEvery.dismiss();
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ident.equals("Owner")) {
                    Intent intent = new Intent(MapsActivity.this, GiveScoreToPassenger.class);
                    startActivity(intent);
                    finish();
                } else if (ident.equals("Passenger")) {
                    Intent intent = new Intent(MapsActivity.this, GiveScoreToOwner.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ShowForEvery.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ShowForEvery.show();
    }

    public void inputCarryPlace(final JSONObject jsonObj) {
        final TextView txtclose;
        Button btnNext, btnExit;
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_request_carryPlace);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                carryPlace = place.getName().toString();
                carryPlace_latitude = place.getLatLng().latitude;
                carryPlace_longitude = place.getLatLng().longitude;
            }

            @Override
            public void onError(Status status) {

            }
        });
        txtclose = (TextView) inputDialog.findViewById(R.id.txt_close3);
        btnExit = (Button) inputDialog.findViewById(R.id.btn_exit_three);
        btnNext = (Button) inputDialog.findViewById(R.id.btn_next);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
                try {
                    ShowPopup(jsonObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carryPlace.equals("")) {
                    Toast.makeText(MapsActivity.this, "請輸入上車地點喔", Toast.LENGTH_SHORT).show();
                } else {
                    inputDialog.dismiss();
                    try {
                        SendText(jsonObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        inputDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        inputDialog.show();
    }

    public void SendText(final JSONObject jsonObj) throws JSONException {
        final TextView txtclose;
        final EditText description;
        Button btnSend, btnExit;
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_request_finishPlace);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                finishPlace = place.getName().toString();
                finishPlace_latitude = place.getLatLng().latitude;
                finishPlace_longitude = place.getLatLng().longitude;
            }

            @Override
            public void onError(Status status) {

            }
        });
        description = (EditText) sendDialog.findViewById(R.id.for_owner);
        txtclose = (TextView) sendDialog.findViewById(R.id.txt_close2);
        btnExit = (Button) sendDialog.findViewById(R.id.btn_exit_two);
        btnSend = (Button) sendDialog.findViewById(R.id.btn_send);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDialog.dismiss();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDialog.dismiss();
                inputCarryPlace(jsonObj);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description_str = description.getText().toString();
                if (finishPlace.equals("")) {
                    Toast.makeText(MapsActivity.this, "請輸入目標地點喔", Toast.LENGTH_SHORT).show();
                } else {
                    sendDialog.dismiss();
                    try {
                        GetProfile getProfile = new GetProfile();
                        SendRequest sendRequest = new SendRequest(jsonObj.get("ID").toString(), getProfile.GetProfile());
                        if (sendRequest.SendRequest()) {
                            Toast.makeText(MapsActivity.this, "已成功送出共乘請求，待車主確認", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sendDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sendDialog.show();
    }

    private void removeMarkers() {
        for (Marker marker : markerList) {
            marker.remove();
        }
        markerList.clear();
    }
}
