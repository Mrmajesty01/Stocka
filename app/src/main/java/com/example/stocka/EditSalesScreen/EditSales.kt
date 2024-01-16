package com.example.stocka.EditSalesScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditSalesScreen(navController: NavController, viewModel: AuthViewModel) {

    val focus = LocalFocusManager.current
    val context = LocalContext.current
    val customerSelected = viewModel.customerSelected.value
    val stockSelected = viewModel.stockSelected.value
    val stocks = viewModel.stocks.value
    val customers = viewModel.customerData.value
    val selected = viewModel.salesSelected.value

    var single = viewModel.singleSaleSelected.value
    var unmodified = viewModel.unmodifiedSingle.value


    var customerName by remember {
        mutableStateOf("")
    }

    var productName by remember {
        mutableStateOf("")
    }

    var unitPrice by remember {
        mutableStateOf("")
    }

    var totalCost by remember {
        mutableStateOf("")
    }

    var quantity by remember {
        mutableStateOf("")
    }

    var editCustomerClickable by remember {
        mutableStateOf(false)
    }

    var editStockClickable by remember {
        mutableStateOf(false)
    }




    Box(modifier = Modifier.fillMaxSize()) {

        quantity = unmodified?.quantity.toString()
        totalCost = unmodified?.totalPrice.toString()
        unitPrice = unmodified?.price.toString()

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                            viewModel.stockSelected.value = null
                        },
                    tint = ListOfColors.black
                )

                Text(

                    text = "Edit Sales",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center

                )
            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Spacer(modifier = Modifier.padding(15.dp))


            Column(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {


                Box(
                    modifier = Modifier.fillMaxWidth()

                ) {

                    OutlinedTextField(
                        value = viewModel.singleSaleSelected.value?.customerName.toString(),
                        onValueChange = {newValue ->
                            viewModel.singleSaleSelected.value = viewModel.singleSaleSelected.value?.copy(customerName = newValue)

                        },
                        label = {
                            Text(
                                text = "Customer Name"
                            )
                        },
                        modifier = Modifier.align(Alignment.Center),
                        enabled = false

                        )

                }

                Spacer(modifier = Modifier.padding(10.dp))


                Box(
                    modifier = Modifier.fillMaxWidth()
                )
                {

                    OutlinedTextField(
                        value = viewModel.singleSaleSelected.value?.productName.toString(),
                        onValueChange = {newValue->
                            viewModel.singleSaleSelected.value = viewModel.singleSaleSelected.value?.copy(productName = newValue)
                        },
                        label = {
                            Text(
                                text = "Product Name"
                            )
                        },
                        modifier = Modifier.align(Alignment.Center),
                        enabled = false
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = viewModel.singleSaleSelected.value?.price.toString(),
                    onValueChange =
                    {newValue->
                        viewModel.singleSaleSelected.value = viewModel.singleSaleSelected.value?.copy(price = newValue)
                        viewModel.singleSaleSelected.value =
                            viewModel.singleSaleSelected.value?.copy(totalPrice = updateTotalCost(viewModel.singleSaleSelected.value?.price!!.toFloat(), viewModel.singleSaleSelected.value?.quantity!!.toInt()))

                    },
                    label = {
                        Text(
                            text = "Unit Price"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
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
                            if (viewModel.singleSaleSelected.value?.quantity!!.toInt() > 0) {
                                var temp = viewModel.singleSaleSelected.value?.quantity!!.toInt()
                                temp--
                                viewModel.singleSaleSelected.value = viewModel.singleSaleSelected.value!!.copy(quantity = temp.toString())
                                viewModel.singleSaleSelected.value =
                                    viewModel.singleSaleSelected.value?.copy(totalPrice = updateTotalCost(viewModel.singleSaleSelected.value?.price!!.toFloat(), viewModel.singleSaleSelected.value?.quantity!!.toInt()))
                            }
                        }
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .width(100.dp)
                            .height(70.dp),
                        value = viewModel.singleSaleSelected.value?.quantity.toString(),
                        onValueChange = {newQty->
                            if (newQty.isNotEmpty()) {
                                val newValue = newQty.toInt()
                                viewModel.singleSaleSelected.value = viewModel.singleSaleSelected.value!!.copy(quantity =  if (newValue >= 0) newValue.toString() else "1")
//                                viewModel.singleSaleSelected.value = viewModel.singleSaleSelected.value?.copy(quantity = newQty)
                                viewModel.singleSaleSelected.value =
                                    viewModel.singleSaleSelected.value?.copy(totalPrice = updateTotalCost(viewModel.singleSaleSelected.value?.price!!.toFloat(), viewModel.singleSaleSelected.value?.quantity!!.toInt()))
                            } else {
                                val qty = "1"
                                viewModel.singleSaleSelected.value = viewModel.singleSaleSelected.value?.copy(quantity = qty)
                                viewModel.singleSaleSelected.value =
                                    viewModel.singleSaleSelected.value?.copy(totalPrice = updateTotalCost(viewModel.singleSaleSelected.value?.price!!.toFloat(), viewModel.singleSaleSelected.value?.quantity!!.toInt()))
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        label = {
                            Text(
                                text = " Quantity"
                            )
                        },
                        enabled = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addIcon",
                        modifier = Modifier.clickable {
                            var temp = viewModel.singleSaleSelected.value?.quantity!!.toInt()
                            temp++
                            viewModel.singleSaleSelected.value = viewModel.singleSaleSelected.value!!.copy(quantity = temp.toString())
                            viewModel.singleSaleSelected.value =
                                viewModel.singleSaleSelected.value?.copy(totalPrice = updateTotalCost(viewModel.singleSaleSelected.value?.price!!.toFloat(), viewModel.singleSaleSelected.value?.quantity!!.toInt()))
                        }
                    )

                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = viewModel.singleSaleSelected.value?.totalPrice.toString(),
                    onValueChange = {newValue->
                        viewModel.singleSaleSelected.value = viewModel.singleSaleSelected.value?.copy(totalPrice = newValue)
                    },
                    label = {
                        Text(
                            text = "Total Amount"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.padding(20.dp))


                Button(
                    onClick = {
                        focus.clearFocus(force = true)
                        if(single !=null){
                            val profit = (single.price?.toInt()!!
                                .minus(stockSelected?.stockPurchasePrice?.toInt()!!)).toString()
                            viewModel.updateSale(customerName = single.customerName.toString(),
                                customerId = selected?.customerId.toString(),
                                stockName = single.productName.toString(),
                                unitPrice = single.price.toString(), quantity = single.quantity.toString(),
                                totalPrice = single.totalPrice.toString(), profit = profit,
                                saleId = selected?.salesId.toString(),
                                singleSaleId = single.saleId.toString(),
                                stockId = stockSelected.stockId.toString(),
                                saleDiff = (single.totalPrice?.toDouble()!! - totalCost.toDouble()).toString(),
                                profitDiff = ((profit.toInt()*single.quantity!!.toInt()) - unmodified?.profit!!.toInt()).toString(),
                                sale = selected!!,
                                singleSale = single
                            ){
                                viewModel.unmodifiedSingle.value = null
                                viewModel.singleSaleSelected.value = null
                                viewModel.getSale(selected?.salesId.toString())
                                if(selected.type=="SR"){
                                     navigateTo(navController, Destination.SalesInfoHome)
                                }
                                else{
                                    viewModel.onCustomerSelectedHome(selected.customerId.toString())
                                    navigateTo(navController, Destination.CreditInfoHome)
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
                        text = "Update",
                        color = ListOfColors.black
                    )
                }


                Spacer(modifier = Modifier.padding(10.dp))


                Button(
                    onClick = {
                        focus.clearFocus(force = true)
                        if(single!=null) {
                            viewModel.deleteSingleSale(selected?.salesId.toString(), single.saleId.toString()){
                                viewModel.getSale(selected?.salesId.toString())
                                navigateTo(navController, Destination.SalesInfoHome)
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
                        text = "Delete",
                        color = ListOfColors.black
                    )
                }

            }
        }
    }
}
fun updateTotalCost(cost: Float, count: Int): String {
    return (cost * count).toString()
}







//@Preview(showBackground = true)
//@Composable
//fun EditSalesPrev(){
//    EditSalesScreen()
//}