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
fun ManageStaffScreen(){

    Scaffold(
        topBar = { ManageStaffTopBar() }
    ){
        ManageStaff()
    }

}


@Composable
fun ManageStaff(){

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.Center
    ){

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(0.7f)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ) {

            Text(
                text = "Add Staff",
                color = ListOfColors.black
            )
        }
        
        Spacer(modifier = Modifier.padding(10.dp))


        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(0.7f)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        ){

            Text(
                text = "View Staffs",
                color = ListOfColors.black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageStaffPrev(){
    ManageStaffScreen()
}
