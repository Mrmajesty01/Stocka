package com.example.stocka.CustomerHistoryScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CustomerHistoryScreen(navController: NavController, viewModel: AuthViewModel){

    val customerSales = viewModel.customerHistoryData.value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "BackIcon",
                modifier = Modifier.padding(start = 5.dp)
                    .size(15.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = ListOfColors.black
            )


            Text(
                text = "Customer History",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }

        Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        ) {


            if (customerSales.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "This customer has no sales yet"
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    items(customerSales) {
                        com.example.stocka.HomeScreen.CustomerSalesItem(sales = it) {
                            if (it.type.equals("SR")) {
                                viewModel.getSale(it.salesId.toString())
                                navigateTo(navController, Destination.SalesInfoHome)
                                viewModel.onCustomerSelectedHome(it.customerId.toString())
                            } else {
                                viewModel.getSale(it.salesId.toString())
                                navigateTo(navController, Destination.CreditInfoHome)
                                viewModel.onCustomerSelectedHome(it.customerId.toString())
                            }
                        }
                    }
                }

            }
        }
    }


}

