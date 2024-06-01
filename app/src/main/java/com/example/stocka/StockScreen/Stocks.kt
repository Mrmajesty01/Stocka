@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.stocka.StockScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
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
import com.example.stocka.ui.theme.ListOfColors


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StockScreen(navController: NavController,viewModel: AuthViewModel) {

    val isLoading = viewModel.inProgress.value
    val searchStockLoading = viewModel.stockSearchProgress.value
    val searchedStock = viewModel.searchedStocks.value
    val stocks = viewModel.stocks.value
    val focus = LocalFocusManager.current
    val totalStockValue = viewModel.totalStockValue.value
    val lazyListState = rememberLazyListState()


    var searching by rememberSaveable {
        mutableStateOf(false)
    }

    var searchValue by remember {
        mutableStateOf("")
    }
    var isTextFieldActive by rememberSaveable {
        mutableStateOf(true)
    }

    var expanded by remember { mutableStateOf(false) }
    var filterAppliedMostSold by remember { mutableStateOf(false) }
    var filterAppliedLeastSold by remember { mutableStateOf(false) }
    var filterAppliedHighestQty by remember { mutableStateOf(false) }
    var filterAppliedLowestQty by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }



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
                        text = "Filter by:",
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                    DropdownMenuItem(onClick = {
                        selectedItem = "Highest Quantity"
                    }) {
                        Text(
                            "Highest Quantity",
                            fontWeight = if (selectedItem == "Highest Quantity") FontWeight.Bold else null
                        )
                    }

                    DropdownMenuItem(onClick = {
                        selectedItem = "Lowest Quantity"
                    }) {
                        Text(
                            "Lowest Quantity",
                            fontWeight = if (selectedItem == "Lowest Quantity") FontWeight.Bold else null
                        )
                    }

                    DropdownMenuItem(onClick = {
                        selectedItem = "Most Sold"
                    }) {
                        Text(
                            "Most Sold",
                            fontWeight = if (selectedItem == "Most Sold") FontWeight.Bold else null
                        )
                    }

                    DropdownMenuItem(onClick = {
                        selectedItem = "Least Sold"
                    }) {
                        Text(
                            "Least Sold",
                            fontWeight = if (selectedItem == "Least Sold") FontWeight.Bold else null
                        )
                    }

                    Divider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                expanded = false
                                selectedItem = ""
                                viewModel.retrieveStocks()
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
                                if (selectedItem.isNotBlank()) {

                                    when (selectedItem) {
                                        "Most Sold" -> viewModel.filterStocksByMostSold()
                                        "Least Sold" -> viewModel.filterStocksByLeastSold()
                                        "Highest Quantity" -> viewModel.filterStocksByHighestQuantity()
                                        "Lowest Quantity" -> viewModel.filterStocksByLowestQuantity()
                                    }
                                }
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


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
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
                        .border(1.dp, Color.LightGray, CircleShape)
                        .onFocusChanged { focusState: FocusState ->
                            searching = focusState.isFocused
                        },
                    shape = CircleShape,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.stockSearch(searchValue)
                            focus.clearFocus()
                            searching = false
                        },
                        onDone = {
                            focus.clearFocus()
                            isTextFieldActive = false
                        },

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
                            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                        }
                    },
                )


                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(imageVector = Icons.Default.FilterList, contentDescription = "filterIcon")
                }
            }



            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 20.dp,
                        end = 20.dp
                    )
            ) {


                Spacer(modifier = Modifier.padding(5.dp))
                TotalStockValue(totalStockValue, viewModel)
                Spacer(modifier = Modifier.padding(5.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (searchValue.isEmpty()) {
                        if (stocks.isEmpty()) {
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
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(7.dp),
                                state = lazyListState
                            ) {
                                items(stocks) { stock ->
                                    StockItem(stock = stock) { go ->
                                        navigateTo(navController, Destination.StockInfo)
                                        viewModel.getStock(go)
                                    }
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(7.dp),
                            state = lazyListState
                        ) {
                            items(searchedStock) { stock ->
                                StockItem(stock = stock) { go ->
                                    navigateTo(navController, Destination.StockInfo)
                                    viewModel.getStock(go)
                                }
                            }
                        }
                    }
                }

            }
            if (!searching || !isTextFieldActive) {
                BottomNavMenu(selectedItem = BottomNavItem.Stocks, navController = navController)
            }
        }
        if (searchStockLoading) {
            CommonProgressSpinner()
        }
    }
}





//@Preview (showBackground = true)
//@Composable
//fun stockPrev(){
//    StockScreen()
//}