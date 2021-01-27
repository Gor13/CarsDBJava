package com.hardzei.carsdbjava;

import com.hardzei.carsdbjava.db.Car;

import java.util.List;

public interface MainContract {

    interface ViewCallBack {
        void showData(List<Car> cars);

        void showError(String errorMessage);
    }

    interface ModelCallBack {
        interface OnFinishedListener {
            void onFinished(List<Car> cars);

            void onError(String errorMessage);
        }

        void getSortedCars(OnFinishedListener onFinishedListener, int sortMethod);

        void getCarsBySearch(OnFinishedListener onFinishedListener, String searchRequest);

        void dispose();
    }

    interface PresenterCallBack {
        void getListWithCars(int sortMethod);

        void getCarsBySearchRequest(String searchRequest);

        void onDestroy();
    }
}
