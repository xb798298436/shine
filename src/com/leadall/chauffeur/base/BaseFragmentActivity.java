package com.leadall.chauffeur.base;


import com.example.com.leadall.chauffeur.R;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @author ��ȡ��
 *
 */
@SuppressLint("NewApi")
public abstract class BaseFragmentActivity extends FragmentActivity {

	/**
     * Ϊfragment����functions������ʵ����������
     * @param fragmentId
     */
    public void setFunctionsForFragment(int fragmentId){}
	public Gson gson = new Gson();

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);			
			tintManager.setStatusBarTintResource(R.color.white);// ֪ͨ��������ɫ
			StatusBarLightMode(this);
		}
		setFirst(savedInstanceState);
		setParameters();
	};

	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
//		  //͸��״̬��  
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
//        //͸��������  
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); 
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	/**
     * ����״̬����ɫ����ͼ�꣬
     * ����4.4���ϰ汾MIUI6��Flyme��6.0���ϰ汾����Android
     *
     * @param activity
     * @return 1:MIUI 2:Flyme 3:android6.0
     */
    public static int StatusBarLightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            }
//                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 
//                		|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                    );
//                result = 3;
//            }
        }
        return result;
    }
    
    /**
     * ��֪ϵͳ����ʱ������״̬����ɫ����ͼ�ꡣ
     * ����4.4���ϰ汾MIUIV��Flyme��6.0���ϰ汾����Android
     *
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 3) {
//            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | 
//            		View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }
    
    /**
     * ���MIUI��flyme��6.0���ϰ汾״̬����ɫ����
     */
    public static void StatusBarDarkMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }
    
	/**
	 * ����״̬������ͼ��Ϊ��ɫ����ҪMIUI6����
	 * @param window ��Ҫ���õĴ���
	 * @param dark �Ƿ��״̬�����弰ͼ����ɫ����Ϊ��ɫ
	 * @return  boolean �ɹ�ִ�з���true
	 *
	 */
	public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
	    boolean result = false;
	    if (window != null) {
	        Class clazz = window.getClass();
	        try {
	            int darkModeFlag = 0;
	            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
	            Field  field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
	            darkModeFlag = field.getInt(layoutParams);
	            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
	            if(dark){
	                extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//״̬��͸���Һ�ɫ����
	            }else{
	                extraFlagField.invoke(window, 0, darkModeFlag);//�����ɫ����
	            }
	            result=true;
	        }catch (Exception e){

	        }
	    }
	    return result;
	}

	/**
	 * ����״̬��ͼ��Ϊ��ɫ�������ض������ַ��
	 * ���������ж��Ƿ�ΪFlyme�û�
	 * @param window ��Ҫ���õĴ���
	 * @param dark �Ƿ��״̬�����弰ͼ����ɫ����Ϊ��ɫ
	 * @return  boolean �ɹ�ִ�з���true
	 *
	 */
	public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
	    boolean result = false;
	    if (window != null) {
	        try {
	            WindowManager.LayoutParams lp = window.getAttributes();
	            Field darkFlag = WindowManager.LayoutParams.class
	                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
	            Field meizuFlags = WindowManager.LayoutParams.class
	                    .getDeclaredField("meizuFlags");
	            darkFlag.setAccessible(true);
	            meizuFlags.setAccessible(true);
	            int bit = darkFlag.getInt(null);
	            int value = meizuFlags.getInt(lp);
	            if (dark) {
	                value |= bit;
	            } else {
	                value &= ~bit;
	            }
	            meizuFlags.setInt(lp, value);
	            window.setAttributes(lp);
	            result = true;
	        } catch (Exception e) {

	        }
	    }
	    return result;
	}	
	
	@Override
	protected void onDestroy() {
		setContentView(R.layout.ui_null);
		System.gc();
		super.onDestroy();
	}
	
	 
	/**
	 * ����ʱ�͵���
	 */
	public abstract void setFirst(Bundle savedInstanceState);

	/**
	 * ���ò���
	 */
	public abstract void setParameters();

	public void L(String msg) {
		android.util.Log.i("zwen", msg);
	}
	
	public void O(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
