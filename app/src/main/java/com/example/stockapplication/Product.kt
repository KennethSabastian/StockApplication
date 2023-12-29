package com.example.stockapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val description: String,
    val stock: Int,
    val price: Int,
    val sold: Int,
    val image: String
)
