package com.hardzei.carsdbjava.di.module;

import com.hardzei.carsdbjava.db.CarDao;
import com.hardzei.carsdbjava.model.CarsListModel;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    @Provides
    public CarsListModel provideModelClass(CarDao carDao) {
        return new CarsListModel(carDao);
    }
}
