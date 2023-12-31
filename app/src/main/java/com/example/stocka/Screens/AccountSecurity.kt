package com.example.stocka.Screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.ui.theme.ListOfColors


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountSecurityScreen(){

    Scaffold(
        topBar = { AccountSecurityTopBar() }
    ){

        AccountSecurity()
    }

}


@Composable
fun AccountSecurity(){
     
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ) {

            Text(
                text = "Change Password",
                color = ListOfColors.black
            )
        }
    }
}


@Preview (showBackground = true)
@Composable
fun AccountSecurityPrev(){
    AccountSecurityScreen()
}

