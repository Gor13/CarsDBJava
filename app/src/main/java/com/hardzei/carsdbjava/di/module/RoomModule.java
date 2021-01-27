package com.hardzei.carsdbjava.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hardzei.carsdbjava.MainContract;
import com.hardzei.carsdbjava.db.CarDao;
import com.hardzei.carsdbjava.db.CarsDatabase;
import com.hardzei.carsdbjava.model.CarsListModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private CarsDatabase carsDatabase;

    public RoomModule(Application application) {
        carsDatabase = Room.databaseBuilder(application,
                CarsDatabase.class,
                "cars.db")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                })
                .build();
    }

    @Singleton
    @Provides
    CarsDatabase providesCarsDatabase() {
        return carsDatabase;
    }

    @Singleton
    @Provides
    CarDao providesCarDao(CarsDatabase carsDatabase) {
        return carsDatabase.carDao();
    }

    @Singleton
    @Provides
    MainContract.ModelCallBack providesModelCallBack(CarDao carDao) {
        return new CarsListModel(carDao);
    }

}