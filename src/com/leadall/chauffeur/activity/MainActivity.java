package com.leadall.chauffeur.activity;

import com.example.com.leadall.chauffeur.R;
import com.leadall.chauffeur.base.BaseFragmentActivity;
import com.leadall.chauffeur.base.ChauffeurApplication;
import com.leadall.chauffeur.fragment.PersonalFragment;
import com.leadall.chauffeur.fragment.ServeFrgment;
import com.leadall.chauffeur.fragment.WaybillFrgment;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends BaseFragmentActivity {
	
	private long exitTime; // 是否连续点击返回键
	private FragmentTabHost mTabHost;	
	private Class[] mFragments = new Class[] {WaybillFrgment.class,
			ServeFrgment.class, PersonalFragment.class };
	private int[] mTabSelectors = new int[] {R.drawable.waybill_btn,
			R.drawable.serve_btn,R.drawable.personal_btn};
	private String[] mTabSpecs = new String[] {"货源","服务","我的中心"};

	
	@Override
	public void setFirst(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
		ChauffeurApplication.getInstance().addActivity(this);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		initTab();
	}
	
	/**
	 * 初始化组件
	 */
	private void initTab(){
		//得到fragment的个数
		int count = mFragments.length;	
		for (int i = 0; i < count; i++) {
			View tabIndicator = getLayoutInflater().inflate(R.layout.ui_icon, null);
			ImageView imageView = (ImageView) tabIndicator.findViewById(R.id.iv_icon);
			imageView.setImageResource(mTabSelectors[i]);

			mTabHost.addTab(
					mTabHost.newTabSpec(mTabSpecs[i])
							.setIndicator(tabIndicator), mFragments[i], null);
		}
	}
				
	
	
	@Override
	public void setParameters() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// 若当前不在主页，则先返回主页
			if (mTabHost.getCurrentTab() != 0) {
				mTabHost.setCurrentTab(0);
				return false;
			}
			// 双击返回桌面，默认返回true，调用finish() 
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	        	O("再次点击退出程序");                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	        	ChauffeurApplication.getInstance().exit();//完全退出App
	        }
	        return true;   

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
