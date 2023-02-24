package com.example.wordclicking;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {EngWord.class, RusWord.class, EngWordRusWordCrossRef.class}, version = 1)
public abstract class MyRoomDB extends RoomDatabase {
    abstract WordsDAO wordsDAO();

    private static final String DB_NAME = "words.db";
    private static volatile MyRoomDB INSTANCE = null;

    static MyRoomDB create(Context ctxt, boolean memoryOnly) {
        Builder<MyRoomDB> b;
        if (memoryOnly) {
            b = Room.inMemoryDatabaseBuilder(ctxt.getApplicationContext(),
                    MyRoomDB.class);
        }
        else {
            b = Room.databaseBuilder(ctxt.getApplicationContext(), MyRoomDB.class,
                    DB_NAME);
        }
        return(b.build());
    }

    synchronized static MyRoomDB get(Context ctxt) {
        if (INSTANCE == null) {
            INSTANCE = create(ctxt, false);
        }
        return(INSTANCE);
    }
}
