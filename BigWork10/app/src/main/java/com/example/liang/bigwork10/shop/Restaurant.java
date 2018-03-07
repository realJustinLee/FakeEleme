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
 * @author liang
 * @date 2018/3/6
 */
public class Restaurant extends AppCompatActivity {
    ListView classified;
    ListView food;
    /**
     * 菜系
     */
    TextView name;
    /**
     * 商店的名字
     */
    TextView brand;
    /**
     * 返回键
     */
    Button back;
    Button sure;
    ArrayList<HashMap<String, Object>> list;
    String[] nameList = {"1", "2", "3", "4", "5", "6", "7", "8"};
    String[] foodList = {".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8"};
    ShopAdapter shopAdapter;
    FoodAdapter foodAdapter;
    ArrayList<HashMap<String, Object>> foodArray = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopitem);
        final Intent intent = getIntent();
        classified = findViewById(R.id.classifity);
        sure = findViewById(R.id.ok);
        food = findViewById(R.id.food);
        name = findViewById(R.id.name);
        name.setText(intent.getStringExtra("name"));
        brand = findViewById(R.id.shopname);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("classifityname", nameList[i]);
            map.put("classifitynum", 0);
            ArrayList<HashMap<String, Object>> foodList = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                HashMap<String, Object> foodMap = new HashMap<>();
                foodMap.put("foodname", i + this.foodList[j]);
                foodMap.put("foodsellnum", 100 + j * 8);
                foodMap.put("foodevaluate", 80 + j + "%");
                foodMap.put("price", "8.8");
                foodMap.put("count", 0);
                foodList.add(foodMap);
            }
            map.put("food", foodList);
            Log.i("tab", ":" + i);
            list.add(map);
        }
        classified.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("tab", (String) list.get(position).get("name"));
                foodArray = (ArrayList<HashMap<String, Object>>) list.get(position).get("food");
                foodAdapter.changeData(foodArray, position);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Restaurant.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        Log.i("tab", "shopAdapter0");
        shopAdapter = new ShopAdapter(this, list);
        Log.i("tab", "shopAdapter0");
        foodArray = (ArrayList<HashMap<String, Object>>) list.get(0).get("food");
        foodAdapter = new FoodAdapter(this, foodArray, shopAdapter);
        Log.i("tab", "shopAdapter1");
        classified.setAdapter(shopAdapter);
        food.setAdapter(foodAdapter);
    }
}
