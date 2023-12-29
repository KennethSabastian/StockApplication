package com.example.stockapplication

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductDialog(state: ProductState, onEvent: (ProductEvent)-> Unit, modifier: Modifier){
    AlertDialog(
        onDismissRequest = { onEvent(ProductEvent.HideUpdateDialog) },
    ) {
        var imageuri by remember {
            mutableStateOf<Uri?>(null)
        }
        val photoPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {uri -> imageuri = uri}
        )
        Text(text = "Update Product", fontSize = 20.sp)
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            var price by remember { mutableStateOf(state.price.toString()) }
            var stock by remember { mutableStateOf(state.stock.toString()) }
            var title by remember { mutableStateOf(state.title) }
            var description by remember { mutableStateOf(state.description) }


            TextField(value = title, onValueChange = {title = it}, label = { Text(text = "Title") })
            TextField(value = description, onValueChange = {description = it}, label = { Text(text = "Description") })
            TextField(value = price, onValueChange = { price = it }, label = { Text(text = "Price") })
            TextField(value = stock, onValueChange = { stock = it }, label = { Text(text = "Stock") })
            Button(onClick = {
                onEvent(ProductEvent.SetPrice(price.toInt()))
                onEvent(ProductEvent.SetStock(stock.toInt()))
                onEvent(ProductEvent.SetTitle(title))
                onEvent(ProductEvent.SetDescription(description))
                onEvent(ProductEvent.UpdateProduct)
            }) {
                Text(text = "Update Product")
            }
        }
    }
}