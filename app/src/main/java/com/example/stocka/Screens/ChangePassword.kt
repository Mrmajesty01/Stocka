package com.example.stocka.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChangePasswordScreen(){

    Scaffold(
        topBar = { ChangePasswordTopBar() }
    ){
        ChangePassword()
    }
}

@Composable
fun ChangePassword(){

    var currentPassword by remember{
        mutableStateOf("")
    }

    var newPassword by remember{
        mutableStateOf("")
    }

    var confirmNewPassword by remember{
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 20.dp)
    ){

        OutlinedTextField(
            value = currentPassword,
            modifier = Modifier.height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> currentPassword = value },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter your current password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = "pswIcon",
                    tint = ListOfColors.matteBlack
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.RemoveRedEye,
                    contentDescription ="seePassword",
                    tint = ListOfColors.black
                )
            },
            singleLine = true

        )


        Spacer(Modifier.padding(10.dp))


        OutlinedTextField(
            value = newPassword,
            modifier = Modifier.height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> newPassword = value },
            label = { Text(text = "New Password") },
            placeholder = { Text(text = "Enter your new password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = "pswIcon",
                    tint = ListOfColors.matteBlack
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.RemoveRedEye,
                    contentDescription ="seePassword",
                    tint = ListOfColors.black
                )
            },
            singleLine = true

        )

        Spacer(Modifier.padding(10.dp))



        OutlinedTextField(
            value = confirmNewPassword,
            modifier = Modifier.height(60.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { value -> confirmNewPassword = value },
            label = { Text(text = "Confirm New Password") },
            placeholder = { Text(text = "Enter your new password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = "pswIcon",
                    tint = ListOfColors.matteBlack
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.RemoveRedEye,
                    contentDescription ="seePassword",
                    tint = ListOfColors.black
                )
            },
            singleLine = true

        )

        Spacer(modifier = Modifier.padding(20.dp))


        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
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
fun ChangePasswordPrev(){
    ChangePasswordScreen()
}