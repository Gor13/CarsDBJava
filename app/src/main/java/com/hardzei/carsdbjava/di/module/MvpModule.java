package com.hardzei.carsdbjava.di.module;

import com.hardzei.carsdbjava.MainContract;
import com.hardzei.carsdbjava.model.CarsListModel;
import com.hardzei.carsdbjava.presenter.CarsListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MvpModule {

    private MainContract.ViewCallBack viewCallBack;

    public MvpModule(MainContract.ViewCallBack viewCallBack) {
        this.viewCallBack = viewCallBack;
    }

    @Provides
    public MainContract.ViewCallBack provideView() {
        return viewCallBack;
    }

    @Provides
    public MainContract.PresenterCallBack providePresenter(MainContract.ViewCallBack view, CarsListModel model) {
        return new CarsListPresenter(view, model);
    }
}