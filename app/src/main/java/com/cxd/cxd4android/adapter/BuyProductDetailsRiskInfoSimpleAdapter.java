package com.cxd.cxd4android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.GhbGlobalData;
import com.cxd.cxd4android.model.InvesListLoanInfoListModel;
import com.cxd.cxd4android.model.InvesListLoanPicUrlListModel;
import com.cxd.cxd4android.model.InvesListModel;
import com.cxd.cxd4android.model.InvestModel;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;

import java.util.List;

/**
 * @author YanLu
 * @since 15/9/15
 */
public class BuyProductDetailsRiskInfoSimpleAdapter extends BaseLoadMoreRecyclerAdapter<InvesListLoanInfoListModel, BuyProductDetailsRiskInfoSimpleAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {
    Context context;
    private boolean Clicked = false;
    List<InvesListLoanInfoListModel> listLoanInfoMap;
    List<InvesListLoanPicUrlListModel> InvesListLoanPicUrlListModel;
    FragmentManager manager;
    private InvesListModel mInvesListModel;

    public BuyProductDetailsRiskInfoSimpleAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<InvesListLoanInfoListModel> listLoanInfoMap, List<com.cxd.cxd4android.model.InvesListLoanPicUrlListModel> InvesListLoanPicUrlListModel, FragmentManager manager, InvesListModel invesListModel) {
        this.listLoanInfoMap = listLoanInfoMap;
        this.InvesListLoanPicUrlListModel = InvesListLoanPicUrlListModel;
        this.manager = manager;
        mInvesListModel = invesListModel;
        appendToList(listLoanInfoMap);
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        ItemViewHolder holder; //对不同的flag创建不同的Holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_buyproduct_detailsriskinfo, parent, false);
        holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {

        return listLoanInfoMap.size();

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindItemViewHolder(final BuyProductDetailsRiskInfoSimpleAdapter.ItemViewHolder vh, int position) {

        if (!("".equals(mInvesListModel.addRate))) {

            if (!("0".equals(mInvesListModel.addRate))) {
                String rate = mInvesListModel.rate;
                String addRate = mInvesListModel.addRate;
                float Number = Float.parseFloat(rate) - Float.parseFloat(addRate);
                String s = setString(String.valueOf(Number));
                String s1 = setString(s);
                String string = setString(addRate);

                vh.mItem_detailed_income.setText(s1 + "+" + string);
            } else {
                vh.mItem_detailed_income.setText(mInvesListModel.rate);
            }
        } else {
            vh.mItem_detailed_income.setText(mInvesListModel.rate);

        }

        vh.item_tv_product_name.setText(getItem(position).key);
        vh.item_tv_product_info.setText(getItem(position).value);
        vh.mItem_detailed_name.setText(mInvesListModel.name);
        vh.mItem_detailed_money.setText(mInvesListModel.loanMoney);
        vh.mItem_detailed_start_money.setText(mInvesListModel.minInvestMoney);
        vh.mItem_detailed_increase_money.setText(mInvesListModel.cardinalNumber);
        vh.mItem_detailed_name.setText(mInvesListModel.name);
        vh.mItem_detailed_way.setText(mInvesListModel.loanType);
        vh.mItem_detailed_custodys.setText(mInvesListModel.custodySave);
        vh.mItem_detailed_closure.setText(mInvesListModel.deadline);
        vh.mItem_detailed_time.setText(mInvesListModel.verifyTime + "至" + mInvesListModel.exceptTimeApp);

        vh.mItem_detailed_interest_accruals.setText(mInvesListModel.interestType);
        vh.mItem_detailed_service_money.setText("无");
        if ("天".equals(mInvesListModel.unit)) {
            vh.item_info.setText("项目期限(天)");
        }
        vh.mInclude_tv_chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GhbGlobalData(context).setPlanning(mInvesListModel.typeUrl);

            }
        });
        if (mInvesListModel.type.equals("loan_type_6")) {
            vh.mInclude_lin_chakan.setVisibility(View.VISIBLE);
            vh.mInclude_inc_xian.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v, int position) {

    }

    @SuppressLint("NewApi")
    public class ItemViewHolder extends ClickableViewHolder {
        public TextView item_tv_product_name;
        public TextView item_tv_product_info;
        //项目名称
        public TextView mItem_detailed_name;
        //项目金额
        public TextView mItem_detailed_money;
        //预期收益
        public TextView mItem_detailed_income;
        //募集起止时间
        public TextView mItem_detailed_time;
        //项目封闭期
        public TextView mItem_detailed_closure;
        //起投金额
        public TextView mItem_detailed_start_money;
        //递增金额
        public TextView mItem_detailed_increase_money;
        //还款方式
        public TextView mItem_detailed_way;
        //计息方式
        public TextView mItem_detailed_interest_accruals;
        //资金存管
        public TextView mItem_detailed_custodys;
        //服务费
        public TextView mItem_detailed_service_money;
        public TextView item_info;
        //查看字体
        public TextView mInclude_tv_chakan;
        //查看字体整体条目
        public LinearLayout mInclude_lin_chakan;
        public View mInclude_inc_xian;

        public ItemViewHolder(View view) {
            super(view);
            item_tv_product_name = (TextView) view.findViewById(R.id.item_tv_product_name);
            item_tv_product_info = (TextView) view.findViewById(R.id.item_tv_product_info);
            //项目名称
            mItem_detailed_name = (TextView) view.findViewById(R.id.item_detailed_name);
            //项目金额
            mItem_detailed_money = (TextView) view.findViewById(R.id.item_detailed_money);
            //预期收益
            mItem_detailed_income = (TextView) view.findViewById(R.id.item_detailed_income);
            //募集起止时间
            mItem_detailed_time = (TextView) view.findViewById(R.id.item_detailed_time);
            //项目封闭期
            mItem_detailed_closure = (TextView) view.findViewById(R.id.item_detailed_closure);
            //器投金额
            mItem_detailed_start_money = (TextView) view.findViewById(R.id.item_detailed_start_money);
            //递增金额
            mItem_detailed_increase_money = (TextView) view.findViewById(R.id.item_detailed_increase_money);
            //还款方式
            mItem_detailed_way = (TextView) view.findViewById(R.id.item_detailed_way);
            //计息方式
            mItem_detailed_interest_accruals = (TextView) view.findViewById(R.id.item_detailed_interest_accruals);
            //资金存管
            mItem_detailed_custodys = (TextView) view.findViewById(R.id.item_detailed_custodys);
            //服务费
            mItem_detailed_service_money = (TextView) view.findViewById(R.id.item_detailed_service_money);
            item_info = (TextView) view.findViewById(R.id.item_info);
            mInclude_tv_chakan = (TextView) view.findViewById(R.id.include_tv_chakan);
            mInclude_lin_chakan = (LinearLayout) view.findViewById(R.id.include_lin_chakan);
            mInclude_inc_xian = view.findViewById(R.id.include_inc_xian);


            setOnRecyclerItemClickListener(BuyProductDetailsRiskInfoSimpleAdapter.this);
            //view.setOnClickListener(this);
            //mTvContent.setOnClickListener(this);
//            addOnItemViewClickListener();
//            addOnViewClickListener(item_ll_type_money);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "toString";
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

}
