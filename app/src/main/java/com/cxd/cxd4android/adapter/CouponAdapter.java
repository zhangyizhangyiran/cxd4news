package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.model.RedPaperModel;
import com.cxd.cxd4android.widget.dialog.CouponDialog;
import com.cxd.cxd4android.widget.dialog.DialogPost;
import com.github.captain_miao.recyclerviewutils.recyclerview.RecyclerViewUtils;
import com.micros.utils.D;
import com.micros.utils.S;
import com.micros.utils.T;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by zhangyi on 17/2/14.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private CouponDialog couponDialog;
    private Context context;
    private RedPaperModel redPaper;

    public CouponAdapter(RedPaperModel redPaperModel, CouponDialog couponDialog, Context context) {
        this.redPaper = redPaperModel;
        this.couponDialog = couponDialog;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_coupon, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //判断是否可用
        if (redPaper.getResult().get(position).getIs_can_use().equals("YES")) {
            holder.lin_coupon.setClickable(true);
            holder.dialog_dot.setVisibility(View.VISIBLE);
            holder.dialog_use.setText("可用");
            String id = (String) S.get(context, "couponid", "");
            //判断是否点击过
            if (id.equals(redPaper.getResult().get(position).getCoupon_user_id())) {
                holder.dialog_dot.setImageDrawable(context.getResources().getDrawable(R.drawable.vip_selected));
            }

        } else {
            holder.lin_coupon.setClickable(false);
            holder.dialog_dot.setVisibility(View.INVISIBLE);
            holder.dialog_use.setText("不可用");
        }
        //红包开始时间
        long coupon_time = redPaper.getResult().get(position).getCoupon_time();
        //红包结束时间
        long overdue_time = redPaper.getResult().get(position).getOverdue_time();
        String stringcoupon_time = D.getStringByFormat(coupon_time, "yyyy-MM-dd");
        String stringoverdue_time = D.getStringByFormat(overdue_time, "yyyy-MM-dd");
        //红包条件
        String coupon_describe = redPaper.getResult().get(position).getCoupon_describe();
        holder.dialog_condition.setText(coupon_describe);
        //红包有效期
        holder.dialog_validity.setText(stringcoupon_time + "-" + stringoverdue_time);
        //红包编号
        holder.dialog_number.setText(String.valueOf(redPaper.getResult().get(position).getCoupon_user_id()));
        //红包大小
        holder.dialog_hongbao.setText(String.valueOf(redPaper.getResult().get(position).getCoupon_amount()));
        //判断月标类型
        String loan_type = redPaper.getResult().get(position).getLoan_type();
        holder.dialog_suit.setText(loan_type);

        holder.lin_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //不可用
                if (redPaper.getResult().get(position).getIs_can_use().equals("NO")) {
                    holder.lin_coupon.setClickable(false);
                }
                //可用
                else {
                    S.put(context, "couponid", redPaper.getResult().get(position).getCoupon_user_id());
                    EventBus.getDefault().post(new DialogPost(redPaper.getResult().get(position).getCoupon_user_id(), redPaper.getResult().get(position).getCoupon_amount()));
                    couponDialog.dismiss();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return redPaper.getResult().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout lin_coupon;
        public ImageView img_coupon;
        //红包大小
        public TextView dialog_hongbao;
        //条件
        public TextView dialog_condition;
        //适用
        public TextView dialog_suit;
        //有效期
        public TextView dialog_validity;
        //编号
        public TextView dialog_number;
        //圆点
        public ImageView dialog_dot;
        //判断是否可用
        public TextView dialog_use;

        public ViewHolder(View view) {
            super(view);
            lin_coupon = (LinearLayout) view.findViewById(R.id.lin_coupon);
            img_coupon = (ImageView) view.findViewById(R.id.img_coupon);
            dialog_hongbao = (TextView) view.findViewById(R.id.dialog_hongbao);
            dialog_condition = (TextView) view.findViewById(R.id.dialog_condition);
            dialog_suit = (TextView) view.findViewById(R.id.dialog_suit);
            dialog_validity = (TextView) view.findViewById(R.id.dialog_validity);
            dialog_number = (TextView) view.findViewById(R.id.dialog_number);
            dialog_dot = (ImageView) view.findViewById(R.id.dialog_dot);
            dialog_use = (TextView) view.findViewById(R.id.dialog_use);

        }
    }


}
