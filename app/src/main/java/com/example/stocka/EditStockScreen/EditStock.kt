package com.example.stocka.EditStockScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditStockInfoScreen(navController: NavController, viewModel: AuthViewModel){


    val stock = viewModel.stockSelected.value

    var productName by remember{
        mutableStateOf("")
    }
    var purchasePrice by remember{
        mutableStateOf("")
    }
    var sellingPrice by remember{
        mutableStateOf("")
    }
    var addQuantity by remember{
        mutableStateOf("0")
    }
    var quantitySold by remember{
        mutableStateOf("")
    }
    var quantityRemaining by remember{
        mutableStateOf("")
    }
    var expiryDate by remember{
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,

            ){
            Icon(imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "BackIcon",
                modifier = Modifier.padding(start = 5.dp)
                    .size(15.dp)
                    .clickable {
                       navController.popBackStack()
                        viewModel.getStock(stock!!)
                    },
                tint = ListOfColors.black
            )



            Text(
                text = "Edit Stock",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }

        Divider(thickness = 1.dp, color = ListOfColors.lightGrey)


        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 20.dp),
        ) {

            OutlinedTextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                value = viewModel.stockSelected.value!!.stockName.toString(),
                onValueChange =
                {
                    viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockName = it)
                },
                label = {

                    Text(
                        text = "Product Name",
                    )
                },
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    value = viewModel.stockSelected.value!!.stockPurchasePrice.toString(),
                    onValueChange = {
                        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockPurchasePrice = it)
                    },
                    placeholder = {
                        Text(
                            text = "Purchase Price"
                        )
                    },
                    label = {

                        Text(
                            text = "Purchase Price",
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                OutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    value = viewModel.stockSelected.value!!.stockSellingPrice.toString(),
                    onValueChange = {
                       viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockSellingPrice = it)
                    },
                    placeholder = {
                        Text(
                            text = "Selling Price"
                        )
                    },
                    label = {

                        Text(
                            text = "Selling Price",
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    value = viewModel.stockSelected.value!!.stockQuantitySold.toString(),
                    onValueChange = {
                        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockQuantitySold = it)
                    },
                    placeholder = {
                        Text(
                            text = "Qty Sold"
                        )
                    },
                    label = {

                        Text(
                            text = "Quantity Sold",
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    value = viewModel.stockSelected.value!!.stockQuantity.toString(),
                    onValueChange = {
                       viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockQuantity = it)
                    },
                    placeholder = {
                        Text(
                            text = "Qty Remaining"
                        )
                    },
                    label = {

                        Text(
                            text = "Quantity Remaining",
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }


            Spacer(modifier = Modifier.padding(20.dp))


            OutlinedTextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                value = viewModel.stockSelected.value!!.stockExpiryDate.toString(),
                onValueChange =
                {
                    expiryDate = it
                },
                label = {

                    Text(
                        text = "Expiry Date",
                    )
                },
                trailingIcon = {

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "DropdownIcon",
                        tint = ListOfColors.black
                    )
                }
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add Stock",
                )

                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "removeIcon",
                    modifier = Modifier.clickable {
                        var count = addQuantity.toInt()
                        count--
                        addQuantity = count.toString()
                    }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp),
                    value = addQuantity,
                    onValueChange = {
                        addQuantity = it
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "addIcon",
                    modifier = Modifier.clickable {
                        var count = addQuantity.toInt()
                        count++
                        addQuantity = count.toString()
                    }
                )

            }

            Spacer(modifier = Modifier.padding(20.dp))

            Button(
                onClick = {

                          viewModel.editStock(
                              stock!!,
                              viewModel.stockSelected.value!!.stockName.toString(),
                              viewModel.stockSelected.value!!.stockPurchasePrice.toString(),
                              viewModel.stockSelected.value!!.stockSellingPrice.toString(),
                              viewModel.stockSelected.value!!.stockQuantitySold.toString(),
                              viewModel.stockSelected.value!!.stockQuantity.toString(),
                              viewModel.stockSelected.value!!.stockExpiryDate.toString(),
                              addQuantity
                              ){
                                viewModel.getStock(stock)
                                navController.navigate(Destination.StockInfo.routes)
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


        }
    }

}


