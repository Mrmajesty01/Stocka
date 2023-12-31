package com.example.stocka.SalesInfoScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stocka.data.SingleSale

@Composable
fun SalesItemsDetails(
    sales: SingleSale,
    onClick:(SingleSale)->Unit
) {

    Card(
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth(.99f)
            .height(30.dp)
            .clickable {
              onClick.invoke(sales)
            },
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                text = sales.quantity.toString(),
                modifier = Modifier.padding(start = 5.dp)
            )

            Text(
                text = sales.productName.toString(),
                textAlign = TextAlign.Center
            )

            Text(
                text = sales.price.toString(),
            )

            Text(
                text = sales.totalPrice.toString(),
                modifier = Modifier.padding(end = 5.dp)
            )

        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun ItemDetailsPrev(){
//    ItemDetails()
//}