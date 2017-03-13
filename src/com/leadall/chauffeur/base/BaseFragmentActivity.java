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
 * @author 读取中
 *
 */
@SuppressLint("NewApi")
public abstract class BaseFragmentActivity extends FragmentActivity {

	/**
     * 为fragment设置functions，具体实现子类来做
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
			tintManager.setStatusBarTintResource(R.color.white);// 通知栏所需颜色
			StatusBarLightMode(this);
		}
		setFirst(savedInstanceState);
		setParameters();
	};

	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
//		  //透明状态栏  
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
//        //透明导航栏  
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
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUI6、Flyme和6.0以上版本其他Android
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
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
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
     * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
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
	 * 设置状态栏字体图标为深色，需要MIUI6以上
	 * @param window 需要设置的窗口
	 * @param dark 是否把状态栏字体及图标颜色设置为深色
	 * @return  boolean 成功执行返回true
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
	                extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
	            }else{
	                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
	            }
	            result=true;
	        }catch (Exception e){

	        }
	    }
	    return result;
	}

	/**
	 * 设置状态栏图标为深色和魅族特定的文字风格
	 * 可以用来判断是否为Flyme用户
	 * @param window 需要设置的窗口
	 * @param dark 是否把状态栏字体及图标颜色设置为深色
	 * @return  boolean 成功执行返回true
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
	 * 创建时就调用
	 */
	public abstract void setFirst(Bundle savedInstanceState);

	/**
	 * 设置参数
	 */
	public abstract void setParameters();

	public void L(String msg) {
		android.util.Log.i("zwen", msg);
	}
	
	public void O(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
