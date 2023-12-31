package com.example.stocka.ui.theme

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
fun StockItem() {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
                .height(60.dp),
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
                    text = "Qty Sold",
                    modifier = Modifier.align(Alignment.TopEnd)

                )



                Text(
                    text = "Qty Remaining",
                    modifier = Modifier.align(Alignment.BottomStart)
                )


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PrevStockItem() {
    StockItem()
}