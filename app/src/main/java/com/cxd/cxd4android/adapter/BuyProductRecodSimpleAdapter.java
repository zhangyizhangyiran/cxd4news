package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.model.ProductRecodModel;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.micros.utils.Q;

/**
 * @author YanLu
 * @since 15/9/15
 */
public class BuyProductRecodSimpleAdapter extends BaseLoadMoreRecyclerAdapter<ProductRecodModel, BuyProductRecodSimpleAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    Context  context;
    private  boolean Clicked = false;
    public BuyProductRecodSimpleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_buyproduct_recod, parent, false);

        return new BuyProductRecodSimpleAdapter.ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(final BuyProductRecodSimpleAdapter.ItemViewHolder vh, int position) {
        StringBuffer name = new StringBuffer();
        if (!Q.isEmpty(getItem(position).investUser)){
            for (int i = 0 ; i< getItem(position).investUser.length();i++){
                if (i == getItem(position).investUser.length()-1){
                    name.append(getItem(position).investUser.substring(getItem(position).investUser.length()-1));
                }else if (i == getItem(position).investUser.length()-2){
                    name.append(getItem(position).investUser.substring(getItem(position).investUser.length()-2,getItem(position).investUser.length()-1));
                }else {
                    name.append("*");
                }

            }
        }else {
            vh.item_tv_id_dete.setText("");
        }
        vh.item_tv_id_dete.setText(name);

        vh.item_tv_time_time.setText(getItem(position).money);
        vh.item_tv_amount_money.setText(getItem(position).time+"");
        vh.item_tv_balance_money.setText(getItem(position).status + "");
    }


    @Override
    public void onClick(View v, int position) {

    }


    public class ItemViewHolder extends ClickableViewHolder {
        public TextView item_tv_id_dete;
        public TextView item_tv_time_time;
        public TextView item_tv_amount_money;
        public TextView item_tv_balance_money;


        public ItemViewHolder(View view) {
            super(view);
            item_tv_id_dete = (TextView) view.findViewById(R.id.item_tv_id_dete);
            item_tv_time_time = (TextView) view.findViewById(R.id.item_tv_time_time);
            item_tv_amount_money = (TextView) view.findViewById(R.id.item_tv_amount_money);
            item_tv_balance_money = (TextView) view.findViewById(R.id.item_tv_balance_money);

            setOnRecyclerItemClickListener(BuyProductRecodSimpleAdapter.this);
            //view.setOnClickListener(this);
            //mTvContent.setOnClickListener(this);
            addOnItemViewClickListener();
//            addOnViewClickListener(item_ll_type_money);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "toString";
        }
    }

}
