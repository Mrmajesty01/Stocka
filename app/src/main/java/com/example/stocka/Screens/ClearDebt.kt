package com.example.stocka.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClearDebtScreen(){

    Scaffold(
        topBar = { ClearDebtTopBar() }
    ){
        ClearDebt()
    }



}

@Composable
fun ClearDebt(){
    var amountPaid by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .padding(all = 20.dp)
            .fillMaxSize()
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
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

        Spacer(modifier = Modifier.padding(20.dp))

        OutlinedTextField(
            value = amountPaid ,
            onValueChange = {
                amountPaid = it
            },
            label = {
                Text(
                    "Enter amount"
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ){
            Text(
                text = "Save"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prevCD(){
    ClearDebtScreen()
}
