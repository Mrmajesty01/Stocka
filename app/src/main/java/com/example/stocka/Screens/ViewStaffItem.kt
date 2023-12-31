package com.example.stocka.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocka.ui.theme.ListOfColors

@Composable
fun ViewStaffItem(){

    Card(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        shape = RoundedCornerShape(10.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                text = "Aliyu",
                modifier = Modifier.padding(start =5.dp)
            )

            Icon(imageVector = Icons.Default.Edit,
                contentDescription = "EditIcon",
                 tint = ListOfColors.green)

            Icon(imageVector = Icons.Default.Delete,
                  contentDescription = "DeleteIcon",
                  tint = ListOfColors.red,
                  modifier = Modifier.padding(end = 5.dp))

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ViewStaffItemPrev(){
    ViewStaffItem()
}