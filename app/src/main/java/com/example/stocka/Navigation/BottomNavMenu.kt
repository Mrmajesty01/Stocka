package com.example.stocka.Navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stocka.main.navigateTo

enum class BottomNavItem(val icon: ImageVector, val navDestination: Destination){
    Home(Icons.Default.Store, Destination.Home),
    Stocks(Icons.Default.Inventory, Destination.Stocks),
    Sheet(Icons.Default.Add,Destination.BottomSheet),
    Customers(Icons.Default.People, Destination.Customers),
    Settings(Icons.Default.Settings, Destination.Settings)
}


@Composable
fun BottomNavMenu(
    selectedItem: BottomNavItem, navController: NavController
){

    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(top = 4.dp)
        .background(Color.White)){

        for(item in BottomNavItem.values()){
            Image(
                imageVector = item.icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
                    .weight(1f)
                    .padding(5.dp)
                    .clickable {
                        navigateTo(navController, item.navDestination)
                    },
                    colorFilter = if(item==selectedItem) ColorFilter.tint(Color.Black)
                    else ColorFilter.tint(Color.Gray)
            )
        }

    }

}