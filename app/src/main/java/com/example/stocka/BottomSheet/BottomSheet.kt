package com.example.stocka.BottomSheet

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.navigateTo

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BSheet(navController: NavController, viewModel: AuthViewModel){
    val sheetState = rememberModalBottomSheetState()


        ModalBottomSheet(
            modifier = Modifier.wrapContentHeight(),
            onDismissRequest = {
                navController.popBackStack()
            },
            sheetState = sheetState
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        viewModel.customerSelected.value = null
                        viewModel.stockSelected.value = null
                        navigateTo(navController, Destination.MakeSales)
                    }
            ){
            Text(
                text = "Make Sales",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 10.dp)
            )

            }

            Spacer(modifier = Modifier.padding(10.dp))


            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        navigateTo(navController, Destination.AddExpense)
                    }
            ) {
                Text(
                    text = "Add Expense",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 10.dp)

                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        navigateTo(navController, Destination.AddCustomer)
                    }
            ) {
                Text(
                    text = "Add Customer",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 10.dp)

                )
            }


            Spacer(modifier = Modifier.padding(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        navigateTo(navController, Destination.AddStock)
                    }
            ) {
                Text(
                    text = "Add Stock",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 10.dp)

                )
            }

            Spacer(modifier = Modifier.padding(30.dp))
    }

}

//@Composable
//@Preview(showBackground = true)
//fun BottomSheetPrev(){
//    val navController:NavController = rememberNavController()
//    BSheet(navController)
//}