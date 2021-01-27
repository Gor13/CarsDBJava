package com.hardzei.carsdbjava.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hardzei.carsdbjava.InitApplication;
import com.hardzei.carsdbjava.MainContract;
import com.hardzei.carsdbjava.R;
import com.hardzei.carsdbjava.adapters.CarAdapter;
import com.hardzei.carsdbjava.db.Car;
import com.hardzei.carsdbjava.di.component.DaggerActivityComponent;
import com.hardzei.carsdbjava.di.module.MvpModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarsListActivity extends AppCompatActivity implements MainContract.ViewCallBack {

    @Inject
    MainContract.PresenterCallBack presenterCallBack;

    @Inject
    Context mContext;

    @Nullable
    @BindView(R.id.carsRecyclerView)
    RecyclerView carsRecyclerView;
    @Nullable
    @BindView(R.id.sortedSpinner)
    Spinner sortedSpinner;
    SearchView searchView;

    private CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);

        DaggerActivityComponent.builder()
                .appComponent(InitApplication.get(this).component())
                .mvpModule(new MvpModule(this))
                .build()
                .inject(this);

        ButterKnife.bind(this);

        initAdapter();
        initListeners();
    }

    private void initAdapter() {
        carAdapter = new CarAdapter();
        if (carsRecyclerView != null) {
            carsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        carsRecyclerView.setAdapter(carAdapter);
    }

    private void initListeners() {
        if (sortedSpinner != null) {
            sortedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    presenterCallBack.getListWithCars(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    Log.d("CarsListActivity", "onNothingSelected");
                }

            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(onQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    private final SearchView.OnQueryTextListener onQueryTextListener =
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    getCarsFromDb(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    getCarsFromDb(newText);
                    return true;
                }

                private void getCarsFromDb(String searchText) {
                    searchText = "%"+searchText+"%";
                    presenterCallBack.getCarsBySearchRequest(searchText);
                }
            };

    @Override
    public void showData(List<Car> cars) {
        carAdapter.setCars(cars);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterCallBack.onDestroy();
    }
}