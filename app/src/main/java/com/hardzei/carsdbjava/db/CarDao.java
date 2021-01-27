package com.hardzei.carsdbjava.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface CarDao {
    @RawQuery(observedEntities = Car.class)
    Observable<List<Car>> getSortedCars(SupportSQLiteQuery query);

    @Query("SELECT * FROM cars_table WHERE model LIKE :searchRequest")
    Observable<List<Car>> getCarsListByRequest(String searchRequest);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCars(List<Car> movies);

    @Query("DELETE FROM cars_table")
    void deleteAllCars();
}
