package com.example.stocka.AddExpenseScreen

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
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddExpenseScreen(navController: NavController, viewModel: AuthViewModel){

    val isLoading = viewModel.addExpenseProgress.value

    val context = LocalContext.current

    val focus = LocalFocusManager.current

    var expenseName by remember{
        mutableStateOf("")
    }

    var expenseDescription by remember{
        mutableStateOf("")
    }

    var isExpanded by remember{
        mutableStateOf(false)
    }

    var expenseCategory by remember{
        mutableStateOf("")
    }

    var expenseAmount by remember{
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
                    text = "Add Expense",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Spacer(modifier = Modifier.padding(15.dp))

            OutlinedTextField(
                value = expenseName,
                onValueChange = {
                    expenseName = it
                },
                placeholder = {
                    Text(
                        text = "Expense Name"
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = expenseDescription,
                onValueChange = {
                    expenseDescription = it
                },
                placeholder = {
                    Text(
                        text = "Expense Description"
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.padding(10.dp))


            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = expenseCategory,
                    onValueChange = {
                        expenseCategory = it
                    },
                    placeholder = {
                        Text(
                            text = "Expense Category"
                        )
                    },
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text,),
                    modifier = Modifier.align(Alignment.Center)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            isExpanded = true
                        }
                ) {
                    Icon(
                        imageVector = (Icons.Default.ArrowDropDown), // Replace with your dropdown icon
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }

                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {
                        isExpanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    DropdownMenuItem(onClick = {
                        expenseCategory = "Transport"
                        isExpanded = false
                    }) {
                        Text("Transport")
                    }
                    DropdownMenuItem(onClick = {
                        expenseCategory = "Food"
                        isExpanded = false
                    }) {
                        Text("Food")
                    }
                    DropdownMenuItem(onClick = {
                        expenseCategory = "Salary"
                        isExpanded = false
                    }) {
                        Text("Salary")
                    }
                    DropdownMenuItem(onClick = {
                        expenseCategory = "Utility Fee"
                        isExpanded = false
                    }) {
                        Text("Utility Fee")
                    }
                    DropdownMenuItem(onClick = {
                        expenseCategory = "Other"
                        isExpanded = false
                    }) {
                        Text("Others")
                    }
                }
            }



                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = expenseAmount,
                    onValueChange = {
                        expenseAmount = it
                    },
                    placeholder = {
                        Text(
                            text = "Expense Amount"
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    onClick = {
                        focus.clearFocus(force = true)
                        if (!isLoading) {
                            if (expenseName.isNotEmpty() && expenseAmount.isNotEmpty() && expenseCategory.isNotEmpty()) {
                                viewModel.AddExpense(
                                    name = expenseName.toLowerCase(),
                                    description = expenseDescription,
                                    category = expenseCategory,
                                    amount = expenseAmount
                                ) {
                                    navController.popBackStack()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Expense name, expense amount and category can't be empty",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(ListOfColors.orange)
                ) {
                    Text(
                        text = "Save"
                    )
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




//@Preview(showBackground = true)
//@Composable
//fun AddExpensePrev(){
//    AddExpenseScreen()
//}
