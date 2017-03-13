package com.leadall.chauffeur.base.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

public class QuickPagerAdapter<T> extends FragmentStatePagerAdapter{

		private List<T> data;
   
	    public QuickPagerAdapter(FragmentManager fm) {
	    	// TODO Auto-generated constructor stub
			super(fm);
			data = new ArrayList<T>();	
		}

//	    @Override
//		public Fragment getItem(int position) {
//			BannerItemFragment fragment = new BannerItemFragment();
//			fragment.setResId((Integer) data.get(position % data.size()));
//			return fragment;
//		}
	    
	    //运行次数
	    @Override
	    public int getCount() {
	        return 10000;
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

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

	}