package com.example.junedshaikh_project.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    LiveData<List<Product>> getCartProducts();

    @Query("SELECT Count(*) FROM product_table ")
    LiveData<Integer> getProductCount();
}