package com.cxd.cxd4android.injection.module;

import com.cxd.cxd4android.injection.ShoppingCartModel;

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
public class ContainerModule {
    @Provides
    ShoppingCartModel provideCartModel() {
        return new ShoppingCartModel();
    }
}