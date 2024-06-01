package com.example.stocka.AddCredit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.HomeScreen.formatNumberWithDelimiter
import com.example.stocka.MakeCreditScreen.MakeSale
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.data.SingleSale
import com.example.stocka.data.Stock
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors
import java.util.UUID


internal data class MakeSale(
    var sales: List<SingleSale> = emptyList()
) {
    fun isFull() = sales.size >= 5

    fun add(sale: SingleSale) {
        if (sales.size < 5) {
            sales = sales + sale
        }
    }
}
@Composable
fun AddCreditScreen(navController: NavController, viewModel: AuthViewModel){

    val focus = LocalFocusManager.current
    val context = LocalContext.current
    val userId = viewModel.userData.value
    val salesId = UUID.randomUUID()
    var isLoadingStock = viewModel.onMultipleSoldProgress.value
    var isLoading = viewModel.inProgress.value
    val customer = viewModel.customerSelected.value
    val stockSelected = viewModel.stockSelected.value
    val stocks = viewModel.stocks.value
    val customers = viewModel.customerData.value
    val selected = viewModel.salesSelected.value
    val fromPage = viewModel.fromPageValue.value

    var sales by rememberSaveable {
        mutableStateOf<List<MakeSale>>(emptyList())
    }


    var stockToUpdate by rememberSaveable {
        mutableStateOf<MutableList<Pair<Stock, Int>>>(mutableListOf())
    }

    var isDropdownVisible by rememberSaveable { mutableStateOf(false) }

    var editStockClickable by rememberSaveable {
        mutableStateOf(true)
    }

    var exist by rememberSaveable {
        mutableStateOf(false)
    }

    var productName by rememberSaveable {
        mutableStateOf("")
    }

    var productCost by rememberSaveable{
        mutableStateOf("")
    }

    var totalCost by rememberSaveable{
        mutableStateOf("")
    }

    var quantity by rememberSaveable {
        mutableStateOf("1")
    }


    if (stockSelected != null && stocks.any {it.stockName == stockSelected.stockName}  && stocks.any {it.stockId == stockSelected.stockId}) {
        productName = stockSelected.stockName
        productCost = stockSelected.stockSellingPrice
        totalCost = stockSelected.stockTotalPrice
        editStockClickable = false
        exist = true
    }

    if(stockSelected != null && !stocks.any {it.stockName == stockSelected!!.stockName}  && !stocks.any {it.stockId == stockSelected!!.stockId}) {
        productName = stockSelected.stockName
        productCost = stockSelected.stockSellingPrice
        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockPurchasePrice = productCost)
        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = productCost)
        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockFixedSellingPrice = productCost)
        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockQuantity = quantity)
        totalCost = stockSelected.stockTotalPrice
        exist = false
    }

    var customerBalance = customer?.customerBalance!!.toDoubleOrNull() ?: 0.0
    var formattedCustomerBalance = formatNumberWithDelimiter(customerBalance!!)

    Box(modifier = Modifier.fillMaxSize()) {

        if (isLoading || isLoadingStock) {
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
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackIcon",
                    modifier = Modifier.padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            if(!isLoading || !isLoadingStock) {
                                viewModel.stockSelected.value = null
                                viewModel.customerSelected.value = null
                                navController.popBackStack()
                            }
                        },
                    tint = ListOfColors.black
                )



                Text(
                    text = "Add Credit",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(20.dp)
            ) {


                Box(
                    modifier = Modifier
                        .height(45.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Customer name",
                        modifier = Modifier.align(Alignment.TopStart),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Amount Owed",
                        modifier = Modifier.align(Alignment.TopEnd),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = customer?.customerName.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                    Text(
                        text = formattedCustomerBalance,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))


                Box(
                    modifier = Modifier.fillMaxWidth()
                )
                {

                    OutlinedTextField(
                        value = productName,
                        onValueChange = {
                            viewModel.stockSelected.value = Stock()
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockName = it)
                            productName = it
                            isDropdownVisible = it.isNotEmpty()
                        },
                        label = {
                            Text(
                                text = "Product Name"
                            )
                        },
                        modifier = Modifier.align(Alignment.Center),
                        enabled = editStockClickable
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addIcon",
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .clickable {
                                if (!isLoading || !isLoadingStock) {
                                    if (stocks.isEmpty()) {
                                        Toast
                                            .makeText(
                                                context,
                                                "you have no stock in your stock's list, add one to select",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    } else {
                                        navigateTo(navController, Destination.SearchStock)
                                    }
                                }
                            }
                    )

                    if(!editStockClickable) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "cancelIcon",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .clickable {
                                    viewModel.stockSelected.value = null
                                    productName = ""
                                    productCost = ""
                                    totalCost = ""
                                    editStockClickable = true
                                }
                        )
                    }
                }

                if (isDropdownVisible && stocks.isNotEmpty() && viewModel.stockSelected.value?.stockName?.isNotEmpty() == true) {

                    LazyColumn(
                        modifier = Modifier
                            .background(Color.White)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            items = stocks.filter {
                                it.stockName.contains(viewModel.stockSelected.value!!.stockName, ignoreCase = true)
                            }.take(3)
                        ) { stock ->
                            DropdownMenuItem(onClick = {
                                // Handle item selection
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockName = stock.stockName)
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockId = stock.stockId)
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockPurchasePrice = stock.stockPurchasePrice)
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = stock.stockSellingPrice)
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockFixedSellingPrice = stock.stockFixedSellingPrice)
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = stock.stockTotalPrice)
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockQuantity = stock.stockQuantity)
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockQuantitySold = stock.stockQuantitySold)
                                isDropdownVisible = false
                            }) {
                                Text(text = stock.stockName)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value =  if(stockSelected!=null) viewModel.stockSelected.value!!.stockSellingPrice.toString() else "",
                    onValueChange = {
                        if (it.isNullOrEmpty() || stockSelected?.stockSellingPrice == null) {
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = "")
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = "")
                        }
                        else{
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = it)
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = updateTotalCost(viewModel.stockSelected.value!!.stockSellingPrice!!.toFloat(), quantity.toInt()))
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockFixedSellingPrice = it)
                        }
                    },
                    label = {
                        Text(
                            text = "Item Cost"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = !isLoading || !isLoadingStock
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(70.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                    )

                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "removeIcon",
                        modifier = Modifier.clickable {
                            if (!isLoading || !isLoadingStock) {
                                if (quantity.isNotEmpty() && quantity.toInt() > 0) {
                                    if (viewModel.stockSelected.value != null && quantity.isNotEmpty()) {
                                        var temp = quantity.toInt()
                                        temp--
                                        quantity = temp.toString()
                                        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = updateTotalCost(viewModel.stockSelected.value!!.stockSellingPrice!!.toFloat(), quantity.toInt()))
                                    }
                                    else{
                                        Toast.makeText(context,"Select a stock to decrease quantity",Toast.LENGTH_LONG).show()
                                    }
                                }
                                else{
                                    Toast.makeText(context,"Add quantity to make sale",Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .width(100.dp)
                            .height(70.dp),
                        value = quantity,
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                val newValue = it.toInt()
                                quantity = if (newValue >= 0) newValue.toString() else ""
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = (updateTotalCost(viewModel.stockSelected.value!!.stockSellingPrice!!.toFloat(), quantity.toInt())))
                            } else {
                                quantity = ""
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = (updateTotalCost(viewModel.stockSelected.value!!.stockSellingPrice!!.toFloat(), 0)))

                            }
                        },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        label = {
                            Text(
                                text = " Quantity"
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = !isLoading || !isLoadingStock
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addIcon",
                        modifier = Modifier.clickable {
                            if(!isLoading || !isLoadingStock) {
                                if (quantity.isNotEmpty() && quantity.toInt() >= 0) {
                                    if (viewModel.stockSelected.value != null && quantity.isNotEmpty()) {
                                        var temp = quantity.toInt()
                                        temp++
                                        quantity = temp.toString()
                                        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = updateTotalCost(viewModel.stockSelected.value!!.stockSellingPrice!!.toFloat(), quantity.toInt()))
                                    }
                                    else {
                                        Toast.makeText(context, "Select a stock to increase quantity", Toast.LENGTH_LONG).show()
                                    }
                                }
                                else{
                                    Toast.makeText(context, "Add quantity to make sale", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    )

                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = if(stockSelected!=null) viewModel.stockSelected.value!!.stockTotalPrice.toString() else "",
                    onValueChange = {
                        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = it)
                    },
                    label = {
                        Text(
                            text = "Total Cost"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = !isLoading || !isLoadingStock
                )



                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    onClick = {
                        focus.clearFocus(force = true)

                        if(!isLoading || !isLoadingStock) {


                            if (quantity.isEmpty() || quantity.toInt() == 0) {
                                // Show a toast message indicating that the count is not an integer
                                Toast.makeText(
                                    context,
                                    "stock quantity can't be zero or null",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            if (!quantity.isInt()) {
                                // Show a toast message indicating that the count is not an integer
                                Toast.makeText(
                                    context,
                                    "invalid value for stock quantity",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            if (stockSelected != null && stockSelected.stockQuantity?.toInt()!! < quantity.toInt()) {
                                Toast.makeText(
                                    context,
                                    "The quantity selected is greater than stock quantity available",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            if ((productName.isEmpty()) || (productCost.isEmpty()) || (totalCost.isEmpty())) {
                                Toast.makeText(
                                    context,
                                    "Add a product to add to sales",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }
                            else{
                                if (stockSelected != null) {
                                    stockToUpdate.add(Pair(stockSelected, quantity.toInt()))
                                }


                                val profit = productCost.toDouble()
                                    .minus(stockSelected?.stockPurchasePrice?.toDouble()!!)


                                val sale =
                                    SingleSale(
                                        saleId = salesId.toString(),
                                        userId = userId?.userId,
                                        customerName = customer?.customerName.toString(),
                                        productName = productName,
                                        quantity = quantity,
                                        price = productCost,
                                        totalPrice = totalCost,
                                        profit = profit.toString(),
                                        exist = exist
                                    )
                                if (sales.isNotEmpty()) {
                                    sales.last().add(sale)
                                } else {
                                    sales = listOf(MakeSale(listOf(sale)))
                                }

                                viewModel.onAddCredit(
                                    customerName = customer?.customerName.toString(),
                                    customerId = if (customer?.customerId != null) customer.customerId.toString()
                                    else UUID.randomUUID().toString(),
                                    sales = sales.flatMap { it.sales },
                                    totalPrice = sales.flatMap { it.sales }
                                        .sumByDouble { it.totalPrice?.toDoubleOrNull() ?: 0.0 }
                                        .toString(),
                                    totalProfit = sales.flatMap { it.sales }
                                        .sumByDouble {
                                            (it.profit?.toDoubleOrNull()
                                                ?: 0.0) * (it.quantity?.toDoubleOrNull() ?: 0.0)
                                        }
                                        .toString(),
                                    totalQuantity = sales.flatMap { it.sales }
                                        .sumByDouble { it.quantity?.toDoubleOrNull() ?: 0.0 }
                                        .toString(),
                                    sale = selected!!,
                                    stockQuantityList = stockToUpdate,
                                    exist = exist
                                ) {
                                    viewModel.stockSelected.value = null
                                    viewModel.customerSelected.value = null
                                    sales = emptyList()
                                    stockToUpdate.clear()

                                    if (fromPage == "home") {
                                        viewModel.getSale(selected.salesId.toString())
                                        navigateTo(navController, Destination.CreditInfoHome)
                                        viewModel.onCustomerSelectedHome(selected.customerId.toString())
                                    } else {
                                        viewModel.getSale(selected.salesId.toString())
                                        navigateTo(navController, Destination.CreditInfo)
                                        viewModel.onCustomerSelectedHome(selected.customerId.toString())
                                    }
                                }
                            }
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(ListOfColors.orange)
                ) {
                    Text(
                        text = "Done",
                        color = ListOfColors.black
                    )
                }
            }
        }
        if (isLoading || isLoadingStock) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
            )
        }

    }
}

private fun String.isInt(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}
private fun updateTotalCost(cost: Float, count: Int): String {
    return (cost * count).toString()
}