package com.example.stocka.InvoiceScreen


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.StockScreen.StocksTopBar

@Composable
fun InvoiceTopBar(){

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 10.dp),
    ){
        Text(
            text = "Invoices",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()

        )
    }

}

@Preview(showBackground = true)
@Composable
fun InvoiceTopBarPrev(){
    StocksTopBar()
}