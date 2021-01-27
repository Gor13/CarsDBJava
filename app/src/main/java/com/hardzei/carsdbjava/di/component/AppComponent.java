package com.hardzei.carsdbjava.di.component;

import android.app.Application;
import android.content.Context;

import com.hardzei.carsdbjava.InitApplication;
import com.hardzei.carsdbjava.MainContract;
import com.hardzei.carsdbjava.db.CarDao;
import com.hardzei.carsdbjava.db.CarsDatabase;
import com.hardzei.carsdbjava.di.module.AppModule;
import com.hardzei.carsdbjava.di.module.ContextModule;
import com.hardzei.carsdbjava.di.module.DataModule;
import com.hardzei.carsdbjava.di.module.RoomModule;
import com.hardzei.carsdbjava.model.CarsListModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, ContextModule.class, RoomModule.class})
public interface AppComponent {
    void inject(InitApplication initApplication);

    Context getContext();

    CarsListModel getFindItemsInteractor();
}
