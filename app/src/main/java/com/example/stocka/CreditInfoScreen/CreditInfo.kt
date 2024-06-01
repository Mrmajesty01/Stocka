package com.example.stocka.CreditInfoScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
fun CreditInfoScreen(navController: NavController, viewModel: AuthViewModel){

    val creditItem = viewModel.salesDetail.value
    val sales = viewModel.salesData.value
    val isLoading = viewModel.inProgress.value
    val isLoadingDelete = viewModel.deleteSaleProgress.value
    val customer = viewModel.customerSelected.value
    val context = LocalContext.current

    val formattedDate = creditItem?.salesDate?.let {
        SimpleDateFormat("dd MMM yyyy").format(Date(it))
    } ?: ""

    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var openDialogDelete by rememberSaveable {
        mutableStateOf(false)
    }

    var openDialogPayCredit by rememberSaveable {
        mutableStateOf(false)
    }

    val features = viewModel.featuresToPin.value

    var editSalesHidden by remember {
        mutableStateOf(true)
    }

    var deleteSalesHidden by remember {
        mutableStateOf(true)
    }

    var payCreditHidden by remember {
        mutableStateOf(true)
    }

    var pin by remember { mutableStateOf(TextFieldValue()) }
    val userData = viewModel.userData.value

    LaunchedEffect(features){
        editSalesHidden = viewModel.featuresToPin.value.EditSales
        deleteSalesHidden = viewModel.featuresToPin.value.DeleteSales
        payCreditHidden = viewModel.featuresToPin.value.PayCredit
    }




    if(creditItem?.sales.isNullOrEmpty()){
        navigateTo(navController,Destination.BottomSheet)
    }

    var customerBalance = creditItem?.balance!!.toDoubleOrNull() ?: 0.0
    var formattedCustomerBalance = formatNumberWithDelimiter(customerBalance!!)

    var totalAmount = creditItem?.totalPrice!!.toDoubleOrNull() ?: 0.0
    var formattedTotalAmount = formatNumberWithDelimiter(totalAmount!!)

    var totalAmountPaid = creditItem?.amountPaid!!.toDoubleOrNull() ?: 0.0
    var formattedTotalAmountPaid = formatNumberWithDelimiter(totalAmountPaid!!)

    var totalBalance = creditItem?.balance!!.toDoubleOrNull() ?: 0.0
    var formattedTotalBalance = formatNumberWithDelimiter(totalBalance!!)

    if(openDialog){
        AlertDialog(
            onDismissRequest = { openDialog = false },

            title = {
                Text(text = "Delete Sale")
            },

            text = {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN To Delete Sale") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if(pin.text.toInt() == userData?.pin?.toInt()) {
                        openDialog = false
                        deleteSalesHidden = false
                        viewModel.deleteEntireDocument(
                            creditItem!!.customerId.toString(),
                            creditItem!!.salesId.toString()
                        ) {
                            navController.navigate(Destination.Home.routes)
                        }
                    }
                    else{
                        Toast.makeText(context, "wrong pin try again", Toast.LENGTH_LONG).show()
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

    if(openDialogDelete){
        AlertDialog(
            onDismissRequest = { openDialogDelete = false },

            title = {
                Text(text = "Delete Sale")
            },

            text = {
                Text(text = "Are you sure you want to delete sale ?")
            },

            confirmButton = {
                TextButton(onClick = {
                    openDialogDelete = false
                    viewModel.deleteEntireDocument(creditItem!!.customerId.toString(), creditItem!!.salesId.toString()){
                        navController.navigate(Destination.Home.routes)
                    }
                }) {
                    Text(text = "Yes")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    openDialogDelete = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }

    if(openDialogPayCredit){
        AlertDialog(
            onDismissRequest = { openDialogPayCredit = false },

            title = {
                Text(text = "Pay Credit")
            },

            text = {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN To Pay Credit") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if(pin.text.toInt() == userData?.pin?.toInt()) {
                        openDialogPayCredit = false
                        payCreditHidden = false
                        navController.navigate(Destination.PayCredit.routes)
                        viewModel.onSaleSelected(creditItem!!)
                        viewModel.onCustomerSelectedHome(creditItem.customerId.toString())
                    }
                    else{
                        Toast.makeText(context, "wrong pin try again", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Yes")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    openDialogPayCredit = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }

    Box(modifier = Modifier
        .fillMaxSize()
    ) {


        if (isLoading || isLoadingDelete) {
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
                    modifier = Modifier.padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            if(!isLoading || !isLoadingDelete) {
                                viewModel.customerSelected.value = null
                                navigateTo(navController, Destination.Customers)
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
                modifier = Modifier.padding(top = 20.dp)
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
                        text = formattedCustomerBalance,
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
                        .background(if (isLoading || isLoadingDelete) ListOfColors.lightGrey else Color.Transparent),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    sales.forEach { sale ->
                        sale.sales?.let { salesList ->
                            items(salesList) { singleSale ->
                                if (!isLoading || !isLoadingDelete) {
                                    SalesItemsDetails(sales = singleSale, viewModel) {
                                        viewModel.fromPage("creditInfo")
                                        viewModel.getStockSelected(singleSale)
                                        viewModel.getSingleSale(singleSale, creditItem!!)
                                        viewModel.onSaleSelected(creditItem)
                                        navigateTo(navController, Destination.EditSales)
                                    }
                                }
                            }
                        }
                    }

                    item {

                        Spacer(modifier = Modifier.padding(10.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
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


                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        if (!isLoading || !isLoadingDelete) {
                                            if (creditItem?.sales.isNullOrEmpty() || (creditItem?.sales?.size
                                                    ?: 0) < 15
                                            ) {
                                                viewModel.onCustomerSelected(customer!!)
                                                viewModel.onSaleSelected(creditItem!!)
                                                viewModel.fromPage("notHome")
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
                                    modifier = Modifier.width(120.dp)
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
                                        if (!isLoading || !isLoadingDelete) {
                                            viewModel.onSaleSelected(creditItem!!)
                                            navController.navigate(Destination.CreditReceipt.routes)
                                        }
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                                    modifier = Modifier.width(120.dp)
                                        .height(50.dp)
                                ) {
                                    Text(
                                        text = "Generate Receipt",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(10.dp))


                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    if (!isLoading || !isLoadingDelete) {
                                        if (payCreditHidden) {
                                            openDialogPayCredit = true
                                        } else {
                                            navController.navigate(Destination.PayCredit.routes)
                                            viewModel.onSaleSelected(creditItem!!)
                                            viewModel.onCustomerSelectedHome(creditItem.customerId.toString())
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                                modifier = Modifier.fillMaxWidth(0.7f)
                                    .height(50.dp)
                            ) {
                                Text(
                                    "Pay Credit"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(10.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    if (!isLoading || !isLoadingDelete) {
                                        if (deleteSalesHidden) {
                                            openDialog = true
                                        } else {
                                            openDialogDelete = true
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                                modifier = Modifier.fillMaxWidth(0.7f)
                                    .height(50.dp)
                            ) {
                                Text(
                                    "Delete"
                                )
                            }
                        }

                    }
                }

            }
        }

        if(isLoading || isLoadingDelete){
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