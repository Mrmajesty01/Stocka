@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.stocka.CustomerScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
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
import com.example.stocka.Navigation.BottomNavItem
import com.example.stocka.Navigation.BottomNavMenu
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.CommonProgressSpinner
import com.example.stocka.main.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CustomersScreen(navController:NavController,viewModel: AuthViewModel){

    val customers = viewModel.customerData.value
    val isLoading = viewModel.inProgress.value
    val searchCustomerLoading = viewModel.customerSearchProgress.value
    val searchedCustomer = viewModel.searchedCustomer.value
    val focus = LocalFocusManager.current
    val totalAmountOwingCustomers = viewModel.totalAmountOwed.value

    var searchValue by remember {
        mutableStateOf("")
    }

    var text by remember {
        mutableStateOf("")
    }
    var active by remember{
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ){

                Text(
                    text = "Customers",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 20.dp,
                        end = 20.dp
                    )
            ) {

                TextField(
                    value = searchValue,
                    onValueChange = {
                        searchValue = it
                        viewModel.customerSearchWhenTyping(searchValue)
                    },
                    label ={
                        Text(text = "Search for a customer")
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .border(1.dp, Color.LightGray, CircleShape),
                    shape = CircleShape,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.customerSearch(searchValue)
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
                            viewModel.retrieveCustomer()
                            focus.clearFocus()
                        }) {
                            if (searchValue.isEmpty()) {

                            } else {
                                Icon(imageVector = Icons.Filled.Cancel, contentDescription = null)
                            }
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.customerSearch(searchValue)
                            focus.clearFocus()
                        }) {
                            Icon(imageVector = Icons.Filled.Search , contentDescription = null )
                        }
                    }


                )

                Spacer(modifier = Modifier.padding(5.dp))
                TotalAmountOwing(totalAmountOwingCustomers)
                Spacer(modifier = Modifier.padding(5.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if(searchValue.isEmpty()) {
                        if(customers.isEmpty()){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = "You have not added any customer yet"
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
                                items(customers) {
                                    CustomerItem(customer = it) { go ->
                                        navigateTo(navController, Destination.CustomerInfo)
                                        viewModel.getCustomer(go)
                                    }
                                }
                            }
                        }
                    }
                    else {
                        LazyColumn(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(7.dp)
                        ) {
                            items(searchedCustomer) {
                                CustomerItem(customer = it){go->
                                    navigateTo(navController, Destination.CustomerInfo)
                                    viewModel.getCustomer(go)
                                }

                            }
                        }
                    }

                }


            }

            BottomNavMenu(selectedItem = BottomNavItem.Customers, navController = navController)
        }

        if(searchCustomerLoading){
            CommonProgressSpinner()
        }
    }
}





//@Preview (showBackground = true)
//@Composable
//fun CustomerPrev(){
//    CustomersScreen()
//}
