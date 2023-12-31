package com.example.stockapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Product::class],
    version = 2
)

abstract class ProductDatabase : RoomDatabase() {
    abstract val dao : ProductDao
}