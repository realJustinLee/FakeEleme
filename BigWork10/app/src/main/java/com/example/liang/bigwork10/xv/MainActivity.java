package com.example.liang.bigwork10.xv;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.liang.bigwork10.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author liang
 */
public class MainActivity extends Activity implements CartExpandableListViewAdapter.CheckInterface, CartExpandableListViewAdapter.ModifyCountInterface, OnClickListener {
    private ExpandableListView exListView;
    private CheckBox cbCheckAll;
    private TextView tvTotalPrice;
    private TextView tvDelete;
    private TextView tvGoToPay;
    private Context context;

    /**
     * 购买的商品总价
     */
    private double totalPrice = 0.00;
    /**
     * 购买的商品总数量
     */
    private int totalCount = 0;

    private CartExpandableListViewAdapter sellValue;

    /**
     * 组元素数据列表
     */
    private List<GroupInfo> groups = new ArrayList<>();
    /**
     * 子元素数据列表
     */
    private Map<String, List<ProductInfo>> children = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        initView();
        initEvents();
    }

    private void initView() {
        context = this;
        virtualData();
        exListView = findViewById(R.id.exListView);
        cbCheckAll = findViewById(R.id.all_chekbox);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        tvDelete = findViewById(R.id.tv_delete);
        tvGoToPay = findViewById(R.id.tv_go_to_pay);
    }

    private void initEvents() {
        sellValue = new CartExpandableListViewAdapter(groups, children, this);
        // 关键步骤1,设置复选框接口
        sellValue.setCheckInterface(this);
        // 关键步骤2,设置数量增减接口
        sellValue.setModifyCountInterface(this);
        exListView.setAdapter(sellValue);

        for (int i = 0; i < sellValue.getGroupCount(); i++) {
            // 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
            exListView.expandGroup(i);
        }

        cbCheckAll.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvGoToPay.setOnClickListener(this);
    }

    /**
     * 模拟数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
     * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
     */
    private void virtualData() {
        //restaurantList存放店铺数据
        String[] restaurantList = {"KFC", "麻辣烫"};
        int restaurantCount = 0;
        for (String restaurant : restaurantList) {
            groups.add(new GroupInfo(restaurantCount++ + "", restaurant));
            //commodityList存放相应的商品
            String[] commodityList = {"辣翅", "汉堡"};
            List<ProductInfo> products = new ArrayList<>();
            int commodityCount = 0;
            for (String commodity : commodityList) {
                products.add(new ProductInfo(commodityCount++ + "", "商品", "", commodity, 12, 1));
            }
            children.put(groups.get(restaurantCount).getId(), products);
        }

		/*for (int i = 0; i < 6; i++)
        {

			groups.add(new GroupInfo(i + "", "KFC" + (i + 1) + "号店"));

			List<ProductInfo> products = new ArrayList<ProductInfo>();
			for (int j = 0; j <= i; j++)
			{

				products.add(new ProductInfo(j + "", "商品", "", groups.get(i).getName() + "的第" + (j + 1) + "个商品", 120.00 + i * j, 1));
			}
			children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
		}*/
    }

    @Override
    public void onClick(View v) {
        AlertDialog alert;
        switch (v.getId()) {
            case R.id.all_chekbox:
                doCheckAll();
                break;
            case R.id.tv_go_to_pay:
                if (totalCount == 0) {
                    Toast.makeText(context, "请选择要支付的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(context).create();
                alert.setTitle("操作提示");
                alert.setMessage("总计:\n" + totalCount + "种商品\n" + totalPrice + "元");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO:???
                    }
                });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO:???
                    }
                });
                alert.show();
                break;
            case R.id.tv_delete:
                if (totalCount == 0) {
                    Toast.makeText(context, "请选择要移除的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(context).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO:下单支付功能
                    }
                });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doDelete();
                    }
                });
                alert.show();
                break;
            default:
                break;
        }
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {
        // 待删除的组元素列表
        List<GroupInfo> toBeDeleteGroups = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            // 待删除的子元素列表
            List<ProductInfo> toBeDeleteProducts = new ArrayList<>();
            List<ProductInfo> child = children.get(group.getId());
            for (ProductInfo cell : child) {
                if (cell.isChoosed()) {
                    toBeDeleteProducts.add(cell);
                }
            }
            child.removeAll(toBeDeleteProducts);
        }
        groups.removeAll(toBeDeleteGroups);
        sellValue.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

        ProductInfo product = (ProductInfo) sellValue.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        currentCount++;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(String.valueOf(currentCount));

        sellValue.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

        ProductInfo product = (ProductInfo) sellValue.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        if (currentCount == 1) {
            return;
        }
        currentCount--;

        product.setCount(currentCount);
        ((TextView) showCountView).setText(String.valueOf(currentCount));

        sellValue.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> child = children.get(group.getId());
        for (ProductInfo cell : child) {
            cell.setChoosed(isChecked);
        }
        if (isAllCheck()) {
            cbCheckAll.setChecked(true);
        } else {
            cbCheckAll.setChecked(false);
        }
        sellValue.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        // 判断改组下面的所有子元素是否是同一种状态
        boolean allChildSameState = true;
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> child = children.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            // 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
            group.setChoosed(isChecked);
        } else {
            // 否则，组元素一律设置为未选中状态
            group.setChoosed(false);
        }

        if (isAllCheck()) {
            cbCheckAll.setChecked(true);
        } else {
            cbCheckAll.setChecked(false);
        }
        sellValue.notifyDataSetChanged();
        calculate();
    }

    private boolean isAllCheck() {
        for (GroupInfo group : groups) {
            if (!group.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(cbCheckAll.isChecked());
            GroupInfo group = groups.get(i);
            List<ProductInfo> child = children.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChoosed(cbCheckAll.isChecked());
            }
        }
        sellValue.notifyDataSetChanged();
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            List<ProductInfo> child = children.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                ProductInfo product = child.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                }
            }
        }
        tvTotalPrice.setText("￥" + totalPrice);
        tvGoToPay.setText("去支付(" + totalCount + ")");
    }
}
