package com.leadall.chauffeur.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.com.leadall.chauffeur.R;
import com.leadall.chauffeur.activity.LoginActivity;
import com.leadall.chauffeur.base.BaseFragment;
import com.leadall.chauffeur.view.TopTitleBar;

public class WaybillFrgment extends BaseFragment{

	private View layout;
	
	@Override
	public View onIntroduceLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		layout = inflater.inflate(R.layout.fragment_waybill, container,false);
		TopTitleBar.setNosetRollbackTitleBar(getActivity(), "ªı‘¥", "±‡º≠", LoginActivity.class);
//		initView();
		return layout;
	}

	@Override
	public void setFirst() {
		// TODO Auto-generated method stub
		
	}

}
