package com.cxd.cxd4android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseFragment;

/**
 * Created by moon.zhong on 2015/2/4.
 */

public class MainServiceFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_main_se, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SeMainFragment SeMainFragment = new SeMainFragment();
        add(R.id.main_fr_main_se, SeMainFragment, "SeMainFragment", null);
    }

}
