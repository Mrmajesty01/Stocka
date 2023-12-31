package com.example.stocka.EditCustomerScreen

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
fun EditCustomerInfoScreen(){
    Scaffold(
        topBar = { EditCustomerTopBar() }
    ){
        EditCustomerInfo()
    }
}


@Composable
fun EditCustomerInfo(){
    var customerName by remember{
        mutableStateOf("")
    }
    var customerPhoneNo by remember{
        mutableStateOf("")
    }
    var customerAddress by remember{
        mutableStateOf("")
    }
    var customerBalance by remember{
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .padding(all = 20.dp)
            .fillMaxSize()

    ) {


        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = customerName,
            onValueChange =
            {
                customerName = it
            },
            label = {

                Text(
                    text = "Customer Name",
                )
            },
        )

        Spacer(modifier = Modifier.padding(10.dp))


        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = customerPhoneNo,
            onValueChange =
            {
                customerPhoneNo = it
            },
            label = {

                Text(
                    text = "Customer Number",
                )
            },
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = customerAddress,
            onValueChange =
            {
                customerAddress = it
            },
            label = {

                Text(
                    text = "Customer Address",
                )
            },
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = customerBalance,
            onValueChange =
            {
                customerBalance = it
            },
            label = {

                Text(
                    text = "Customer Balance",
                )
            },
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Button(
            onClick =  {},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)


        ){
            Text(
                text = "Update",
                color = ListOfColors.black
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun   EditCustomerInfoPrev(){
    EditCustomerInfoScreen()
}