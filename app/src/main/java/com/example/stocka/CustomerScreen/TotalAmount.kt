package com.example.stocka.CustomerScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.HomeScreen.formatNumberWithDelimiter

@Composable
fun TotalAmountOwing(
    amount: Double
){

    val totalValue = amount
    val formattedAmount = formatNumberWithDelimiter(totalValue)
    Card(
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth()
            .height(50.dp)
            .clickable {
            },
        shape = RoundedCornerShape(15.dp)

    ) {
        Box(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp)
        ) {


            Text(
                text ="Total Amount Owing",
                modifier = Modifier.align(Alignment.TopCenter),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = formattedAmount,
                modifier = Modifier.align(Alignment.BottomCenter),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }

}



//@Preview
//@Composable
//fun PrevTotalAmountOwing(){
//    TotalAmountOwing()
//}