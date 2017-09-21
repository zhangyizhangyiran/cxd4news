package com.cxd.cxd4android.global;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.cxd.cxd4android.R;

import com.micros.ui.widget.MicroAVLIDialog;
import com.micros.utils.T;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment{


    /**
     * Fragment跳转控制器
     */
//	public IFragmentAction actionHandler;

    /**
     * 加载对话框
     */
    public Context context;

    public T T;
    public MicroAVLIDialog P;

    public View contentView = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        T = new T(getActivity());
//
        P = new MicroAVLIDialog(getActivity());

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, contentView);
        //只要在Fragment中重写onTouch方法(Fragment要实现OnTouchListener).返回true.
        //给view添加上onTouchListener,这样就能让fragment点击的时候不再穿透到上一层的Fragment中去了
        if (contentView != null) {
            contentView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }
    /**
     * 开启刷新
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    protected void startRefresh(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    /**
     * 结束刷新
     */
    protected void stopRefresh(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (contentView != null) {
            //移除当前视图，防止重复加载相同视图使得程序闪退
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    //	public String TStr(int resId) {
//		return this.getResources().getString(resId);
//	}
//
//	public View F(int id) {
//		return view.findViewById(id);
//	}
//
//    public abstract View initView(LayoutInflater inflater);
//
//	public abstract void initData(Bundle savedInstanceState);
//
//	public void switchFragment(int res, Fragment fragment) {
//		getFragmentManager().beginTransaction().replace(res, fragment).commit();
//
//	}


//	@Override
//	public void goBack() {
//		// TODO Auto-generated method stub
//		if (actionHandler != null) {
//			actionHandler.goBack();
//		}
//	}
//
//	@Override
//	public void goPrevious() {
//		// TODO Auto-generated method stub
//		if (actionHandler != null) {
//			actionHandler.goPrevious();
//		}
//	}


    /**
     * Fragment添加控制
     *
     * @param containerViewId
     * @param fragment
     * @param stackName
     */
    public void add(int containerViewId, BaseFragment fragment, String tag, String stackName) {
        if (!fragment.isAdded())
        getActivity().getSupportFragmentManager().beginTransaction().add(containerViewId, fragment, tag).addToBackStack(stackName).commitAllowingStateLoss();
    }

    /**
     * Fragment添加控制
     *
     * @param containerViewId
     * @param fragment
     * @param stackName
     */
    public void replace(int containerViewId, BaseFragment fragment, String tag, String stackName) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, tag).addToBackStack(stackName).commitAllowingStateLoss();
    }

    /**
     * Fragment移除控制
     *
     * @param tag
     */
    public void remove(String tag) {
        getActivity().getSupportFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(tag)).commitAllowingStateLoss();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
