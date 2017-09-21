package com.cxd.cxd4android.widget.maintab;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cxd.cxd4android.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 15-11-23.
 */
public class TabView extends LinearLayout implements View.OnClickListener {


    public static ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private PagerAdapter mPagerAdapter;
    private int mChildSize;
    private List<TabItem> mTabItems;
    private OnItemIconTextSelectListener mListener;

    private int mTextSize = 12;
    private int mTextColorSelect = 0xff3e64b5;//0xff45c01a
    private int mTextColorNormal = 0xff707070;//0xff777777
    private int mPadding = 5;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.TabView);
        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            switch (typedArray.getIndex(i)) {
                case R.styleable.TabView_text_size:
                    mTextSize = (int) typedArray.getDimension(i, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            mTextSize, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TabView_text_normal_color:
                    mTextColorNormal = typedArray.getColor(i, mTextColorNormal);
                    break;
                case R.styleable.TabView_text_select_color:
                    mTextColorSelect = typedArray.getColor(i, mTextColorSelect);
                    break;
                case R.styleable.TabView_item_padding:
                    mPadding = (int) typedArray.getDimension(i, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            mPadding, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
        mTabItems = new ArrayList<>();
    }

    public void setViewPager(final ViewPager mViewPager) {
        if (mViewPager == null) {
            return;
        }
        this.mViewPager = mViewPager;
        this.mPagerAdapter = mViewPager.getAdapter();
        if (this.mPagerAdapter == null) {
            throw new RuntimeException("亲，在您设置TabView的ViewPager时，请先设置ViewPager的PagerAdapter");
        }
        this.mChildSize = this.mPagerAdapter.getCount();
        this.mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                Log.v("zgy","=============position="+position+",====positionOffset="+positionOffset) ;
                View leftView;
                View rightView;

                if (positionOffset > 0) {
                    leftView = mViewPager.getChildAt(position);
                    rightView = mViewPager.getChildAt(position + 1);
                    //leftView.setAlpha(1 - positionOffset);/** Modify By Gele **/
                    //rightView.setAlpha(positionOffset);/** Modify By Gele **/
                    mTabItems.get(position).setTabAlpha(1 - positionOffset);
                    mTabItems.get(position + 1).setTabAlpha(positionOffset);
                } else {
                    //mViewPager.getChildAt(position).setAlpha(1);/** Modify By Gele **/
                    mTabItems.get(position).setTabAlpha(1 - positionOffset);
                }
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        if (mPagerAdapter instanceof OnItemIconTextSelectListener) {
            mListener = (OnItemIconTextSelectListener) mPagerAdapter;
        } else {
            throw new RuntimeException("请让你的pageAdapter实现OnItemIconTextSelectListener接口");
        }
        initItem();
        setListener();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }

    int[] epp1 = new int[]{R.mipmap.eqe, R.mipmap.eqe};
    /**
     * Modify By Gele
     **///icon_badge_red
    int[] epp2 = new int[]{R.mipmap.eqe, R.mipmap.eqe};

    private void initItem() {
        for (int i = 0; i < mChildSize; i++) {
            TabItem tabItem = new TabItem(getContext());
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tabItem.setPadding(mPadding, mPadding, mPadding, 0);/** Modify By Gele **///mPadding
            tabItem.setIconText(mListener.onIconSelect(i), mListener.onTextSelect(i));

//            tabItem.setBedgeAlpha(0);
            if (i == 2) {
                tabItem.setBadge(epp1);
//                tabItem.setBadgeVisibility(View.VISIBLE);
            } else {
                tabItem.setBadge(epp2);
            }
            tabItem.setTextSize(mTextSize);
            tabItem.setTextColorNormal(mTextColorNormal);
            tabItem.setTextColorSelect(mTextColorSelect);
            tabItem.setLayoutParams(params);
            tabItem.setTag(i);
            tabItem.setOnClickListener(this);
            mTabItems.add(tabItem);
            addView(tabItem);

//            TabItem tabItems = new TabItem(getContext());
//            LayoutParams paramss = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//            tabItems.setPadding(mPadding, mPadding, mPadding, 0);/** Modify By Gele **///mPadding
//            tabItems.setBadge(R.mipmap.eqd);
//            tabItems.setIconText(eqd, "");
//            tabItems.setTextSize(mTextSize);
//            tabItems.setTextColorNormal(mTextColorNormal);
//            tabItems.setTextColorSelect(mTextColorSelect);
//            tabItems.setLayoutParams(paramss);
//            tabItems.setTag(i);
//            tabItems.setOnClickListener(this);
//            mTabItems.add(tabItems);
//            addView(tabItems);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        if (mViewPager.getCurrentItem() == position) {
            return;
        }
        for (TabItem tabItem : mTabItems) {
            tabItem.setTabAlpha(0);
        }
        mTabItems.get(position).setTabAlpha(1);
        mViewPager.setCurrentItem(position, false);
    }

    public interface OnItemIconTextSelectListener {

        int[] onIconSelect(int position);

        String onTextSelect(int position);
    }


    /**
     * 再次设置回调跳转
     */
    private void setListener() {
        /**
         * 小红点提示
         */
        /*SeMainFragment.ISeMainBedgeCallBack = new ISeMainBedgeCallBack() {
            @Override
            public void OnSeMainBedge(boolean bedge) {
                if (bedge) {
                    for (int i = 0; i < mTabItems.size(); i++) {
                        mTabItems.get(i).setBedgeAlpha(0);
                    }

                } else {
                    for (int i = 0; i < mTabItems.size(); i++) {
                        mTabItems.get(i).setBedgeAlpha(1);
                    }

                }
            }
        };*/

    }

    /**
     * 再次设置回调跳转,重新绘制.设置current值然后跳转到相应的页面
     */
    public void setCurrentItemView(int Current) {
        for (int i = 0; i < mTabItems.size(); i++) {
//            int currentItem = mViewPager.getCurrentItem();
            if (i == Current) {
//                mViewPager.setCurrentItem(Current, true); //MeLoginFragment
            } else {
                mTabItems.get(i).setTabAlpha(0);
            }
        }
        mViewPager.setCurrentItem(Current, false);

    }
}
