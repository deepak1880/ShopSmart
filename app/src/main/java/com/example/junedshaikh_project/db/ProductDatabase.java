package com.example.junedshaikh_project.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.junedshaikh_project.utils.Constants;
@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductDao getProductDao();

    private static volatile ProductDatabase INSTANCE;

    public static ProductDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ProductDatabase.class, Constants.DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
