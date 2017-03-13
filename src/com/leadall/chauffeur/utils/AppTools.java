package com.leadall.chauffeur.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.telephony.TelephonyManager;

public class AppTools {

	/**
	 * 
	 * 判断网络是否可用
	 * 对是否连接WIFI进行判断
	 * @return true, 可用； false， 不可用
	 */
	public static boolean netWorkAvailable(Context mContext) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetInfo = connectivityManager
					.getActiveNetworkInfo();
			if (activeNetInfo != null
					&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return true;
			}else {
				if (activeNetInfo != null 
						&& activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {  
			        return true;  
			    }  
			}
		} catch (Exception e) {
			// TODO: handle exception		
		}
		return false;
	} 
	
	/**
	 * 
	 * 获取IP地址
	 * 
	 */
	public static String GetHostIp(Context mContext) {
		try {
			if (netWorkAvailable(mContext)) {
				WifiManager wifiManager = (WifiManager) mContext
						.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				int ipAddress = wifiInfo.getIpAddress();
				if (ipAddress == 0)
					return null;
				return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff)
						+ "." + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
			}else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}  
	/**
	 * 
	 * 获取当前设置的电话号码
	 * 
	 */
	public static String getNativePhoneNumber(Context context) {
		String NativePhoneNumber = null;
		NativePhoneNumber = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
		return NativePhoneNumber;
	}

	/**
	 * 
	 * 获取手机IMEI
	 * 
	 */
	public static String getIMEI(Context context) {
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
				.getDeviceId();

	}
	
	/**
	 * 
	 * 获取手机SDK号
	 * 
	 */
	public static String getSDK() {
		return android.os.Build.VERSION.SDK;    // SDK号

	}
	
	/**
	 * 
	 * 获取手机型号
	 * 
	 */
	public static String getMODEL() {
		return android.os.Build.MODEL;   // 手机型号

	}

	/**
	 * 
	 * 获取android系统版本号
	 * 
	 */
	public static String getAndroidVERSIONS () {
		return android.os.Build.VERSION.RELEASE;  // android系统版本号

	}
	
	/**
	 * 
	 * 获取App版本号
	 * 
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageInfo mPackageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return mPackageInfo.versionCode;
		} catch (NameNotFoundException e) {
		}
		return 1;
	}

	/**
	 * 
	 * 获取App版本名
	 * 
	 */
	public static String getVersionName(Context context) {
		try {
			PackageInfo mPackageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return mPackageInfo.versionName;
		} catch (NameNotFoundException e) {
		}
		return "1.0";
	}

	/**
     * 判断指定的应用是否在前台运行
     *
     * @param packageName
     * @return
     */
    private boolean isAppForeground(String packageName,Context mContext) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (ChauffeurUtils.isNotEmpty(currentPackageName) && currentPackageName.equals(packageName)) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 将当前应用运行到前台
     * 
     */
    private void bring2Front(Context mContext) {
        ActivityManager activtyManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activtyManager.getRunningTasks(3);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos) {
            if (mContext.getPackageName().equals(runningTaskInfo.topActivity.getPackageName())) {
                activtyManager.moveTaskToFront(runningTaskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
                return;
            }
        }
    }
    /**
     * 
     * 将当前应用运行到前台
     * 
     */
    public static boolean isInBackground(Context context) {
		boolean isInBackground =true;
		List<RunningTaskInfo> tasksInfo = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			if (context.getPackageName().equals(
					tasksInfo.get(0).topActivity.getPackageName())) {
				isInBackground =  false;
			}
		}
		tasksInfo.clear();
		tasksInfo = null;
		return isInBackground;
	}
    
    /**
     * 
     * 回到系统桌面
     * 
     */
    private void back2Home(Context mContext) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);
        mContext.startActivity(home);
    }
    
    /**
     * 
     * 系统是否在锁屏状态
     *
     * @return
     */
    private boolean isScreenLocked(Context mContext) {
        KeyguardManager keyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }
    
    /**
     * 
     * 无密码锁屏时解锁
     * @param mContext
     * 
     */
    private void wakeAndUnlock(Context mContext) {
    	KeyguardManager.KeyguardLock kl;
        //获取电源管理器对象
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        //点亮屏幕
        wl.acquire(1000);
        //得到键盘锁管理器对象
        KeyguardManager km = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
    }
    
//    private void release() {
//        if (locked && kl != null) {
//            android.util.Log.d("maptrix", "release the lock");
//            //得到键盘锁管理器对象
//            kl.reenableKeyguard();
//            locked = false;
//        }
//    }

    /**
     * 
     * 获取第一个打开Activity名
     * @param context
     * @return
     * 
     */
	public static String getTopActivityName(Context context) {
		String topActivityClassName = null;
		ActivityManager activityManager = (ActivityManager) (context
				.getSystemService(android.content.Context.ACTIVITY_SERVICE));
		List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
		if (runningTaskInfos != null) {
			ComponentName f = runningTaskInfos.get(0).topActivity;
			topActivityClassName = f.getClassName();
		}
		return topActivityClassName;
	}

	
	
	 
}
