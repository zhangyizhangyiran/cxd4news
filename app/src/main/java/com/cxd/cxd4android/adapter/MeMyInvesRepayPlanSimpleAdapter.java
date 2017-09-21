package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.model.RepayPlanModel;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.micros.utils.Z;


/**
 * @author YanLu
 * @since 15/9/15
 */
public class MeMyInvesRepayPlanSimpleAdapter extends BaseLoadMoreRecyclerAdapter<RepayPlanModel, MeMyInvesRepayPlanSimpleAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    Context  context;
    public MeMyInvesRepayPlanSimpleAdapter( Context context) {
        this.context = context;
//        appendToList(items);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_my_inves_repayplan, parent, false);

        return new MeMyInvesRepayPlanSimpleAdapter.ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(MeMyInvesRepayPlanSimpleAdapter.ItemViewHolder vh, int position) {
        
        vh.item_tv_periods_num.setText(getItem(position).period+"");
        vh.item_tv_total_money.setText(String.valueOf(Z.round(Double.parseDouble(getItem(position).corpus) + Double.parseDouble(getItem(position).interest),2)));
        vh.item_tv_principal_money.setText(getItem(position).corpus+"");
        vh.item_tv_interest_money.setText(getItem(position).interest+"");
        vh.item_tv_counter_fee.setText(getItem(position).fee);
        vh.item_tv_default_interest.setText(getItem(position).defaultInterest+"");
        vh.item_tv_repay_status.setText(getItem(position).status);
        vh.item_tv_repay_dete.setText(getItem(position).repayDay+"");
        vh.item_tv_repay_time.setText(getItem(position).time+"");

        //总额 = 本金+利息
    }


    @Override
    public void onClick(View v, int position) {
        switch (v.getId()) {
            case R.id.item_tv_periods_num:


                break;
            default:
                //mock click todo  last item
                //remove(position);
                //notifyItemRemoved(position);
                break;
        }
    }


    public class ItemViewHolder extends ClickableViewHolder {
        public TextView item_tv_periods_num;
        public TextView item_tv_total_money;
        public TextView item_tv_principal_money;
        public TextView item_tv_interest_money;
        public TextView item_tv_counter_fee;
        public TextView item_tv_default_interest;
        public TextView item_tv_repay_status;
        public TextView item_tv_repay_dete;
        public TextView item_tv_repay_time;


        public ItemViewHolder(View view) {
            super(view);
            item_tv_periods_num = (TextView) view.findViewById(R.id.item_tv_periods_num);
            item_tv_total_money = (TextView) view.findViewById(R.id.item_tv_total_money);
            item_tv_principal_money = (TextView) view.findViewById(R.id.item_tv_principal_money);
            item_tv_interest_money = (TextView) view.findViewById(R.id.item_tv_interest_money);
            item_tv_counter_fee = (TextView) view.findViewById(R.id.item_tv_counter_fee);
            item_tv_default_interest = (TextView) view.findViewById(R.id.item_tv_default_interest);
            item_tv_repay_status = (TextView) view.findViewById(R.id.item_tv_repay_status);
            item_tv_repay_dete = (TextView) view.findViewById(R.id.item_tv_repay_dete);
            item_tv_repay_time = (TextView) view.findViewById(R.id.item_tv_repay_time);


            setOnRecyclerItemClickListener(MeMyInvesRepayPlanSimpleAdapter.this);
            //view.setOnClickListener(this);
            //mTvContent.setOnClickListener(this);
            addOnItemViewClickListener();
            addOnViewClickListener(item_tv_periods_num);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + item_tv_periods_num.getText();
        }
    }

}
