package com.example.stocka.SalesInfoScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
fun SalesInfoScreen(navController: NavController,viewModel:AuthViewModel) {

    val isLoading = viewModel.inProgress.value
    val isLoadingDelete = viewModel.deleteSaleProgress.value
    val salesItem = viewModel.salesDetail.value
    val sales = viewModel.salesData.value
    val context = LocalContext.current

    var saleToEdit = viewModel.salesSelected.value

    val formattedDate = salesItem?.salesDate?.let {
        SimpleDateFormat("dd MMM yyyy").format(Date(it))
    } ?: ""
    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var openDialogDelete by rememberSaveable {
        mutableStateOf(false)
    }
    var openDialogEdit by rememberSaveable {
        mutableStateOf(false)
    }

    if(salesItem?.sales.isNullOrEmpty()){
        navigateTo(navController,Destination.BottomSheet)
    }

    val features = viewModel.featuresToPin.value

    var editSalesHidden by remember {
        mutableStateOf(true)
    }

    var deleteSalesHidden by remember {
        mutableStateOf(true)
    }

    var pin by remember { mutableStateOf(TextFieldValue()) }
    val userData = viewModel.userData.value

    LaunchedEffect(features){
        editSalesHidden = viewModel.featuresToPin.value.EditSales
        deleteSalesHidden = viewModel.featuresToPin.value.DeleteSales
    }

    val totalAmount = salesItem?.totalPrice?.toDoubleOrNull() ?: 0.0
    val formattedTotalAmount = formatNumberWithDelimiter(totalAmount!!)

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
                            salesItem!!.customerId.toString(),
                            salesItem!!.salesId.toString()
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
                    viewModel.deleteEntireDocument(
                        salesItem!!.customerId.toString(),
                        salesItem!!.salesId.toString()
                    ) {
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

    if(openDialogEdit){
        AlertDialog(
            onDismissRequest = { openDialogEdit = false },

            title = {
                Text(text = "Edit Sale")
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
                        label = { Text("Enter PIN To Edit Sale") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if(pin.text.toInt() == userData?.pin?.toInt()) {
                        openDialogEdit = false
                        editSalesHidden = false

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
                    openDialogEdit = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }



    Box(
        modifier = Modifier.fillMaxSize()

    ){

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
                            if(!isLoading || !isLoadingDelete) {
                                navigateTo(navController, Destination.BottomSheet)
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
                        text = salesItem?.customerName.toString(),
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = "Invoice Number",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )

                    Text(
                        text = salesItem?.salesNo.toString(),
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
                    modifier = Modifier.fillMaxHeight()
                        .background(if (isLoading || isLoadingDelete) ListOfColors.lightGrey else Color.Transparent),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {

                    sales.forEach { sale ->
                        if (!isLoading || !isLoadingDelete) {
                            sale.sales?.let { salesList ->
                                items(salesList) { singleSale ->
                                    SalesItemsDetails(sales = singleSale, viewModel) {
                                        viewModel.onSaleSelected(sale)
                                        viewModel.fromPage("saleInfo")
                                        viewModel.getStockSelected(singleSale)
                                        viewModel.getSingleSale(singleSale, sale)
                                        navController.navigate(Destination.EditSales.routes)
                                    }
                                }
                            }
                        }
                    }

                    item{

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
                                text = formattedTotalAmount,
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
                                            if (salesItem?.sales.isNullOrEmpty() || (salesItem?.sales?.size
                                                    ?: 0) < 15
                                            ) {
                                                viewModel.onSaleSelected(salesItem!!)
                                                viewModel.fromPage("notHome")
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
                                        if (!isLoading || !isLoadingDelete) {
                                            viewModel.onSaleSelected(salesItem!!)
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
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .height(50.dp),
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
        }
        if(isLoading || isLoadingDelete){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
                    .align(Alignment.Center)
            )
        }
    }
}


@Preview (showBackground = true)
@Composable
fun SalesInfoPrev(){
//    SalesInfoScreen()
}