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
	 * �ж������Ƿ����
	 * ���Ƿ�����WIFI�����ж�
	 * @return true, ���ã� false�� ������
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
	 * ��ȡIP��ַ
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
	 * ��ȡ��ǰ���õĵ绰����
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
	 * ��ȡ�ֻ�IMEI
	 * 
	 */
	public static String getIMEI(Context context) {
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
				.getDeviceId();

	}
	
	/**
	 * 
	 * ��ȡ�ֻ�SDK��
	 * 
	 */
	public static String getSDK() {
		return android.os.Build.VERSION.SDK;    // SDK��

	}
	
	/**
	 * 
	 * ��ȡ�ֻ��ͺ�
	 * 
	 */
	public static String getMODEL() {
		return android.os.Build.MODEL;   // �ֻ��ͺ�

	}

	/**
	 * 
	 * ��ȡandroidϵͳ�汾��
	 * 
	 */
	public static String getAndroidVERSIONS () {
		return android.os.Build.VERSION.RELEASE;  // androidϵͳ�汾��

	}
	
	/**
	 * 
	 * ��ȡApp�汾��
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
	 * ��ȡApp�汾��
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
     * �ж�ָ����Ӧ���Ƿ���ǰ̨����
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
     * ����ǰӦ�����е�ǰ̨
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
     * ����ǰӦ�����е�ǰ̨
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
     * �ص�ϵͳ����
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
     * ϵͳ�Ƿ�������״̬
     *
     * @return
     */
    private boolean isScreenLocked(Context mContext) {
        KeyguardManager keyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }
    
    /**
     * 
     * ����������ʱ����
     * @param mContext
     * 
     */
    private void wakeAndUnlock(Context mContext) {
    	KeyguardManager.KeyguardLock kl;
        //��ȡ��Դ����������
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        //��ȡPowerManager.WakeLock���󣬺���Ĳ���|��ʾͬʱ��������ֵ�������ǵ����õ�Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        //������Ļ
        wl.acquire(1000);
        //�õ�����������������
        KeyguardManager km = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("unLock");
        //����
        kl.disableKeyguard();
    }
    
//    private void release() {
//        if (locked && kl != null) {
//            android.util.Log.d("maptrix", "release the lock");
//            //�õ�����������������
//            kl.reenableKeyguard();
//            locked = false;
//        }
//    }

    /**
     * 
     * ��ȡ��һ����Activity��
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
