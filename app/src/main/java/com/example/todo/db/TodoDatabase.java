package com.example.todo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Tasks.class},version = 1,exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TasksDao tasksDao();
    private static TodoDatabase INSTANCE;
    public static TodoDatabase getDbInstance(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TodoDatabase.class,"Tasks.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
