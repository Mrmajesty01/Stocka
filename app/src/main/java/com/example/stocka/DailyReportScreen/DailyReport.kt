package com.example.stocka.DailyReportScreen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.HomeScreen.formatNumberWithDelimiter
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors
import java.text.SimpleDateFormat
import java.util.Date


@Composable

fun DailyReportScreen(navController: NavController, viewModel:AuthViewModel){

    val saleReceiptTotal = viewModel.saleReceiptTotalToday.value
    val creditReceiptTotal = viewModel.creditReceiptTotalToday.value
    var mostBoughtProduct = viewModel.mostBoughtGoodToday.value
    var mostBoughtProductQty = viewModel.mostBoughtGoodQuantity.value
    val userData = viewModel.userData.value




    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf("Today") }
    var date by remember { mutableStateOf("Today's Date") }


    val options = listOf("Today", "This Week", "This Month", "All Time")


    var formattedDate = userData?.currentDate?.let {
            SimpleDateFormat("dd MMM yyyy").format(Date(it))
        } ?: ""


    val totalSalesFilter = viewModel.totalSalesFilter.value
    val totalSaleReceiptFilter = viewModel.totalSaleReceiptFilter.value
    val totalCreditFilter = viewModel.totalCreditReceiptFilter.value
    val totalProfitFilter = viewModel.totalProfitFilter.value
    val totalExpenseFilter = viewModel.totalExpenseFilter.value
    val totalProfitAfterExpenseFilter = viewModel.totalProfitAfterExpenseFilter.value
    val mostSoldGoodFilter = viewModel.mostBoughtGoodFilter.value
    val mostSoldGoodQtyFilter = viewModel.mostBoughtGoodQuantityFilter.value

    val profitAfterExpense = userData!!.totalProfit!!.toDouble()-userData.totalExpenses!!.toDouble()

    val totalSales = userData?.totalSales?.toDoubleOrNull() ?: 0.0
    val totalExpenses = userData?.totalExpenses?.toDoubleOrNull() ?: 0.0
    val totalProfit = userData?.totalProfit?.toDoubleOrNull() ?: 0.0

    var formattedSaleReceiptTotal = formatNumberWithDelimiter(saleReceiptTotal)
    var formattedCreditReceiptTotal = formatNumberWithDelimiter(creditReceiptTotal)
    var formattedTotalSales = formatNumberWithDelimiter(totalSales)
    var formattedTotalExpenses = formatNumberWithDelimiter(totalExpenses)
    var formattedTotalProfit = formatNumberWithDelimiter(totalProfit)
    var formattedProfitAfterExpense = formatNumberWithDelimiter(profitAfterExpense)


    if(selectedOption!="Today"){
        date = selectedOption
        formattedDate = selectedOption
        formattedSaleReceiptTotal= formatNumberWithDelimiter(totalSaleReceiptFilter)
        formattedCreditReceiptTotal = formatNumberWithDelimiter(totalCreditFilter)
        formattedTotalSales = formatNumberWithDelimiter(totalSalesFilter)
        formattedTotalProfit = formatNumberWithDelimiter(totalProfitFilter)
        formattedTotalExpenses = formatNumberWithDelimiter(totalExpenseFilter)
        formattedProfitAfterExpense = formatNumberWithDelimiter(totalProfitAfterExpenseFilter)
        mostBoughtProduct = mostSoldGoodFilter
        mostBoughtProductQty = mostSoldGoodQtyFilter

    }
    else{
        date = "Today's Date"
    }

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
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    tint = ListOfColors.black
                )

                when (date) {

                    "Today's Date" -> {
                        Text(
                            text = "Daily Report",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    "This Week" -> {
                        Text(
                            text = "Weekly Report",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    "This Month" -> {
                        Text(
                            text = "Monthly Report",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    "All Time" -> {
                        Text(
                            text = "All Time Report",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(all = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {


                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                ) {
                    options.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = option
                                viewModel.getDailyReport(option)
                                selectedIndex = index
                                expanded = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (index == selectedIndex) {
                                        Color.White
                                    } else {
                                        Color.Transparent
                                    }
                                )
                        ) {
                            Text(text = option, color = Color.Black)
                        }
                    }
                }



                Box(
                    modifier = Modifier
                        .height(45.dp)
                        .fillMaxWidth()
                )
                {

                    Text(
                        text = date,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )

                    if(date == "Today's Date") {

                        Text(
                            text = formattedDate,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }


                }


                Icon(imageVector = Icons.Default.FilterList, contentDescription ="filterList",
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            expanded = !expanded
                        }

                )




                Spacer(modifier = Modifier.padding(10.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                )
                {

                    if(date ==  "Today's Date") {
                        Text(
                            text = "Total Sales Today",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                    }
                    else{
                        Text(
                            text = "Total Sales",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                    }


                    if(date ==  "Today's Date") {
                        Text(
                            text = "Total Profit Today",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }
                    else
                    {
                        Text(
                            text = "Total Profit",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }


                    Text(
                        text = formattedTotalSales,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = formattedTotalProfit,
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

                    if(date ==  "Today's Date") {
                        Text(
                            text = "Sales Receipt Total",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                    }
                    else{
                        Text(
                            text = "Sales Receipt Total",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                    }

                    if(date ==  "Today's Date") {
                        Text(
                            text = "Credit Receipt Total",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }
                    else{
                        Text(
                            text = "Credit Receipt Total",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }

                    Text(
                        text = formattedSaleReceiptTotal,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = formattedCreditReceiptTotal,
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

                    if(date ==  "Today's Date") {
                        Text(
                            text = "Total Expenses",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                    }

                    else{
                        Text(
                            text = "Total Expenses",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                    }

                    if(date ==  "Today's Date") {
                        Text(
                            text = "Total Profit After Expenses",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }
                    else{
                        Text(
                            text = "Profit After Expenses",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }

                    Text(
                        text = formattedTotalExpenses,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = formattedProfitAfterExpense,
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

                    if(date ==  "Today's Date") {
                        Text(
                            text = "Most Sold Stock Today",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.BottomCenter)

                        )
                    }
                    else{
                        Text(
                            text = "Most Sold Stock $date",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.BottomCenter)

                        )
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                {

                    Text(
                        text = "Product Name: $mostBoughtProduct, Quantity Sold: $mostBoughtProductQty",
                        modifier = Modifier.align(Alignment.TopCenter)
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