package com.example.stocka.EditStockScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.ui.theme.ListOfColors


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditStockInfoScreen(){

    Scaffold(
        topBar = { EditStockTopBar() }
    ){
        EditStockInfo()
    }
}

@Composable
fun EditStockInfo(){
    var productName by remember{
        mutableStateOf("")
    }
    var purchasePrice by remember{
        mutableStateOf("")
    }
    var sellingPrice by remember{
        mutableStateOf("")
    }
    var addQuantity by remember{
        mutableStateOf("")
    }
    var quantitySold by remember{
        mutableStateOf("")
    }
    var quantityRemaining by remember{
        mutableStateOf("")
    }
    var expiryDate by remember{
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
    ) {

        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = productName,
            onValueChange =
            {
                productName = it
            },
            label = {

                Text(
                    text = "Product Name",
                )
            },
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ){

            OutlinedTextField(
                modifier = Modifier.width(150.dp),
                value = purchasePrice ,
                onValueChange ={
                    purchasePrice = it
                },
                placeholder = {
                    Text(
                        text = "Purchase Price"
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier.width(150.dp),
                value = sellingPrice ,
                onValueChange = {
                    sellingPrice = it
                },
                placeholder = {
                    Text(
                        text = "Selling Price"
                    )
                }
            )
        }

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ){

            OutlinedTextField(
                modifier = Modifier.width(150.dp),
                value = quantitySold ,
                onValueChange ={
                    quantitySold = it
                },
                placeholder = {
                    Text(
                        text = "Qty Sold"
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier.width(150.dp),
                value = quantityRemaining ,
                onValueChange = {
                    quantityRemaining = it
                },
                placeholder = {
                    Text(
                        text = "Qty Remaining"
                    )
                }
            )
        }


        Spacer(modifier = Modifier.padding(20.dp))


        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = expiryDate,
            onValueChange =
            {
                expiryDate = it
            },
            label = {

                Text(
                    text = "Expiry Date",
                )
            },
            trailingIcon = {

                Icon(imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropdownIcon",
                    tint = ListOfColors.black)
            }
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text = "Add Stock",
            )

            Icon(
                imageVector = Icons.Default.Remove ,
                contentDescription = "removeIcon"
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(80.dp)
                    .height(30.dp),
                value = addQuantity,
                onValueChange = {
                    addQuantity = it
                }
            )

            Icon(
                imageVector = Icons.Default.Add ,
                contentDescription = "addIcon"
            )

        }

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
fun EditStockInfoPrev(){
    EditStockInfoScreen()
}