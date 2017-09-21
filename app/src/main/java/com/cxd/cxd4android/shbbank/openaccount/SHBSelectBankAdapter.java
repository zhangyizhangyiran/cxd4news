package com.cxd.cxd4android.shbbank.openaccount;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.shbbank.model.SHBQueryBankList;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by administrator on 17/8/30.
 */

public class SHBSelectBankAdapter extends BaseLoadMoreRecyclerAdapter<SHBQueryBankList.SHBBankLIstModel, SHBSelectBankAdapter.ItemViewHolder> {
    private Context mContext;

    public SHBSelectBankAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shb_layout_select_bank_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.setIsRecyclable(false);

        return itemViewHolder;

    }

    @Override
    public void onBindItemViewHolder(final ItemViewHolder holder, final int position) {
        holder.mShb_layout_select_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SHBBankSelect(getItem(position).getBankName(), getItem(position).getBankDescription()));

                holder.mShb_activity_bank_icon_select.setVisibility(View.VISIBLE);
                Log.d("qwertyuytre", "12345676");
                ((SHBBankListActivity) mContext).finish();


            }
        });

        holder.mShb_tv_bank_name.setText(getItem(position).getBankName());
        holder.mShb_activity_bank_explain.setText(getItem(position).getBankDescription());

        Glide.with(mContext).load(getItem(position).getBankUrl()).into(holder.mShb_activity_bank_icon);


    }

    public class ItemViewHolder extends ClickableViewHolder {

        public LinearLayout mShb_layout_select_item;
        public TextView mShb_tv_bank_name;
        public ImageView mShb_activity_bank_icon;
        public TextView mShb_activity_bank_explain;
        public ImageView mShb_activity_bank_icon_select;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mShb_layout_select_item = (LinearLayout) itemView.findViewById(R.id.shb_layout_select_item);
            mShb_tv_bank_name = (TextView) itemView.findViewById(R.id.shb_tv_bank_name);
            mShb_activity_bank_icon = (ImageView) itemView.findViewById(R.id.shb_activity_bank_icon);
            mShb_activity_bank_explain = (TextView) itemView.findViewById(R.id.shb_activity_bank_explain);
            mShb_activity_bank_icon_select = (ImageView) itemView.findViewById(R.id.shb_activity_bank_icon_select);
        }

    }


}
