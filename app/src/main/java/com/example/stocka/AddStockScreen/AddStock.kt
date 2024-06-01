package com.example.stocka.AddStockScreen

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.stocka.ui.theme.ListOfColors
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddStockScreen(navController: NavController,viewModel: AuthViewModel) {


    val dateDialogState = rememberMaterialDialogState()
    val context = LocalContext.current

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

    val isLoading = viewModel.inProgress.value

    val focus = LocalFocusManager.current

    var productName by remember {
        mutableStateOf("")
    }
    var purchasePrice by remember {
        mutableStateOf("")
    }
    var sellingPrice by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableStateOf("")
    }
    var count by rememberSaveable {
        mutableStateOf("1")
    }

    var expiryDate by remember {
        mutableStateOf("")
    }



    Box(modifier = Modifier.fillMaxSize()) {

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
                        contentDescription = "BackIcon",
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .size(15.dp)
                            .clickable {
                                if (!isLoading) {
                                    navController.popBackStack()
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

                Spacer(modifier = Modifier.padding(15.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {

                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = productName,
                    onValueChange =
                    {
                        productName = it
                    },
                    label = {

                        Text(
                            text = "Enter Product Name",
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
                        value = purchasePrice,
                        onValueChange = {
                            purchasePrice = it
                        },
                        placeholder = {
                            Text(
                                text = "Purchase Price"
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = !isLoading
                    )

                    OutlinedTextField(
                        modifier = Modifier.width(150.dp),
                        value = sellingPrice,
                        onValueChange = {
                            sellingPrice = it
                        },
                        placeholder = {
                            Text(
                                text = "Selling Price"
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
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
                                if (count.toInt() > 0) {
                                    var temp = count.toInt()
                                    temp--
                                    count = temp.toString()
                                }
                            }
                        }
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .width(100.dp)
                            .height(70.dp),
                        value = count,
                        onValueChange = {
                            count = it
                        },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        label = {
                            Text(
                                text = " Quantity"
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = !isLoading
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addIcon",
                        modifier = Modifier.clickable {
                            if (!isLoading) {
                                var temp = count.toInt()
                                temp++
                                count = temp.toString()
                            }
                        }
                    )

                }

                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    onClick = {
                        if (!isLoading) {
                            focus.clearFocus(force = true)
                            if (sellingPrice.toDouble() < purchasePrice.toDouble()) {
                                Toast.makeText(
                                    context,
                                    "stock purchase price can't be greater than selling price",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }
                            if (!count.isInt()) {
                                // Show a toast message indicating that the count is not an integer
                                Toast.makeText(
                                    context,
                                    "invalid value for stock quantity",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }
                            viewModel.createStock(
                                name = productName,
                                purchasePrice = purchasePrice,
                                sellingPrice = sellingPrice,
                                expiryDate = formatedDate.toString(),
                                quantity = count.toInt().toString(),
                                oneMonthToExpiry.toString(),
                                twoWeeksToExpiry.toString(),
                                oneWeekToExpiry.toString()
                            ) {
                                navController.navigate(Destination.StockInfo.routes){
                                    popUpTo(Destination.AddStock.routes){
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
                        text = "Add",
                        color = ListOfColors.black
                    )
                }

            }

        }
        if (isLoading) {
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




//@Preview(showBackground = true)
//@Composable
//fun AddStockPrev(){
//    AddStockScreen()
//}