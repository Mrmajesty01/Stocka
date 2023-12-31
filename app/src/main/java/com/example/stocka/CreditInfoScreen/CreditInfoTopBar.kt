package com.example.stocka.CreditInfoScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
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
fun CreditInfoTopBar(){

    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){

        Icon(imageVector = Icons.Default.ArrowBackIos,
            contentDescription = "ArrowBack",
            modifier = Modifier.padding(start = 5.dp)
                .size(15.dp),
            tint = ListOfColors.black
        )

        Text(
            text = "Credit Info",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

    }
}

@Preview (showBackground = true)
@Composable
fun CreditInfoTopBarPrev(){
    CreditInfoTopBar()
}