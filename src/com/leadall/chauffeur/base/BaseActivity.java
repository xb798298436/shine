package com.leadall.chauffeur.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.example.com.leadall.chauffeur.R;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;


public abstract class BaseActivity extends Activity implements OnClickListener,
							OnLongClickListener, OnItemClickListener, OnItemLongClickListener{

	public Gson gson = new Gson();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  
            setTranslucentStatus(true);  
            SystemBarTintManager tintManager = new SystemBarTintManager(this);  
            tintManager.setStatusBarTintEnabled(true);  
            tintManager.setStatusBarTintResource(R.color.white);//֪ͨ��������ɫ
            StatusBarLightMode(this);
        }  	
		setFirst(savedInstanceState);
	}
	
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
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
//	                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//	                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//	                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//	                result = 3;
//	            }
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
//	            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | 
//	            		View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
	 
	 /**
	  * ����ʱ�͵���
	  */
	 public abstract void setFirst(Bundle savedInstanceState);
	 


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}
	
	
	
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initInjectedView(this);
	}

	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initInjectedView(this);
	}

	public void setContentView(View view) {
		super.setContentView(view);
		initInjectedView(this);
	}

	private void initInjectedView(Activity activity) {
		initInjectedView(activity, activity.getWindow().getDecorView());
	}

	private void initInjectedView(Object activity, View sourceView) {
		Field[] fields = activity.getClass().getDeclaredFields(); // ��ȡ�ֶ�
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					field.setAccessible(true); // ��Ϊ�ɷ���
					if (field.get(activity) != null)
						continue;
					BindView bindView = field.getAnnotation(BindView.class);
					if (bindView != null) {
						int viewId = bindView.id();
						if (viewId == 0)
							viewId = getResources().getIdentifier(
									field.getName(), "id", getPackageName());
						if (viewId == 0)
							Log.e("BindActivity", "field " + field.getName()
									+ "not found");
						// �ؼ�,ע���ʼ�����൱�� backBtn = (TextView)
						// findViewById(R.id.back_btn);
						field.set(activity, sourceView.findViewById(viewId));
						// �¼�
						Object obj = field.get(activity);
						if (bindView.click()) {
							if (obj instanceof View) {
								((View) field.get(activity))
										.setOnClickListener((android.view.View.OnClickListener) this);
							}
						}
						if (bindView.itemClick()) {
							if (obj instanceof AbsListView) {
								((AbsListView) obj)
										.setOnItemClickListener(this);
							}
						}
						if (bindView.longClick()) {
							if (obj instanceof View) {
								((View) obj).setOnLongClickListener(this);
							}
						}
						if (bindView.itemLongClick()) {
							if (obj instanceof AbsListView) {
								((AbsListView) obj)
										.setOnItemLongClickListener(this);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		BindOnClick(v);
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return BindOnLongClick(v);
	}

	@Override
	public void onItemClick(AdapterView<?> av, View v, int position, long l) {
		// TODO Auto-generated method stub
		BindOnItemClick(av, v, position, l);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> av, View v, int position,
			long l) {
		// TODO Auto-generated method stub
		return BindOnItemLongClick(av, v, position, l);
	}

	/**
	 * ����¼�
	 * 
	 * @param v
	 */
	public void BindOnClick(View v) {
	};

	/**
	 * �����¼� Ĭ�Ϸ���false
	 * 
	 * @param v
	 * @return
	 */
	public boolean BindOnLongClick(View v) {
		return false;
	};

	/**
	 * �������¼�
	 * 
	 * @param av
	 * @param v
	 * @param position
	 * @param l
	 */
	public void BindOnItemClick(AdapterView<?> av, View v, int position, long l) {
	};

	/**
	 * ������¼� Ĭ�Ϸ���false
	 * 
	 * @param av
	 * @param v
	 * @param position
	 * @param l
	 * @return
	 */
	public boolean BindOnItemLongClick(AdapterView<?> av, View v, int position,
			long l) {
		return false;
	};
	
	
	
	
	
	public void Log(String msg) {
		android.util.Log.i("zwen", msg);
	}
}
