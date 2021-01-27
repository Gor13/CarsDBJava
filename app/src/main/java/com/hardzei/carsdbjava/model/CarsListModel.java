package com.hardzei.carsdbjava.model;

import android.util.Log;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.hardzei.carsdbjava.MainContract;
import com.hardzei.carsdbjava.db.Car;
import com.hardzei.carsdbjava.db.CarDao;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CarsListModel implements MainContract.ModelCallBack {

    private final CompositeDisposable compositeDisposable;

    private final String SORTED_BY_MODEL_ASC = "model ASC";
    private final String SORTED_BY_MODEL_DESC = "model DESC";

    private final String SORTED_BY_COLOR_ASC = "color ASC";
    private final String SORTED_BY_COLOR_DESC = "color DESC";

    private final String SORTED_BY_YEAR_ASC = "year ASC";
    private final String SORTED_BY_YEAR_DESC = "year DESC";

    private final String SORTED_BY_PRICE_ASC = "price ASC";
    private final String SORTED_BY_PRICE_DESC = "price DESC";

    private final String SORTED_BY_MILEAGE_ASC = "mileage ASC";
    private final String SORTED_BY_MILEAGE_DESC = "mileage DESC";

    private final CarDao carDao;

    @Inject
    public CarsListModel(CarDao carDao) {
        this.carDao = carDao;
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
                carDao.deleteAllCars();
                carDao.insertCars(List.of(
                        new Car("Mercedes", "red", 2000, 2234.0, 20000.0),
                        new Car("BMW", "green", 1992, 2564.0, 33333.0),
                        new Car("Jeep", "blue", 1500, 4467.0, 444444.0),
                        new Car("Honda", "pink", 1999, 44367.0, 4443444.0),
                        new Car("Jeep", "gold", 2030, 5467.0, 554444.0),
                        new Car("Audi", "black", 2020, 1234.0, 111.0),
                        new Car("Volga", "white", 2014, 6777.0, 7777.0)));
            }
        });
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getSortedCars(OnFinishedListener onFinishedListener, int sortMethod) {
        Disposable disposable = carDao
                .getSortedCars(new SimpleSQLiteQuery("SELECT * FROM cars_table ORDER BY " + getSortRequest(sortMethod)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cars -> {
                    onFinishedListener.onFinished(cars);
                    Log.d("SUCCESS SORT", String.valueOf(cars.size()));
                }, throwable -> {
                    onFinishedListener.onError(throwable.getMessage());
                    Log.d("ERROR SORT", throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void getCarsBySearch(OnFinishedListener onFinishedListener, String searchRequest) {
        Disposable disposable = carDao
                .getCarsListByRequest(searchRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cars -> {
                    onFinishedListener.onFinished(cars);
                    Log.d("SUCCESS SEARCH", String.valueOf(cars.size()));
                }, throwable -> {
                    onFinishedListener.onError(throwable.getMessage());
                    Log.d("ERROR SEARCH", throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void dispose() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    private String getSortRequest(int sortMethod) {
        switch (sortMethod) {
            case 0: {
                return SORTED_BY_MODEL_ASC;
            }
            case 1: {
                return SORTED_BY_MODEL_DESC;
            }
            case 2: {
                return SORTED_BY_YEAR_ASC;
            }
            case 3: {
                return SORTED_BY_YEAR_DESC;
            }
            case 4: {
                return SORTED_BY_PRICE_ASC;
            }
            case 5: {
                return SORTED_BY_PRICE_DESC;
            }
            case 6: {
                return SORTED_BY_COLOR_ASC;
            }
            case 7: {
                return SORTED_BY_COLOR_DESC;
            }
            case 8: {
                return SORTED_BY_MILEAGE_ASC;
            }
            default: {
                return SORTED_BY_MILEAGE_DESC;
            }
        }
    }
}
