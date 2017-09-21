package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.model.BillModel;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;

/**
 * @author YanLu
 * @since 15/9/15
 */
public class MeMyBillSimpleAdapter extends BaseLoadMoreRecyclerAdapter<BillModel, MeMyBillSimpleAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    Context  context;
//    private  boolean Clicked = false;
    public MeMyBillSimpleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_my_bill, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.setIsRecyclable(false);
        return itemViewHolder;
    }


    @Override
    public void onBindItemViewHolder(final MeMyBillSimpleAdapter.ItemViewHolder vh, final int position) {
//        vh.item_tv_id_dete.setText(getItem(position).seqNum+"");
//        vh.item_tv_id_num.setText(getItem(position));

//        vh.model = getItem(position);
//        vh.item_ll_type_money.setTag(getItem(position));
        vh.setIsRecyclable(false);
        final BillModel billModel = getItem(position);
        vh.item_tv_time_time.setText(getItem(position).time);
//        vh.item_tv_time_dete.setText(getItem(position));
        vh.item_tv_amount_money.setText(getItem(position).money+"");
        vh.item_tv_balance_money.setText(getItem(position).balance+"");
        vh.item_tv_type_money.setText(getItem(position).typeInfo);
        vh.item_tv_remarks_money.setText("备注:\t"+getItem(position).detail);

        vh.item_bill_lly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BillModel  dd = (BillModel) v.getTag();
//                            if (vh.popoposion == position){


                if (billModel.Clicked) {
                    billModel.Clicked = false;
                    vh.item_tv_remarks_money.setVisibility(View.GONE);
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_common_top);
                    vh.item_tv_type_money.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                }else {
                    billModel.Clicked = true;
                    vh.item_tv_remarks_money.setVisibility(View.VISIBLE);
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_common_bottom);
                    vh.item_tv_type_money.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable, null);

                }

//                            }
            }
        });
    }


    @Override
    public void onClick(View v, int position) {
        switch (v.getId()) {
            case R.id.item_bill_lly:
                //Toast.makeText(v.getContext(), "on click " + position, Toast.LENGTH_SHORT).show();
                //vh.item_tv_remarks_money.setVisibility(View.VISIBLE);
                break;
            default:
                //mock click todo  last item
                //remove(position);
                //notifyItemRemoved(position);
        }
    }


    public class ItemViewHolder extends ClickableViewHolder {
//        public TextView item_tv_id_dete;
//        public TextView item_tv_id_num;
        public TextView item_tv_time_time;
//        public TextView item_tv_time_dete;
        public TextView item_tv_amount_money;
        public TextView item_tv_balance_money;
        public TextView item_tv_type_money;
        public TextView item_tv_remarks_money;

        public LinearLayout item_bill_lly;

        public boolean popoposion;
//        BillModel model;

        public ItemViewHolder(View view) {
            super(view);
            popoposion = false;
//            item_tv_id_dete = (TextView) view.findViewById(R.id.item_tv_id_dete);
//            item_tv_id_num = (TextView) view.findViewById(R.id.item_tv_id_num);
            item_tv_time_time = (TextView) view.findViewById(R.id.item_tv_time_time);
//            item_tv_time_dete = (TextView) view.findViewById(R.id.item_tv_time_dete);
            item_tv_amount_money = (TextView) view.findViewById(R.id.item_tv_amount_money);
            item_tv_balance_money = (TextView) view.findViewById(R.id.item_tv_balance_money);
            item_tv_type_money = (TextView) view.findViewById(R.id.item_tv_type_money);
            item_tv_remarks_money = (TextView) view.findViewById(R.id.item_tv_remarks_money);
            item_bill_lly = (LinearLayout) view.findViewById(R.id.item_bill_lly);

            setOnRecyclerItemClickListener(MeMyBillSimpleAdapter.this);
            //view.setOnClickListener(this);
            //mTvContent.setOnClickListener(this);
            addOnItemViewClickListener();
            addOnViewClickListener(item_bill_lly);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + item_tv_type_money.getText();
        }
    }

}
