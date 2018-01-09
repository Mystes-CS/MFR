package com.example.motorfreerider;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.concurrent.ExecutionException;

public class GoogleSignInActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private Button SignOut;
    private SignInButton SignIn;
    public static GoogleSignInAccount account;

    public static GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SignOut = (Button) findViewById(R.id.sign_out_button);
        SignIn = (SignInButton) findViewById(R.id.sign_in_button);
        SignIn.setOnClickListener(this);
//        SignOut.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                dialog = ProgressDialog.show(GoogleSignInActivity.this,
                        "取得資料中", "請稍後",true);
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    public static void signOut() {
        if ( googleApiClient != null &&  googleApiClient.isConnected()) {
            googleApiClient.clearDefaultAccountAndReconnect().setResultCallback(new ResultCallback<Status>() {

                @Override
                public void onResult(Status status) {

                    googleApiClient.disconnect();
                }
            });

        }
    }

    private void handleResult(GoogleSignInResult result) throws ExecutionException, InterruptedException {
        account = result.getSignInAccount();
        if (result.isSuccess()) {
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            String idToken = account.getIdToken();

            updateUI(true, account);
        } else {
            updateUI(false, account);
        }
    }

    private void updateUI(boolean isLogin, GoogleSignInAccount account) throws ExecutionException, InterruptedException {
        if (isLogin) {
            //String idToken = account.getId();
            //    ID.setText(getString(R.string.id_fmt,idToken));
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            if (firstLogin()) {
                dialog.dismiss();
                Intent intent = new Intent(GoogleSignInActivity.this, chooseIdentity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(GoogleSignInActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            //   findViewById(R.id.ID).setVisibility(View.GONE);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            //   findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            try {
                handleResult(result);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean firstLogin() throws ExecutionException, InterruptedException {
        client _client = new client();
        return _client.firstLogin();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
