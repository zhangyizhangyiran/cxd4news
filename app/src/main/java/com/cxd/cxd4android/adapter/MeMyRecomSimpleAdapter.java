package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.model.RecomListModel;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.micros.utils.D;
import com.micros.utils.Q;

import java.util.Date;

/**
 * @author YanLu
 * @since 15/9/15
 */
public class MeMyRecomSimpleAdapter extends BaseLoadMoreRecyclerAdapter<RecomListModel, MeMyRecomSimpleAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    Context  context;
    private  boolean Clicked = false;
    public MeMyRecomSimpleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_my_recom, parent, false);

        return new MeMyRecomSimpleAdapter.ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(final MeMyRecomSimpleAdapter.ItemViewHolder vh, int position) {
        if (!Q.isEmpty(getItem(position).regTime)){
            vh.item_tv_recom_time.setText(D.getStringByFormat(new Date(Long.parseLong(getItem(position).regTime)+0),D.dateFormatYMD));
        }else {
            vh.item_tv_recom_time.setText("");
        }

        if (!Q.isEmpty(getItem(position).moblieNumber)){
            vh.item_tv_recom_user.setText(Q.hiddenMobile(getItem(position).moblieNumber));
        }else {
            vh.item_tv_recom_user.setText("");
        }

        if (("0").equals(getItem(position).status)){//0：未投标
            vh.item_tv_recom_status.setText("未投资");
            vh.item_tv_recom_status.setTextColor(Color.parseColor("#999999"));
        }else if (("1").equals(getItem(position).status)){//1：已投标
            vh.item_tv_recom_status.setText("已投资");
            vh.item_tv_recom_status.setTextColor(Color.parseColor("#F64242"));
        }


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


    public class ItemViewHolder extends ClickableViewHolder {
        public TextView item_tv_recom_time;
        public TextView item_tv_recom_user;
        public TextView item_tv_recom_status;
        public LinearLayout item_ll_type_money;

        public ItemViewHolder(View view) {
            super(view);
            item_tv_recom_time = (TextView) view.findViewById(R.id.item_tv_recom_time);
            item_tv_recom_user = (TextView) view.findViewById(R.id.item_tv_recom_user);
            item_tv_recom_status = (TextView) view.findViewById(R.id.item_tv_recom_status);

            setOnRecyclerItemClickListener(MeMyRecomSimpleAdapter.this);
            //view.setOnClickListener(this);
            //mTvContent.setOnClickListener(this);
            addOnItemViewClickListener();
            addOnViewClickListener(item_ll_type_money);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + item_tv_recom_time.getText();
        }
    }

}
