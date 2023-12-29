package com.example.stockapplication

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProductScreen(state: ProductState, onEvent: (ProductEvent) -> Unit ){
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick ={
                onEvent(ProductEvent.ShowDialog)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ){
        padding ->
        if (state.isAddingProduct){
            AddProductDialog(state = state, onEvent = onEvent, modifier = Modifier)
        }
        if (state.isUpdatingProduct){
            UpdateProductDialog(state = state, onEvent = onEvent, modifier = Modifier)
        }
        @DrawableRes var image = R.drawable.ayam
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
            items(state.products) { product ->
                Card(
                    border = BorderStroke(1.dp, Color.Black),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),

                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Image(
                            painter = painterResource(id = image),
                            contentDescription = product.title,
                            modifier = Modifier
                                .height(120.dp)
                                .width(120.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                                .fillMaxHeight()

                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = product.title,
                                    fontSize = 25.sp
                                )
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    IconButton(onClick = {
                                        onEvent(ProductEvent.SetId(product.id))
                                        onEvent(ProductEvent.SetTitle(product.title))
                                        onEvent(ProductEvent.SetDescription(product.description))
                                        onEvent(ProductEvent.SetPrice(product.price))
                                        onEvent(ProductEvent.SetStock(product.stock))
                                        onEvent(ProductEvent.ShowUpdateDialog)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = "Edit",
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                    IconButton(onClick = {
                                        onEvent(
                                            ProductEvent.DeleteProduct(
                                                product
                                            )
                                        )
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "Delete",
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "${product.description}",
                                fontSize = 10.sp
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                            ) {
                                Text(
                                    text = "Rp. ${product.price}",
                                    fontSize = 25.sp
                                )
                                Text(
                                    text = "Stock: ${product.stock}",
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(end = 10.dp)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}