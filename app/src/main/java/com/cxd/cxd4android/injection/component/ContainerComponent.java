package com.cxd.cxd4android.injection.component;

import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.injection.module.ContainerModule;

import dagger.Component;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/15 15:48
 * version：V1.0
 */
@Component(dependencies = ActivityComponent.class, modules = ContainerModule.class)
public interface ContainerComponent {
    void inject(BaseActivity baseActivity);

}