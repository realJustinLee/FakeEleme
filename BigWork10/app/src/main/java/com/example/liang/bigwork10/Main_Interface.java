package com.example.liang.bigwork10;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by liang on 2018/2/27.
 */

public class Main_Interface extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_interface);
//        getActionBar().hide();
        int[] liid={R.id.delicious,R.id.breakfast,R.id.fruit,R.id.drink};
        LinearLayout[] linearLayout=new LinearLayout[4];
        for(int i=0;i<4;i++){
            linearLayout[i]=(LinearLayout)findViewById(liid[i]);
            linearLayout[i].setOnClickListener(listener);
        }
    }
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Main_Interface.this,Delicacy.class);
            switch (view.getId()){
                case R.id.delicious:
                    Log.i("tab","美食");
                    intent.putExtra("name","美食");
                    break;
                case R.id.breakfast:
                    Log.i("tab","早餐");
                    intent.putExtra("name","早餐");
                    break;
                case R.id.fruit:
                    Log.i("tab","水果");
                    intent.putExtra("name","水果");
                    break;
                case R.id.drink:
                    Log.i("tab","饮品");
                    intent.putExtra("name","饮品");
                    break;
            }
            startActivity(intent);
        }
    };
}
