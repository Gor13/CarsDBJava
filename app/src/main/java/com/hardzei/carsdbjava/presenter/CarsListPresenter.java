package com.hardzei.carsdbjava.presenter;

import com.hardzei.carsdbjava.MainContract;
import com.hardzei.carsdbjava.db.Car;
import com.hardzei.carsdbjava.model.CarsListModel;

import java.util.List;

public class CarsListPresenter implements MainContract.PresenterCallBack, MainContract.ModelCallBack.OnFinishedListener {

    private MainContract.ViewCallBack mainView;
    private CarsListModel model;

    public CarsListPresenter(MainContract.ViewCallBack mainView, CarsListModel model) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void getListWithCars(int sortMethod) {
        model.getSortedCars(this, sortMethod);
    }

    @Override
    public void getCarsBySearchRequest(String searchRequest) {
        model.getCarsBySearch(this, searchRequest);
    }

    @Override
    public void onFinished(List<Car> cars) {
        if (mainView != null) {
            mainView.showData(cars);
        }
    }

    @Override
    public void onError(String errorMessage) {
        mainView.showError(errorMessage);
    }

    @Override
    public void onDestroy() {
        model.dispose();
        mainView = null;
    }
}
