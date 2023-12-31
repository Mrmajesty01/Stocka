package com.example.stocka.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.ui.theme.ListOfColors

@Composable
fun StaffAccessTopBar(){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically

    ){
        Icon(imageVector = Icons.Default.ArrowBackIos,
            contentDescription = "BackIcon",
            modifier = Modifier.padding(start = 5.dp)
                .size(15.dp),
            tint = ListOfColors.black
        )



        Text(
            text = "Staff Access",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview (showBackground = true)
@Composable
fun StaffAccessTopPrev(){
    StaffAccessTopBar()
}