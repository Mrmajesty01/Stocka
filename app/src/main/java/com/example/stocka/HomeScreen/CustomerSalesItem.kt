package com.example.stocka.HomeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.stocka.data.Sales
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun CustomerSalesItem(
    sales: Sales,
    onClick:(Sales)-> Unit
) {
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
                onClick.invoke(sales)
            },
        shape = RoundedCornerShape(15.dp)

    ) {
        val formattedDate = sales?.salesDate?.let {
            SimpleDateFormat("dd MMM yyyy").format(Date(it))
        } ?: ""


        val salesAmountPaid = sales.amountPaid!!.toDoubleOrNull()
        val salesTotalAmount = sales.totalPrice!!.toDoubleOrNull()
        val salesBalance = sales.balance!!.toDoubleOrNull()

        val formattedTotalAmountPaid = formatNumberWithDelimiter(salesAmountPaid!!)
        val formattedTotalAmount = formatNumberWithDelimiter(salesTotalAmount!!)
        val formattedBalanceAmount = formatNumberWithDelimiter(salesBalance!!)

        Box(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 5.dp, bottom = 5.dp)
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = "Customer Name",
                    modifier = Modifier.align(Alignment.TopStart)
                )
                Text(
                    text = sales.customerName.toString(),
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.BottomStart)
                        .widthIn(max = 200.dp),
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Total Amount",
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = formattedTotalAmount,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }


            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                if (sales.type == "SR") {
                    Text(
                        text = "Sales Invoice",
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }
                else {
                    Text(
                        text = "Credit Invoice",
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }

                Text(
                    text = "Date",
                    modifier = Modifier.align(Alignment.TopCenter)
                )

                Text(
                    text = formattedDate,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )

                Text(
                    text = "Invoice Number",
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = sales.salesNo.toString(),
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .widthIn(max = 150.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = "Amount Payed",
                    modifier = Modifier.align(Alignment.TopStart)
                )

                Text(
                    text = formattedTotalAmountPaid,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = "Balance",
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = formattedBalanceAmount,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }

        }
    }

}







//@Preview(showBackground = true)
//@Composable
//fun CustomerSalesItemPrev(){
//    CustomerSalesItem()
//}