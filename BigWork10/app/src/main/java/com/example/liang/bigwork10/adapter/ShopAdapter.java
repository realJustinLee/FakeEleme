package com.example.liang.bigwork10.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liang.bigwork10.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by liang on 2018/3/6.
 */

public class ShopAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<HashMap<String,Object>>list;
    public ShopAdapter(Context context,ArrayList<HashMap<String,Object>>list){
        this.context=context;
        this.list=list;
        layoutInflater=LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.classifityitem,null);
            viewHolder.name=(TextView)convertView.findViewById(R.id.name_one);
            viewHolder.count=(TextView)convertView.findViewById(R.id.count_one);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        String str=(String)list.get(position).get("classifityname");
        Log.i("tab",str);
        viewHolder.name.setText(str);
        int n=(int)list.get(position).get("classifitynum");
        if(n==0){
            viewHolder.count.setVisibility(View.GONE);
        }else{
            viewHolder.count.setVisibility(View.VISIBLE);
            viewHolder.count.setText(n+"");
        }
        Log.i("tab","on"+n);
        return convertView;
    }
    public void setCount(int n,int m){
        list.get(n).put("classifitynum",+m);
        notifyDataSetChanged();
    }
    class ViewHolder{
        TextView name;
        TextView count;
    }
}
