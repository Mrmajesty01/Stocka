package com.example.stocka.MakeSalesScreen

import android.annotation.SuppressLint
import android.os.Build
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
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.data.Customer
import com.example.stocka.data.SingleSale
import com.example.stocka.data.Stock
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors
import java.util.UUID

private data class MakeSale(
    var sales: List<SingleSale> = emptyList()
) {
    fun isFull() = sales.size >= 15

    fun add(sale: SingleSale) {
        if (sales.size < 15) {
            sales = sales + sale
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MakeSalesScreen(navController:NavController,viewModel: AuthViewModel) {


    val focus = LocalFocusManager.current
    val context = LocalContext.current
    val isLoading = viewModel.inProgress.value
    val isLoadingStock = viewModel.onMultipleSoldProgress.value
    val userId = viewModel.userData.value
    val salesId = UUID.randomUUID()
    val customerSelected = viewModel.customerSelected.value
    var stockSelected = viewModel.stockSelected.value
    val stocks = viewModel.stocks.value
    val customers = viewModel.customerData.value




    var sales by rememberSaveable {
        mutableStateOf<List<MakeSale>>(emptyList())
    }


    var stockToUpdate by rememberSaveable {
        mutableStateOf<MutableList<Pair<Stock, Int>>>(mutableListOf())
    }


//    var stockToUpdate by rememberSaveable {
//        mutableStateOf<MutableList<Stock>>(mutableListOf())
//    }


    var empty by rememberSaveable {
        mutableStateOf(false)
    }

    var exist by rememberSaveable {
        mutableStateOf(false)
    }

    var customerName by rememberSaveable {
        mutableStateOf("")
    }



    var productName by rememberSaveable {
        mutableStateOf("")
    }

    var cost by rememberSaveable {
        mutableStateOf("")
    }

    var totalCost by rememberSaveable {
        mutableStateOf("")
    }

    var quantity by rememberSaveable {
        mutableStateOf("1")
    }

    var editCustomerClickable by rememberSaveable {
        mutableStateOf(true)
    }

    var isDropdownVisible by rememberSaveable { mutableStateOf(false) }

    var editStockClickable by rememberSaveable {
        mutableStateOf(true)
    }


    if(customerSelected != null && customers.any { it.customerName == customerSelected.customerName } && customers.any { it.customerId == customerSelected.customerId }){
            customerName = customerSelected.customerName.toString()
            editCustomerClickable = false

    }

    if(customerSelected != null){
        customerName = customerSelected.customerName.toString()

    }

    if(stockSelected != null && stocks.any {it.stockName == stockSelected!!.stockName}  && stocks.any {it.stockId == stockSelected!!.stockId}) {
            productName = stockSelected.stockName
            cost = stockSelected.stockSellingPrice
            totalCost = stockSelected.stockTotalPrice
            editStockClickable = false
            exist = true
    }

    if(stockSelected != null && !stocks.any {it.stockName == stockSelected!!.stockName}  && !stocks.any {it.stockId == stockSelected!!.stockId}) {
        productName = stockSelected.stockName
        cost = stockSelected.stockSellingPrice
        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockPurchasePrice = cost)
        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = cost)
        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockFixedSellingPrice = cost)
        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockQuantity = quantity)
        totalCost = stockSelected.stockTotalPrice
        exist = false
    }

    if(stockSelected!=null) {
        if (stockSelected!!.stockSellingPrice == null && stockSelected.stockFixedSellingPrice == null && stockSelected.stockPurchasePrice == null && stockSelected.stockTotalPrice == null) {
            stockSelected.stockSellingPrice = ""
            stockSelected.stockPurchasePrice = ""
            stockSelected.stockTotalPrice = ""
            stockSelected.stockFixedSellingPrice = ""
            customerName = customerSelected?.customerName.toString()
            productName = stockSelected.stockName.toString()
            cost = stockSelected.stockSellingPrice.toString()
            totalCost = stockSelected.stockTotalPrice.toString()
            empty = true
        }
    }


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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackIcon",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            if (!isLoading || !isLoadingStock) {
                                navController.popBackStack()
                            }
                        },
                    tint = ListOfColors.black
                )


                Text(
                    text = "Make Sales",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }


            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Spacer(modifier = Modifier.padding(15.dp))


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    OutlinedTextField(
                        value = if(customerSelected!=null) viewModel.customerSelected.value!!.customerName.toString() else "",
                        onValueChange = {
                          customerName = it
                          viewModel.customerSelected.value = Customer()
                          viewModel.customerSelected.value = viewModel.customerSelected.value!!.copy(customerName = it)
                        },
                        label = {
                            Text(
                                text = "Customer Name"
                            )
                        },
                        modifier = Modifier.align(Alignment.Center),
                        enabled = editCustomerClickable
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addIcon",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                if (!isLoading || !isLoadingStock) {
                                    if (customers.isEmpty()) {
                                        Toast
                                            .makeText(
                                                context,
                                                "you have no customers in your customer's list, add one to select",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    } else {
                                        navigateTo(navController, Destination.SearchCustomer)
                                    }
                                }
                            }
                    )

                    if(!editCustomerClickable) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "cancelIcon",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .clickable {
                                    viewModel.customerSelected.value = null
                                    customerName = ""
                                    editCustomerClickable = true
                                }
                        )
                    }
                }


                Spacer(modifier = Modifier.padding(10.dp))


                Box(
                    modifier = Modifier.fillMaxWidth()
                )
                {

                    OutlinedTextField(
                        value = if(stockSelected!=null) viewModel.stockSelected.value!!.stockName else "",
                        onValueChange = {
                            viewModel.stockSelected.value = Stock()
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockName = it)
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
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
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
                                    cost = ""
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
                    value = if(stockSelected!=null) viewModel.stockSelected.value!!.stockSellingPrice.toString() else "",
                    onValueChange =
                    {
                        if (it.isNullOrEmpty() || stockSelected?.stockSellingPrice == null) {
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = "")
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = "")
                            empty = true
                        }
                        else {
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = it)
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = updateTotalCost(viewModel.stockSelected.value!!.stockSellingPrice!!.toFloat(), quantity.toInt()))
                            viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockFixedSellingPrice = it)
                        }

                    },
                    label = {
                        Text(
                            text = "Unit Price"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = !isLoading || !isLoadingStock
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            if(!isLoading || !isLoadingStock  ||!empty) {
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
                            if (viewModel.stockSelected.value != null) {
                                if (it.isNotEmpty()) {
                                    val newValue = it.toInt()
                                    quantity = if (newValue >= 0) newValue.toString() else ""
                                    viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = updateTotalCost(viewModel.stockSelected.value!!.stockSellingPrice!!.toFloat(), quantity.toInt()))
                                }
                                else {
                                    quantity = ""
                                    viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = updateTotalCost(viewModel.stockSelected.value!!.stockSellingPrice!!.toFloat(), 0))
                                }
                            }
                            else{
                                Toast.makeText(context,"Select a stock to change quantity",Toast.LENGTH_LONG).show()
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        label = {
                            Text(
                                text = " Quantity"
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = !isLoading || !isLoadingStock ||!empty
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addIcon",
                        modifier = Modifier.clickable {
                            if (!isLoading || !isLoadingStock ||!empty) {
                                if (quantity.isNotEmpty() && quantity.toInt() >= 0) {
                                    if (viewModel.stockSelected.value != null && quantity.isNotEmpty()) {
                                        var temp = quantity.toInt()
                                        temp++
                                        quantity = temp.toString()
                                        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = updateTotalCost(viewModel.stockSelected.value!!.stockSellingPrice!!.toFloat(), quantity.toInt()))
                                    } else {
                                        Toast.makeText(context, "Select a stock to increase quantity", Toast.LENGTH_LONG).show()
                                    }
                                }
                                else{
                                    Toast.makeText(context,"Add quantity to make sale",Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    )

                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = if(stockSelected!=null) viewModel.stockSelected.value!!.stockTotalPrice.toString() else "" ,
                    onValueChange = {
                        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = it)
                    },
                    label = {
                        Text(
                            text = "Total Amount"
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
                        editStockClickable = true
                        if(!isLoading || !isLoadingStock ) {
                            if (stockToUpdate.any { it.first.stockName == productName }) {
                                val existingStock =
                                    stockToUpdate.firstOrNull { it.first.stockName == productName }

                                existingStock?.let { (existingProduct, existingQuantity) ->
                                    val newTotalQuantity = existingQuantity + quantity.toInt()

                                    // Check if the new total quantity exceeds a certain limit
                                    if (newTotalQuantity > stockSelected?.stockQuantity?.toInt()!!) {
                                        Toast.makeText(
                                            context,
                                            "The quantity selected in your add list with this quantity is greater than stock quantity available",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@Button
                                    }
                                }
                            }

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

                            if (customerName.isBlank()) {
                                customerName = "Customer"
                                viewModel.customerSelected.value = Customer()
                                viewModel.customerSelected.value = viewModel.customerSelected.value!!.copy(customerName = "Customer")
                                return@Button
                            }

                            if (stockSelected != null && stockSelected!!.stockQuantity?.toInt()!! < quantity.toInt()) {
                                Toast.makeText(
                                    context,
                                    "The quantity selected is greater than stock quantity available",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }
                            if (customerName.isEmpty() || productName.isEmpty() || cost.isEmpty() || totalCost.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Add a customer name and a product to make sales",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            } else {
                                if (stockSelected != null) {
                                    stockToUpdate.add(Pair(stockSelected!!, quantity.toInt()))
                                }
                                val profit = cost.toDouble()
                                    ?.minus(stockSelected!!.stockPurchasePrice?.toDouble()!!)


                                val sale =
                                    SingleSale(
                                        saleId = salesId.toString(),
                                        userId = userId?.userId,
                                        customerName = viewModel.customerSelected.value!!.customerName.toString(),
                                        productName = viewModel.stockSelected.value!!.stockName.toString(),
                                        quantity = quantity,
                                        price = viewModel.stockSelected.value!!.stockSellingPrice.toString(),
                                        totalPrice = viewModel.stockSelected.value!!.stockTotalPrice.toString(),
                                        profit = profit.toString(),
                                        exist = exist
                                    )
                                if (sales.isNotEmpty() && sales.last().isFull()) {
                                    Toast.makeText(
                                        context,
                                        "Limit exceeded for adding sales",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    stockToUpdate.removeAt(stockToUpdate.size - 1)
                                    return@Button

                                }
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockName = "")
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = "")
                                viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockTotalPrice = "")
                                quantity = "1"
                                if (sales.isNotEmpty()) {
                                    sales.last().add(sale)
                                } else {
                                    sales = listOf(MakeSale(listOf(sale)))
                                }

                            }
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
                        text = "Add Goods",
                        color = ListOfColors.black
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {

                        focus.clearFocus(force = true)

                        if(!isLoading || !isLoadingStock)  {

                            if (stockToUpdate.any { it.first.stockName == productName }) {
                                val existingStock =
                                    stockToUpdate.firstOrNull { it.first.stockName == productName }

                                existingStock?.let { (existingProduct, existingQuantity) ->
                                    val newTotalQuantity = existingQuantity + quantity.toInt()

                                    // Check if the new total quantity exceeds a certain limit
                                    if (newTotalQuantity > stockSelected?.stockQuantity?.toInt()!!) {
                                        Toast.makeText(
                                            context,
                                            "The quantity selected in your add list with this quantity is greater than stock quantity available",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@Button
                                    }
                                }
                            }

                            if (quantity.isEmpty() || quantity.toInt() == 0 ) {
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

                            if (customerName.isBlank()) {
                                customerName = "Customer"
                                viewModel.customerSelected.value = Customer()
                                viewModel.customerSelected.value = viewModel.customerSelected.value!!.copy(customerName = "Customer")
                                return@Button
                            }

                            if (stockSelected != null && stockSelected!!.stockQuantity?.toInt()!! < quantity.toInt()) {
                                Toast.makeText(
                                    context,
                                    "The quantity selected is greater than stock quantity available",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            if (customerName.isEmpty() || (productName.isEmpty() && stockToUpdate.isEmpty()) || (cost.isEmpty() && stockToUpdate.isEmpty()) || (totalCost.isEmpty() && stockToUpdate.isEmpty())) {
                                Toast.makeText(
                                    context,
                                    "Add a customer name and a product to make sales",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            if (sales.isNotEmpty() && !sales.last().isFull()) {

                                if (stockSelected!!.stockName!!.isNotEmpty() && stockSelected!!.stockSellingPrice!!.isNotEmpty() && stockSelected!!.stockTotalPrice!!.isNotEmpty()) {

                                    stockToUpdate.add(Pair(stockSelected!!, quantity.toInt()))
                                    var profit =
                                        stockSelected!!.stockFixedSellingPrice!!.toDouble().minus(stockSelected?.stockPurchasePrice?.toDouble()!!)
                                    val sale =
                                        SingleSale(
                                            saleId = salesId.toString(),
                                            userId = userId?.userId,
                                            customerName = viewModel.customerSelected.value!!.customerName.toString(),
                                            productName = viewModel.stockSelected.value!!.stockName.toString(),
                                            quantity = quantity,
                                            price = viewModel.stockSelected.value!!.stockFixedSellingPrice.toString(),
                                            totalPrice = viewModel.stockSelected.value!!.stockTotalPrice.toString(),
                                            profit = profit.toString(),
                                            exist = exist
                                        )
                                    sales.last().add(sale)
                                }

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    viewModel.onNewSales(
                                        customerName = viewModel.customerSelected.value!!.customerName.toString(),
                                        customerId = if (customerSelected?.customerId != null) customerSelected.customerId.toString()
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
                                        stockQuantityList = stockToUpdate,
                                        exist = exist
                                    ) {
                                        sales = emptyList()
                                        stockToUpdate.clear()
                                        viewModel.stockSelected.value = null
                                        viewModel.customerSelected.value = null
                                        navigateTo(navController, Destination.SalesInfo)
                                    }
                                }

                            }


                           else if (sales.isNotEmpty() && sales.last().isFull()) {


                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    viewModel.onNewSales(
                                        customerName = viewModel.customerSelected.value!!.customerName.toString(),
                                        customerId = if (customerSelected?.customerId != null) customerSelected.customerId.toString()
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
                                        stockQuantityList = stockToUpdate,
                                        exist = exist
                                    ) {
                                        sales = emptyList()
                                        stockToUpdate.clear()
                                        viewModel.stockSelected.value = null
                                        viewModel.customerSelected.value = null
                                        navigateTo(navController, Destination.SalesInfo)
                                    }
                                }
                            }

                           else{

                                if (stockSelected!!.stockName!!.isNotEmpty() && stockSelected!!.stockSellingPrice!!.isNotEmpty() && stockSelected!!.stockTotalPrice!!.isNotEmpty()) {

                                    stockToUpdate.add(Pair(stockSelected!!, quantity.toInt()))
                                    var profit =
                                        stockSelected!!.stockFixedSellingPrice!!.toDouble().minus(stockSelected?.stockPurchasePrice?.toDouble()!!)
                                    val sale =
                                        SingleSale(
                                            saleId = salesId.toString(),
                                            userId = userId?.userId,
                                            customerName = viewModel.customerSelected.value!!.customerName.toString(),
                                            productName = viewModel.stockSelected.value!!.stockName.toString(),
                                            quantity = quantity,
                                            price = viewModel.stockSelected.value!!.stockFixedSellingPrice.toString(),
                                            totalPrice = viewModel.stockSelected.value!!.stockTotalPrice.toString(),
                                            profit = profit.toString(),
                                            exist = exist
                                        )
                                    sales = listOf(MakeSale(listOf(sale)))
                                }


                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    viewModel.onNewSales(
                                        customerName = viewModel.customerSelected.value!!.customerName.toString(),
                                        customerId = if (customerSelected?.customerId != null) customerSelected.customerId.toString()
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
                                        stockQuantityList = stockToUpdate,
                                        exist = exist
                                    ) {
                                        sales = emptyList()
                                        stockToUpdate.clear()
                                        viewModel.stockSelected.value = null
                                        viewModel.customerSelected.value = null
                                        navigateTo(navController, Destination.SalesInfo)
                                    }
                                }

                            }

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


//
//@Preview (showBackground = true)
//@Composable
//fun prevMakeSales(){
//    MakeSalesScreen()
//}