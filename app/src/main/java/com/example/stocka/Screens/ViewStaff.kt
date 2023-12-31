package com.example.stocka.Screens

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
fun ViewStaffScreen(){

    Scaffold(
        topBar = { ViewStaffTopBar() }
    ){
        ViewStaff()
    }
}

@Composable
fun ViewStaff(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp)
    ){

        ViewStaffItem()
        Spacer(modifier = Modifier.padding(7.dp))
        ViewStaffItem()
        Spacer(modifier = Modifier.padding(7.dp))
        ViewStaffItem()
        Spacer(modifier = Modifier.padding(7.dp))

    }


}

@Preview (showBackground = true)
@Composable
fun ViewStaffPrev(){
    ViewStaffScreen()
}