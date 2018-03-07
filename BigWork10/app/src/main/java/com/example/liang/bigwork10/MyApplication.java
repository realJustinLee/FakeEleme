package com.example.liang.bigwork10;

import android.app.Application;

import com.example.liang.bigwork10.inter.MySocket;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by liang on 2018/3/6.
 */

public class MyApplication extends Application {
    //商店的列表
    ArrayList<HashMap<String,Object>> shoplist=null;
    //类别列表
    ArrayList<HashMap<String,Object>> classifitylist=null;
    //食物列表
    ArrayList<HashMap<String,Object>> foodlist=null;
    //购物车列表
    ArrayList<HashMap<String,Object>> cartlist=null;
    //连接服务器
    MySocket mySocket;
}
