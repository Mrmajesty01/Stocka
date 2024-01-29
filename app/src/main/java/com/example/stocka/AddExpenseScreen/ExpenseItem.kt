package com.example.stocka.AddExpenseScreen

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
import com.example.stocka.data.Expense
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ExpenseItem(
    expense: Expense,
    onClick:(Expense)->Unit
){
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
               onClick.invoke(expense)
            },
        shape = RoundedCornerShape(15.dp)

    ) {
        val formattedDate = expense?.expenseDate.let {
            SimpleDateFormat("dd MMM yyyy").format(Date(it!!))
        } ?: ""

        Box(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top= 5.dp, bottom = 5.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .height(40.dp)
            ){
                Text(
                    text = "Expense Name",
                    modifier = Modifier.align(Alignment.TopStart),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = expense.expenseName.toString(),
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = "Expense Category",
                    modifier = Modifier.align(Alignment.TopEnd),
                    fontWeight = FontWeight.Medium

                )

                Text(
                    text = expense.expenseCategory.toString(),
                    modifier = Modifier.align(Alignment.BottomEnd)

                )

            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .height(40.dp)
            ){
                Text(
                    text = "Expense Date",
                    modifier = Modifier.align(Alignment.TopCenter),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = formattedDate,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )

            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .height(40.dp)
            ){
                Text(
                    text = "Expense Description",
                    modifier = Modifier.align(Alignment.TopStart),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = expense.expenseDescription.toString(),
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = "Expense Amount",
                    modifier = Modifier.align(Alignment.TopEnd),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = expense.expenseAmount.toString(),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}

//@Preview (showBackground = true)
//@Composable
//fun ExpenseItemPrev(){
//    ExpenseItem()
//}