package com.example.stocka.PayCredit

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
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PayCreditScreen(navController: NavController, viewModel: AuthViewModel) {

    var creditItem = viewModel.salesSelected.value
    var customer = viewModel.customerSelected.value
    var focus = LocalFocusManager.current
    var context = LocalContext.current
    val isLoading = viewModel.inProgress.value

    var amountPaid by remember {
        mutableStateOf("")
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
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackIcon",
                    modifier = Modifier.padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            if (!isLoading) {
                                navController.popBackStack()
                            }
                        },
                    tint = ListOfColors.black
                )



                Text(
                    text = "Pay Credit",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)


            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(all = 20.dp)
            ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
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
                        text = creditItem?.customerName.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                            .padding(start = 5.dp)
                    )

                    Text(
                        text = customer?.customerBalance.toString(),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {

                    Text(
                        text = "Total Amount",
                        modifier = Modifier.align(Alignment.TopStart),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Amount Paid",
                        modifier = Modifier.align(Alignment.TopEnd),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = creditItem?.totalPrice.toString(),
                        modifier = Modifier.align(Alignment.BottomStart)
                            .padding(start = 5.dp)
                    )

                    Text(
                        text = creditItem?.amountPaid.toString(),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {

                    Text(
                        text = "Balance",
                        modifier = Modifier.align(Alignment.TopCenter),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = creditItem?.balance.toString(),
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )

                }

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    value = amountPaid,
                    onValueChange = {
                        amountPaid = it
                    },
                    label = {
                        Text(
                            "Enter amount"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                        focus.clearFocus(force = true)
                        if (!isLoading) {
                            if (amountPaid.toDouble() > creditItem?.totalPrice!!.toDouble()) {
                                Toast.makeText(
                                    context,
                                    "amount greater than credit total amount",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }
                            if (amountPaid.toDouble() > creditItem?.totalPrice!!.toDouble() - creditItem.amountPaid!!.toDouble()) {
                                Toast.makeText(
                                    context,
                                    "amount greater than credit balance amount",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            viewModel.payCredit(
                                amountPaid,
                                customer!!,
                                creditItem.salesId.toString()
                            ) {
                                navigateTo(navController, Destination.CreditInfoHome)
                                viewModel.getSale(creditItem.salesId.toString())
                                viewModel.onCustomerSelectedHome(customer.customerId.toString())
                            }
                        }

                    },
                    colors = ButtonDefaults.buttonColors(ListOfColors.orange)
                ) {
                    Text(
                        text = "Update"
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




//@Preview(showBackground = true)
//@Composable
//fun prevPC(){
//    PayCreditScreen()
//}
