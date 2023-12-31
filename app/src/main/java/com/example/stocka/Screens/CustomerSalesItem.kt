package com.example.stocka.Screens

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
fun CustomerSalesItem(){
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
                text = "Name of Customer",
                modifier = Modifier.align(Alignment.TopStart)
            )

            Text(
                text = "Sales Invoice",
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Text(
                text = "Invoice Number",
                modifier = Modifier.align(Alignment.CenterEnd)
            )

            Text(
                text = "Invoice Date",
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = "Total Amount",
                modifier = Modifier.align(Alignment.BottomStart)
            )

            Text(
                text = "Amount Payed",
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            Text(
                text = "Balance",
                modifier = Modifier.align(Alignment.BottomEnd)
            )


        }
    }
}

@Preview (showBackground = true)
@Composable
fun CustomerSalesItemPrev(){
    CustomerSalesItem()
}