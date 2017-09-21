package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.cxd.cxd4android.R;
import com.cxd.cxd4android.widget.rollout.activity.RolloutPreviewActivity;
import com.cxd.cxd4android.widget.rollout.model.RolloutBDInfo;
import com.cxd.cxd4android.widget.rollout.model.RolloutInfo;
import com.cxd.cxd4android.widget.rollout.tools.RGlideUtil;

import java.io.Serializable;
import java.util.ArrayList;


public class SeShowRolloutAnimViewAdapter extends RecyclerView.Adapter<SeShowRolloutAnimViewAdapter.NormalTextViewHolder> {
    private RolloutBDInfo bdInfo;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private RecyclerView mListView;
    private ArrayList<RolloutInfo> mData;
    private LinearLayoutManager mLinearLayoutManager;

    public SeShowRolloutAnimViewAdapter(Context context, RecyclerView listView, ArrayList<RolloutInfo> data, LinearLayoutManager linearLayoutManager) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        mListView = listView;
        mData = data;
        mLinearLayoutManager = linearLayoutManager;
        bdInfo = new RolloutBDInfo();
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.rollout_list_view, parent, false));

    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        RolloutInfo info = mData.get(position);
        RGlideUtil.setImage(mContext, info.url, holder.list_img);
        holder.list_img.setOnClickListener(new ImageOnclick(position, holder.list_img));


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class NormalTextViewHolder extends RecyclerView.ViewHolder {

        public ImageView list_img;

        NormalTextViewHolder(View view) {
            super(view);
            list_img = (ImageView) view.findViewById(R.id.list_img);
        }


    }

    private class ImageOnclick implements View.OnClickListener {

        private int index;
        private ImageView imageView;

        public ImageOnclick(int index, ImageView imageView) {
            this.index = index;
            this.imageView = imageView;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();


            //屏幕宽
            int widthPixels = displayMetrics.widthPixels;
            //屏幕高
            int heightPixels = displayMetrics.heightPixels;

            View c = mListView.getChildAt(0);
            //相对于其父视图的顶部位置
            int top = c.getTop();
            //返回适配器的数据集中显示在屏幕上的第一个项目的位置。
            int firstVisiblePosition = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();



            int[] location = new int[2];
            this.imageView.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];

            bdInfo.y = y;
//            bdInfo.y = heightPixels - this.imageView.getBottom();
            bdInfo.x =  x;


            //关于imageView想要有多宽
            bdInfo.width = imageView.getLayoutParams().width;
            bdInfo.height = imageView.getLayoutParams().height;

            Log.d("bdInfo.y", String.valueOf(bdInfo.y));
            Log.d("bdInfo.x", String.valueOf(bdInfo.x));

//            Toast.makeText(mContext, String.valueOf(bdInfo.x) +"pppppppp" +String.valueOf(bdInfo.y), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext, RolloutPreviewActivity.class);
            intent.putExtra("data", (Serializable) mData);
            intent.putExtra("bdinfo", bdInfo);
            intent.putExtra("index", index);
            intent.putExtra("type", 1
            );
            mContext.startActivity(intent);
        }
    }


}
