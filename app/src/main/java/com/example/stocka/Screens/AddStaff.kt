package com.example.stocka.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddStaffScreen(){

    Scaffold(
        topBar = { AddStaffTopBar() }
    ){
        AddStaff()
    }
}

@Composable
fun AddStaff() {

    var staffName by remember {
        mutableStateOf("")
    }

    var staffUserName by remember {
        mutableStateOf("")
    }

    var staffPassword by remember{
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        OutlinedTextField(
            value = staffName ,
            onValueChange =
            {
                staffName = it
            } ,
            label = {
                Text(
                    text = "Enter Staff Name",
                    textAlign = TextAlign.Center
                )
            }
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = staffUserName ,
            onValueChange = {
                staffUserName = it
            },
            label = {
                Text(
                    text = "Enter Staff Username"
                )
            }
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = staffPassword ,
            onValueChange = {
                staffPassword = it
            },
            label = {
                Text(
                    text = "Enter Staff Password"
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = "pswdIcon",
                    tint = ListOfColors.black
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.RemoveRedEye,
                    contentDescription ="seePassword",
                    tint = ListOfColors.black
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

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
            Text (
                text = "Continue",
                color = ListOfColors.black
            )
        }


    }

}

@Preview(showBackground = true)
@Composable
fun AddStaffPrev(){
    AddStaffScreen()
}