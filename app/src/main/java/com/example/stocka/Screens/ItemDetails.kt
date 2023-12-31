package com.example.stocka.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.HomeScreen.HomeEvent


@Composable
fun ItemDetails(){

    Card(
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth()
            .height(30.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                text = "quantity",
                modifier = Modifier.padding(start = 5.dp)
            )

            Text(
                text = "productName",
                textAlign = TextAlign.Center
            )

            Text(
                text = "price"
            )

            Text(
                text = "totalPrice",
                modifier = Modifier.padding(end = 5.dp)
            )

        }

    }
}

//@Preview (showBackground = true)
//@Composable
//fun ItemDetailsPrev(){
//    ItemDetails()
//}