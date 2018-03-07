package com.example.liang.bigwork10.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liang.bigwork10.R;

import java.util.List;
import java.util.Map;

/**
 * Created by liang on 2018/3/3.
 */

public class MyListAdpter extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<Map<String,Object>> list;
    public MyListAdpter(List<Map<String,Object>>list, Context context){
        this.list=list;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=layoutInflater.inflate(R.layout.item1,null);
            viewHolder.picture=(ImageView)view.findViewById(R.id.picture);
            viewHolder.shopstar=(ImageView)view.findViewById(R.id.shopstar);
            viewHolder.shopname=(TextView) view.findViewById(R.id.shopname);
            viewHolder.shopevaluate=(TextView)view.findViewById(R.id.shopevaluate);
            viewHolder.sellnum=(TextView)view.findViewById(R.id.sellnum);
            viewHolder.send=(TextView)view.findViewById(R.id.send);
            viewHolder.freight=(TextView)view.findViewById(R.id.freight);

            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.picture.setImageResource((Integer)list.get(i).get("picture"));
        viewHolder.shopstar.setImageResource((Integer)list.get(i).get("shopstar"));
//        Log.i("shopname",""+(String)list.get(i).get("shopname"));
        viewHolder.shopname.setText((String)list.get(i).get("shopname"));
        viewHolder.shopevaluate.setText((String)list.get(i).get("shopevaluate"));
        viewHolder.sellnum.setText((String)list.get(i).get("sellnum"));
        viewHolder.send.setText((String)list.get(i).get("send"));
//        viewHolder.freight.setText((String)list.get(i).get("freight"));
        return view;
    }
    private class ViewHolder{
        TextView shopname,shopevaluate,sellnum,send,freight;
        ImageView picture,shopstar;
    }

}
