//package com.cxd.cxd4android.fragment;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.cxd.cxd4android.R;
//import com.cxd.cxd4android.global.BaseFragment;
//import com.cxd.cxd4android.interfaces.IPageSelectCallBack;
//import com.cxd.cxd4android.widget.view.SlidingTabLayout;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import butterknife.Bind;
//
///**
// * @version V1.0
// * @ClassName: 投资页面 - 主fragment
// * @Description:
// * @Author：xiaofa
// * @Date：2016/6/24 11:21
// */
//public class MainPageInvestFragment extends BaseFragment {
//    //    private List<Fragment> mFragmentList = new ArrayList<>();
//    private Map<Integer, android.support.v4.app.Fragment> mFragmentMap;
//    /**
//     * Tab的那个引导线
//     */
//    @Bind(R.id.pager)
//     ViewPager pager;
//    @Bind(R.id.tab)
//     SlidingTabLayout tab ;
//    /** 投资正中间标题 **/
//    @Bind(R.id.tv_title)
//     TextView tv_title;
//    /** 投资右上角全部 **/
//    @Bind(R.id.Btn_right)
//     TextView Btn_right;
//    private int page=0;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        if (contentView == null) {
//            contentView = inflater.inflate(R.layout.fragment_page_invest, container, false);
//            return contentView;
//        }
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        //初始化状态栏title
//        initStatusBar();
//        init();
////        initTabLineWidth();
//    }
//    private void initStatusBar() {
//        tv_title.setText("产品");
//        Btn_right.setVisibility(View.VISIBLE);
//        Btn_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                EventBus.getDefault().post(new IPageSelectCallBack(page));
//            }
//        });
//    }
//    /**
//     * 初始化数据
//     */
//    private void init() {
//        mFragmentMap=new HashMap<>();
//        PageAdapter adapter = new PageAdapter(getFragmentManager());
//        pager.setAdapter(adapter);
//
//        tab.setCustomTabView(R.layout.custom_tab, 0);
//        tab.setViewPager(pager);
//
//        tab.setTabTitleTextSize(14);//标题字体大小
//        tab.setTitleTextColor(0xFF336699, Color.GRAY);//标题字体颜色
////        tab.setSelectedIndicatorColors(Color.WHITE);//滑动条颜色
//        tab.setDistributeEvenly(true); //均匀平铺选项卡
//        //tab.setTabStripWidth(110);//滑动条宽度
//        //设置指示标和分割线颜色
//        tab.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                //return colors[position];
//                return colors;
//            }
//
//            @Override
//            public int getDividerColor(int position) {//垂直分割线
//                return 0x00FFFFFF;
//            }
//        });
//
//        //viewpager切换监听事件
//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                page=position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//
//    /**
//     * viewpager中fragment切换视图适配器
//     */
//    class PageAdapter extends FragmentPagerAdapter  {
//        String[] titles = {"项目投资", "债权转让"};
//        public PageAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public android.support.v4.app.Fragment getItem(int position) {
//            return getFragment(position);
//        }
//
//
//        @Override
//        public int getCount() {
//            return titles.length;
//        }
//        @Override
//        public CharSequence getPageTitle(int position) {
////            Drawable image = getResources().getDrawable(imageResId[position]);
////            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
////            SpannableString sb = new SpannableString(" ");
////            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
////            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            return titles[position];
//        }
//    }
//
//    /**
//     * 添加fragment视图
//     * @param position
//     * @return
//     */
//    public android.support.v4.app.Fragment getFragment(int position) {
//        android.support.v4.app.Fragment fragment = mFragmentMap.get(position);
//        if (fragment == null) {
//            switch (position) {
//                case 0:
//                    fragment = new MainInvestmentFragment();
//                    break;
//                case 1:
//                    fragment = new MainDebtTransferFragment();
//                    break;
//
//            }
//            mFragmentMap.put(position, fragment);
//        }
//        return fragment;
//    }
//    //    int[] colors = {0xFF123456, 0xFF336699};
//    int colors = 0xFF336699;
//}
