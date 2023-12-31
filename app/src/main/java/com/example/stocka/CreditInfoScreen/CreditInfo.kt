package com.example.stocka.CreditInfoScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.Screens.ItemDetails
import com.example.stocka.ui.theme.ListOfColors
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreditInfoScreen(){
    Scaffold(
        topBar = { CreditInfoTopBar() }
    ) {
        CreditInfo()
    }
}

@Composable
fun CreditInfo(){
    
    var amountPaid by remember{
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
                .height(20.dp)
        ){

            Text(
                text = "Name of Customer",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Text(
                text = "Usman",
                modifier = Modifier.align(Alignment.TopEnd)
            )

        }

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){


            Text(
                text = "Quantity",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Item",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Unit Price",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Total",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))
        ItemDetails()
        Spacer(modifier = Modifier.padding(5.dp))
        ItemDetails()
        Spacer(modifier = Modifier.padding(5.dp))
        ItemDetails()

        Spacer(modifier = Modifier.padding(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ){

            Text(
                text = "Total Quantity",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Text(
                text = "Total Amount",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Text(
                text = "15,000",
                modifier = Modifier.align(Alignment.BottomStart)
            )

            Text(
                text = "35,250,000",
                modifier = Modifier.align(Alignment.BottomEnd)
            )

        }


        Spacer(modifier = Modifier.padding(5.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ){

            Text(
                text = "Amount Paid",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Text(
                text = "0",
                modifier = Modifier.align(Alignment.BottomEnd)
            )

        }

        Spacer(modifier = Modifier.padding(5.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ){

            OutlinedTextField(
                value = amountPaid,
                onValueChange = {
                    amountPaid = it
                },
                placeholder = {
                    Text (
                        text = "Amount Paid"
                    )
                },
                modifier = Modifier.align(Alignment.TopStart)
                    .width(200.dp)
            )

            Text(
                text = "Balance",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Text(
                text = "35,250,000",
                modifier = Modifier.align(Alignment.BottomEnd)
            )

        }


        Spacer(modifier = Modifier.padding(20.dp))


        Row(
            Modifier
                .horizontalScroll(rememberScrollState())
                .align(Alignment.CenterHorizontally),
        ){
            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                modifier = Modifier.width(120.dp)
                    .height(50.dp)
            ){
                Text(

                    text = "Add Goods",
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                modifier = Modifier.width(120.dp)
                    .height(50.dp)
            ){
                Text(
                    text = "Generate Receipt",
                    textAlign = TextAlign.Center
                )
            }

        }


        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange),
            modifier = Modifier.fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally)
        ){
            Text(
                "Save"
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange),
            modifier = Modifier.fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally)
        ){
            Text(
                "Delete"
            )
        }

    }

}

@Preview (showBackground = true)
@Composable
fun CreditInfoPrev(){
    CreditInfoScreen()
}