package com.example.stocka.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StaffAccessScreen(){

    Scaffold(
        topBar = { StaffAccessTopBar() }
    ){
        StaffAccess()
    }
}

@Composable
fun StaffAccess(){

    var staffName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 20.dp)
    ){

        Text(
            text = "Staff Name",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Aliyu",
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(10.dp))



        Text(
            text = "Home Access",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "See Sales, Expenses & Profit",
                fontSize = 15.sp
            )

            RadioButton(
                selected = (staffName == null) ,
                onClick = { /*TODO*/ }
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Make Sales",
                fontSize = 15.sp
            )

            RadioButton(
                selected = (staffName == null) ,
                onClick = { /*TODO*/ }
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Add Expenses",
                fontSize = 15.sp
            )

            RadioButton(
                selected = (staffName == null) ,
                onClick = { /*TODO*/ }
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Edit/Delete Expenses",
                fontSize = 15.sp
            )

            RadioButton(
                selected = (staffName == null) ,
                onClick = { /*TODO*/ }
            )

        }

        Spacer(modifier = Modifier.padding(2.dp))


        Text(
            text = "Customers Access",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Add Customers",
                fontSize = 15.sp
            )

            RadioButton(
                selected = (staffName == null) ,
                onClick = { /*TODO*/ }
            )

        }


        Row(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Edit/Delete Customer",
                fontSize = 15.sp
            )

            RadioButton(
                selected = (staffName == null) ,
                onClick = { /*TODO*/ }
            )

        }

        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = "Stocks Access",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Add Stocks",
                fontSize = 15.sp
            )

            RadioButton(
                selected = (staffName == null) ,
                onClick = { /*TODO*/ }
            )

        }


        Row(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Edit/Delete Stock",
                fontSize = 15.sp
            )

            RadioButton(
                selected = (staffName == null) ,
                onClick = { /*TODO*/ }
            )

        }


        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = "Invoices Access",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Edit/Delete Invoices",
                fontSize = 15.sp
            )

            RadioButton(
                selected = (staffName == null) ,
                onClick = { /*TODO*/ }
            )

        }

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
            Text (
                text = "Save",
                color = ListOfColors.black
            )
        }



    }

}

@Preview (showBackground = true)
@Composable
fun StaffAccessPrev(){
    StaffAccessScreen()
}