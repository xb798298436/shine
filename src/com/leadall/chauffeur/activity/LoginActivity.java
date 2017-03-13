package com.leadall.chauffeur.activity;

import android.os.Bundle;

import com.example.com.leadall.chauffeur.R;
import com.leadall.chauffeur.base.BaseActivity;
import com.leadall.chauffeur.base.ChauffeurApplication;

public class LoginActivity extends BaseActivity{

	@Override
	public void setFirst(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_login);
		ChauffeurApplication.getInstance().addActivity(this);
	}

}
