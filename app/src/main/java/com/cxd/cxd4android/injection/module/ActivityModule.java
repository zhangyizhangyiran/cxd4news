package com.cxd.cxd4android.injection.module;

import android.app.Activity;
import android.content.Context;

import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.injection.component.ActivityContext;

import dagger.Module;
import dagger.Provides;


/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/15 15:48
 * version：V1.0
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }
    @Provides
    LocalUserModel provideLocalUserModel() {
        return new LocalUserModel(provideContext());
    }
}