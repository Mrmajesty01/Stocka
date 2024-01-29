package com.example.stocka.DailyReportScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors
import java.text.SimpleDateFormat
import java.util.Date


@Composable

fun DailyReportScreen(navController: NavController, viewModel:AuthViewModel){

    val saleReceiptTotal = viewModel.saleReceiptTotalToday.value
    val creditReceiptTotal = viewModel.creditReceiptTotalToday.value
    val mostBoughtProduct = viewModel.mostBoughtGoodToday.value
    val mostBoughtProductQty = viewModel.mostBoughtGoodQuantity.value
    val userData = viewModel.userData.value
    val formattedDate = userData?.currentDate?.let {
        SimpleDateFormat("dd MMM yyyy").format(Date(it))
    } ?: ""

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackIcon",
                    modifier = Modifier.padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                               navController.popBackStack()
                        },
                    tint = ListOfColors.black
                )

                Text(
                    text = "Daily Report",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(all = 20.dp)
            ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                )
                {

                    Text(
                        text = "Today's Date",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )

                    Text(
                        text = formattedDate,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                )
                {

                    Text(
                        text = "Total Sales Today",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = "Total Profit Today",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = userData!!.totalSales.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = userData.totalProfit.toString(),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                )
                {

                    Text(
                        text = "Sales Receipt Total",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = "Credit Receipt Total",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = saleReceiptTotal.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = creditReceiptTotal.toString(),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                )
                {

                    Text(
                        text = "Expenses Today",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = "Profit After Expenses",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = userData!!.totalExpenses.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = (userData.totalProfit!!.toDouble()-userData.totalExpenses!!.toDouble()).toString(),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                {

                    Text(
                        text = "Most Sold Stock Today",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopCenter)
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Product Name: $mostBoughtProduct, Quantity Sold: $mostBoughtProductQty",
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }


            }

        }
    }

}

//@Composable
//@Preview(showBackground = true)
//fun DailyReportPrev(){
//    DailyReportScreen()
//}