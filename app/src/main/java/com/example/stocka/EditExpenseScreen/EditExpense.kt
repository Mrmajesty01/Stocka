package com.example.stocka.EditExpenseScreen

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.ui.theme.ListOfColors


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditExpenseScreen(){
    var expenseName by remember{
        mutableStateOf("")
    }

    var expenseDescription by remember{
        mutableStateOf("")
    }

    var expenseCategory by remember{
        mutableStateOf("")
    }

    var expenseAmount by remember{
        mutableStateOf("")
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
                    .size(15.dp),
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
            value = expenseName ,
            onValueChange = {
                expenseAmount = it
            },
            placeholder = {
                Text(
                    text = "Expense Name"
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(20.dp))

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

        Spacer(modifier = Modifier.padding(20.dp))

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
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown ,
                    contentDescription ="DropDownIcon",
                    tint = ListOfColors.black
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(20.dp))

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
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .width(50.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ){
            Text(
                text = "Update"
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun EditExpensePrev(){
    EditExpenseScreen()
}
