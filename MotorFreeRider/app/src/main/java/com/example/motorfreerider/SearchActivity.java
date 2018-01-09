package com.example.motorfreerider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class SearchActivity extends AppCompatActivity {
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        send = (Button) findViewById(R.id.search_send);
        final EditText editText = (EditText) findViewById(R.id.editText);
        RadioGroup groupRadio = (RadioGroup) findViewById(R.id.group_radio);
        groupRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.owner) {
                    editText.setHint("請輸入車主姓名");
                    editText.setText("");
                } else if (checkedId == R.id.number) {
                    editText.setHint("請輸入車牌號碼");
                    editText.setText("");
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                if (!content.equals("")) {
                    Toast.makeText(SearchActivity.this, content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onSelect(View view) {
        switch (view.getId()) {
            case R.id.owner:
                Toast.makeText(SearchActivity.this, "車主姓名!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.number:
                Toast.makeText(SearchActivity.this, "車牌號碼!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
