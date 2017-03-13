package com.leadall.chauffeur.base;


import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.google.gson.Gson;


public abstract class BaseFragment extends Fragment implements OnClickListener,
									OnLongClickListener, OnItemClickListener, OnItemLongClickListener{
	
	public Gson gson = new Gson();
	protected BaseFragmentActivity mBaseFragmentActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = onIntroduceLayout(inflater, container, savedInstanceState);
		initInjectedView(this, v);		
		return v;
	}
	
	/**
	 * 返回fragment的布局
	 * 
	 * @return
	 */
	public abstract View onIntroduceLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setFirst();
	}
	
	public abstract void setFirst();
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseActivity){
        	mBaseFragmentActivity = (BaseFragmentActivity)activity;
        	mBaseFragmentActivity.setFunctionsForFragment(getId());
        }
    }
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
 

	private void initInjectedView(Object activity, View sourceView) {
		Field[] fields = activity.getClass().getDeclaredFields(); // 获取字段
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					field.setAccessible(true); // 设为可访问
					if (field.get(activity) != null)
						continue;
					BindView bindView = field.getAnnotation(BindView.class);
					if (bindView != null) {
						int viewId = bindView.id();
						if (viewId == 0)
							viewId = getResources().getIdentifier(
									field.getName(), "id",
									getActivity().getPackageName());
						if (viewId == 0)					
							Log.e("BindActivity", "field " + field.getName()
									+ "not found");
						// 关键,注解初始化，相当于 backBtn = (TextView)
						field.set(activity, sourceView.findViewById(viewId));
						// 事件
						Object obj = field.get(activity);
						if (bindView.click()) {
							if (obj instanceof View) {
								((View) field.get(activity))
										.setOnClickListener(this);
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
	 * 点击事件
	 * 
	 * @param v
	 */
	public void BindOnClick(View v) {
	};

	/**
	 * 长按事件 默认返回false
	 * 
	 * @param v
	 * @return
	 */
	public boolean BindOnLongClick(View v) {
		return false;
	};

	/**
	 * 子项点击事件
	 * 
	 * @param av
	 * @param v
	 * @param position
	 * @param l
	 */
	public void BindOnItemClick(AdapterView<?> av, View v, int position, long l) {
	};

	/**
	 * 子项长按事件 默认返回false
	 * 
	 * @param av
	 * @param v
	 * @param position
	 * @param l
	 * @return
	 */
	public boolean BindOnItemLongClick(AdapterView<?> av, View v, int position, long l) {
		return false;
	};

	public void L(String msg) {
		Log.i("zwen", msg);
	}

	public void O(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}


}
