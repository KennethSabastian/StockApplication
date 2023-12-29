package com.example.stockapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.stockapplication.ui.theme.StockApplicationTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, ProductDatabase::class.java,"products.db").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
    private val viewModel by viewModels<ProductViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T:ViewModel> create(modelClass: Class<T>): T{
                    return ProductViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockApplicationTheme {
                val state = viewModel.state.collectAsState().value
                ProductScreen(state = state, onEvent = viewModel::onEvent )
            }
        }
    }
}