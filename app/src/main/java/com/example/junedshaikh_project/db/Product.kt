package com.example.junedshaikh_project.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "product_table")
data class Product(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    var inCart: Boolean = false,
    var quantity: Int = 1

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
