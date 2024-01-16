package com.example.stocka.StockInfo

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StockInfoScreen(
    navController: NavController, viewModel: AuthViewModel){

    val stock = viewModel.stockSelected.value

    stock?.userId.let {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "BackIcon",
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .size(15.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                               navController.navigate(Destination.Stocks.routes)
                            },
                        tint = ListOfColors.black
                    )

                    Text(
                        text = "Stock Info",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )

                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "HistoryIcon",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(15.dp)
                            .align(Alignment.CenterEnd)
                            .clickable {
                                viewModel.retrieveStockHistory(stock?.stockId.toString())
                                navController.navigate(Destination.StockHistory.routes)
                            },
                        tint = ListOfColors.black
                    )
                }

                Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

                Spacer(modifier = Modifier.padding(15.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 10.dp, end = 10.dp)
                ) {

                    Text(
                        text = "Stock Name",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = "Unit Price",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = stock?.stockName.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = stock?.stockSellingPrice.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 10.dp, end = 10.dp)
                ) {

                    Text(
                        text = "Quantity Sold",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = "Quantity Remaining",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = stock?.stockQuantitySold.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = stock?.stockQuantity.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    onClick = {
                          viewModel.getStock(stock!!)
                          navController.navigate(Destination.EditStock.routes)
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(ListOfColors.orange)


                ) {
                    Text(
                        text = "Edit",
                        color = ListOfColors.black
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))


                Button(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(ListOfColors.orange)


                ) {
                    Text(
                        text = "Delete",
                        color = ListOfColors.black
                    )
                }

            }
        }

    }
}



//@Preview (showBackground = true)
//@Composable
//fun StockInfoPrev(){
//    StockInfoScreen()
//}