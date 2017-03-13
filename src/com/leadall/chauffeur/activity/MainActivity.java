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
	
	private long exitTime; // �Ƿ�����������ؼ�
	private FragmentTabHost mTabHost;	
	private Class[] mFragments = new Class[] {WaybillFrgment.class,
			ServeFrgment.class, PersonalFragment.class };
	private int[] mTabSelectors = new int[] {R.drawable.waybill_btn,
			R.drawable.serve_btn,R.drawable.personal_btn};
	private String[] mTabSpecs = new String[] {"��Դ","����","�ҵ�����"};

	
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
	 * ��ʼ�����
	 */
	private void initTab(){
		//�õ�fragment�ĸ���
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
			// ����ǰ������ҳ�����ȷ�����ҳ
			if (mTabHost.getCurrentTab() != 0) {
				mTabHost.setCurrentTab(0);
				return false;
			}
			// ˫���������棬Ĭ�Ϸ���true������finish() 
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	        	O("�ٴε���˳�����");                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	        	ChauffeurApplication.getInstance().exit();//��ȫ�˳�App
	        }
	        return true;   

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
