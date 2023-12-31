package com.example.stocka.GenerateCreditReceipt

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.GenerateReceiptScreen.GenerateReceiptTopBar
import com.example.stocka.Screens.ItemDetails
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GenerateCreditReceiptScreen(){

    Scaffold(
        topBar = { GenerateReceiptTopBar() }
    ){
        GenerateCreditReceipt()
    }

}

@Composable
fun GenerateCreditReceipt(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ){


        Text(
            text = "Afrah Store",
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 25.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(3.dp))

        Text(
            text = "Dealers in all kinds of drinks, bottled water, indomie, biscuit, etc",
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(3.dp))

        Text(
            text = "No. 4, New Madorawa plaza, Kaduna Road, Sokoto State",
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(3.dp))

        Text(
            text = "Mobile: 08054062271, 0706600215",
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
                .height(65.dp)
        ){

            Text(
                text = "Customer's Name",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Text(
                text = "CREDIT INVOICE",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Text(
                text = "Usman",
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = "Invoice Number: CR01",
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.CenterEnd)
            )

            Text(
                text = "Invoice Date: july 4, 2023",
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

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
                text = "20,000,000",
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
                text = "Balance",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Text(
                text = "15,250,000",
                modifier = Modifier.align(Alignment.BottomEnd)
            )

        }


        Spacer(modifier = Modifier.padding(20.dp))

        Button(
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ){

            Text(
                text = "Print",
                color = ListOfColors.black
            )
        }

        
    }

}


@Preview (showBackground = true)
@Composable
fun GenerateCreditReceiptPrev(){
    GenerateCreditReceiptScreen()
}