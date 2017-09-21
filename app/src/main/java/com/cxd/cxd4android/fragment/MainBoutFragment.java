package com.cxd.cxd4android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseFragment;

import butterknife.ButterKnife;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MainBoutFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_main_bout, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view!=null) {
            BoutMainFragment BoutMainFragment = new BoutMainFragment();
            add(R.id.main_fr_main___, BoutMainFragment, "BoutMainFragment", null);
        }
    }
}
