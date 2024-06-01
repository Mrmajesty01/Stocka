package com.example.stocka.AddToStock

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
@Composable
fun AddToStockScreen(navController: NavController, viewModel:AuthViewModel) {

    val dateDialogState = rememberMaterialDialogState()


    val stock = viewModel.stockSelected.value

    val context = LocalContext.current
    val isLoading = viewModel.inProgress.value
    val currentDate = LocalDate.now()
    val threeMonthLater = currentDate.plusMonths(3)
    var oneMonthToExpiry by remember { mutableStateOf(LocalDate.now()) }
    var twoWeeksToExpiry by remember { mutableStateOf(LocalDate.now()) }
    var oneWeekToExpiry by remember { mutableStateOf(LocalDate.now()) }

    var addQuantity by remember {
        mutableStateOf("0")
    }

    var purchasePrice by remember {
        mutableStateOf("")
    }
    var sellingPrice by remember {
        mutableStateOf("")
    }

    if(stock!=null){
        purchasePrice = viewModel.stockSelected.value!!.stockPurchasePrice.toString()
        sellingPrice = viewModel.stockSelected.value!!.stockSellingPrice.toString()
    }

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackIcon",
                    modifier = Modifier
                        .padding(start = 5.dp)
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
                    text = "Add Stock",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {

                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = viewModel.stockSelected.value!!.stockName.toString(),
                    onValueChange =
                    {
//
                    },
                    label = {

                        Text(
                            text = "Product Name",
                        )
                    },
                    enabled = false
                )

                Spacer(modifier = Modifier.padding(20.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    OutlinedTextField(
                        modifier = Modifier.width(150.dp),
                        value = purchasePrice,
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = !isLoading
                    )


                    Spacer(modifier = Modifier.padding(20.dp))


                    OutlinedTextField(
                        modifier = Modifier.width(150.dp),
                        value = sellingPrice,
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = !isLoading
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = formatedDate,
                    onValueChange =
                    {

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

                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        if (!isLoading) {

                            if(!addQuantity.isInt()){
                                // Show a toast message indicating that the count is not an integer
                                Toast.makeText(
                                    context,
                                    "invalid value for stock quantity",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            if (sellingPrice.toDouble() < purchasePrice.toDouble()) {
                                Toast.makeText(
                                    context,
                                    "stock purchase price can't be greater than selling price",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            if(purchasePrice.isEmpty()){
                                Toast.makeText(context,"add purchase price to add stock",Toast.LENGTH_LONG).show()
                                return@Button
                            }

                            if(sellingPrice.isEmpty()){
                                Toast.makeText(context,"add selling price to add stock",Toast.LENGTH_LONG).show()
                                return@Button
                            }

                            if(addQuantity.isEmpty() || addQuantity.toInt()<=0){
                                Toast.makeText(context,"add the quantity to add to stock",Toast.LENGTH_LONG).show()
                                return@Button
                            }


                            viewModel.addToStock(
                                stock!!,
                                viewModel.stockSelected.value!!.stockName.toString(),
                                purchasePrice,
                                sellingPrice,
                                viewModel.stockSelected.value!!.stockQuantity.toString(),
                                viewModel.stockSelected.value!!.stockExpiryDate.toString(),
                                addQuantity,
                                oneMonthToExpiry.toString(),
                                twoWeeksToExpiry.toString(),
                                oneWeekToExpiry.toString()
                            ) {
                                viewModel.getStock(stock)
                                navController.navigate(Destination.StockInfo.routes){
                                    popUpTo(Destination.StockInfo.routes){
                                        inclusive = true
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
                        text = "Add Stock",
                        color = ListOfColors.black
                    )
                }

            }
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



//@Preview(showBackground = true)
//@Composable
//fun AddStockPrev(){
//    AddToStockScreen()
//}
