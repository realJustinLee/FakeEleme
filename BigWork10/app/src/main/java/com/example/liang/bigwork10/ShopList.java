package com.example.liang.bigwork10;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.liang.bigwork10.adapter.MyListAdpter;
import com.example.liang.bigwork10.inter.MySocket;
import com.example.liang.bigwork10.shop.ItemShop;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by liang on 2018/3/3.
 */

public class ShopList extends Fragment {
    String[] shopname={"开心厨房","湖北小酌","在吃陕西老面馆","庄记冒菜","汤品皇养生主题餐厅","胖哥两肉蟹煲","一品居黄焖鸡米饭","阿云家常菜","盒饭的进犯",
    "魔锅坊麻辣香锅","卤人家","韩国铁板炒饭","素芳斋养生素食","杨国富麻辣烫","我呀便当","鸭蹼鸭蹼","麦乐堡","我家酸菜鱼","武汉热干面","上海麦当劳"};
    int[]pic={R.mipmap.pic1,R.mipmap.pic2,R.mipmap.pic3,R.mipmap.pic4,R.mipmap.pic5,R.mipmap.pic6,R.mipmap.pic7,R.mipmap.pic8,R.mipmap.pic9,R.mipmap.pic10,
    R.mipmap.pic11,R.mipmap.pic12,R.mipmap.pic13,R.mipmap.pic14,R.mipmap.pic15,R.mipmap.pic16,R.mipmap.pic17,R.mipmap.pic18,R.mipmap.pic19,R.mipmap.pic20};
    int[] star={R.mipmap.star0,R.mipmap.star1,R.mipmap.star2,R.mipmap.star3,R.mipmap.star4,R.mipmap.star5,R.mipmap.star6,
    R.mipmap.star7,R.mipmap.star8,R.mipmap.star9,R.mipmap.star10};
    MyListAdpter myListAdpter;
    MySocket mySocket;
    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.shoplist,container,false);
        ListView listView=(ListView)view.findViewById(R.id.listview);
        List<Map<String,Object>>list=new ArrayList<>();

        Random random=new Random();
        for (int i=0;i<shopname.length;i++){
            int n;
            do{
                n=random.nextInt()%51;
            }while(n<0);
            HashMap<String,Object> mp=new HashMap<>();
            mp.put("picture",pic[i]);
            mp.put("shopstar",star[n/5]);
            float m= (float) ((float)n/10.0);
            mp.put("shopevaluate",Float.toString(m));
            do{
                n=random.nextInt()%2555;
            }while(n<0);
            mp.put("sellnum",Integer.toString(n));
            do{
                n=random.nextInt()%100;
            }while(n<0);
            m=(float)((float)n/10.0);
            mp.put("send",Float.toString(m));
            mp.put("shopname",shopname[i]);
            mp.put("freight",5+"");
            Log.i("shopname_list",shopname[i]);
            list.add(mp);
        }
        //获取数据
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                MyApplication myApplication=(MyApplication)getActivity().getApplication();
//                myApplication.mySocket=new MySocket("192.168.0.108",8066);
//                myApplication.mySocket.send(0);
//                Log.i("socket","发送成功");
//                myApplication.shoplist=myApplication.mySocket.getShopList();
//            }
//        });
        myListAdpter=new MyListAdpter(list,getContext());
        listView.setAdapter(myListAdpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("tab",shopname[i]);
                Intent intent=new Intent(getContext(), ItemShop.class);
                intent.putExtra("name",shopname[i]);
                startActivity(intent);
            }
        });
        return view;
    }
}
