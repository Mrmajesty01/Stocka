package com.example.stocka.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditPersonalDetailsScreen(){

    Scaffold(
        topBar = { EditPersonalDetailsTopBar() }
    ){
        EditPersonalDetails()
    }
}

@Composable
fun EditPersonalDetails(){

    var name by remember{
        mutableStateOf("")
    }

    var number by remember{
        mutableStateOf("")
    }

    var email by remember{
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ){


        OutlinedTextField(
            value = name,
            modifier = Modifier
                .height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> name = value },
            label = { Text(text = "Full Name") },
            placeholder = { Text(text = "Enter your FullName") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "userIcon",
                    tint = ListOfColors.black
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = number,
            modifier = Modifier.height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> number = value },
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
            value = email,
            modifier = Modifier.height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> email = value },
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
fun EditPersonalDetailsPrev(){
    EditPersonalDetailsScreen()
}