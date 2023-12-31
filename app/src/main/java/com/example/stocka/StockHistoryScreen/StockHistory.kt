package com.example.stocka.StockHistoryScreen

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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StockHistoryScreen(){
    Scaffold(
        topBar = { StockHistoryTopBar() }
    ){
        StockHistory()
    }
}

@Composable
fun StockHistory(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 20.dp,
                start = 20.dp,
                end = 20.dp)
         )
        {

        StockHistoryItem()

        Spacer(modifier = Modifier.padding(7.dp))

        StockHistoryItem()

        Spacer(modifier = Modifier.padding(7.dp))

        StockHistoryItem()

        Spacer(modifier = Modifier.padding(7.dp))

        StockHistoryItem()
    }

}


@Preview(showBackground = true)
@Composable
fun StockHistoryPrev(){
    StockHistoryScreen()
}