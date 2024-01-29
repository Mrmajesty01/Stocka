package com.example.stocka.EditExpenseScreen

import android.annotation.SuppressLint
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
fun EditExpenseScreen(navController: NavController, viewModel: AuthViewModel){

    val expense = viewModel.expenseSelected.value

    val unmodifiedExpense = viewModel.unmodifiedexpense.value

    val isLoading = viewModel.expenseProgress.value

    var isExpanded by remember{
        mutableStateOf(false)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(imageVector = Icons.Default.ArrowBackIos ,
                contentDescription ="BackIcon",
                modifier = Modifier.padding(start = 5.dp)
                    .size(15.dp)
                    .clickable {
                       if (!isLoading){
                           navController.popBackStack()
                       }
                    },
                tint = ListOfColors.black
            )

            Text(
                text = "Edit Expense",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

        Spacer(modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            value = expense!!.expenseName.toString() ,
            onValueChange = {
                viewModel.expenseSelected.value = viewModel.expenseSelected.value!!.copy(expenseName = it)
            },
            placeholder = {
                Text(
                    text = "Expense Name"
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.padding(20.dp))

        OutlinedTextField(
            value = expense.expenseDescription.toString() ,
            onValueChange = {
                viewModel.expenseSelected.value = viewModel.expenseSelected.value!!.copy(expenseDescription = it)
            },
            placeholder = {
                Text(
                    text = "Expense Description"
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = expense.expenseCategory.toString(),
                onValueChange = {
                   viewModel.expenseSelected.value = viewModel.expenseSelected.value!!.copy(expenseCategory = it)
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
                    viewModel.expenseSelected.value = viewModel.expenseSelected.value!!.copy(expenseCategory = "Transport")
                    isExpanded = false
                }) {
                    Text("Transport")
                }
                DropdownMenuItem(onClick = {
                    viewModel.expenseSelected.value = viewModel.expenseSelected.value!!.copy(expenseCategory = "Food")
                    isExpanded = false
                }) {
                    Text("Food")
                }
                DropdownMenuItem(onClick = {
                    viewModel.expenseSelected.value = viewModel.expenseSelected.value!!.copy(expenseCategory = "Salary")
                    isExpanded = false
                }) {
                    Text("Salary")
                }
                DropdownMenuItem(onClick = {
                    viewModel.expenseSelected.value = viewModel.expenseSelected.value!!.copy(expenseCategory = "Utility Fee")
                    isExpanded = false
                }) {
                    Text("Utility Fee")
                }
                DropdownMenuItem(onClick = {
                    viewModel.expenseSelected.value = viewModel.expenseSelected.value!!.copy(expenseCategory = "Other")
                    isExpanded = false
                }) {
                    Text("Others")
                }
            }
        }

        Spacer(modifier = Modifier.padding(20.dp))

        OutlinedTextField(
            value = expense.expenseAmount.toString() ,
            onValueChange = {
               viewModel.expenseSelected.value = viewModel.expenseSelected.value!!.copy(expenseAmount = it)
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
                if(!isLoading) {
                    viewModel.editExpense(
                        expense.expenseId.toString(),
                        expense.expenseName.toString(),
                        expense.expenseDescription.toString(),
                        expense.expenseCategory.toString(),
                        (expense.expenseAmount!!.toDouble() - unmodifiedExpense!!.expenseAmount!!.toDouble()).toString()
                    ) {
                        viewModel.getExpense(expense)
                        navController.navigate(Destination.ExpenseInfo.routes)
                    }
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ){
            Text(
                text = "Update"
            )
        }

    }
}


//@Preview(showBackground = true)
//@Composable
//fun EditExpensePrev(){
//    EditExpenseScreen()
//}
