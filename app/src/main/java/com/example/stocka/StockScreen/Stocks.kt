@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.stocka.StockScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
fun StockScreen(navController: NavController,viewModel: AuthViewModel) {

    val isLoading = viewModel.inProgress.value
    val searchStockLoading = viewModel.stockSearchProgress.value
    val searchedStock = viewModel.searchedStocks.value
    val stocks = viewModel.stocks.value
    val focus = LocalFocusManager.current
    val totalStockValue = viewModel.totalStockValue.value

    var searchValue by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            var text by remember {
                mutableStateOf("")
            }

            var active by remember {
                mutableStateOf(false)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            ) {
                Text(
                    text = "Stocks",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()

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
                        viewModel.stockSearchWhenTyping(searchValue)
                    },
                    label = {
                           Text(text = "Search for a stock")
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
                            viewModel.stockSearch(searchValue)
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
                            viewModel.retrieveStocks()
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
                            viewModel.stockSearch(searchValue)
                            focus.clearFocus()
                        }) {
                            Icon(imageVector = Icons.Filled.Search , contentDescription = null )
                        }
                    }

                )

                Spacer(modifier = Modifier.padding(5.dp))
                TotalStockValue(totalStockValue)
                Spacer(modifier = Modifier.padding(5.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (searchValue.isEmpty()) {
                        if(stocks.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "You have not added any stock yet"
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
                                items(stocks) { stock ->
                                    StockItem(stock = stock) { go ->
                                        navigateTo(navController, Destination.StockInfo)
                                        viewModel.getStock(go)
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
                            items(searchedStock) { stock ->
                                StockItem(stock = stock){go->
                                    navigateTo(navController, Destination.StockInfo)
                                    viewModel.getStock(go)
                                }
                            }
                        }
                    }
                }

            }
            BottomNavMenu(selectedItem = BottomNavItem.Stocks, navController = navController)
        }
        if(searchStockLoading){
            CommonProgressSpinner()
        }
    }
}





//@Preview (showBackground = true)
//@Composable
//fun stockPrev(){
//    StockScreen()
//}