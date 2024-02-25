package com.example.stocka.CreditInfoScreen


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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.stocka.SalesInfoScreen.SalesItemsDetails
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreditInfoHomeScreen(navController: NavController, viewModel: AuthViewModel) {

    val creditItem = viewModel.salesDetail.value
    val sales = viewModel.salesData.value
    val customer = viewModel.customerSelected.value
    val isLoading = viewModel.getSaleProgress.value
    val isLoadingCustomer = viewModel.customerProgress.value
    val isLoadingDelete = viewModel.deleteSaleProgress.value
    val context = LocalContext.current
    val focus = LocalFocusManager.current

    if(creditItem?.sales.isNullOrEmpty()){
        navigateTo(navController,Destination.Home)
    }

    val formattedDate = creditItem?.salesDate?.let {
        SimpleDateFormat("dd MMM yyyy").format(Date(it))
    } ?: ""

    if(creditItem?.sales.isNullOrEmpty()){
        navigateTo(navController,Destination.Home)
    }


    var totalAmount = creditItem?.totalPrice?.toDoubleOrNull() ?: 0.0
    var formattedTotalAmount = formatNumberWithDelimiter(totalAmount)

    var totalAmountPaid = creditItem?.amountPaid?.toDoubleOrNull() ?: 0.0
    var formattedTotalAmountPaid = formatNumberWithDelimiter(totalAmountPaid)

    var totalBalance = creditItem?.balance?.toDoubleOrNull() ?: 0.0
    var formattedTotalBalance = formatNumberWithDelimiter(totalBalance)

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
                        customerId = creditItem?.customerId.toString(),
                        salesId = creditItem?.salesId.toString()
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

    Box(modifier = Modifier
        .fillMaxSize()
    ) {

        if (isLoading || isLoadingCustomer || isLoadingDelete) {
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
                            if(!isLoading || !isLoadingCustomer || !isLoadingDelete) {
                                navigateTo(navController, Destination.Home)
                            }
                        },
                    tint = ListOfColors.black
                )

                Text(
                    text = "Credit Info",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)


            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxSize()
            ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 5.dp, end = 5.dp)
                ) {

                    Text(
                        text = "Name of Customer",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = creditItem?.customerName.toString(),
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = "Customer balance",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                    Text(
                        text = customer?.customerBalance.toString(),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )

                }

                Spacer(modifier = Modifier.padding(5.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 5.dp, end = 5.dp)
                ) {

                    Text(
                        text = "Invoice Number",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Text(
                        text = creditItem?.salesNo.toString(),
                        modifier = Modifier.align(Alignment.TopEnd)
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
                    modifier = Modifier.wrapContentHeight()
                        .background(if (isLoading || isLoadingCustomer || isLoadingDelete) ListOfColors.lightGrey else Color.Transparent),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    items(creditItem?.sales.orEmpty()) { sale ->
                        SalesItemsDetails(sales = sale, viewModel) {
                            if (!isLoading || !isLoadingCustomer || !isLoadingDelete) {
                                if (sale != null) {
                                    viewModel.fromPage("creditInfoHome")
                                    viewModel.onSaleSelected(creditItem!!)
                                    viewModel.getStockSelected(sale)
                                    viewModel.getSingleSale(sale, creditItem!!)
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
                            .height(40.dp)
                            .padding(start = 5.dp, end = 5.dp)
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
                            text = creditItem?.totalQuantity.toString(),
                            modifier = Modifier.align(Alignment.BottomStart)
                        )

                        Text(
                            text = formattedTotalAmount,
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )

                    }


                    Spacer(modifier = Modifier.padding(5.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(start = 5.dp, end = 5.dp)
                    ) {

                        Text(
                            text = "Amount Paid",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )

                        Text(
                            text = formattedTotalAmountPaid,
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )

                    }

                    Spacer(modifier = Modifier.padding(5.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(start = 5.dp, end = 5.dp)
                    ) {


                        Text(
                            text = "Balance",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )

                        Text(
                            text = formattedTotalBalance,
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

                                    if (creditItem?.sales.isNullOrEmpty() || (creditItem?.sales?.size
                                            ?: 0) < 15
                                    ) {
                                        viewModel.onSaleSelected(creditItem!!)
                                        viewModel.fromPage("home")
                                        navigateTo(navController, Destination.AddCredit)
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
                                    viewModel.onSaleSelected(creditItem!!)
                                    navController.navigate(Destination.CreditReceipt.routes)
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
                                navController.navigate(Destination.PayCredit.routes)
                                viewModel.onSaleSelected(creditItem!!)
                                viewModel.onCustomerSelectedHome(creditItem.customerId.toString())
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(50.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            "Pay Credit"
                        )
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Button(
                        onClick = {
                            if (!isLoading || !isLoadingCustomer || !isLoadingDelete) {
                                openDialog = true
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(50.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            "Delete"
                        )
                    }
                }
            }
        }

        if(isLoading || isLoadingCustomer || isLoadingDelete){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
                    .align(Alignment.Center)
            )
        }
    }

}


//@Preview (showBackground = true)
//@Composable
//fun CreditInfoPrev(){
//    CreditInfoScreen()
//}