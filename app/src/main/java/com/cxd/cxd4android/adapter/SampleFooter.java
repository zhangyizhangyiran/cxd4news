package com.cxd.cxd4android.adapter;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.model.BannerModel;
import com.cxd.cxd4android.model.InvesListLoanPicUrlListModel;
import com.cxd.cxd4android.widget.viewpager.CycleViewPager;
import com.cxd.cxd4android.widget.viewpager.ViewFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cundong on 2015/10/9.
 * <p/>
 * RecyclerView的FooterView，简单的展示个 TextView
 */
public class SampleFooter extends RelativeLayout {

    Context context;
    CycleViewPager cycleViewPager;
    private List<ImageView> views = new ArrayList<ImageView>();
    List<InvesListLoanPicUrlListModel> InvesListLoanPicUrlListModel;
    FragmentManager manager;
    public SampleFooter(Context context, CycleViewPager  cycleViewPager,List<InvesListLoanPicUrlListModel> InvesListLoanPicUrlListModel,FragmentManager manager) {
        super(context);
        this.context = context;
        this.cycleViewPager = cycleViewPager;
        this.InvesListLoanPicUrlListModel = InvesListLoanPicUrlListModel;
        this.manager = manager;
        init(context);
    }

    public SampleFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SampleFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private static View view;

    public void init(Context context) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
//            removeView(view);
        }
        try {
            view = inflate(context, R.layout.aaalsjdsdd, this);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

//        view = inflate(context, R.layout.sa, this);

//        L.I("长度=="+InvesListLoanPicUrlListModel.size());
////        initialize();
//        recycler_view = (RecyclerView)inflate.findViewById(R.id.recycler_view);
//        mAdapterPhoto = new BuyProductDetailsRiskPhotoSimpleAdapter(context);
//        mAdapterPhoto.setHasFooter(false);
//        mAdapterPhoto.setHasMoreData(true);
//
//        mAdapterPhoto.setData(InvesListLoanPicUrlListModel);
//        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(context);
//        recycler_view.setLayoutManager(linearLayoutManager);
//        recycler_view.setAdapter(mAdapterPhoto);
//        cycleViewPager = (CycleViewPager) view.findViewById(R.id.fragdsaf);
        initialize();
    }



    @SuppressLint("NewApi")
    private void initialize() {
        cycleViewPager = (CycleViewPager) manager.findFragmentById(R.id.otherfragment);
        /*for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("ͼƬ-->" + i);
            infos.add(info);
        }*/

        List<BannerModel> list = new ArrayList<BannerModel>();

        for (int i = 0; i < InvesListLoanPicUrlListModel.size(); i++) {
            BannerModel model = new BannerModel();
            model.imgRootUrl = InvesListLoanPicUrlListModel.get(i).picUrl;
            list.add(model);
        }




        if(InvesListLoanPicUrlListModel.size() > 1){ /** Modify By Gele **/
            // 将最后一个ImageView添加进来
            views.add(ViewFactory.getImageView(context, InvesListLoanPicUrlListModel.get(InvesListLoanPicUrlListModel.size() - 1).picUrl));

            for (int i = 0; i < InvesListLoanPicUrlListModel.size(); i++) {
                views.add(ViewFactory.getImageView(context, InvesListLoanPicUrlListModel.get(i).picUrl));
            }
            // 将第一个ImageView添加进来
            views.add(ViewFactory.getImageView(context, InvesListLoanPicUrlListModel.get(0).picUrl));
            // 设置循环，在调用setData方法前调用
            cycleViewPager.setCycle(true);
        }else {

            for (int i = 0; i < InvesListLoanPicUrlListModel.size(); i++) {
                views.add(ViewFactory.getImageView(context, InvesListLoanPicUrlListModel.get(i).picUrl));
            }

            // 设置循环，在调用setData方法前调用
            cycleViewPager.setCycle(false);
        }


        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, list, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();

        //cycleViewPager.disableParentViewPagerTouchEvent();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(BannerModel model, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
//                Toast.makeText(getActivity(), "position-->" + model.url, Toast.LENGTH_SHORT).show();
//                BoutBannerFragment(model);

            }

        }

    };

}