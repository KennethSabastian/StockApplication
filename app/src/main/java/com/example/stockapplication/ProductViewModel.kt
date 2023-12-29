package com.example.stockapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val dao: ProductDao
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    private val _sortType = MutableStateFlow(SortType.TITLE)
    private val _product = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.TITLE -> dao.getProductOrderedByTitle()
            SortType.ID -> dao.getProductOrderedById()
            SortType.PRICE -> dao.getProductOrderedByPrice()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList() )
    val state = combine(_state,_product,_sortType){ state, product, sortType ->
        state.copy(
            products = product,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())
    fun onEvent(event: ProductEvent){
        when(event){
            is ProductEvent.DeleteProduct -> {
                dao.deleteProduct(event.product)
            }
            ProductEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingProduct = false
                ) }
            }
            is ProductEvent.SaveProduct -> {
                val title = state.value.title
                val description = state.value.description
                val price = state.value.price
                val sold = state.value.sold
                val image = state.value.image
                val stock = _state.value.stock
                val product = Product(
                    title = title,
                    description = description,
                    price = price,
                    stock = stock,
                    sold = sold,
                    image = image,
                    id = null
                )
                viewModelScope.launch {
                    dao.insertProduct(product)
                }
                _state.update {it.copy(
                    isAddingProduct = false,
                    title = "",
                    description = "",
                    price = 0,
                    stock = 0,
                    sold = 0,
                    id = null
                    )
                }
            }
            is ProductEvent.UpdateProduct -> {
                val title = state.value.title
                val description = state.value.description
                val price = state.value.price
                val stock = state.value.stock
                val sold = state.value.sold
                val image = state.value.image
                val id = state.value.id

                val product = Product(
                    title = title,
                    description = description,
                    price = price,
                    stock = stock,
                    sold = sold,
                    image = image,
                    id = id
                )
                viewModelScope.launch {
                    dao.upsertProduct(product)
                }
                _state.update {it.copy(
                    isUpdatingProduct = false,
                    title = "",
                    description = "",
                    price = 0,
                    stock = 0,
                    sold = 0,
                    id = null
                )
                }
            }
            is ProductEvent.SetDescription -> {
                _state.update { it.copy(
                    description  = event.description
                ) }
            }
            is ProductEvent.SetPrice -> {
                _state.update { it.copy(
                    price = event.price
                ) }
            }
            is ProductEvent.SetStock -> {
                _state.update { it.copy(
                    stock = event.stock
                ) }
            }
            is ProductEvent.SetSold -> {
                _state.update { it.copy(
                    sold = event.sold
                ) }
            }
            is ProductEvent.SetTitle ->{
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            is ProductEvent.SetId ->{
                _state.update { it.copy(
                    id = event.id
                ) }
            }
            ProductEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingProduct = true
                ) }
            }
            ProductEvent.ShowUpdateDialog -> {
                _state.update { it.copy(
                    isUpdatingProduct = true
                ) }
            }
            ProductEvent.HideUpdateDialog -> {
                _state.update { it.copy(
                    isUpdatingProduct = false
                ) }
            }
            is ProductEvent.SortProduct -> {
                _sortType.value = event.sortType
            }
            is ProductEvent.SetImage ->{
                _state.update { it.copy(
                    image = event.image
                    ) }
            }
        }
    }
}