package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.MyBsDetailsActivity;
import com.cxd.cxd4android.model.GiftRecommendModel;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;

/**
 * @version V1.0
 * @ClassName: 我的积分-适配器
 * @Description:
 * @Author：xiaofa
 * @Date：2016/4/22 10:23
 */
public  class MeMyBonusAdapter extends BaseLoadMoreRecyclerAdapter<GiftRecommendModel.ResultBean, MeMyBonusAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    Context  context;
    FragmentManager FragmentManager;
    public MeMyBonusAdapter( Context context) {
        this.context = context;
    }
    public MeMyBonusAdapter(Context context, FragmentManager FragmentManager) {
        this.context = context;
        this.FragmentManager = FragmentManager;
    }
    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bouns_points_list, parent, false);

        return new MeMyBonusAdapter.ItemViewHolder(view);
    }

    private int type = 1;//页面类型

    @Override
    public void onBindItemViewHolder(ItemViewHolder vh, final int position) {
        vh.item_bouns_points_title.setText(getItem(position).getName());
        vh.item_bouns_points_store.setText("库存："+getItem(position).getInventory());
        vh.item_bouns_points_count.setText(getItem(position).getIntegration());
        Glide.with(context).load(getItem(position).getDetailImgpath()).into(vh.item_bouns_points_img);
    }


    @Override
    public void onClick(View v, int position) {
        switch (v.getId()) {
            case R.id.tv_content:
                //Toast.makeText(v.getContext(), "on click " + position, Toast.LENGTH_SHORT).show();
                break;
            default:
                //mock click todo  last item
                //remove(position);
                //notifyItemRemoved(position);

                Intent intent = new Intent(context, MyBsDetailsActivity.class);
                if(position>=0 && getItem(position)!=null) {
                    intent.putExtra("GiftRecommendModel", getItem(position));
                    context.startActivity(intent);
                }
                //((Activity)context).overridePendingTransition(R.anim.activity_next_in_animation, R.anim.activity_next_out_animation);
        }
    }

    public class ItemViewHolder extends ClickableViewHolder {
        public TextView item_bouns_points_title;
        public TextView item_bouns_points_store;
        public TextView item_bouns_points_count;
        public ImageView item_bouns_points_img;
        public LinearLayout item_bouns_points_ll;
//        public LinearLayout item_ll_item_layout;

        public ItemViewHolder(View view) {
            super(view);
            item_bouns_points_title = (TextView) view.findViewById(R.id.item_bouns_points_title);
            item_bouns_points_store = (TextView) view.findViewById(R.id.item_bouns_points_store);
            item_bouns_points_count = (TextView) view.findViewById(R.id.item_bouns_points_count);
            item_bouns_points_img = (ImageView) view.findViewById(R.id.item_bouns_points_img);
            item_bouns_points_ll = (LinearLayout) view.findViewById(R.id.item_bouns_points_ll);
//            item_ll_item_layout = (LinearLayout) view.findViewById(R.id.item_ll_item_layout);


            setOnRecyclerItemClickListener(MeMyBonusAdapter.this);
            //view.setOnClickListener(this);
            //mTvContent.setOnClickListener(this);
            addOnItemViewClickListener();
            //addOnViewClickListener(item_tv_housing_mortgage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + item_bouns_points_title.getText();
        }
    }

}

