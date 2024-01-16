package com.example.stocka.StockHistoryScreen

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
import com.example.stocka.data.StockHistory
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun StockHistoryItem(
    stockHistory: StockHistory
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
                .height(130.dp),
            shape = RoundedCornerShape(15.dp)

        ) {
            val formattedDateAdded = stockHistory?.stockDateAdded?.let {
                SimpleDateFormat("dd MMM yyyy").format(Date(it))
            } ?: ""
            val formattedDateExpiry = stockHistory?.stockExpiryDate?.let {
                SimpleDateFormat("dd MMM yyyy").format(Date(it))
            } ?: ""
            Box(
                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 5.dp, bottom = 5.dp)
            ) {

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                ) {


                    Text(
                        text = "Stock Name",
                        modifier = Modifier.align(Alignment.TopStart),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Stock Expiry Date",
                        modifier = Modifier.align(Alignment.TopEnd),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = stockHistory.stockName.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = formattedDateExpiry,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )

                }

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                ) {


                    Text(
                        text = "Stock Purchase Price",
                        modifier = Modifier.align(Alignment.TopStart),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Stock Selling Price",
                        modifier = Modifier.align(Alignment.TopEnd),
                        fontWeight = FontWeight.Bold

                    )

                    Text(
                        text = stockHistory.stockPurchasePrice.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = stockHistory.stockSellingPrice.toString(),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )

                }


                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                ) {


                    Text(
                        text = "Quantity Added",
                        modifier = Modifier.align(Alignment.TopStart),
                        fontWeight = FontWeight.Bold

                    )


                    Text(
                        text = "Date Added",
                        modifier = Modifier.align(Alignment.TopEnd),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = stockHistory.stockQuantityAdded.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = formattedDateAdded,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )

                }

            }
        }
    }
}

