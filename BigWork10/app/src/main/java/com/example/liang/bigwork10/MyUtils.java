package com.example.liang.bigwork10;

/**
 * Created by liang on 2018/3/7.
 */

public class MyUtils {
    //字符串的打包
    public static String StringPack(String[] item){
        String one=item[0]+" ";
        for(int i=1;i<item.length;i++){
            one+=(item[i]+" ");
        }
        return one;
    }
    //字符串的解包
    public static String[] StringUnPack(String str){
        return str.split(" ");
    }
}
