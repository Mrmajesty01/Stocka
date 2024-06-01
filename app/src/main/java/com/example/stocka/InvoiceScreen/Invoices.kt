package com.example.stocka.InvoiceScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.HomeScreen.CustomerSalesItem
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.CommonProgressSpinner
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InvoiceScreen(navController:NavController,viewModel: AuthViewModel){


    val focus = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    val searchInvoiceLoading = viewModel.invoiceSearchProgress.value
    val searchedInvoice = viewModel.invoiceData.value
    val invoices = viewModel.invoiceData.value
    var searchValue by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier.wrapContentHeight(),
            contentAlignment = Alignment.TopCenter // Aligning content to the top end
        ) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(250.dp)
            ) {
                Column {
                    Text(
                        text = "Search by:",
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                    DropdownMenuItem(onClick = {
                        selectedItem = "Invoice Number"
                    }) {
                        Text(
                            "Invoice Number",
                            fontWeight = if (selectedItem == "Invoice Number") FontWeight.Bold else null
                        )
                    }

                    DropdownMenuItem(onClick = {
                        selectedItem = "Customer Name"
                    }) {
                        Text(
                            "Customer Name",
                            fontWeight = if (selectedItem == "Customer Name") FontWeight.Bold else null
                        )
                    }

                    DropdownMenuItem(onClick = {
                        selectedItem = "Total Amount"
                    }) {
                        Text(
                            "Total Amount",
                            fontWeight = if (selectedItem == "Total Amount") FontWeight.Bold else null
                        )
                    }


                    androidx.compose.material.Divider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                expanded = false
                                selectedItem = ""
                                viewModel.retrieveInvoices()
                            },
                            modifier = Modifier.wrapContentHeight()
                                .wrapContentWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = ListOfColors.lightRed)
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                expanded = false
                            },
                            modifier = Modifier.wrapContentHeight()
                                .wrapContentWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = ListOfColors.orange)
                        ) {
                            Text("Apply")
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ){

                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackIcon",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            if(!searchInvoiceLoading) {
                                navController.popBackStack()
                            }
                        },
                    tint = ListOfColors.black
                )

                Text(
                    text = "Invoices",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()

                )
            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 20.dp,
                        end = 20.dp
                    )
            ) {


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    TextField(
                        value = searchValue,
                        onValueChange = {
                            searchValue = it
                            if (selectedItem.isNotBlank()) {

                                when (selectedItem) {
                                    "Invoice Number" -> viewModel.invoiceSearchByInvoiceNumberWhenTyping(searchValue)
                                    "Customer Name" -> viewModel.invoiceSearchByCustomerNameWhenTyping(searchValue)
                                    "Total Amount" -> viewModel.invoiceSearchByTotalAmountWhenTyping(searchValue)
                                }
                            }
                        },
                        label = {
                            Text(text = "Search for invoice")
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .border(1.dp, Color.LightGray, CircleShape),
                        shape = CircleShape,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (selectedItem.isNotBlank()) {

                                    when (selectedItem) {
                                        "Invoice Number" -> viewModel.invoiceSearchByInvoiceNumber(searchValue)
                                        "Customer Name" -> viewModel.invoiceSearchByCustomerName(searchValue)
                                        "Total Amount" -> viewModel.invoiceSearchByTotalAmount(searchValue)
                                    }
                                }
                                focus.clearFocus()
                            }
                        ),
                        maxLines = 1,
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            IconButton(onClick = {
                                searchValue = ""
                                viewModel.retrieveInvoices()
                                focus.clearFocus()
                            }) {
                                if (searchValue.isEmpty()) {

                                } else {
                                    Icon(
                                        imageVector = Icons.Filled.Cancel,
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                if (selectedItem.isNotBlank()) {

                                    when (selectedItem) {
                                        "Invoice Number" -> viewModel.invoiceSearchByInvoiceNumber(searchValue)
                                        "Customer Name" -> viewModel.invoiceSearchByCustomerName(searchValue)
                                        "Total Amount" -> viewModel.invoiceSearchByTotalAmount(searchValue)
                                    }
                                }
                                focus.clearFocus()
                            }) {
                                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                            }
                        }

                    )

                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(imageVector = Icons.Default.FilterList, contentDescription = "filterIcon")
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (searchValue.isEmpty()) {
                        if(invoices.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "You have no invoices yet"
                                )
                            }
                        }
                        else {
                            LazyColumn(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(7.dp)
                            ) {
                                items(invoices) {
                                    CustomerSalesItem(sales = it){
                                        if(it.type.equals("SR")){
                                            viewModel.getSale(it.salesId.toString())
                                            navigateTo(navController,Destination.SalesInfoHome)
                                            viewModel.onCustomerSelectedHome(it.customerId.toString())
                                        }
                                        else {
                                            viewModel.getSale(it.salesId.toString())
                                            navigateTo(navController,Destination.CreditInfoHome)
                                            viewModel.onCustomerSelectedHome(it.customerId.toString())
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        LazyColumn(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(7.dp)
                        ) {
                            items(invoices) {
                                CustomerSalesItem(sales = it){
                                    if(it.type.equals("SR")){
                                        viewModel.getSale(it.salesId.toString())
                                        navigateTo(navController,Destination.SalesInfoHome)
                                        viewModel.onCustomerSelectedHome(it.customerId.toString())
                                    }
                                    else {
                                        viewModel.getSale(it.salesId.toString())
                                        navigateTo(navController,Destination.CreditInfoHome)
                                        viewModel.onCustomerSelectedHome(it.customerId.toString())
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        if (searchInvoiceLoading) {
            CommonProgressSpinner()
        }
    }
}


//@Preview (showBackground = true)
//@Composable
//fun InvoicePrev(){
//    InvoiceScreen()
//}