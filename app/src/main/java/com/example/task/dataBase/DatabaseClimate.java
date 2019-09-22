package com.example.task.dataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.task.dataBase.tables.ForecastTable;
import com.example.task.dataBase.tables.WeatherTable;

@androidx.room.Database(entities = {WeatherTable.class, ForecastTable.class}, version = 1)
public abstract class DatabaseClimate extends RoomDatabase {

    private static DatabaseClimate databaseClimate = null;
    private static final String DATABASE_NAME = "climateDB";

    public abstract DataAsscessObjects getDataAsscessObjects();

    public static DatabaseClimate getInstance(Context context) {
        if (databaseClimate == null) {
            databaseClimate = Room.databaseBuilder(context,
                    DatabaseClimate.class, DATABASE_NAME).
                    fallbackToDestructiveMigration().
                    addCallback(roomCallback).
                    build();
        }
        return databaseClimate;
    }



    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(databaseClimate).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DataAsscessObjects noteDao;

        private PopulateDbAsyncTask(DatabaseClimate db) {
            noteDao = db.getDataAsscessObjects();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WeatherTable weatherTable = new WeatherTable();
            weatherTable.setMintemp(23.2);
            noteDao.insertWeather(weatherTable);
            return null;
        }
    }
}
