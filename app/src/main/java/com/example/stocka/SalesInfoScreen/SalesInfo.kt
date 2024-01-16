package com.example.stocka.SalesInfoScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.NavPram
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors
import java.text.SimpleDateFormat
import java.util.Date


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SalesInfoScreen(navController: NavController,viewModel:AuthViewModel) {

    val isLoading = viewModel.inProgress.value
    val salesLoading = viewModel.refreshSalesProgress.value
    val salesItem = viewModel.salesDetail.value
    val sales = viewModel.salesData.value
    val context = LocalContext.current
    val formattedDate = salesItem?.salesDate?.let {
        SimpleDateFormat("dd MMM yyyy").format(Date(it))
    } ?: ""



    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){

                Icon(imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "ArrowBack",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            navigateTo(navController, Destination.BottomSheet)
                        },
                    tint = ListOfColors.black
                )

                Text(
                    text = "Sales Info",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Spacer(modifier = Modifier.padding(15.dp))


            Column(
                modifier = Modifier.fillMaxSize()
            ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(start = 3.dp, end = 3.dp)
                ) {


                    Text(
                        text = "Customer Name",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = salesItem?.customerName.toString(),
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = "Invoice Number",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )

                    Text(
                        text = salesItem?.type.toString()+salesItem?.salesNo.toString(),
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )

                    Text(
                        text = "Date",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = formattedDate,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )

                }

                Spacer(modifier = Modifier.padding(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Text(
                        text = "Quantity",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Item",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Unit Price",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Total",
                        fontWeight = FontWeight.Bold
                    )
                }


                Spacer(modifier = Modifier.padding(5.dp))


                LazyColumn(
                    modifier = Modifier.wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    items(sales) { sale ->
                        sale.sales.let { salesList ->
                            salesList?.forEach { singleSale ->
                                SalesItemsDetails(sales = singleSale){
                                    viewModel.onSaleSelected(sale)
                                    navigateTo(navController,Destination.EditSales,NavPram("sale",it))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(start = 3.dp, end = 3.dp)
                ) {

                    Text(
                        text = "Total Quantity",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = "Total Amount",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = salesItem?.totalQuantity.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = salesItem?.totalPrice.toString(),
                        modifier = Modifier.align(Alignment.BottomEnd)
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
                            if (salesItem?.sales.isNullOrEmpty() || (salesItem?.sales?.size ?: 0) < 5) {
                                viewModel.onSaleSelected(salesItem!!)
                                viewModel.fromPage("notHome")
                                navigateTo(navController,Destination.AddSale)
                            }

                            else{
                                Toast.makeText(context,"Limit exceeded for adding sale", Toast.LENGTH_LONG).show()
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                        Text(

                            text = "Add Goods",
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                            viewModel.onSaleSelected(salesItem!!)
                            navController.navigate(Destination.SalesReceipt.routes)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Generate Receipt",
                            textAlign = TextAlign.Center
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
                        text = "Delete ",
                        color = ListOfColors.black
                    )
                }

            }


        }
    }
}


@Preview (showBackground = true)
@Composable
fun SalesInfoPrev(){
//    SalesInfoScreen()
}