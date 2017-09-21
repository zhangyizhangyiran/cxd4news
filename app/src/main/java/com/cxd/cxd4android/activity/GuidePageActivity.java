package com.cxd.cxd4android.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.adapter.GuideViewPagerAdapter;
import com.cxd.cxd4android.global.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 
 * @{# GuideActivity.java Create on 2015-3-26
 *   class desc: 引导界面
 * @Author Rongxh
 */
public class GuidePageActivity extends BaseActivity implements OnPageChangeListener {
	@Bind(R.id.viewpager)
	ViewPager vp;
	private GuideViewPagerAdapter vpAdapter;
	private List<View> views;
	// 底部小点图片
	private ImageView[] dots;
	// 记录当前选中位置
	private int currentIndex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		ButterKnife.bind(this);

		// 初始化页面
		initViews();
		// 初始化底部小点
		 initDots();
	}
	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.what_new_one, null));
		views.add(inflater.inflate(R.layout.what_new_two, null));
		views.add(inflater.inflate(R.layout.what_new_three, null));
		views.add(inflater.inflate(R.layout.what_new_four, null));
		// 初始化Adapter
		vpAdapter = new GuideViewPagerAdapter(views, this);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		//vp.setOnPageChangeListener(this);
		vp.addOnPageChangeListener(this);
	}
	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		dots = new ImageView[views.size()];
		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}
	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}
		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);
		currentIndex = position;
	}
	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurrentDot(arg0);
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		/**
		 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 与StatService.onResume(this)类似;
		 */
		StatService.onPageStart(this, "欢迎页面");//(this);
	}

	public void onPause() {
		super.onPause();

		/**
		 * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 与StatService.onPause(this)类似;
		 */
		StatService.onPageEnd(this, "欢迎页面");//(this);
	}
}
