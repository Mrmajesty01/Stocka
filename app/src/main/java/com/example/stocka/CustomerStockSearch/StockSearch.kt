package com.example.stocka.CustomerStockSearch

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
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
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.CommonProgressSpinner
import com.example.stocka.ui.theme.ListOfColors

@Composable
fun StockSearch(navController:NavController, viewModel:AuthViewModel){

    val isLoading = viewModel.inProgress.value
    val searchStockLoading = viewModel.stockSearchProgress.value
    val searchedStock = viewModel.searchedStocks.value
    val stocks = viewModel.stocks.value
    val focus = LocalFocusManager.current

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
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "ArrowBack",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    tint = ListOfColors.black
                )

                Text(
                    text = "Select a stock",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()

                )
            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Spacer(modifier = Modifier.padding(10.dp))

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
                        viewModel.customerSearch(searchValue)
                    },
                    label ={
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

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (searchValue.isEmpty()) {
                        if (stocks.isEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "You have not added any stock yet"
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(7.dp)
                            ) {
                                items(stocks) { stock ->
                                    StockSearchItem(stock = stock) { stock ->
                                        viewModel.onStockSelected(stock)
                                        navController.popBackStack()
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
                                StockSearchItem(stock = stock){stock->
                                    viewModel.onStockSelected(stock)
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }

            }

        }
        if(searchStockLoading){
            CommonProgressSpinner()
        }
    }
}