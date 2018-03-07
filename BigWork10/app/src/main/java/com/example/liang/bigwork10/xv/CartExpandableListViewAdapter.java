package com.example.liang.bigwork10.xv;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.liang.bigwork10.R;

import java.util.List;
import java.util.Map;


/**
 * @author liang
 */
public class CartExpandableListViewAdapter extends BaseExpandableListAdapter
{
	private List<GroupInfo> groups;
	private Map<String, List<ProductInfo>> children;
	private Context context;
	//HashMap<Integer, View> groupMap = new HashMap<Integer, View>();
	//HashMap<Integer, View> childrenMap = new HashMap<Integer, View>();
	private CheckInterface checkInterface;
	private ModifyCountInterface modifyCountInterface;

	/**
	 * 构造函数
	 * 
	 * @param groups
	 *            组元素列表
	 * @param children
	 *            子元素列表
	 * @param context
	 */
	public CartExpandableListViewAdapter(List<GroupInfo> groups, Map<String, List<ProductInfo>> children, Context context)
	{
		super();
		this.groups = groups;
		this.children = children;
		this.context = context;
	}

	public void setCheckInterface(CheckInterface checkInterface)
	{
		this.checkInterface = checkInterface;
	}

	public void setModifyCountInterface(ModifyCountInterface modifyCountInterface)
	{
		this.modifyCountInterface = modifyCountInterface;
	}

	@Override
	public int getGroupCount()
	{
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		String groupId = groups.get(groupPosition).getId();
		return children.get(groupId).size();
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		List<ProductInfo> childs = children.get(groups.get(groupPosition).getId());

		return childs.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return 0;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{

		GroupHolder gholder;
		if (convertView == null)
		{
			gholder = new GroupHolder();
			convertView = View.inflate(context, R.layout.item_shopcart_group, null);
			gholder.cb_check = (CheckBox) convertView.findViewById(R.id.determine_chekbox);
			gholder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_source_name);
			//groupMap.put(groupPosition, convertView);
			 convertView.setTag(gholder);
		} else
		{
			// convertView = groupMap.get(groupPosition);
			gholder = (GroupHolder) convertView.getTag();
		}
		final GroupInfo group = (GroupInfo) getGroup(groupPosition);
		if (group != null)
		{
			gholder.tv_group_name.setText(group.getName());
			gholder.cb_check.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)

				{
					group.setSelected(((CheckBox) v).isChecked());
					checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
				}
			});
			gholder.cb_check.setChecked(group.isSelected());
		}
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{

		final ChildHolder cholder;
		if (convertView == null)
		{
			cholder = new ChildHolder();
			convertView = View.inflate(context, R.layout.item_shopcart_product, null);
			cholder.cb_check = (CheckBox) convertView.findViewById(R.id.check_box);

			cholder.tv_product_desc = (TextView) convertView.findViewById(R.id.tv_intro);
			cholder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			cholder.iv_increase = (TextView) convertView.findViewById(R.id.tv_add);
			cholder.iv_decrease = (TextView) convertView.findViewById(R.id.tv_reduce);
			cholder.tv_count = (TextView) convertView.findViewById(R.id.tv_num);
			// childrenMap.put(groupPosition, convertView);
			convertView.setTag(cholder);
		} else
		{
			// convertView = childrenMap.get(groupPosition);
			cholder = (ChildHolder) convertView.getTag();
		}
		final ProductInfo product = (ProductInfo) getChild(groupPosition, childPosition);

		if (product != null)
		{

			cholder.tv_product_desc.setText(product.getDesc());
			cholder.tv_price.setText("￥" + product.getPrice() + "");
			cholder.tv_count.setText(product.getCount() + "");
			cholder.cb_check.setChecked(product.isSelected());
			cholder.cb_check.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					product.setSelected(((CheckBox) v).isChecked());
					cholder.cb_check.setChecked(((CheckBox) v).isChecked());
					checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
				}
			});
			cholder.iv_increase.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					modifyCountInterface.doIncrease(groupPosition, childPosition, cholder.tv_count, cholder.cb_check.isChecked());// 暴露增加接口
				}
			});
			cholder.iv_decrease.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					modifyCountInterface.doDecrease(groupPosition, childPosition, cholder.tv_count, cholder.cb_check.isChecked());// 暴露删减接口
				}
			});
		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return false;
	}

	/**
	 * 组元素绑定器
	 * 
	 * 
	 */
	private class GroupHolder
	{
		CheckBox cb_check;
		TextView tv_group_name;
	}

	/**
	 * 子元素绑定器
	 * 
	 * 
	 */
	private class ChildHolder
	{
		CheckBox cb_check;

		TextView tv_product_name;
		TextView tv_product_desc;
		TextView tv_price;
		TextView iv_increase;
		TextView tv_count;
		TextView iv_decrease;
	}

	/**
	 * 复选框接口
	 * 
	 * 
	 */
	public interface CheckInterface
	{
		/**
		 * 组选框状态改变触发的事件
		 * 
		 * @param groupPosition
		 *            组元素位置
		 * @param isChecked
		 *            组元素选中与否
		 */
		public void checkGroup(int groupPosition, boolean isChecked);

		/**
		 * 子选框状态改变时触发的事件
		 * 
		 * @param groupPosition
		 *            组元素位置
		 * @param childPosition
		 *            子元素位置
		 * @param isChecked
		 *            子元素选中与否
		 */
		public void checkChild(int groupPosition, int childPosition, boolean isChecked);
	}

	/**
	 * 改变数量的接口
	 * 
	 * 
	 */
	public interface ModifyCountInterface
	{
		/**
		 * 增加操作
		 * 
		 * @param groupPosition
		 *            组元素位置
		 * @param childPosition
		 *            子元素位置
		 * @param showCountView
		 *            用于展示变化后数量的View
		 * @param isChecked
		 *            子元素选中与否
		 */
		public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

		/**
		 * 删减操作
		 * 
		 * @param groupPosition
		 *            组元素位置
		 * @param childPosition
		 *            子元素位置
		 * @param showCountView
		 *            用于展示变化后数量的View
		 * @param isChecked
		 *            子元素选中与否
		 */
		public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);
	}

}
