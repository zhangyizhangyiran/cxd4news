package com.cxd.cxd4android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.PdfShowActivity;
import com.cxd.cxd4android.fragment.MeMyInvesContractFragment;
import com.cxd.cxd4android.fragment.MeMyInvesRepayPlanFragment;
import com.cxd.cxd4android.fragment.MeMySaveVoucherFragment;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.model.UserInvestsModel;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.micros.utils.S;
import com.micros.utils.T;

import static android.R.attr.name;


/**
 * 我的投资
 *
 * @author YanLu
 * @since 15/9/15
 */
public class MeMyInvesSimpleAdapter extends BaseLoadMoreRecyclerAdapter<UserInvestsModel, MeMyInvesSimpleAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    Context context;
    FragmentManager FragmentManager;
    private MeMyInvesRepayPlanFragment MeMyInvesRepayPlanFragment;
    private MeMyInvesContractFragment MeMyInvesContractFragment;
    private MeMySaveVoucherFragment MeMySaveVoucherFragment;

    UserInvestsModel model;
    public String loanId = "";

    public MeMyInvesSimpleAdapter(Context context) {
        this.context = context;
    }

    public MeMyInvesSimpleAdapter(Context context, FragmentManager FragmentManager) {
        this.context = context;
        this.FragmentManager = FragmentManager;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ItemViewHolder vh;
        parent.getChildAt(viewType);
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_my_inves, parent, false);
            vh = new ItemViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ItemViewHolder) view.getTag();
        }
        vh.setIsRecyclable(false);
        return vh;
    }


//    Handler UIHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            ItemViewHolder vh = (ItemViewHolder) msg.obj;
//            if (vh.ss.equals(vh.item_iv_repay_type.getTag())) {
//                if (("cancel").equals(vh.item_iv_repay_type.getTag())) {//getItem(vh.posi).status
//                    vh.item_iv_repay_type.setImageResource(R.mipmap.ic_myinves_flow);
////                    vh.item_ll_repay_plan.setVisibility(View.GONE);
//                } else if (("repaying").equals(vh.item_iv_repay_type.getTag())) {
//                    vh.item_iv_repay_type.setImageResource(R.mipmap.ic_myinves_repaying);
//                } else if (("complete").equals(vh.item_iv_repay_type.getTag())) {
//                    vh.item_iv_repay_type.setImageResource(R.mipmap.ic_myinves_repayment);
//                } else if (("bid_success").equals(vh.item_iv_repay_type.getTag())) {
//                    vh.item_iv_repay_type.setImageResource(R.mipmap.ic_myinves_invesing);
//                }
//            }
//
//        }
//    };

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(ItemViewHolder vh, int position) {

        if (!(getItem(position).addRate.equals("0"))) {
            //总利息
            String rate = getItem(position).rate;
            //额外利息
            String addRate = getItem(position).addRate;
            //加息前利息
            float FinalRate = Float.parseFloat(rate) - Float.parseFloat(addRate);
            String totalRate = setString(String.valueOf(FinalRate));
            String s = setString(getItem(position).addRate);
            vh.item_tv_profit_value.setText(totalRate);
            vh.mItem_tv_jiaxi_type.setText("+" + s + "%");
            vh.mItem_tv_jiaxi_type.setVisibility(View.VISIBLE);

        } else {
            vh.item_tv_profit_value.setText(getItem(position).rate);
        }


        vh.item_tv_housing_mortgage.setText(getItem(position).name);
//        vh.item_tv_repay_next.setText(getItem(position).investInfo.nextCollectMoney);
//        vh.item_tv_collect_interest.setText(getItem(position).investInfo.waitingCollectMoney);
        vh.item_tv_investment_amount.setText(getItem(position).investMoney);
        vh.item_tv_repay_date.setText(getItem(position).investInfo.nextRepayDate);

        vh.item_tv_investment_time.setText(getItem(position).investTime);
        vh.item_tv_total_time.setText(getItem(position).deadline + getItem(position).unit);


        String ssssss = getItem(position).status;

        //已流标,已转出状态显示数据
        if (("cancel").equals(ssssss) || ("transfered").equals(ssssss)) {
            getItem(position).isSelect = false;
        } else {
            getItem(position).isSelect = true;
        }

        //已完成状态显示数据
        if (("complete").equals(ssssss)) {
            vh.item_tv_repaynext_text.setText("回收金额(元):");
            vh.item_tv_collectinterest_text.setText("已赚金额(元):");

            vh.item_tv_repay_next.setText(getItem(position).investInfo.totalAmount);
            vh.item_tv_collect_interest.setText(getItem(position).investInfo.totalInterest);
        } else {
            vh.item_tv_repaynext_text.setText("下期还款额(元):");
            vh.item_tv_collectinterest_text.setText("待收本息(元):");

            vh.item_tv_repay_next.setText(getItem(position).investInfo.nextCollectMoney);
            vh.item_tv_collect_interest.setText(getItem(position).investInfo.waitingCollectMoney);
        }

        //右上角图标
        if (("cancel").equals(ssssss)) {//getItem(vh.posi).status
            vh.item_iv_repay_type.setImageResource(R.mipmap.ic_myinves_flow);
        } else if (("repaying").equals(ssssss)) {
            vh.item_iv_repay_type.setImageResource(R.mipmap.ic_myinves_repaying);
        } else if (("complete").equals(ssssss)) {
            vh.item_iv_repay_type.setImageResource(R.mipmap.ic_myinves_repayment);
        } else if (("bid_success").equals(ssssss)) {
            vh.item_iv_repay_type.setImageResource(R.mipmap.ic_myinves_invesing);
        } else if (("transfered").equals(ssssss)) {
            vh.item_iv_repay_type.setImageResource(R.mipmap.ic_myinves_transfered);
        }

        //已流标状态隐藏显示底部布局
        if (getItem(position).isSelect) {
            vh.item_ll_repay_plan.setVisibility(View.VISIBLE);
        } else {
            vh.item_ll_repay_plan.setVisibility(View.GONE);
        }

        //安村凭证是否生成
        if (getItem(position).isAnCun.equals("0")) {
            vh.item_tv_save_voucher.setTextColor(Color.GRAY);
        } else {
            vh.item_tv_save_voucher.setTextColor(Color.parseColor("#3E64B5"));
        }
        /**
         * cancel			流标                       流标 (已终止,已结束)
         * repaying 		还款中                     还款中 (还款中)
         * complete		    完成                       已还款 (已还款)
         * bid_success	    投标成功                   投资中 (投资中)
         * transfered       转让                       已转出
         *
         * wait_affirm      第三方资金托管确认中
         *
         */
    }


    @Override
    public void onClick(View v, int position) {
        switch (v.getId()) {
            case R.id.item_bt_repay_plan://还款计划

                loanId = getItem(position).id;

                MeMyInvesRepayPlanFragment();
                StatService.onEvent(context, BaiDustatistic.myinvest_repayplan, "", 1);//事件统计
                break;
            case R.id.item_tv_loan_agreement://借款合同


                model = getItem(position);
                //新增PDF类型判断zy
                if ("pdf".equals(model.contractType)) {

                    Intent PdfIntent = new Intent(context, PdfShowActivity.class);
                    PdfIntent.putExtra("PdfIntent", model);
                    context.startActivity(PdfIntent);
                    return;

                }

                MeMyInvesContractFragment();
                StatService.onEvent(context, BaiDustatistic.myinvest_loancontract, "", 1);//事件统计


                break;
            case R.id.item_tv_save_voucher://保全凭证
                model = getItem(position);
                if ("0".equals(model.isAnCun)) {

                    return;
                }
                MeMySaveVoucherFragment();
                StatService.onEvent(context, BaiDustatistic.myinvest_preserdocument, "", 1);//事件统计
                break;

            default:

                break;
        }
    }


    private String setString(String s) {


        if (s.contains(".")) {

            String[] array = s.split("\\.");

            String s1 = array[1];
            char FirstNumber = s1.charAt(0);
            String firstNumber = String.valueOf(FirstNumber);
            int parseInt = Integer.parseInt(firstNumber);


            if (s1.length() > 1) {
                char TwoNumber = s1.charAt(1);
                String twoNumber = String.valueOf(TwoNumber);
                int two = Integer.parseInt(twoNumber);

                if (parseInt > 0 && two > 0) {
                    return array[0] + "." + firstNumber + twoNumber;
                }

                if (parseInt < 0 && two > 0) {
                    return array[0] + "." + firstNumber + twoNumber;
                }
                if (parseInt > 0 && two < 0) {
                    return array[0] + "." + firstNumber;
                }


            } else {
                if (firstNumber.equals("0")) {
                    return array[0];
                }
            }

        }


        return s;
    }


    public class ItemViewHolder extends ClickableViewHolder {
        public TextView item_tv_housing_mortgage;
        public TextView item_tv_repay_next;
        public TextView item_tv_collect_interest;
        public TextView item_tv_investment_amount;
        public TextView item_tv_repay_date;
        public TextView item_tv_profit_value;
        public TextView item_tv_investment_time;
        public TextView item_tv_total_time;
        public TextView item_tv_loan_agreement;
        public TextView item_tv_save_voucher;

        public ImageView item_iv_repay_type;
        public Button item_bt_repay_plan;

        public LinearLayout item_ll_repay_plan;

        public String ss;

        public TextView item_tv_repaynext_text;
        public TextView item_tv_collectinterest_text;
        public TextView mItem_tv_jiaxi_type;

        public ItemViewHolder(View view) {
            super(view);
            mItem_tv_jiaxi_type = (TextView) view.findViewById(R.id.item_tv_jiaxi_type);
            item_tv_housing_mortgage = (TextView) view.findViewById(R.id.item_tv_housing_mortgage);
            item_tv_repay_next = (TextView) view.findViewById(R.id.item_tv_repay_next);
            item_tv_collect_interest = (TextView) view.findViewById(R.id.item_tv_collect_interest);
            item_tv_investment_amount = (TextView) view.findViewById(R.id.item_tv_investment_amount);
            item_tv_repay_date = (TextView) view.findViewById(R.id.item_tv_repay_date);
            item_tv_profit_value = (TextView) view.findViewById(R.id.item_tv_profit_value);
            item_tv_investment_time = (TextView) view.findViewById(R.id.item_tv_investment_time);
            item_tv_total_time = (TextView) view.findViewById(R.id.item_tv_total_time);
            item_tv_loan_agreement = (TextView) view.findViewById(R.id.item_tv_loan_agreement);
            item_tv_save_voucher = (TextView) view.findViewById(R.id.item_tv_save_voucher);

            item_iv_repay_type = (ImageView) view.findViewById(R.id.item_iv_repay_type);
            item_bt_repay_plan = (Button) view.findViewById(R.id.item_bt_repay_plan);

            item_ll_repay_plan = (LinearLayout) view.findViewById(R.id.item_ll_repay_plan);

            item_tv_repaynext_text = (TextView) view.findViewById(R.id.item_tv_repaynext_text);
            item_tv_collectinterest_text = (TextView) view.findViewById(R.id.item_tv_collectinterest_text);

            setOnRecyclerItemClickListener(MeMyInvesSimpleAdapter.this);
            //view.setOnClickListener(this);
            //mTvContent.setOnClickListener(this);
            addOnItemViewClickListener();
            addOnViewClickListener(item_bt_repay_plan);
            addOnViewClickListener(item_tv_loan_agreement);
            addOnViewClickListener(item_tv_save_voucher);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + item_iv_repay_type.getTag();
        }
    }


    /**
     * 借款合同
     */
    public void MeMyInvesContractFragment() {

//        if (MeMyInvesContractFragment == null) {
        MeMyInvesContractFragment = new MeMyInvesContractFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("UserInvestsModel", model);
        MeMyInvesContractFragment.setArguments(bundle);
//        }
//        replace(MeMyInvesContractFragment);

//        replace(R.id.main_fr_main_me,MeMyInvesContractFragment,"MeMyInvesContractFragment");

        add(R.id.fragment_myinves, MeMyInvesContractFragment, "MeMyInvesContractFragment", null);
    }

    /**
     * 保全凭证
     */
    public void MeMySaveVoucherFragment() {

//        if (MeMyInvesContractFragment == null) {
        MeMySaveVoucherFragment = new MeMySaveVoucherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("AnCunModel", model);
        MeMySaveVoucherFragment.setArguments(bundle);
//        }
//        replace(MeMyInvesContractFragment);

//        replace(R.id.main_fr_main_me,MeMyInvesContractFragment,"MeMyInvesContractFragment");

        add(R.id.fragment_myinves, MeMySaveVoucherFragment, "MeMySaveVoucherFragment", null);
    }

    /**
     * 还款计划
     */
    public void MeMyInvesRepayPlanFragment() {

//        if (MeMyInvesRepayPlanFragment == null) {
        MeMyInvesRepayPlanFragment = new MeMyInvesRepayPlanFragment();
        Bundle bundle = new Bundle();
        bundle.putString("loanId", loanId);//model.id
        MeMyInvesRepayPlanFragment.setArguments(bundle);
//        }
//        replace(MeMyInvesRepayPlanFragment);
        add(R.id.fragment_myinves, MeMyInvesRepayPlanFragment, "MeMyInvesRepayPlanFragment", null);

    }

    public void add(int containerViewId, BaseFragment fragment, String tag, String stackName) {
        FragmentManager.beginTransaction().add(containerViewId, fragment, tag).addToBackStack(stackName).commitAllowingStateLoss();
    }
    /*public void replace(int containerViewId,BaseFragment fragment,String stackName) {
        FragmentManager.beginTransaction().add(containerViewId, fragment).addToBackStack(stackName).commitAllowingStateLoss();
    }*/
}
