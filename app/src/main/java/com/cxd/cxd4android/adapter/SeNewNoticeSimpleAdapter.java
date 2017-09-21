package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.SeNewNoticeDetailsFragment;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.model.NewNoticeListModel;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.orhanobut.logger.Logger;


/**
 * @author YanLu
 * @since 15/9/15
 */
public class SeNewNoticeSimpleAdapter extends BaseLoadMoreRecyclerAdapter<NewNoticeListModel, SeNewNoticeSimpleAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    Context context;
    FragmentManager FragmentManager;
    private String notice;

    public SeNewNoticeSimpleAdapter(Context context, FragmentManager FragmentManager ,String notice) {
        this.context = context;
        this.FragmentManager = FragmentManager;
        this.notice = notice;
        Logger.i("page===notice2=="+notice);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_new_notices, parent, false);

        return new ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(final ItemViewHolder vh, int position) {
        vh.newnotices_title.setText(getItem(position).title);
        vh.newnotices_time.setText(getItem(position).create_time);
        vh.newnotices_content.setText(getItem(position).description);
    }

    @Override
    public void onClick(View v, int position) {
        switch (v.getId()) {
            case R.id.newnotices_linear:
                //Toast.makeText(v.getContext(), "on click " + position, Toast.LENGTH_SHORT).show();

                NewNoticeListModel model = getItem(position);
                SeNewNoticeDetailsFragment(model);

                break;
            default:
                //mock click todo  last item
                //remove(position);
                //notifyItemRemoved(position);
                break;
        }
    }


    public class ItemViewHolder extends ClickableViewHolder {
        public LinearLayout newnotices_linear;
        public TextView newnotices_title;
        public TextView newnotices_time;
        public TextView newnotices_content;


        public ItemViewHolder(View view) {
            super(view);
            newnotices_linear = (LinearLayout) view.findViewById(R.id.newnotices_linear);
            newnotices_title = (TextView) view.findViewById(R.id.newnotices_title);
            newnotices_time = (TextView) view.findViewById(R.id.newnotices_time);
            newnotices_content = (TextView) view.findViewById(R.id.newnotices_content);


            setOnRecyclerItemClickListener(SeNewNoticeSimpleAdapter.this);
            //view.setOnClickListener(this);
            //mTvContent.setOnClickListener(this);
            addOnItemViewClickListener();
            addOnViewClickListener(newnotices_linear);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + newnotices_title.getText();
        }
    }
    private SeNewNoticeDetailsFragment SeNewNoticeDetailsFragment;
    /**
     * 服务最新公告
     */
    public void SeNewNoticeDetailsFragment(NewNoticeListModel model) {

        SeNewNoticeDetailsFragment = new SeNewNoticeDetailsFragment();
            Bundle Bundle = new Bundle();
            Bundle.putString("noticeId",model.id);
            Bundle.putString("notice",notice);
        SeNewNoticeDetailsFragment.setArguments(Bundle);

        add(R.id.fragment_newnotice, SeNewNoticeDetailsFragment, "SeNewNoticeDetailsFragment", null);
    }

    public void add(int containerViewId,BaseFragment fragment,String tag,String stackName) {
        FragmentManager.beginTransaction().add(containerViewId, fragment,tag).addToBackStack(stackName).commitAllowingStateLoss();
    }

}
