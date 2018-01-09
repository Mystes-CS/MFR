package com.example.motorfreerider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.concurrent.ExecutionException;

public class GiveScoreToPassenger extends AppCompatActivity implements View.OnClickListener {
    private Button submit;
    private EditText Commit;
    private RatingBar ratingBar;
    float score;
    String text;
    GoogleSignInAccount account = GoogleSignInActivity.account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_score);
        submit = (Button) findViewById(R.id.submit);
        Commit = (EditText) findViewById(R.id.commit);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setMax(5);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar rtBar, float rating,boolean fromUser) {
                 score = rating;
              }
        });
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                text = Commit.getText().toString();
                sendCommit sc = new sendCommit(account.getId(),text,score,"Owner");
                try {
                    String Stat  = sc.sendcommit();
                    if(Stat.equals("OK")){
                        Toast.makeText(GiveScoreToPassenger.this, "謝謝您，祝您旅途愉快！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(GiveScoreToPassenger.this, MapsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(GiveScoreToPassenger.this, "伺服器忙碌中請稍後在試..", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
