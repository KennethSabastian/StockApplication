package com.example.stockapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: Product)

    @Upsert
    fun upsertProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM product ORDER BY title ASC")
    fun getProductOrderedByTitle(): Flow<List<Product>>

    @Query("SELECT * FROM product ORDER BY price ASC")
    fun getProductOrderedByPrice(): Flow<List<Product>>

    @Query("SELECT * FROM product ORDER BY id ASC")
    fun getProductOrderedById(): Flow<List<Product>>
}