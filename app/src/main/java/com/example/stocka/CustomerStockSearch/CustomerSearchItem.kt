package com.example.stocka.CustomerStockSearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import com.example.stocka.data.Customer

@Composable
fun CustomerSearchItem(
    customer: Customer,
    onClick:(Customer)->Unit
){
    Card(
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth()
            .height(70.dp)
            .clickable {
               onClick.invoke(customer)
            },
        shape = RoundedCornerShape(15.dp)

    ) {
        Box(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top= 5.dp, bottom = 5.dp)
        ) {


            Text(
                text ="Name",
                modifier = Modifier.align(Alignment.TopStart),
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "${customer.customerName}",
                modifier = Modifier.align(Alignment.BottomStart)
            )

            Text(
                text = "Balance",
                modifier = Modifier.align(Alignment.TopEnd),
                fontWeight = FontWeight.Medium

            )

            Text(
                text = "${customer.customerBalance}",
                modifier = Modifier.align(Alignment.BottomEnd)

            )


        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PrevCustomerItem(){
//    CustomerItem()
//}