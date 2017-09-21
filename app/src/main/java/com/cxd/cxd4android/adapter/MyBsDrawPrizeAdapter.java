package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.MyBsShipAddrActivity;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.model.PointHistoryResultModel;
import com.cxd.cxd4android.model.UphAddrDataModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.presenter.MyBsPresenter;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:抽奖记录adapter
 * Description：
 * Author：XiaoFa
 * Date：2016/5/4 11:23
 * version：V1.0
 */
public class MyBsDrawPrizeAdapter extends BaseLoadMoreRecyclerAdapter<PointHistoryResultModel, MyBsDrawPrizeAdapter.ItemViewHolder> implements OnRecyclerItemClickListener ,LoadingView{

    Context context;
    FragmentManager fragmentManager;

    MyBsPresenter myBsPresenter;
    private String productId = "";
    ItemViewHolder viewHolder;
    private String mUphId = null;
    public MyBsDrawPrizeAdapter(Context context) {
        this.context = context;
        myBsPresenter = new MyBsPresenter(this);
    }

    public MyBsDrawPrizeAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_integral_detail, parent, false);

        return new MyBsDrawPrizeAdapter.ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(final MyBsDrawPrizeAdapter.ItemViewHolder vh, final int position) {
//        vh.item_tv_id_dete.setText(getItem(position).seqNum+"");
//        vh.item_tv_id_num.setText(getItem(position));

//        vh.model = getItem(position);
//        vh.item_ll_type_money.setTag(getItem(position));
        vh.setIsRecyclable(true);

        vh.item_tv_time_time.setText(getItem(position).getCreateTime());
        vh.item_tv_amount_money.setText(getItem(position).getPoint() + "");
        vh.item_tv_balance_money.setText(getItem(position).getSurplusPoint() + "");
        if ("3".equals( getItem(position).getType())) {
            vh.item_tv_type_money.setText(getItem(position).getProductName());
            vh.integral_detail_lly.setEnabled(false);

            Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_common_left_white);
            vh.item_tv_type_money.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            vh.item_tv_type_money.setText(getItem(position).getProductName());
            vh.integral_detail_lly.setEnabled(true);
            Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_common_top);
            vh.item_tv_type_money.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }

        if (!getItem(position).getisclicked()) {
            if (getItem(position).getMode().contains("prize")) {

                vh.item_layout_integral_type.setVisibility(View.GONE);
                vh.item_integral_btn.setVisibility(View.GONE);
            }
        } else {
            if (getItem(position).getMode().contains("prize")) {
                vh.item_layout_integral_type.setVisibility(View.VISIBLE);

//            Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_common_bottom);
//            vh.item_tv_type_money.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                productId = getItem(position).getProductId();
                //获取Mode返回值，  2.4.9uphID获取产品发货信息
                getModeUphData(getItem(position).getUphId(), vh);
            }
        }
        vh.integral_detail_lly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getItem(position).getisclicked()) {
                    getItem(position).setisclicked(false);

                    if (getItem(position).getMode().contains("prize")) {

                        vh.item_layout_integral_type.setVisibility(View.GONE);
                        vh.item_integral_btn.setVisibility(View.GONE);
                        Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_common_top);
                        vh.item_tv_type_money.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                    }
                } else {
                    getItem(position).setisclicked(true);
                    if (getItem(position).getMode().contains("prize")) {
                        vh.item_layout_integral_type.setVisibility(View.VISIBLE);

                        Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_common_bottom);
                        vh.item_tv_type_money.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                        productId = getItem(position).getProductId();
                        //获取Mode返回值，  2.4.9uphID获取产品发货信息
                        getModeUphData(getItem(position).getUphId(), vh);
                    }

                }

            }
        });
    }


    //请求数据,2.4.9uphID获取产品发货信息
    private void getModeUphData(final String uphId, final ItemViewHolder vh) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uphId", uphId);//

        Gson gson = new Gson();
        String strn = gson.toJson(map);

        viewHolder = vh; mUphId = uphId;
        myBsPresenter.loadModeUphData(strn);
//        MicroRequestParams params = new MicroRequestParams();
//        params.put("value", strn);
//
//        TypeToken<List<UphAddrDataModel>> typeToken = new TypeToken<List<UphAddrDataModel>>() {
//        };
//        FinalFetch<UphAddrDataModel> fetch = new FinalFetch<UphAddrDataModel>(new IFetch<UphAddrDataModel>() {
//            @Override
//            public void onSuccess(List<UphAddrDataModel> datas) {
//                UphAddrDataModel model = datas.get(0);
//                if (Constant.STATUS_SUCCESS.equals(model.getStatus())) {
//                    UphAddrDataModel.ResultBean uphAddrResult = model.getResult();
//                    if (uphAddrResult != null) {
//                        vh.integral_type_point_name.setText(uphAddrResult.getProductName());
//
//                        if ("verify".equals(uphAddrResult.getStatus())) {
//                            vh.integral_type_point_state.setText("收货信息确认中");
//                            vh.item_integral_btn.setVisibility(View.VISIBLE);
//
//                            vh.item_integral_btn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = new Intent(context, MyBsShipAddrActivity.class);
//                                    intent.putExtra("uphId", uphId);
//                                    intent.putExtra("page_id", "DrawPrize");
//
//                                    context.startActivity(intent);
//                                }
//                            });
//                        } else if ("delivery_to".equals(uphAddrResult.getStatus())) {
//                            vh.integral_type_point_state.setText("待发货");
//                            vh.item_integral_btn.setVisibility(View.GONE);
//                        } else if ("delivery_complete".equals(uphAddrResult.getStatus())) {
//                            vh.integral_type_point_state.setText("已发货");
//                            vh.item_integral_btn.setVisibility(View.GONE);
//                        } else if ("delivery_success".equals(uphAddrResult.getStatus())) {
//                            vh.item_integral_btn.setVisibility(View.GONE);
//                            vh.integral_type_point_state.setText("已送达");
//                        } else if ("delivery_fail".equals(uphAddrResult.getStatus())) {
//                            vh.item_integral_btn.setVisibility(View.GONE);
//                            vh.integral_type_point_state.setText("未收货");
//                        }
//
//                    } else {
//                        Toast.makeText(context, "没有获取到数据", Toast.LENGTH_SHORT).show();
//                    }
//                } else if (Constant.STATUS_FAILED.equals(model.getStatus())) {
//
//                }
//            }
//
//            @Override
//            public void onFail(ResultModel result) {
//                L.I(Constant.CONNECT_FAILURE);
//            }
//
//            @Override
//            public void onFetching(int proccess) {
//            }
//
//            @Override
//            public void onPrevious() {
//            }
//        }, params, typeToken, Constant.pointc_getProductByUphId);
    }


    @Override
    public void onClick(View v, int position) {
        switch (v.getId()) {
            case R.id.item_ll_type_money:
                //Toast.makeText(v.getContext(), "on click " + position, Toast.LENGTH_SHORT).show();
                //vh.item_tv_remarks_money.setVisibility(View.VISIBLE);
                break;
            default:
                //mock click todo  last item
                //remove(position);
                //notifyItemRemoved(position);
        }
    }

    @Override
    public void getDataSuccess(Object model) {
        UphAddrDataModel uphAddrDataModel = (UphAddrDataModel) model;
        if (Constant.STATUS_SUCCESS.equals(uphAddrDataModel.getStatus())) {
            UphAddrDataModel.ResultBean uphAddrResult = uphAddrDataModel.getResult();
            if (uphAddrResult != null) {
                viewHolder.integral_type_point_name.setText(uphAddrResult.getProductName());

                if ("verify".equals(uphAddrResult.getStatus())) {
                    viewHolder.integral_type_point_state.setText("收货信息确认中");
                    viewHolder.item_integral_btn.setVisibility(View.VISIBLE);

                    viewHolder.item_integral_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MyBsShipAddrActivity.class);
                            intent.putExtra("uphId", mUphId);
                            intent.putExtra("page_id", "DrawPrize");

                            context.startActivity(intent);
                        }
                    });
                } else if ("delivery_to".equals(uphAddrResult.getStatus())) {
                    viewHolder.integral_type_point_state.setText("待发货");
                    viewHolder.item_integral_btn.setVisibility(View.GONE);
                } else if ("delivery_complete".equals(uphAddrResult.getStatus())) {
                    viewHolder.integral_type_point_state.setText("已发货");
                    viewHolder.item_integral_btn.setVisibility(View.GONE);
                } else if ("delivery_success".equals(uphAddrResult.getStatus())) {
                    viewHolder.item_integral_btn.setVisibility(View.GONE);
                    viewHolder.integral_type_point_state.setText("已送达");
                } else if ("delivery_fail".equals(uphAddrResult.getStatus())) {
                    viewHolder.item_integral_btn.setVisibility(View.GONE);
                    viewHolder.integral_type_point_state.setText("未收货");
                }

            } else {
                Toast.makeText(context, "没有获取到数据", Toast.LENGTH_SHORT).show();
            }
        } else if (Constant.STATUS_FAILED.equals(uphAddrDataModel.getStatus())) {

        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    public class ItemViewHolder extends ClickableViewHolder {

        public TextView item_tv_time_time;
        public TextView item_tv_amount_money;
        public TextView item_tv_balance_money;
        public TextView item_tv_type_money;

        public TextView integral_type_point_name;
        public TextView integral_type_point_state;

        public TextView item_integral_invest_name;
        public TextView item_integral_invest_money;
        public TextView item_integral_invest_year_rate;
        public TextView item_integral_invest_income;
        public TextView item_integral_invest_date;
        public TextView item_integral_btn;

        public LinearLayout item_layout_integral_invest;
        public RelativeLayout item_layout_integral_type;

        public LinearLayout integral_detail_lly;

        public boolean popoposion;

        public ItemViewHolder(View view) {
            super(view);
            popoposion = false;
//            item_tv_id_dete = (TextView) view.findViewById(R.id.item_tv_id_dete);
//            item_tv_id_num = (TextView) view.findViewById(R.id.item_tv_id_num);
            item_tv_time_time = (TextView) view.findViewById(R.id.item_integral_time_time);
//            item_tv_time_dete = (TextView) view.findViewById(R.id.item_tv_time_dete);
            item_tv_amount_money = (TextView) view.findViewById(R.id.item_integral_amount_money);
            item_tv_balance_money = (TextView) view.findViewById(R.id.item_integral_balance_money);
            item_tv_type_money = (TextView) view.findViewById(R.id.item_integral_type_money);

            integral_type_point_name = (TextView) view.findViewById(R.id.integral_type_point_name);
            integral_type_point_state = (TextView) view.findViewById(R.id.integral_type_point_state);

            item_integral_invest_name = (TextView) view.findViewById(R.id.item_integral_invest_name);
            item_integral_invest_money = (TextView) view.findViewById(R.id.item_integral_invest_money);
            item_integral_invest_year_rate = (TextView) view.findViewById(R.id.item_integral_invest_year_rate);
            item_integral_invest_income = (TextView) view.findViewById(R.id.item_integral_invest_income);
            item_integral_invest_date = (TextView) view.findViewById(R.id.item_integral_invest_date);
            item_integral_btn = (TextView) view.findViewById(R.id.item_integral_btn);
//            item_express_company = (TextView) view.findViewById(R.id.integral_type_express_company);
//            item_express_number = (TextView) view.findViewById(R.id.integral_type_express_number);

            item_layout_integral_invest = (LinearLayout) view.findViewById(R.id.item_layout_integral_invest);
            item_layout_integral_type = (RelativeLayout) view.findViewById(R.id.item_layout_integral_type);
            integral_detail_lly = (LinearLayout) view.findViewById(R.id.integral_detail_lly);

            setOnRecyclerItemClickListener(MyBsDrawPrizeAdapter.this);
            //view.setOnClickListener(this);
            //mTvContent.setOnClickListener(this);
            addOnItemViewClickListener();
            addOnViewClickListener(integral_detail_lly);
            addOnViewClickListener(item_integral_btn);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + item_tv_type_money.getText();
        }
    }
}