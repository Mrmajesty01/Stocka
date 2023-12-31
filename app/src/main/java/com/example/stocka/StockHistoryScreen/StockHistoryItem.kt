package com.example.stocka.StockHistoryScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StockHistoryItem() {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
                .height(80.dp),
            shape = RoundedCornerShape(15.dp)

        ) {
            Box(
                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top= 5.dp, bottom = 5.dp)
            ) {


                Text(
                    text = "Item Name",
                    modifier = Modifier.align(Alignment.TopStart)
                )

                Text(
                    text = "Item Price",
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = "Purchase Price",
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                Text(
                    text = "Selling Price",
                    modifier = Modifier.align(Alignment.CenterEnd)

                )

                Text(
                    text = "Qty Added",
                    modifier = Modifier.align(Alignment.BottomStart)

                )


                Text(
                    text = "Date Added",
                    modifier = Modifier.align(Alignment.BottomEnd)
                )


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StockHistoryItemPrev() {
    StockHistoryItem()
}