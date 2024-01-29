package com.example.stocka.AddCustomerScreen

import android.annotation.SuppressLint
import android.widget.Toast
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
import com.example.stocka.main.CommonProgressSpinner
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddCustomerScreen(navController: NavController,viewModel:AuthViewModel) {


    var isLoading = viewModel.inProgress.value

    val focus = LocalFocusManager.current

    val context = LocalContext.current

    var customerName by remember {
        mutableStateOf("")
    }

    var customerNumber by remember {
        mutableStateOf("")
    }

    var customerAddress by remember {
        mutableStateOf("")
    }


    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.weight(1f)
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
                        text = "Add Customer",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                }

                Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

                Spacer(modifier = Modifier.padding(15.dp))

                OutlinedTextField(
                    value = customerName,
                    onValueChange =
                    {
                        customerName = it
                    },
                    label = {
                        Text(
                            text = "Enter Customer Name",
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = customerNumber,
                    onValueChange = {
                        customerNumber = it
                    },
                    label = {
                        Text(
                            text = "Enter Customer Number"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = customerAddress,
                    onValueChange = {
                        customerAddress = it
                    },
                    label = {
                        Text(
                            text = "Enter Customer Address"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        if(!isLoading) {
                            if(customerName.isNotEmpty()){
                                focus.clearFocus(force = true)
                                viewModel.createCustomer(
                                    name = customerName,
                                    number = customerNumber,
                                    address = customerAddress
                                ) {
                                    navigateTo(navController, Destination.Customers)
                                }
                            }
                            else{
                                Toast.makeText(context,"Customer name cannot be empty",Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(ListOfColors.orange)
                ) {
                    Text(
                        text = "Save",
                        color = ListOfColors.black
                    )
                }
            }
        }
        if (isLoading) {
            CommonProgressSpinner()
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun AddCustomerPrev(){
//    AddCustomerScreen()
//}