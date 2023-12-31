package com.example.stocka.InvoiceScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stocka.Navigation.BottomNavItem
import com.example.stocka.Navigation.BottomNavMenu
import com.example.stocka.Screens.CustomerSalesItem
import com.example.stocka.Viemodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InvoiceScreen(navController:NavController,viewModel: AuthViewModel){

    Scaffold(
        topBar = { InvoiceTopBar()}
    ) {

        var text by rememberSaveable {
            mutableStateOf("")
        }

        var active by rememberSaveable {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp)
        ){
            Column(
                modifier = Modifier.weight(1f)
            ){
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 5.dp),
                    query = text,
                    onQueryChange = {
                        text = it
                    },
                    onSearch = {
                        active = false
                    },
                    active = active,
                    onActiveChange = {
                        active = it
                    },
                    placeholder = {
                        Text(
                            text = "Search for invoice"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "SearchIcon"
                        )
                    },
                    trailingIcon = {
                        if (active) {

                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "CancelIcon",
                                modifier = Modifier.clickable {
                                    if (text.isNotEmpty()) {
                                        text = ""
                                    }
                                }
                            )

                        } else {
                            active = false
                        }
                    }


                ) {

                }
                Spacer(modifier = Modifier.padding(7.dp))
                CustomerSalesItem()
                Spacer(modifier = Modifier.padding(7.dp))
                CustomerSalesItem()
            }
//            BottomNavMenu(selectedItem = BottomNavItem.Invoices, navController = navController)
        }

    }
}


//@Preview (showBackground = true)
//@Composable
//fun InvoicePrev(){
//    InvoiceScreen()
//}