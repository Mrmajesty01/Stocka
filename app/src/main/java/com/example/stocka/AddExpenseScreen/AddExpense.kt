package com.example.stocka.AddExpenseScreen

import android.annotation.SuppressLint
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

    val isLoading = viewModel.expenseData
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

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(imageVector = Icons.Default.ArrowBackIos ,
                contentDescription ="BackIcon",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(15.dp)
                    .clickable {
                        navController.popBackStack()
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
            value = expenseName ,
            onValueChange = {
                expenseName = it
            },
            placeholder = {
                Text(
                    text = "Expense Name"
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = expenseDescription ,
            onValueChange = {
                expenseDescription = it
            },
            placeholder = {
                Text(
                    text = "Expense Description"
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(10.dp))


        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange ={isExpanded = it})
        {
            OutlinedTextField(
                value = expenseCategory ,
                onValueChange = {
                    expenseCategory = it
                },
                placeholder = {
                    Text(
                        text = "Expense Category"
                    )
                },
                readOnly = true,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }) {
                androidx.compose.material3.DropdownMenuItem(
                    text = {
                       Text(text ="Transport")
                    },
                    onClick = {
                        expenseCategory = "Transport"
                        isExpanded = false
                    })

                androidx.compose.material3.DropdownMenuItem(
                    text = {
                        Text(text ="Food")
                    },
                    onClick = {
                        expenseCategory = "Food"
                        isExpanded = false
                    })

                androidx.compose.material3.DropdownMenuItem(
                    text = {
                        Text(text ="Salary")
                    },
                    onClick = {
                        expenseCategory = "Salary"
                        isExpanded = false
                    })

                androidx.compose.material3.DropdownMenuItem(
                    text = {
                        Text(text ="Utility Fee")
                    },
                    onClick = {
                        expenseCategory = "Utility Fee"
                        isExpanded = false
                    })

                androidx.compose.material3.DropdownMenuItem(
                    text = {
                        Text(text ="Others")
                    },
                    onClick = {
                        expenseCategory = "Other"
                        isExpanded = false
                    })
            }
            

        }



        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = expenseAmount ,
            onValueChange = {
                expenseAmount = it
            },
            placeholder = {
                Text(
                    text = "Expense Amount"
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Button(
            onClick = {
                  viewModel.AddExpense(
                      name = expenseName,
                      description = expenseDescription,
                      category = expenseCategory,
                      amount = expenseAmount
                  ){
                      navController.popBackStack()
                  }
            },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ){
            Text(
                text = "Save"
            )
        }

    }

}



//@Preview(showBackground = true)
//@Composable
//fun AddExpensePrev(){
//    AddExpenseScreen()
//}
