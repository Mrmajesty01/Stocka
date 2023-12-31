package com.example.stocka.Navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors


@Composable
fun FabCompose(
    navController:NavController,dest:Destination
){

    var extendedState by remember{
        mutableStateOf(false)
    }

    val rotationalState by animateFloatAsState(
        targetValue = if (extendedState) 0f else 90f, label = ""
    )


    FloatingActionButton(
        onClick = {},
        backgroundColor = ListOfColors.orange,
        contentColor = ListOfColors.black
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
                    extendedState = !extendedState

                },
                modifier = Modifier.rotate(rotationalState)



            ) {
                Icon(Icons.Default.Add,contentDescription = "addSales")
            }

            if(extendedState){
                Text(
                    "Make sells",
                    Modifier
                        .clickable {
                            navigateTo(navController,dest)
                        }
                        .padding(end = 20.dp)
                )
            }


        }

    }
}