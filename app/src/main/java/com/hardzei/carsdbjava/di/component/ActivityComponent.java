package com.hardzei.carsdbjava.di.component;


import com.hardzei.carsdbjava.MainContract;
import com.hardzei.carsdbjava.di.module.MvpModule;
import com.hardzei.carsdbjava.di.scope.ActivityScope;
import com.hardzei.carsdbjava.view.CarsListActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MvpModule.class)
public interface ActivityComponent {
    void inject(CarsListActivity carsListActivity);

    MainContract.PresenterCallBack getCarsListPresenter();
}
