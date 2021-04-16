package com.example.covid19app.localStorege;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = country.class, version = 2)
public abstract class RoomAppDb extends RoomDatabase {

    public ExecutorService databaseWriteExecutor=Executors.newFixedThreadPool(1);
    private static RoomAppDb INSTANCE=null;


    public RoomAppDb() {
    }
    abstract public CountryDeo userDao();
    public static RoomAppDb getAppDatabase(Context context){
        if (INSTANCE==null){
            INSTANCE=Room.databaseBuilder(context,RoomAppDb.class,"AppDBB")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;

    }





}
