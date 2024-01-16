package com.example.stocka.CustomerInfoScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
fun CustomerInfoScreen(
    navController: NavController, viewModel: AuthViewModel){
    val customer = viewModel.customerSelected.value
    customer?.userId.let {
        Box(
            modifier = Modifier.fillMaxWidth()
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
                                navController.navigate(Destination.Customers.routes)
                            },
                        tint = ListOfColors.black
                    )

                    Text(
                        text = "Customer Info",
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
                                viewModel.retrieveCustomerHistory(customer!!.customerId.toString())
                                 navController.navigate(Destination.CustomerHistory.routes)
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
                        text = "Customer's Name",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = "Amount Owed",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = customer?.customerName.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = customer?.customerBalance.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                ) {

                    Text(
                        text = "Customer's Number",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )


                    Text(
                        text = customer?.customerName.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )

                }

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                ) {

                    Text(
                        text = "Customer's Address",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )


                    Text(
                        text = customer?.customerAddress.toString(),
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )


                }


                Spacer(modifier = Modifier.padding(20.dp))

                Row(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .align(Alignment.CenterHorizontally),
                ) {

                    Button(
                        onClick = {
                            navController.navigate(Destination.MakeCredit.routes)
                            viewModel.onCustomerSelected(customer!!)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                        Text(

                            text = "Add Credit",
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                                viewModel.getCustomer(customer!!)
                                 navController.navigate(Destination.EditCustomer.routes)
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .width(120.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.orange)


                    ) {
                        Text(
                            text = "Edit",
                            color = ListOfColors.black
                        )
                    }

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
//fun CustomerInfoPrev(){
//    CustomerInfoScreen()
//}