package com.leadall.chauffeur.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;



/**
 * Adapter快速配置
 */
public abstract class QuickAdapter<T> extends BaseAdapter {
	protected final Context context;
	protected final int layoutResId;
	protected final List<T> data;

	/**
	 * 构造方法 <br/>
	 * data默认为空
	 * 
	 * @param context
	 *            上下文
	 * @param layoutResId
	 *            资源文件
	 */
	public QuickAdapter(Context context, int layoutResId) {
		this(context, layoutResId, null);
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            上下文
	 * @param layoutResId
	 *            资源 文件
	 * @param data
	 *            数据集
	 */
	public QuickAdapter(Context context, int layoutResId, List<T> data) {
		this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
		this.context = context;
		this.layoutResId = layoutResId;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public T getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final QuickAdapterHelper helper = QuickAdapterHelper.get(context,
				convertView, parent, layoutResId, position);
		T item = getItem(position);
		helper.setAssociatedObject(item);
		convert(helper, item, position);
		return helper.getView();//把在QuickAdapterHelper加载的整个布局convertView返给填充器
	}

	@Override
	public boolean isEnabled(int position) {
		return position < data.size();
	}

	public void add(T elem) {
		data.add(elem);
		notifyDataSetChanged();
	}

	public void addAll(List<T> elem) {
		data.addAll(elem);
		notifyDataSetChanged();
	}

	public void set(T oldElem, T newElem) {
		set(data.indexOf(oldElem), newElem);
	}

	public void set(int index, T elem) {
		data.set(index, elem);
		notifyDataSetChanged();
	}

	public void remove(T elem) {
		data.remove(elem);
		notifyDataSetChanged();
	}

	public void remove(int index) {
		data.remove(index);
		notifyDataSetChanged();
	}

	public void replaceAll(List<T> elem) {
		data.clear();
		data.addAll(elem);
		notifyDataSetChanged();
	}

	public boolean contains(T elem) {
		return data.contains(elem);
	}

	public void clear() {
		data.clear();
		notifyDataSetChanged();
	}

	/**
	 * 在此处实现对item的操作
	 */
	protected abstract void convert(QuickAdapterHelper helper, T item,int position);
}
