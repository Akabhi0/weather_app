package com.example.task.dataBase;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;

@Database(entities = {WeatherTable.class, ForecastTable.class}, version = 3)
@TypeConverters(TypeConverter.class)
public abstract class DatabaseClimate extends RoomDatabase {

    private static DatabaseClimate databaseClimate = null;

    public abstract DataAsscessObjects getDataAsscessObjects();

    public static DatabaseClimate getInstance(Application application) {
        if (databaseClimate == null) {
            databaseClimate = Room.databaseBuilder(application,
                    DatabaseClimate.class, "climateDB").
                    fallbackToDestructiveMigration().
                    build();
        }
        return databaseClimate;
    }
}
