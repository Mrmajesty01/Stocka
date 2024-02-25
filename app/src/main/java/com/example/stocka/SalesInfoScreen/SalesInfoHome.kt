package com.example.stocka.SalesInfoScreen


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.HomeScreen.formatNumberWithDelimiter
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors
import java.text.SimpleDateFormat
import java.util.Date


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SalesInfoHome(navController: NavController,viewModel:AuthViewModel) {

    val sale = viewModel.salesDetail.value

    val focus = LocalFocusManager.current

    val isLoadingDelete = viewModel.deleteSaleProgress.value

    val isLoading = viewModel.getSaleProgress.value

    val isLoadingCustomer = viewModel.customerProgress.value

    val context = LocalContext.current

    val formattedDate = sale?.salesDate?.let {
        SimpleDateFormat("dd MMM yyyy").format(Date(it))
    } ?: ""



    if(sale?.sales.isNullOrEmpty()){
        navigateTo(navController,Destination.Home)
    }

    var totalAmount = sale?.totalPrice?.toDoubleOrNull() ?: 0.0
    var formattedTotalAmount = formatNumberWithDelimiter(totalAmount!!)


    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if(openDialog){
        AlertDialog(
            onDismissRequest = { openDialog = false },

            title = {
                Text(text = "Delete Sale")
            },

            text = {
                Text(text = "Are you sure you want to delete sale ?")
            },

            confirmButton = {
                TextButton(onClick = {
                    openDialog = false
                    viewModel.deleteEntireDocument(
                        customerId = sale?.customerId.toString(),
                        salesId = sale?.salesId.toString()
                    ) {
                        navigateTo(navController, Destination.Home)
                    }
                }) {
                    Text(text = "Yes")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    openDialog = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }


    sale?.userId.let {


        Box(
            modifier = Modifier.fillMaxSize()

        ) {

            if (isLoading || isLoadingCustomer ||isLoadingDelete) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray.copy(alpha = 0.5f))
                        .clickable {}
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "ArrowBack",
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .size(15.dp)
                            .clickable {
                                if (!isLoading || !isLoadingCustomer || !isLoadingDelete) {
                                    navController.popBackStack()
                                }
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
                            text = sale?.customerName.toString(),
                            modifier = Modifier.align(Alignment.TopEnd)
                        )


                        Text(
                            text = "Invoice Number",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )

                        Text(
                            text = sale?.salesNo.toString(),
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
                        modifier = Modifier
                            .background(if (isLoading || isLoadingCustomer || isLoadingDelete) ListOfColors.lightGrey else Color.Transparent),
                        verticalArrangement = Arrangement.spacedBy(7.dp),
                    ) {
                        items(sale?.sales.orEmpty()) { singleSale ->
                            SalesItemsDetails(sales = singleSale, viewModel) {
                                if (!isLoading || !isLoadingCustomer || !isLoadingDelete) {
                                    if (sale != null) {
                                        viewModel.onSaleSelected(sale)
                                        viewModel.fromPage("saleInfoHome")
                                        viewModel.getStockSelected(singleSale)
                                        viewModel.getSingleSale(singleSale, sale)
                                    }
                                    navController.navigate(Destination.EditSales.routes)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState()),
                    ) {

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
                                text = sale?.totalQuantity.toString(),
                                modifier = Modifier.align(Alignment.BottomStart)
                            )

                            Text(
                                text = formattedTotalAmount,
                                modifier = Modifier.align(Alignment.BottomEnd)
                            )

                        }

                        Spacer(modifier = Modifier.padding(20.dp))

                        Row(
                            Modifier
                                .align(Alignment.CenterHorizontally),
                        ) {
                            Button(
                                onClick = {
                                    if (!isLoading || !isLoadingCustomer || !isLoadingDelete) {
                                        if (sale?.sales.isNullOrEmpty() || (sale?.sales?.size
                                                ?: 0) < 15
                                        ) {
                                            viewModel.onSaleSelected(sale!!)
                                            viewModel.fromPage("home")
                                            navigateTo(navController, Destination.AddSale)
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Limit exceeded for adding sale",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
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
                                    if (!isLoading || !isLoadingCustomer || !isLoadingDelete) {
                                        viewModel.onSaleSelected(sale!!)
                                        navController.navigate(Destination.SalesReceipt.routes)
                                    }
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
                            onClick = {
                                if (!isLoading || !isLoadingCustomer || !isLoadingDelete) {
                                    focus.clearFocus(force = true)
                                    openDialog = true
                                }
                            },
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

            if(isLoading || isLoadingCustomer || isLoadingDelete){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }

        }
    }
}



