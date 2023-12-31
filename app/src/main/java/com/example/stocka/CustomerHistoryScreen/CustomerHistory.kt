package com.example.stocka.CustomerHistoryScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.Screens.CustomerSalesItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CustomerHistoryScreen(){

    Scaffold(
        topBar = { CustomerHistoryTopBar() }
    ) {
        CustomerHistory()
    }

}


@Composable
fun CustomerHistory(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 20.dp,
                start = 20.dp,
                end = 20.dp
            )
    ){
        CustomerSalesItem()

        Spacer(modifier = Modifier.padding(7.dp))

        CustomerSalesItem()

        Spacer(modifier = Modifier.padding(7.dp))

        CustomerSalesItem()

        Spacer(modifier = Modifier.padding(7.dp))

        CustomerSalesItem()
    }
}

@Preview (showBackground = true)
@Composable
fun CustomerHistoryPrev(){
    CustomerHistoryScreen()
}