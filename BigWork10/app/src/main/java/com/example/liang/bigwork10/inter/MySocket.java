package com.example.liang.bigwork10.inter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.liang.bigwork10.MyConstant;
import com.example.liang.bigwork10.MyUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by liang on 2018/3/6.
 */

public class MySocket {
    private String url;
    private int port;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Bitmap bmp;
    public MySocket(String url, int port){
        this.url=url;
        this.port=port;
        try {
            socket=new Socket(url,port);
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            Log.i("socket","连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //接受商店的列表信息
    public ArrayList<HashMap<String ,Object>> getShopList(){
        ArrayList<HashMap<String,Object>>list=null;
        //接受有多少组信息
        try {
            int n=dataInputStream.readInt();
            for(int i=0;i<n;i++){
                HashMap<String,Object>hm=new HashMap<>();
                //接受字符串
                String str=getString();
                //解包
                String[] item= MyUtils.StringUnPack(str);
                for(int j=0;j<5;j++){
                    hm.put(MyConstant.shoplistorder[j],item[j]);
                }
                //接受图片
                hm.put("picture",getImage());
                //放入数组中
                list.add(hm);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    //接受菜系信息
    public ArrayList<HashMap<String,Object>>getClassifity(){
        ArrayList<HashMap<String,Object>>list=null;
        try {
            //有多少种菜系
            int n=dataInputStream.readInt();
            for(int i=0;i<n;i++){
                HashMap<String,Object>hm=new HashMap<>();
                //接受字符串
                String str=getString();
                //解包
                String[] item=MyUtils.StringUnPack(str);
                for(int j=0;j<item.length;j++){
                    hm.put(MyConstant.classifitylistorder[j],item[j]);
                }
                list.add(hm);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    //接受食物列表信息
    public ArrayList<HashMap<String,Object>>getFood(){
        ArrayList<HashMap<String,Object>>list=null;
        try {
            //每种菜系有多少种菜
            int n=dataInputStream.readInt();
            for(int i=0;i<n;i++){
                HashMap<String,Object>hm=new HashMap<>();
                //接受字符串
                String str=getString();
                //解包
                String[] item=MyUtils.StringUnPack(str);
                for(int j=0;j<4;j++){
                    hm.put(MyConstant.foodlistorder[j],item[j]);
                }
                //图片
                hm.put("picture",getImage());
                list.add(hm);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    //接受数据要在线程中。
    public HashMap<String ,Object> getData()  {
        HashMap<String,Object>hm=new HashMap<>();
        hm.put("str",getString());
        hm.put("image",getImage());
        Log.i("socket","成功接受数据");
        return hm;
    }
    //接受图片
    public Bitmap getImage(){
        Bitmap bitmap=null;
        try {
            int size=dataInputStream.readInt();
            byte[] data=new byte[size];
            int len=0;
            while(len<size){
                len+=dataInputStream.read(data,len,size-len);
            }
            ByteArrayOutputStream output=new ByteArrayOutputStream();
            bitmap=BitmapFactory.decodeByteArray(data,0,data.length);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    //接受字符串
    public String getString(){
        String str=null;
        int size= 0;
        try {
            size = dataInputStream.readInt();
            byte[] data=new byte[size];
            int len=0;
            while(len<size){
                len+=dataInputStream.read(data,len,size-len);
            }
            str=new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    //发送数据
    public void send(String string){
        try {
            byte[] s=string.getBytes();
            dataOutputStream.writeInt(s.length);
            dataOutputStream.write(s);
            dataOutputStream.flush();
//            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(int n){
        try {
            dataOutputStream.writeInt(n);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
