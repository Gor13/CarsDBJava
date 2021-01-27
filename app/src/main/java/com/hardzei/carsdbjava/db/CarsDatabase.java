package com.hardzei.carsdbjava.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Car.class}, version = 1, exportSchema = false)
public abstract class CarsDatabase extends RoomDatabase {

    public abstract CarDao carDao();
}
