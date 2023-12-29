package com.example.stockapplication

import android.net.Uri

data class ProductState(
    val products : List<Product> = emptyList(),
    val title : String = "",
    val description : String = "",
    val price : Int = 0,
    val stock : Int = 0,
    val sold : Int = 0,
    val image : String = "",
    val id : Int? = 0,
    val isAddingProduct : Boolean = false,
    val isUpdatingProduct : Boolean = false,
    val sortType: SortType = SortType.TITLE
)
