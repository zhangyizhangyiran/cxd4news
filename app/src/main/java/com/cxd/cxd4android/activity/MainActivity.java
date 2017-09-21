package com.cxd.cxd4android.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MainBoutFragment;
import com.cxd.cxd4android.fragment.MainInvestmentFragment;
import com.cxd.cxd4android.fragment.MainMeFragment;
import com.cxd.cxd4android.fragment.MainServiceFragment;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.interfaces.ILoginCallBack;
import com.cxd.cxd4android.interfaces.IViewPagerCallBack;
import com.cxd.cxd4android.widget.maintab.TabView;
import com.cxd.cxd4android.widget.scrollview.NoScrollViewPager;
import com.micros.utils.S;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 15-11-23.
 */
public class MainActivity extends BaseActivity {

    private String[] mTitle = {"精选", "投资", "服务", "我"};
    private int[] mIconSelect = {R.mipmap.ic_main_bout1, R.mipmap.ic_main_inves1, R.mipmap.ic_main_se1, R.mipmap.ic_main_me1};
    private int[] mIconNormal = {R.mipmap.ic_main_bout2, R.mipmap.ic_main_inves2, R.mipmap.ic_main_se2, R.mipmap.ic_main_me2};
    private Map<Integer, Fragment> mFragmentMap;
    public static MainActivity mainActivity;

    @Bind(R.id.id_view_pager)
    NoScrollViewPager mViewPager;
    @Bind(R.id.id_tab)
    TabView mTabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检测应用版本
//        UpdateHelper.getInstance().init(this, Color.parseColor("#0A93DB"));
//        UpdateHelper.getInstance().autoUpdate(userModel.getAppPackageName());

        mainActivity = this;
        setContentView(R.layout.activity_layout_main_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

//        ViewUtils.inject(this);
        mFragmentMap = new HashMap<>();
        mViewPager = (NoScrollViewPager) findViewById(R.id.id_view_pager);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));

//        mTabView = (TabView)findViewById(R.id.id_tab) ;
        mTabView.setViewPager(mViewPager);

        //初始化LocalUserModel
        userModel.setGestrueIsFirst("no");


        //检查APP权限
        if (Build.VERSION.SDK_INT < 23) {
            userModel.setREAD_PHONE_STATE("READ_PHONE_STATE");
        }
        checkSelfPermission(this);



    }

    /**
     * 事件响应方法,禁止ViewPager滑动
     */
    @Subscribe
    public void onEvent(IViewPagerCallBack event) {
        if (("DOWN").equals(event.getViewPager())) {
            mViewPager.setNoScroll(true);
        } else if (("UP").equals(event.getViewPager())) {
            mViewPager.setNoScroll(false);
        }
    }

    /**
     * 事件响应方法,地下TabView切换,登录后回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ILoginCallBack event) {
        //

        mTabView.setCurrentItemView(event.getCurrentItem());

    }

    public Fragment getFragment(int position) {
        Fragment fragment = mFragmentMap.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new MainBoutFragment();
                    StatService.onEvent(MainActivity.this, BaiDustatistic.bout, "", 1);//事件统计
                    break;
                case 1:
                    fragment = new MainInvestmentFragment();
//                    fragment = new MainPageInvestFragment();
                    StatService.onEvent(MainActivity.this, BaiDustatistic.invest, "", 1);//事件统计
                    break;
                case 2:
                    fragment = new MainServiceFragment();
                    StatService.onEvent(MainActivity.this, BaiDustatistic.se, "", 1);//事件统计
                    break;
                case 3:
                    fragment = new MainMeFragment();
                    StatService.onEvent(MainActivity.this, BaiDustatistic.me, "", 1);//事件统计
                    break;
            }
            mFragmentMap.put(position, fragment);
        }
        return fragment;
    }

    class PageAdapter extends FragmentPagerAdapter implements TabView.OnItemIconTextSelectListener {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public int[] onIconSelect(int position) {
            int icon[] = new int[2];
            icon[0] = mIconSelect[position];
            icon[1] = mIconNormal[position];
            return icon;
        }

        @Override
        public String onTextSelect(int position) {
            return mTitle[position];
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }
    }

    private Intent onHomeIntent; // home键退出后通过intent启动程序

    @Override
    protected void onNewIntent(Intent intent) {
// 拦截Intent，保存Intent，在onResume中进行处理
        onHomeIntent = intent;
    }

    @Override
    public void onResume() {

        String k = (String) S.get(this, "k", "");
        if (k.equals("first")) {
            EventBus.getDefault().post("cxd");
        }


        if (onHomeIntent != null) { // home键退出后通过intent启动程序
// dosomething···
            onHomeIntent = null;
        }
        super.onResume();
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数

        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            T.D("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {

            finish();
            System.exit(0);
            /**
             * 当应用在后台运行超过30秒（默认）再回到前端，将被认为是两个独立的session(启动)，
             * 例如用户回到home，或进入其他程序，经过一段时间后再返回之前的应用。
             * 可通过接口：MobclickAgent.setSessionContinueMillis(long interval)
             * 来自定义这个间隔（参数单位为毫秒）。
             */
            //MobclickAgent.setSessionContinueMillis(long interval);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


}
