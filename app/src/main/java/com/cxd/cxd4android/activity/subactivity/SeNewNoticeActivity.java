package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.SeNewNoticeDetailsFragment;
import com.cxd.cxd4android.fragment.SeNewNoticeFragment;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.model.NoticeModel;


/**
 * ClassName:服务-最新公告页面
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class SeNewNoticeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_se_new_notice);

        if (savedInstanceState == null) {
            NoticeModel noticeModel = (NoticeModel) getIntent().getSerializableExtra("noticeModel");

            String page= noticeModel != null ? noticeModel.page : null;
            if ("notice".equals(page)){

                SeNewNoticeDetailsFragment SeNewNoticeDetailsFragment = new SeNewNoticeDetailsFragment();
                Bundle Bundle = new Bundle();
                Bundle.putString("noticeId",noticeModel.action);
                Bundle.putString("notice",noticeModel.title);
                Bundle.putString("page",page);
                SeNewNoticeDetailsFragment.setArguments(Bundle);

                add(R.id.fragment_newnotice, SeNewNoticeDetailsFragment, "SeNewNoticeDetailsFragment", null);
            }else {

                SeNewNoticeFragment seNewNoticeFragment = new SeNewNoticeFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("noticeModel", noticeModel);
                seNewNoticeFragment.setArguments(bundle);

                add(R.id.fragment_newnotice, seNewNoticeFragment, "SeNewNoticeFragment", null);
            }
        }
    }

    /**
     * Fragment移除控制
     * @param tag
     */
    public void remove(String tag) {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(tag)).commitAllowingStateLoss();
    }
}
