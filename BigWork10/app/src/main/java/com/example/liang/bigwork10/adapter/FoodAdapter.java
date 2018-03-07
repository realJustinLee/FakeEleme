package com.example.liang.bigwork10.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.liang.bigwork10.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author liang
 * @date 2018/3/6
 */
public class FoodAdapter extends BaseAdapter {
    private int hang;
    private ArrayList<HashMap<String, Object>> list;
    private LayoutInflater layoutInflater;
    private ShopAdapter shopAdapter;

    public FoodAdapter(Context context, ArrayList<HashMap<String, Object>> list, ShopAdapter shopAdapter) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.shopAdapter = shopAdapter;
    }

    public void changeData(ArrayList<HashMap<String, Object>> list, int hang) {
        this.list = list;
        this.hang = hang;
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fooditem, null);
            viewHolder.add = convertView.findViewById(R.id.add);
            viewHolder.reduce = convertView.findViewById(R.id.reduce);
            viewHolder.name = convertView.findViewById(R.id.foodname);
            viewHolder.monthNum = convertView.findViewById(R.id.msale);
            viewHolder.ev = convertView.findViewById(R.id.ev);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.count = convertView.findViewById(R.id.count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText((String) list.get(position).get("foodname"));
        viewHolder.monthNum.setText(String.valueOf(list.get(position).get("foodsellnum")));
        viewHolder.ev.setText((String) list.get(position).get("foodevaluate"));
        viewHolder.price.setText((String) list.get(position).get("price"));
        int n = (int) list.get(position).get("count");
        Log.i("tab", "FoodAdapter 67");
        viewHolder.count.setText(String.valueOf(n));
        if (n == 0) {
            viewHolder.count.setVisibility(View.GONE);
            viewHolder.reduce.setVisibility(View.GONE);
        } else {
            viewHolder.count.setVisibility(View.VISIBLE);
            viewHolder.reduce.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int h = (int) list.get(position).get("count");
                h++;
                list.get(position).put("count", h);
                if (h > 0) {
                    finalViewHolder.count.setVisibility(View.VISIBLE);
                    finalViewHolder.reduce.setVisibility(View.VISIBLE);
                }
                int m = getNum();
                shopAdapter.setCount(hang, m);
                notifyDataSetChanged();
            }
        });
        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int h = (int) list.get(position).get("count");
                h--;
                list.get(position).put("count", h);
                if (h < 0) {
                    finalViewHolder.count.setVisibility(View.GONE);
                    finalViewHolder.reduce.setVisibility(View.GONE);
                }
                int m = getNum();
                shopAdapter.setCount(hang, m);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private int getNum() {
        int n = 0;
        for (int i = 0; i < list.size(); i++) {
            n += (int) list.get(i).get("count");
        }
        return n;
    }

    class ViewHolder {
        Button add, reduce;
        TextView name, monthNum, ev, price, count;
    }
}
