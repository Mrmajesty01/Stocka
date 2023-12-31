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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonalDetailsScreen(){

    Scaffold(
        topBar = { PersonalDetailsTopBar() }
    ){
        PersonalDetails()
    }

}

@Composable
fun PersonalDetails(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
              all = 20.dp
            )
    ){

       Box(
           modifier = Modifier.fillMaxWidth()
               .height(45.dp)
       ) {

           Text(
               text = "Name",
               fontSize = 15.sp,
               fontWeight = FontWeight.Bold,
               modifier = Modifier.align(Alignment.TopStart)
           )


           Text(
               text = "Number",
               fontSize = 15.sp,
               fontWeight = FontWeight.Bold,
               modifier = Modifier.align(Alignment.TopEnd)
           )


           Text(
               text = "Muhammad Auwal Mukhtar",
               fontSize = 15.sp,
               modifier = Modifier.align(Alignment.BottomStart)
           )

           Text(
               text = "08054062271",
               fontSize = 15.sp,
               modifier = Modifier.align(Alignment.BottomEnd)
           )
           
       }
        
        Spacer(modifier = Modifier.padding(5.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
                .height(45.dp)
        ) {

            Text(
                text = "Email Address",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopCenter)
            )


            Text(
                text = "mu.mukhtar30@gmail.com",
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

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
                text = "Edit"
            )
        }
        

        }
}


@Preview (showBackground = true)
@Composable
fun PersonalDetailsPrev(){
    PersonalDetailsScreen()
}