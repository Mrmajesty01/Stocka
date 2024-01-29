package com.example.stocka.EditStockScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditStockInfoScreen(navController: NavController, viewModel: AuthViewModel) {


    val stock = viewModel.stockSelected.value

    val isLoading = viewModel.inProgress.value


    var productName by remember {
        mutableStateOf("")
    }
    var purchasePrice by remember {
        mutableStateOf("")
    }
    var sellingPrice by remember {
        mutableStateOf("")
    }
    var addQuantity by remember {
        mutableStateOf("0")
    }
    var quantitySold by remember {
        mutableStateOf("")
    }
    var quantityRemaining by remember {
        mutableStateOf("")
    }
    var expiryDate by remember {
        mutableStateOf("")
    }

    val dateDialogState = rememberMaterialDialogState()


    val currentDate = LocalDate.now()
    val threeMonthLater = currentDate.plusMonths(3)
    var oneMonthToExpiry by remember { mutableStateOf(LocalDate.now()) }
    var twoWeeksToExpiry by remember { mutableStateOf(LocalDate.now()) }
    var oneWeekToExpiry by remember { mutableStateOf(LocalDate.now()) }

    var datePicked by remember {
        mutableStateOf(LocalDate.now().plusMonths(3))
    }
    val formatedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(datePicked)
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = threeMonthLater,
            title = "Pick a date"
        ) {
            datePicked = it
            oneMonthToExpiry = datePicked.minusMonths(1)
            twoWeeksToExpiry = datePicked.minusWeeks(2)
            oneWeekToExpiry = datePicked.minusWeeks(1)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (isLoading) {
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
                            if (!isLoading) {
                                navController.popBackStack()
                                viewModel.getStock(stock!!)
                            }
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
                        viewModel.stockSelected.value =
                            viewModel.stockSelected.value!!.copy(stockName = it)
                    },
                    label = {

                        Text(
                            text = "Product Name",
                        )
                    },
                    enabled = !isLoading
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
                            viewModel.stockSelected.value =
                                viewModel.stockSelected.value!!.copy(stockPurchasePrice = it)
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = !isLoading
                    )

                    OutlinedTextField(
                        modifier = Modifier.width(150.dp),
                        value = viewModel.stockSelected.value!!.stockSellingPrice.toString(),
                        onValueChange = {
                            viewModel.stockSelected.value =
                                viewModel.stockSelected.value!!.copy(stockSellingPrice = it)
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = !isLoading
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
                            viewModel.stockSelected.value =
                                viewModel.stockSelected.value!!.copy(stockQuantitySold = it)
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = !isLoading
                    )

                    OutlinedTextField(
                        modifier = Modifier.width(150.dp),
                        value = viewModel.stockSelected.value!!.stockQuantity.toString(),
                        onValueChange = {
                            viewModel.stockSelected.value =
                                viewModel.stockSelected.value!!.copy(stockQuantity = it)
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = !isLoading
                    )
                }


                Spacer(modifier = Modifier.padding(20.dp))


                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = viewModel.stockSelected.value!!.stockExpiryDate.toString(),
                    onValueChange =
                    {
                        viewModel.stockSelected.value = viewModel.stockSelected.value!!.copy(stockExpiryDate = formatedDate)
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
                            modifier = Modifier.clickable {
                                dateDialogState.show()
                            },
                            tint = ListOfColors.black
                        )
                    },
                    enabled = !isLoading
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
                            if (!isLoading) {
                                var count = addQuantity.toInt()
                                count--
                                addQuantity = count.toString()
                            }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = !isLoading
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addIcon",
                        modifier = Modifier.clickable {
                            if (!isLoading) {
                                var count = addQuantity.toInt()
                                count++
                                addQuantity = count.toString()
                            }
                        }
                    )

                }

                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    onClick = {
                        if (!isLoading) {
                            viewModel.editStock(
                                stock!!,
                                viewModel.stockSelected.value!!.stockName.toString(),
                                viewModel.stockSelected.value!!.stockPurchasePrice.toString(),
                                viewModel.stockSelected.value!!.stockSellingPrice.toString(),
                                viewModel.stockSelected.value!!.stockQuantitySold.toString(),
                                viewModel.stockSelected.value!!.stockQuantity.toString(),
                                viewModel.stockSelected.value!!.stockExpiryDate.toString(),
                                addQuantity,
                                oneMonthToExpiry.toString(),
                                twoWeeksToExpiry.toString(),
                                oneWeekToExpiry.toString()
                            ) {
                                viewModel.getStock(stock)
                                navController.navigate(Destination.StockInfo.routes)
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


            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
                    .align(Alignment.Center)
            )
        }

    }
}




