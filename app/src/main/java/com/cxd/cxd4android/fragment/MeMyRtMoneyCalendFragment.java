package com.cxd.cxd4android.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.adapter.MeMyInvesSimpleAdapter;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.model.BackCalendarModel;
import com.cxd.cxd4android.model.CalendarList;
import com.cxd.cxd4android.model.UserInvestsBaseModel;
import com.cxd.cxd4android.model.UserInvestsModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MeRtCalendFragmentPresenter;
import com.cxd.cxd4android.widget.FullyLayoutManager.FullyLinearLayoutManager;
import com.cxd.cxd4android.widget.decorators.EventDecorator;
import com.cxd.cxd4android.widget.decorators.MySelectorDecorator;
import com.cxd.cxd4android.widget.decorators.OneDayDecorator;
import com.github.captain_miao.recyclerviewutils.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;
import com.micros.utils.D;
import com.orhanobut.logger.Logger;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ClassName:回款日历
 * Description：
 * Author：XiaoFa
 * Date：2016/4/22 16:40
 * version：V1.0
 */


public class MeMyRtMoneyCalendFragment extends BaseFragment implements OnDateSelectedListener, OnMonthChangedListener,LoadingView {

    /**
     * 回款日历正中间标题
     **/
    @Bind(R.id.tv_title)
     TextView tv_title;
    /**
     * 回款日历左上角全部
     **/
    @Bind(R.id.Btn_left)
     TextView Btn_left;
    /**
     * 回款日历控件
     **/
    @Bind(R.id.calendar_rv_recycler_view)
     RecyclerView calendar_rv_recycler_view;
    /**
     * 无回款项目
     **/
    @Bind(R.id.calendar_text_view)
     TextView calendar_text_view;
    /**
     * 回款日历数据（月份）
     **/
    private CalendarList calendarList = new CalendarList();
    /**
     * 用户信息
     **/
    private LocalUserModel userModel;
    /**
     * 回款日历数据
     **/
    List<UserInvestsModel> list = new ArrayList<UserInvestsModel>();
    /**
     * （我的投资）回款日历 适配器
     **/
    private MeMyInvesSimpleAdapter mAdapter;
    /**
     * 数据类型
     **/
    private String DATATYPE = "REQUEST";
    /**
     * hu还款月份 如：2016-05
     * 还款日期 如：2016-05-18
     **/
    private String backDate, dayDate;
    private int btn_color = Color.parseColor("#3E64B5");
    private int color = Color.parseColor("#F95151");
    MaterialCalendarView widget;
    MeRtCalendFragmentPresenter calendFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_rt_money_calendar, container, false);
            ButterKnife.bind(this,contentView);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        //用户信息
        userModel = new LocalUserModel();
        calendFragmentPresenter = new MeRtCalendFragmentPresenter(this);
        //初始化状态栏title
        initStatusBar();
        //设置widget MaterialCalendarView
        setWidget();
        //请求回款日数据
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        dayDate = format.format(new Date());
        //请求回款日数据
        initDayData();
        //设置监听
        setListener();
    }

    private void initDayData() {
//        P.OperationIng("正在加载");
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("date", dayDate);//

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        calendFragmentPresenter.loadDDBackCalendar(strn);
    }

    /**
     * 设置适配器
     */
    FullyLinearLayoutManager linearLayoutManager;

    private void setAdapter() {
        mAdapter = new MeMyInvesSimpleAdapter(getActivity(), getFragmentManager());
        mAdapter.setHasFooter(false);
        mAdapter.setHasMoreData(true);
        mAdapter.notifyDataSetChanged();
        calendar_rv_recycler_view.setAdapter(mAdapter);
        calendar_rv_recycler_view.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();

        linearLayoutManager = new FullyLinearLayoutManager(getActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        calendar_rv_recycler_view.setLayoutManager(linearLayoutManager);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        setAdapter();

        calendar_rv_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int current_page) {
                DATATYPE = "LODE";
                mAdapter.setHasFooter(true);
                initData();

            }
        });
    }

    private void setWidget() {

        widget.setOnDateChangedListener(this);//设置日期变化监听
        widget.setOnMonthChangedListener(this);//设置月份监听
//        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());


        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        widget.setMinimumDate(calendar.getTime());//设置限制最小月份是本年度1月

//        calendar.set(calendar.get(Calendar.YEAR), Calendar.DECEMBER, 31);
//        widget.setMaximumDate(calendar.getTime());//设置限制最大月份是本年度12月

//        widget.addDecorator(new EnableOneToTenDecorator());
        widget.addDecorators(
                //new HighlightWeekendsDecorator(),
                new OneDayDecorator(getActivity())
        );

        //设置颜色（选择按钮，日历每日选中的颜色）
        widget.setArrowColor(btn_color);
        widget.setSelectionColor(color);//设置选中时 背景颜色
        widget.addDecorator(new MySelectorDecorator(getActivity()));

        //widget.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
        widget.setDateTextAppearance(R.style.TextAppearance_AppCompat_Small);
//        widget.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
    }

    /**
     * 设置状态栏
     */
    private void initStatusBar() {

        Btn_left.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("回款日历");

        Btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    /**
     * 日期选择监听事件
     *
     * @param widget   the view associated with getActivity() listener
     * @param date     the date that was selected or unselected
     * @param selected true if the day is now selected, false otherwise
     */
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
//        oneDayDecorator.setDate(date.getDate());


        widget.invalidateDecorators();
//        Toast.makeText(getActivity(), "" + date.getYear() + date.getMonth() + date.getDay(), Toast.LENGTH_SHORT).show();
//        Log.i("BasicActivity->", "bay->" + date.getDate().getTime());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date dayByChange = date.getDate();

        dayDate = format.format(dayByChange);
        //Toast.makeText(getActivity(), "---?>" + dayDate, Toast.LENGTH_SHORT).show();
        //请求回款日数据
        initDayData();

    }

    /**
     * 改变月份监听事件
     *
     * @param widget the view associated with this listener
     * @param date   the month picked, as the first day of the month
     */
    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        Date monthByChange = date.getDate();

        backDate = format.format(monthByChange);
        //.makeText(getActivity(), "---?>" + backDate, Toast.LENGTH_SHORT).show();
        initData(); //请求数据 ,获取月份中回款日
    }

    /**
     * 请求数据 ,获取月份中回款日
     */
    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userModel.getid());
        map.put("backDate", backDate);//

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        calendFragmentPresenter.loadMMBackCalendar(strn);
    }

    @Override
    public void getDataSuccess(Object model) {

        if (model.getClass().equals(BackCalendarModel.class)){
            BackCalendarModel calendarModel = (BackCalendarModel) model;
            if (Constant.STATUS_SUCCESS.equals(calendarModel.status)) {
                calendarList = calendarModel.result;
                //进入日历默认当天日期
                if ( calendarList.dateList!=null) {
                    new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());//控制小红点显示
                }
            }
        }else if (model.getClass().equals(UserInvestsBaseModel.class)){
            UserInvestsBaseModel investsBaseModel = (UserInvestsBaseModel) model;
            if (Constant.STATUS_SUCCESS.equals(investsBaseModel.status)) {
                list = investsBaseModel.result;
                if (list.size() > 0) {

                    EndlessRecyclerOnScrollListener.loading = false;
                    switch (DATATYPE) {
                        case "REFRESH": //下拉刷新
                            mAdapter.clear();
                            mAdapter.appendToList(list);
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setHasMoreDataAndFooter(true, false);//下拉刷新将footer上的显示内容置换为加载条

                            break;
                        case "REQUEST": //请求
                            if (list != null && list.get(0).contractUrl != null) {
                                mAdapter.clear();
                                mAdapter.appendToList(list);
                                mAdapter.notifyDataSetChanged();
                                calendar_text_view.setText("本日回款项目");
                            } else {
                                calendar_text_view.setText("本日无回款项目");
                            }
                            break;
                        case "LODE": //加载
                            if (list.size() > 0) {
                                mAdapter.appendToList(list);
                                mAdapter.notifyDataSetChanged();

                            } else {
                                mAdapter.setHasFooter(true);
                                mAdapter.setHasMoreData(false);
                                mAdapter.notifyDataSetChanged();
//                        invesfra_rv_recycler_view.setAdapter(mAdapter);
//                        mAdapter.notifyDataSetChanged();
                            }
                            break;
                        default:
                            mAdapter.clear();
                            mAdapter.appendToList(list);
                            mAdapter.notifyDataSetChanged();
                            //Toast.makeText(getActivity(), "->"+list.size(), Toast.LENGTH_SHORT).show();
                            calendar_text_view.setText("本日无回款项目");
                            break;
                    }
                }else{
                    mAdapter.clear();
                    mAdapter.appendToList(list);
                    mAdapter.notifyDataSetChanged();
                    //Toast.makeText(getActivity(), "->"+list.size(), Toast.LENGTH_SHORT).show();
                    calendar_text_view.setText("本日无回款项目");
                }
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        Logger.e(msg);
        mAdapter.clear();
        mAdapter.appendToList(list);
        mAdapter.notifyDataSetChanged();
        //Toast.makeText(getActivity(), "->"+list.size(), Toast.LENGTH_SHORT).show();
        calendar_text_view.setText("本日无回款项目");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();
            calendar.add(Calendar.MONTH, 0);
//            for (int i = 0; i < 30; i++) {

//                calendar.add(Calendar.DATE, 1);
//            calendar.set(Calendar.YEAR, Calendar.MONTH, 5);

            /**
             * for循环遍历 将日期添加到 集合中
             */
            for (int i = 0; i < calendarList.dateList.size(); i++) {
//                Date date = D.getDateByFormat("2016-05-19",D.dateFormatYMD);
                Date date = D.getDateByFormat(calendarList.dateList.get(i), D.dateFormatYMD);
                Calendar calendarsssss = Calendar.getInstance();
                calendarsssss.setTime(date);
                CalendarDay day = CalendarDay.from(calendarsssss);
                dates.add(day);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            try {
                if (getActivity().isFinishing()) {
                    return;
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }finally {

            }
            widget.addDecorator(new EventDecorator(color, calendarDays));
            //widget.addDecorator(new OneDayDecorator());

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "回款日历");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "回款日历");//(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
