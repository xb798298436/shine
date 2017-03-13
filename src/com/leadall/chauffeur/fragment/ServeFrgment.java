package com.leadall.chauffeur.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.com.leadall.chauffeur.R;
import com.leadall.chauffeur.base.BaseFragment;

public class ServeFrgment extends BaseFragment{

	private View layout;
	
	@Override
	public View onIntroduceLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		layout = inflater.inflate(R.layout.fragment_serve,container,false);
//		initView();
		return layout;
	}

	@Override
	public void setFirst() {
		// TODO Auto-generated method stub
		
	}

}
