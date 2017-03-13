package com.leadall.chauffeur.view;

import com.example.com.leadall.chauffeur.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopTitleBar {
	
	static LinearLayout title_rollback;
	static TextView title_tv_right;
	static TextView title_tv_left;
	static TextView title_tv_logo;
	
	private static void BingDing(Activity activity){
		title_rollback = (LinearLayout) activity.findViewById(R.id.title_rollback);
		title_tv_right = (TextView) activity.findViewById(R.id.title_tv_right);
		title_tv_left = (TextView) activity.findViewById(R.id.title_tv_left);
		title_tv_logo = (TextView) activity.findViewById(R.id.title_tv_logo);
		
	}
	
	/**
	 * 不带回退的标题
	 */
	public static void setNosetRollbackTitleBar(final Activity activity, String title, String left,final Class<?> leftclass){
		
		BingDing(activity);
		
		title_tv_left.setText(left);
		title_tv_logo.setText(title);
		
		title_tv_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent leftIntent = new Intent(activity,leftclass);
//				activity.startActivity(leftIntent);
			}
		});
		
		title_tv_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent leftIntent = new Intent(activity,leftclass);
//				activity.startActivity(leftIntent);
			}
		});
		
		
	}
	
	/**
	 * 自带回退的标题
	 */
	public static void setRollbackTitleBar(final Activity activity, String title, String left,final Class<?> leftclass) {
		
		BingDing(activity);
		
		title_tv_left.setText(left);
		title_tv_logo.setText(title);
		title_rollback.setVisibility(View.VISIBLE);
		
		title_rollback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				activity.finish();
			}
		});
		
		title_tv_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent leftIntent = new Intent(activity,leftclass);
				activity.startActivity(leftIntent);
			}
		});	
		
		title_tv_logo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent leftIntent = new Intent(activity,leftclass);
				activity.startActivity(leftIntent);
			}
		});

	}
	
	
}