package com.example.stocka.ExpenseInfoScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.data.Expense
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExpenseInfoScreen(navController: NavController, viewModel:AuthViewModel, expense:Expense?){
expense?.let {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "BackIcon",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(15.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = ListOfColors.black
            )

            Text(
                text = "Expense Info",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(end = 20.dp, start = 20.dp)
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            )
            {

                Text(
                    text = "Name of Expense",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart)
                )

                Text(
                    text = "Expense Category",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = expense?.expenseName.toString(),
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = expense?.expenseCategory.toString(),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {

                Text(
                    text = "Expense Description",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart)
                )

                Text(
                    text = "Expense Amount",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = expense?.expenseDescription.toString(),
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = expense?.expenseAmount.toString(),
                    modifier = Modifier.align(Alignment.BottomEnd)
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
            ) {

                Text(
                    text = "Edit",
                    color = ListOfColors.black
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange)
            ) {

                Text(
                    text = "Delete",
                    color = ListOfColors.black
                )
            }
        }
    }
}

}




//@Preview (showBackground = true)
//@Composable
//fun ExpenseInfoPrev(){
//    ExpenseInfoScreen()
//}