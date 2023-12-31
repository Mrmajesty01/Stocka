package com.example.stocka.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditBusinessDetailsScreen(){

    Scaffold(
        topBar = { EditBusinessDetailsTopBar() }
    ){
        EditBusinessDetails()
    }
}

@Composable
fun EditBusinessDetails(){

    var businessName by remember{
        mutableStateOf("")
    }

    var businessAddress by remember{
        mutableStateOf("")
    }

    var businessEmail by remember{
        mutableStateOf("")
    }

    var businessDescription by remember{
        mutableStateOf("")
    }

    var businessNumber by remember{
        mutableStateOf("")
    }
    var businessAddNumber by remember{
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ){


        OutlinedTextField(
            value = businessName,
            modifier = Modifier
                .height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> businessName = value },
            label = { Text(text = "Business Name") },
            placeholder = { Text(text = "Business Name") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AddBusiness,
                    contentDescription = "businessName",
                    tint = ListOfColors.black
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.padding(10.dp))


        OutlinedTextField(
            value = businessDescription,
            modifier = Modifier
                .height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> businessDescription = value },
            label = { Text(text = "Business Description") },
            placeholder = { Text(text = "Business Description") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "businessInfo",
                    tint = ListOfColors.black
                )
            },
            singleLine = false
        )

        Spacer(modifier = Modifier.padding(10.dp))



        OutlinedTextField(
            value = businessAddress,
            modifier = Modifier
                .height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> businessAddress = value },
            label = { Text(text = "Business Address") },
            placeholder = { Text(text = "Business Address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AddLocation,
                    contentDescription = "businessAddress",
                    tint = ListOfColors.black
                )
            },
            singleLine = false
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = businessNumber,
            modifier = Modifier.height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> businessNumber = value },
            label = { Text(text = "Phone Number") },
            placeholder = { Text(text = "Enter your phone number") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "numberIcon",
                    tint = ListOfColors.black
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)

        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = businessAddNumber,
            modifier = Modifier.height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> businessAddNumber = value },
            label = { Text(text = "Second Phone Number") },
            placeholder = { Text(text = "Enter your phone number") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "numberIcon",
                    tint = ListOfColors.black
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)

        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = businessEmail,
            modifier = Modifier.height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> businessEmail = value },
            label = { Text(text = "Email Address") },
            placeholder = { Text(text = "Enter your Email Address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "emailIcon",
                    tint = ListOfColors.black
                )
            },
            singleLine = true
        )
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
                text = "Update"
            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun EditBusinessDetailsPrev(){
    EditBusinessDetailsScreen()
}