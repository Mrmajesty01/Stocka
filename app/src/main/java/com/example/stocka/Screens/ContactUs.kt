package com.example.stocka.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.R
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactUsScreen(){

    Scaffold(
        topBar = { ContactUsTopBar() }
    ) {

        ContactUs()
    }
}

@Composable
fun ContactUs(){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){

        Image(painter = painterResource(id = R.drawable.contactus),
            contentDescription = "ContactUs" )
        
        Spacer(modifier = Modifier.padding(4.dp))
        
        Text(
            text = "You can contact us via:",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(imageVector = Icons.Default.Phone,
                contentDescription = "PhoneIcon",
                tint = ListOfColors.lightGreen)
            
            Spacer(modifier = Modifier.width(5.dp))
            
            Text(
                text = "08054062271, 09011621344"
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(imageVector = Icons.Default.Email,
                contentDescription = "EmailIcon",
                tint = ListOfColors.lightBlue)

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "mu.mukhtar30@gmail.com"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ContactUsPrev(){
    ContactUsScreen()
}