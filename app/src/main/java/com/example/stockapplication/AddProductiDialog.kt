package com.example.stockapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.io.FileDescriptor
import java.io.IOException
import java.nio.charset.StandardCharsets
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(state: ProductState, onEvent: (ProductEvent)-> Unit, modifier: Modifier){
    AlertDialog(
        onDismissRequest = { onEvent(ProductEvent.HideDialog) },
    ) {
        var imageuri by remember {
            mutableStateOf<Uri?>(null)
        }
        val photoPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {uri -> imageuri = uri}
        )
        Text(text = "Add Product", fontSize = 20.sp)
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            var price by remember { mutableStateOf("") }
            var stock by remember { mutableStateOf("") }



            TextField(value = state.title, onValueChange = {onEvent(ProductEvent.SetTitle(it))}, label = { Text(text = "Title")})
            TextField(value = state.description, onValueChange = {onEvent(ProductEvent.SetDescription(it))}, label = { Text(text = "Description")})
            TextField(value = price, onValueChange = { price = it }, label = { Text(text = "Price")})
            TextField(value = stock, onValueChange = { stock = it }, label = { Text(text = "Stock")})
            Button(onClick = {photoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )}){
                Text(text = "Select Image")
            }
            Button(onClick = {
                onEvent(ProductEvent.SetPrice(price.toInt()))
                onEvent(ProductEvent.SetStock(stock.toInt()))
                onEvent(ProductEvent.SaveProduct)
            }) {
                Text(text = "Add Product")
            }
            AsyncImage(model = imageuri, contentDescription = "Image",
                modifier = Modifier.height(120.dp).width(120.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}


