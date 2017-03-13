package com.leadall.chauffeur.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;



/**
 * Adapter��������
 */
public abstract class QuickAdapter<T> extends BaseAdapter {
	protected final Context context;
	protected final int layoutResId;
	protected final List<T> data;

	/**
	 * ���췽�� <br/>
	 * dataĬ��Ϊ��
	 * 
	 * @param context
	 *            ������
	 * @param layoutResId
	 *            ��Դ�ļ�
	 */
	public QuickAdapter(Context context, int layoutResId) {
		this(context, layoutResId, null);
	}

	/**
	 * ���췽��
	 * 
	 * @param context
	 *            ������
	 * @param layoutResId
	 *            ��Դ �ļ�
	 * @param data
	 *            ���ݼ�
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
		return helper.getView();//����QuickAdapterHelper���ص���������convertView���������
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
	 * �ڴ˴�ʵ�ֶ�item�Ĳ���
	 */
	protected abstract void convert(QuickAdapterHelper helper, T item,int position);
}
