package com.example.liang.bigwork10.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liang.bigwork10.R;
import com.example.liang.bigwork10.adapter.FoodAdapter;
import com.example.liang.bigwork10.adapter.ShopAdapter;
import com.example.liang.bigwork10.xv.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by liang on 2018/3/6.
 */

public class ItemShop extends AppCompatActivity {
    ListView classifity;
    ListView food;
    TextView name;//菜系
    TextView shopname;//商店的名字
    Button back;//返回键
    Button sure;
    ArrayList<HashMap<String,Object>>list;
    String[] strname={"1","2","3","4","5","6","7","8"};
    String[] strfood={".1",".2",".3",".4",".5",".6",".7",".8"};
    ShopAdapter shopAdapter;
    FoodAdapter foodAdapter;
    ArrayList<HashMap<String,Object>>fl=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopitem);
        final Intent intent=getIntent();
        classifity=(ListView)findViewById(R.id.classifity);
        sure=(Button)findViewById(R.id.ok);
        food=(ListView)findViewById(R.id.food);
        name=(TextView)findViewById(R.id.name);
        name.setText(intent.getStringExtra("name"));
        shopname=(TextView)findViewById(R.id.shopname);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list=new ArrayList<>();
        for(int i=0;i<8;i++){
            HashMap<String,Object> hm=new HashMap<>();
            hm.put("classifityname",strname[i]);
            hm.put("classifitynum",0);
            ArrayList<HashMap<String,Object>>foodlist=new ArrayList<>();
            for(int j=0;j<8;j++){
                HashMap<String,Object> foodhm=new HashMap<>();
                foodhm.put("foodname",i+strfood[j]);
                foodhm.put("foodsellnum",100+j*8);
                foodhm.put("foodevaluate",80+j+"%");
                foodhm.put("price","8.8");
                foodhm.put("count",0);
                foodlist.add(foodhm);
            }
            hm.put("food",foodlist);
            Log.i("tab",":"+i);
            list.add(hm);
        }
        classifity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("tab",(String)list.get(position).get("name"));
                fl=(ArrayList<HashMap<String,Object>>)list.get(position).get("food");
                foodAdapter.changeData(fl,position);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ItemShop.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        Log.i("tab","shopAdapter0");
        shopAdapter=new ShopAdapter(this,list);
        Log.i("tab","shopAdapter0");
        fl=(ArrayList<HashMap<String,Object>>)list.get(0).get("food");
        foodAdapter=new FoodAdapter(this,fl,shopAdapter);
        Log.i("tab","shopAdapter1");
        classifity.setAdapter(shopAdapter);
        food.setAdapter(foodAdapter);
    }
}
