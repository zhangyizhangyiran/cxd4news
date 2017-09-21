package com.cxd.cxd4android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.BuyProductActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.model.InvesListModel;
import com.github.captain_miao.recyclerviewutils.BaseLoadMoreRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.micros.utils.Q;


/**
 * @author YanLu
 * @since 15/9/15
 */
public class MainInvesSimpleAdapter extends BaseLoadMoreRecyclerAdapter<InvesListModel, MainInvesSimpleAdapter.ItemViewHolder> implements OnRecyclerItemClickListener {

    Context context;
    private String mNumberFinal;


    public MainInvesSimpleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_inves_product, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.setIsRecyclable(false);

        return itemViewHolder;
    }


    private int type = 1;//页面类型

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindItemViewHolder(MainInvesSimpleAdapter.ItemViewHolder vh, final int position) {


        vh.item_tv_housing_mortgage.setText(getItem(position).name);
        vh.item_tv_total_percent.setText(getItem(position).rate);
        vh.mItem_type.setText(getItem(position).loanType);
        vh.mItem_date.setText(getItem(position).deadline + getItem(position).unit);
        String remainMoney = displayWithComma(getItem(position).remainMoney);
        vh.mItem_money.setText(remainMoney + "元");


        //加息显示判断
        if ("0".equals(getItem(position).addRate) || "".equals(getItem(position).addRate)) {
            vh.item_tv_jiaxi.setVisibility(View.GONE);
            vh.item_tv_jiaxi_number.setVisibility(View.GONE);
            vh.item_tv_total_percent.setText(getItem(position).rate);

        } else {
            vh.item_tv_jiaxi.setText(getItem(position).loanLabel);
            vh.item_tv_jiaxi.setVisibility(View.VISIBLE);
//            vh.item_tv_jiaxi.setText(getItem(position).loanLabel);
            vh.item_tv_jiaxi_number.setVisibility(View.VISIBLE);

            String rate = getItem(position).rate;
            String addRate = getItem(position).addRate;

            Float rateNumber = convertToDouble(rate, 0f);
            Float addRateNnmber = convertToDouble(addRate, 0f);
            Float Number = rateNumber - addRateNnmber;
            mNumberFinal = String.valueOf(Number);
            String s = setString(addRate);
            vh.item_tv_jiaxi_number.setText("+" + s + "%");

            //截取字符串
            if (mNumberFinal.contains(".")) {
                mNumberFinal = setString(mNumberFinal);
            }

            vh.item_tv_total_percent.setText(mNumberFinal);


        }


        /**
         * recheck  ：复核中
         * complete ：已完成
         * raising  投标中 可以投资，其他状态都不可以投资
         * repaying 还款中
         */

        if (("raising").equals(getItem(position).status)) {//投标中 可以投资，其他状态都不可以投资

            String progress = getItem(position).progress;
            String pro = "";
            if (!Q.isEmpty(progress)) {
                pro = String.valueOf((Double.parseDouble(progress) * 100 / 100));
                if (pro.contains(".")) {
                    if (pro.startsWith("0") && Double.parseDouble(progress) != 0) {
                        pro = "1";
                    } else {
                        int index = pro.indexOf(".");
                        pro = pro.substring(0, index);
                    }
                }

                if (progress.contains(".")) {
                    if (progress.startsWith("0") && Double.parseDouble(progress) != 0) {
                        progress = "1";
                    } else {
                        int index = progress.indexOf(".");
                        progress = progress.substring(0, index);
                    }
                }
            } else {
                pro = "0";
                progress = "0";
            }

//            vh.circleBar.setProgress(Integer.valueOf(pro), 1);//显示进度条
//            vh.circleBar.setText(Integer.valueOf(progress));//显示百分比文字
//            vh.circleBar.startCustomAnimation();//开启动画
            vh.mItem_progressbars.setProgress(Integer.valueOf(pro));

        } else if (("complete").equals(getItem(position).status)) {//已完成(已售罄)
            setColor(vh);

        } else if (("recheck").equals(getItem(position).status)) {//复核中
            setColor(vh);

        } else if (("repaying").equals(getItem(position).status)) {//还款中

            setColor(vh);

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setColor(ItemViewHolder vh) {
        vh.mItem_progressbars.setProgress(0);
        vh.mItem_progressbars.setProgress(0);
        vh.mItem_type.setTextColor(context.getResources().getColor(R.color.item_color));
        vh.mItem_money.setTextColor(context.getResources().getColor(R.color.item_color));
        vh.mItem_date.setTextColor(context.getResources().getColor(R.color.item_color));
        vh.item_tv_total_percent.setTextColor(context.getResources().getColor(R.color.item_color));
        vh.item_tv_housing_mortgage.setTextColor(context.getResources().getColor(R.color.item_color));
        vh.mItem_xinhao.setTextColor(context.getResources().getColor(R.color.item_color));
        vh.item_tv_jiaxi_number.setTextColor(context.getResources().getColor(R.color.item_color));
        vh.item_tv_jiaxi.setTextColor(context.getResources().getColor(R.color.item_color));
        vh.item_tv_jiaxi.setBackground(context.getResources().getDrawable(R.drawable.shape_jiaxi_hui));

    }

    private String setString(String s) {


        if (s.contains(".")) {

            String[] array = s.split("\\.");

            String s1 = array[1];
            char FirstNumber = s1.charAt(0);
            String firstNumber = String.valueOf(FirstNumber);
            int parseInt = Integer.parseInt(firstNumber);

            if (s1.length() > 1) {
                char TwoNumber = s1.charAt(1);
                String twoNumber = String.valueOf(TwoNumber);
                int two = Integer.parseInt(twoNumber);

                if (parseInt > 0 && two > 0) {
                    return array[0] + "." + firstNumber + twoNumber;
                }

                if (parseInt < 0 && two > 0) {
                    return array[0] + "." + firstNumber + twoNumber;
                }
                if (parseInt > 0 && two < 0) {
                    return array[0] + "." + firstNumber;
                }


            } else {
                if (firstNumber.equals("0")) {
                    return array[0];
                }
            }

        }


        return s;
    }

    //需要在handler里修改UI
/*    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (type == 1) {
                circleBar.setProgress(270, 1);
                circleBar.setText(1000);
                circleBar.startCustomAnimation();
            } else if (type == 2) {
                circleBar.setProgress(180, 2);
                circleBar.setText(1500);
                circleBar.startCustomAnimation();
            } else if (type == 3) {
                circleBar.setProgress(360, 3);
                circleBar.startCustomAnimation();
            }
        }

        ;
    };*/

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

                Intent intent = new Intent(context, BuyProductActivity.class);
                if (position >= 0 && getItem(position) != null) {
                    intent.putExtra("InvesListModel", getItem(position));
                    context.startActivity(intent);
                }
                //((Activity)context).overridePendingTransition(R.anim.activity_next_in_animation, R.anim.activity_next_out_animation);
                StatService.onEvent(context, BaiDustatistic.invest_item_makemoney, "", 1);//事件统计
        }
    }


    public class ItemViewHolder extends ClickableViewHolder {
        public TextView item_tv_housing_mortgage;
        //        public TextView item_tv_release_time;
        public TextView item_tv_total_percent;

        public TextView item_tv_jiaxi;
        public TextView item_tv_jiaxi_number;
        //投资期限
        public TextView mItem_date;
        //还款方式
        public TextView mItem_type;
        //剩余可投
        public TextView mItem_money;
        // 进度条
        public ProgressBar mItem_progressbars;
        public TextView mItem_xinhao;

        public ItemViewHolder(View view) {
            super(view);
            item_tv_housing_mortgage = (TextView) view.findViewById(R.id.item_tv_housing_mortgage);
            item_tv_total_percent = (TextView) view.findViewById(R.id.item_tv_total_percent);
            item_tv_jiaxi = (TextView) view.findViewById(R.id.item_tv_jiaxi);
            item_tv_jiaxi_number = (TextView) view.findViewById(R.id.item_tv_jiaxi_number);
            mItem_date = (TextView) view.findViewById(R.id.item_date);
            mItem_type = (TextView) view.findViewById(R.id.item_type);
            mItem_money = (TextView) view.findViewById(R.id.item_money);
            mItem_progressbars = (ProgressBar) view.findViewById(R.id.item_progressbars);
            mItem_xinhao = (TextView) view.findViewById(R.id.item_xinhao);
            setOnRecyclerItemClickListener(MainInvesSimpleAdapter.this);
            addOnItemViewClickListener();
            addOnViewClickListener(item_tv_housing_mortgage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + item_tv_housing_mortgage.getText();
        }
    }

    //把String转化为double
    public static Float convertToDouble(String number, Float defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
//            return Double.parseDouble(number);
            return Float.parseFloat(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }

    /**
     * <pre>
     * 将字符串从右至左每三位加一逗号
     * </pre>
     *
     * @param str 需要加逗号的字符串
     * @return 以从右至左每隔3位加一逗号显示
     */
    public static String displayWithComma(String str) {
        str = new StringBuffer(str).reverse().toString(); // 先将字符串颠倒顺序
        String str2 = "";

        int size = (str.length() % 3 == 0) ? (str.length() / 3) : (str.length() / 3 + 1); // 每三位取一长度

    /*
     * 比如把一段字符串分成n段,第n段可能不是三个数,有可能是一个或者两个,
     * 现将字符串分成两部分.一部分为前n-1段,第二部分为第n段.前n-1段，每一段加一",".而第n段直接取出即可
     */
        for (int i = 0; i < size - 1; i++) { // 前n-1段
            str2 += str.substring(i * 3, i * 3 + 3) + ",";
        }

        for (int i = size - 1; i < size; i++) { // 第n段
            str2 += str.substring(i * 3, str.length());
        }

        str2 = new StringBuffer(str2).reverse().toString();

        return str2;
    }


}
