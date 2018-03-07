package com.example.liang.bigwork10;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by liang on 2018/3/7.
 */

public class Delicacy extends AppCompatActivity{
    TextView textView;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delicacy);
        Intent integet=getIntent();
        textView=(TextView)findViewById(R.id.name);
        button=(Button)findViewById(R.id.back2);
        textView.setText(integet.getStringExtra("name"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
