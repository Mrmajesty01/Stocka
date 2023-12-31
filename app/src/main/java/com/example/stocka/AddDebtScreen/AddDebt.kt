package com.example.stocka.AddDebtScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddDebtScreen(){

    Scaffold(
        topBar = { AddDebtTopBar() }
    ){
        AddDebt()
    }
}

@Composable
fun AddDebt(){

    var productName by rememberSaveable {
        mutableStateOf("")
    }

    var productCost by rememberSaveable{
        mutableStateOf("")
    }

    var totalCost by rememberSaveable{
        mutableStateOf("")
    }

    var quantity by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp)
    ){
        Box(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Customer name",
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                text = "Amount Owed",
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Text(
                text = "Musa",
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = "50,000",
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))


        Box(
            modifier = Modifier.fillMaxWidth()
        )
        {

            OutlinedTextField(
                value = productName,
                onValueChange = {
                    productName = it
                },
                label = {
                    Text(
                        text = "Product Name"
                    )
                },
                modifier = Modifier.align(Alignment.Center)
            )

            Icon(imageVector = Icons.Default.Add,
                contentDescription = "addIcon",
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

       OutlinedTextField(
           value = productCost ,
           onValueChange = {
               productCost = it
           },
           label = {
               Text(
                   text = "Item Cost"
               )
           },
           modifier = Modifier.align(Alignment.CenterHorizontally)
       )

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text = "Quantity",
            )

            Icon(
                imageVector = Icons.Default.Remove ,
                contentDescription = "removeIcon"
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(80.dp)
                    .height(30.dp),
                value = quantity,
                onValueChange = {
                    quantity = it
                }
            )

            Icon(
                imageVector = Icons.Default.Add ,
                contentDescription = "addIcon"
            )

        }

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = totalCost ,
            onValueChange ={
                totalCost = it
            },
            label = {
                Text(
                    text = "Total Cost"
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ){
            Text(
                text = "Add Goods",
                color = ListOfColors.black
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ){
            Text(
                text = "Save",
                color = ListOfColors.black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prevAddDebt(){
    AddDebtScreen()
}