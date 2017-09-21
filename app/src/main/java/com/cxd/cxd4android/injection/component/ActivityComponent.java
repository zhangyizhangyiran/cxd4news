package com.cxd.cxd4android.injection.component;

import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.injection.module.ActivityModule;

import dagger.Component;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/15 15:48
 * version：V1.0
 */
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    //    void inject(MainActivityTest activity);
    LocalUserModel userModel();
}