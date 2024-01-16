package com.example.stocka.CustomerStockSearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stocka.data.Stock

@Composable
fun StockSearchItem(
    stock: Stock,
    onClick:(Stock) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
                .height(90.dp)
                .clickable {
                    onClick.invoke(stock)
                },
            shape = RoundedCornerShape(15.dp)

        ) {
            Box(
                modifier = Modifier.padding(top= 5.dp, bottom = 5.dp)
            ) {
                Box(
                    modifier = Modifier.height(40.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                ){
                    Text(
                        text ="Name",
                        modifier = Modifier.align(Alignment.TopStart)
                            .padding(start = 6.dp),
                        fontWeight = FontWeight.Medium,

                        )
                    Text(

                        text =" ${stock.stockName?.trim()}",
                        modifier = Modifier.align(Alignment.BottomStart)
                            .padding(start = 3.dp)
                    )


                    Text(
                        text = "Price",
                        modifier = Modifier.align(Alignment.TopEnd)
                            .padding(end = 6.dp),
                        fontWeight = FontWeight.Medium
                    )


                    Text(
                        text = "${stock.stockSellingPrice}",
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .padding(end = 6.dp)
                    )
                }

                Box(
                    modifier = Modifier.height(40.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                ){

                    Text(
                        text = "Quantity Sold",
                        modifier = Modifier.align(Alignment.TopStart)
                            .padding(start = 6.dp),
                        fontWeight = FontWeight.Medium

                    )

                    Text(
                        text = "${stock.stockQuantitySold}",
                        modifier = Modifier.align(Alignment.BottomStart)
                            .padding(start = 8.dp)

                    )

                    Text(
                        text = "Quantity Remaining",
                        modifier = Modifier.align(Alignment.TopEnd)
                            .padding(end = 6.dp),
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "${stock.stockQuantity}",
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .padding(end = 6.dp)
                    )

                }

            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun PrevStockItem() {
//    StockItem()
//}