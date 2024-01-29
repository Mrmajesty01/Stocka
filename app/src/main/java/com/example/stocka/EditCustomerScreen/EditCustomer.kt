package com.example.stocka.EditCustomerScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.*
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditCustomerInfoScreen(navController: NavController, viewModel: AuthViewModel){


    var customer = viewModel.customerSelected.value

    val context = LocalContext.current

    val focus = LocalFocusManager.current

    val isLoading = viewModel.inProgress.value

    var customerName by remember{
        mutableStateOf("")
    }
    var customerPhoneNo by remember{
        mutableStateOf("")
    }
    var customerAddress by remember{
        mutableStateOf("")
    }
    var customerBalance by remember{
        mutableStateOf("")
    }
    var addAmount by remember{
         mutableStateOf("")
    }

    Box( modifier = Modifier
        .fillMaxSize()) {

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
                            }
                        },
                    tint = ListOfColors.black
                )



                Text(
                    text = "Edit Customer",
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
            ) {


                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = viewModel.customerSelected.value!!.customerName.toString(),
                    onValueChange =
                    {
                        viewModel.customerSelected.value =
                            viewModel.customerSelected.value!!.copy(customerName = it)
                    },
                    label = {

                        Text(
                            text = "Customer Name",
                        )
                    },
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(10.dp))


                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = viewModel.customerSelected.value!!.customerNumber.toString(),
                    onValueChange =
                    {
                        viewModel.customerSelected.value =
                            viewModel.customerSelected.value!!.copy(customerNumber = it)
                    },
                    label = {

                        Text(
                            text = "Customer Number",
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = viewModel.customerSelected.value!!.customerAddress.toString(),
                    onValueChange =
                    {
                        viewModel.customerSelected.value =
                            viewModel.customerSelected.value!!.copy(customerAddress = it)
                    },
                    label = {

                        Text(
                            text = "Customer Address",
                        )
                    },
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = viewModel.customerSelected.value!!.customerBalance.toString(),
                    onValueChange =
                    {
                        viewModel.customerSelected.value =
                            viewModel.customerSelected.value!!.copy(customerBalance = it)
                    },
                    label = {

                        Text(
                            text = "Customer Balance",
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = addAmount,
                    onValueChange =
                    {
                        addAmount = it
                    },
                    label = {

                        Text(
                            text = "Add money to customer balance",
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = !isLoading
                )


                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    onClick = {
                        focus.clearFocus(force = true)
                        if (!isLoading) {
                            if (viewModel.customerSelected.value!!.customerName!!.isNotEmpty()) {
                                viewModel.editCustomer(
                                    customer!!,
                                    viewModel.customerSelected.value!!.customerName.toString(),
                                    viewModel.customerSelected.value!!.customerNumber.toString(),
                                    viewModel.customerSelected.value!!.customerAddress.toString(),
                                    viewModel.customerSelected.value!!.customerBalance.toString(),
                                    addAmount
                                ) {
                                    viewModel.getCustomer(customer!!)
                                    navController.navigate(Destination.CustomerInfo.routes)
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Customer name cannot be empty",
                                    Toast.LENGTH_LONG
                                ).show()
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

        if(isLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
                    .align(Alignment.Center)
            )
        }

    }
}


