package com.hardzei.carsdbjava;

import android.app.Application;
import android.content.Context;

import com.hardzei.carsdbjava.di.component.AppComponent;
import com.hardzei.carsdbjava.di.component.DaggerAppComponent;
import com.hardzei.carsdbjava.di.module.AppModule;
import com.hardzei.carsdbjava.di.module.ContextModule;
import com.hardzei.carsdbjava.di.module.DataModule;
import com.hardzei.carsdbjava.di.module.RoomModule;

public class InitApplication extends Application {

    private AppComponent component;

    public static InitApplication get(Context context) {
        return (InitApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .contextModule(new ContextModule(this))
                .dataModule(new DataModule())
                .roomModule(new RoomModule(this))
                .build();
    }

    public AppComponent component() {
        return component;
    }
}