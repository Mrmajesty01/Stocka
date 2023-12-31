package com.example.stocka.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.R

@Composable
fun HomeTopBar(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){

        Image(painter = painterResource(id = R.drawable.shop),
            contentDescription ="shopIcon",
             modifier = Modifier.clip(CircleShape)
                 .padding(top = 10.dp, end = 10.dp)
            .size(50.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrevHomeTopBar(){
    HomeTopBar()
}