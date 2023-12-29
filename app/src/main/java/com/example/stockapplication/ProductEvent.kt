package com.example.stockapplication

import android.net.Uri

sealed interface ProductEvent {
    data class SetTitle(val title: String): ProductEvent
    data class SetDescription(val description: String): ProductEvent
    data class SetPrice(val price: Int): ProductEvent
    data class SetStock(val stock: Int): ProductEvent
    data class SetSold(val sold: Int): ProductEvent
    data class SetId(val id: Int?): ProductEvent
    object ShowDialog: ProductEvent
    object HideDialog: ProductEvent
    object ShowUpdateDialog: ProductEvent
    object HideUpdateDialog: ProductEvent
    object SaveProduct: ProductEvent
    object UpdateProduct: ProductEvent
    data class SortProduct(val sortType : SortType): ProductEvent
    data class DeleteProduct(val product: Product): ProductEvent
    data class SetImage(val image: String): ProductEvent
}