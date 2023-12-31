package com.example.stocka.EditSalesScreen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.MakeSalesScreen.updateTotalCost
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors

@Composable
fun EditTrial(viewModel: AuthViewModel) {


    val focus = LocalFocusManager.current
    val context = LocalContext.current
    val single = viewModel.singleSaleSelected.value
//    val customerSelected = viewModel.customerSelected.value
//    val stockSelected = viewModel.stockSelected.value
//    val stocks = viewModel.stocks.value
//    val customers = viewModel.customerData.value
//    val selected = viewModel.salesSelected.value


        var customerNameEdit by remember {
            mutableStateOf("")
        }

        var productNameEdit by remember {
            mutableStateOf( "")
        }

        var unitPriceEdit by remember {
            mutableStateOf( "")
        }

        var totalCostEdit by remember {
            mutableStateOf( "")
        }

        var quantityEdit by remember {
            mutableStateOf( "")
        }

        if (single != null) {
            customerNameEdit = single.customerName.toString()
        }

        if (single != null) {
            productNameEdit = single.productName.toString()
            unitPriceEdit = single.price.toString()
            quantityEdit = single.quantity.toString()
            totalCostEdit = updateTotalCost(unitPriceEdit.toFloat(),quantityEdit.toInt())
        }


//    singleSale.let {

//        customerName = singleSale?.customerName.toString()
//        productName = singleSale?.productName.toString()
//        unitPrice = singleSale?.price.toString()
//        quantity = singleSale?.quantity.toString()
//        totalCost = singleSale?.totalPrice.toString()


//        if (singleSale?.customerName != null) {
//            customerName = singleSale.customerName.toString()
//            editCustomerClickable = true
//        }
//
//        if (singleSale?.productName != null) {
//            productName = singleSale.productName.toString()
//            unitPrice = singleSale.price.toString()
//            quantity = singleSale.quantity.toString()
//            totalCost = com.example.stocka.MakeSalesScreen.updateTotalCost(
//                unitPrice.toFloat(),
//                quantity.toInt()
//            )
//            editStockClickable = true
//        }


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
//                            navController.popBackStack()
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
                        value = customerNameEdit,
                        onValueChange = {
                            customerNameEdit = it

                        },
                        label = {
                            Text(
                                text = "Customer Name"
                            )
                        },
                        modifier = Modifier.align(Alignment.Center),

                        )

                }

                Spacer(modifier = Modifier.padding(10.dp))


                Box(
                    modifier = Modifier.fillMaxWidth()
                )
                {

                    OutlinedTextField(
                        value = productNameEdit,
                        onValueChange = {
                            productNameEdit = it
                        },
                        label = {
                            Text(
                                text = "Product Name"
                            )
                        },
                        modifier = Modifier.align(Alignment.Center),
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addIcon",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
//                                if (stocks.isEmpty()) {
//                                    Toast
//                                        .makeText(
//                                            context,
//                                            "you have no stock in your stock's list, add one to select",
//                                            Toast.LENGTH_LONG
//                                        )
//                                        .show()
//                                } else {
//                                    navigateTo(navController, Destination.SearchStock)
//                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = unitPriceEdit,
                    onValueChange =
                    {
                        unitPriceEdit = it
                        totalCostEdit = updateTotalCostss(unitPriceEdit.toFloat(), quantityEdit.toInt())
                    },
                    label = {
                        Text(
                            text = "Unit Price"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = true
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
                            if (quantityEdit.toInt() > 0) {
                                var temp = quantityEdit.toInt()
                                temp--
                                quantityEdit = temp.toString();
                            }
                        }
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .width(100.dp)
                            .height(70.dp),
                        value = quantityEdit,
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                val newValue = it.toInt()
                                quantityEdit = if (newValue >= 0) newValue.toString() else "1"
                                totalCostEdit = updateTotalCostss(unitPriceEdit.toFloat(), quantityEdit.toInt())
                            } else {
                                quantityEdit = "1"
                                totalCostEdit = updateTotalCostss(unitPriceEdit.toFloat(), 1)
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        label = {
                            Text(
                                text = " Quantity"
                            )
                        },
                        enabled = true
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addIcon",
                        modifier = Modifier.clickable {
                            var temp = quantityEdit.toInt()
                            temp++
                            quantityEdit = temp.toString()
                        }
                    )

                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = totalCostEdit,
                    onValueChange = {
                        totalCostEdit = it
                    },
                    label = {
                        Text(
                            text = "Total Amount"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = true
                )

                Spacer(modifier = Modifier.padding(20.dp))


                Button(
                    onClick = {
                        focus.clearFocus(force = true)
//                        if(singleSale !=null){
//                            val profit = (unitPrice.toInt()
//                                .minus(stockSelected?.stockPurchasePrice?.toInt()!!)).toString()
//                            viewModel.updateSale(customerName = customerName, stockName = productName,
//                                unitPrice = unitPrice, quantity = quantity, totalPrice = totalCost, profit = profit, selected?.salesId.toString(),singleSale.saleId.toString())
//                        }
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
//                        focus.clearFocus(force = true)
//                        if(singleSale!=null) {
//                            viewModel.deleteSingleSale(selected?.salesId.toString(), singleSale.saleId.toString()){
//                                viewModel.getSale(selected?.salesId.toString())
//                                navigateTo(navController, Destination.SalesInfoHome)
//                            }
//                        }
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

fun updateTotalCostss(cost: Float, count: Int): String {
    return (cost * count).toString()
}
