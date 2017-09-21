//package com.cxd.cxd4android.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.cxd.cxd4android.R;
//import com.cxd.cxd4android.activity.subactivity.DebtTransferDetailsActivity;
//import com.cxd.cxd4android.model.InvesListModel;
//import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
//import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
//import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
//
///**
// * @version V1.0
// * @ClassName:债权转让适配器
// * @Description:
// * @Author：xiaofa
// * @Date：2016/7/19 15:52
// */
//public class MainDebtTransferAdapter extends BaseLoadMoreRecyclerAdapter<InvesListModel, MainDebtTransferAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {
//
//    Context context;
//    public MainDebtTransferAdapter( Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_layout_debt_transfer, parent, false);
//
//        return new MainDebtTransferAdapter.ItemViewHolder(view);
//    }
//
//
//    private int type = 1;//页面类型
//
//    @Override
//    public void onBindItemViewHolder(MainDebtTransferAdapter.ItemViewHolder vh, final int position) {
//        vh.item_debt_housing_mortgage.setText(getItem(position).name);
//        vh.item_debt_release_time.setText("发布时间:"+ getItem(position).exceptTime);
//        vh.item_debt_total_percent.setText(getItem(position).rate);
//        vh.item_discount_gold.setText(getItem(position).deadline+getItem(position).unit);
//        vh.item_make_over_price.setText(getItem(position).loanMoney);
//        vh.item_on_sale_money.setText(getItem(position).remainMoney);
//
//        vh.buyBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//
//               /* if (type == 1) {
//                    type = 2;
//                } else if (type == 2) {
//                    type = 3;
//                } else if (type == 3) {
//                    type = 1;
//                }
//                Message msg = new Message();
//                handler.sendMessage(msg);*/
//
//
//                Intent intent = new Intent(context, DebtTransferDetailsActivity.class);
//                if(position>=0 && getItem(position)!=null) {
//                    intent.putExtra("InvesListModel", getItem(position));
//                    context.startActivity(intent);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v, int position) {
//        switch (v.getId()){
//            case R.id.item_debt_buybtn:
//                Intent intent = new Intent(context, DebtTransferDetailsActivity.class);
//                if(position>=0 && getItem(position)!=null) {
//                    intent.putExtra("InvesListModel", getItem(position));
//                    context.startActivity(intent);
//                }
//                break;
//        }
//    }
//
//    public class ItemViewHolder extends ClickableViewHolder {
//        public TextView item_debt_housing_mortgage;
//        public TextView item_debt_release_time;
//        public TextView item_debt_total_percent;
//        public TextView item_discount_gold;
//        public TextView item_make_over_price;
//        public TextView item_on_sale_money;
//        public TextView item_remain_days;
//        public TextView buyBtn;
////        public LinearLayout item_ll_item_layout;
//
//        public ItemViewHolder(View view) {
//            super(view);
//            item_debt_housing_mortgage = (TextView) view.findViewById(R.id.item_debt_housing_mortgage);
//            item_debt_release_time = (TextView) view.findViewById(R.id.item_debt_release_time);
//            item_debt_total_percent = (TextView) view.findViewById(R.id.item_debt_total_percent);
//            item_discount_gold = (TextView) view.findViewById(R.id.item_discount_gold);
//            item_make_over_price = (TextView) view.findViewById(R.id.item_make_over_price);
//            item_on_sale_money = (TextView) view.findViewById(R.id.item_on_sale_money);
//            item_remain_days = (TextView) view.findViewById(R.id.item_remain_days);
//            buyBtn = (TextView) view.findViewById(R.id.item_debt_buybtn);
////            item_ll_item_layout = (LinearLayout) view.findViewById(R.id.item_ll_item_layout);
//
//
//            setOnRecyclerItemClickListener(MainDebtTransferAdapter.this);
//            //view.setOnClickListener(this);
//            //mTvContent.setOnClickListener(this);
//            addOnItemViewClickListener();
//            addOnViewClickListener(item_debt_housing_mortgage);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + item_debt_housing_mortgage.getText();
//        }
//    }
//
//}
