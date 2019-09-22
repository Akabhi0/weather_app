package com.example.task.dataBase;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;

@androidx.room.Database(entities = {WeatherTable.class, ForecastTable.class}, version = 5)
public abstract class DatabaseClimate extends RoomDatabase {

    private static DatabaseClimate databaseClimate;
    private static final String DATABASE_NAME = "climate_new_db";

    public abstract DataAsscessObjects getDataAsscessObjects();

    public static DatabaseClimate getInstance(Application application) {
        if (databaseClimate == null) {
            databaseClimate = Room.databaseBuilder(application.getApplicationContext(),
                    DatabaseClimate.class, DATABASE_NAME).
                    fallbackToDestructiveMigration().
                    build();
        }
        return databaseClimate;
    }

}
