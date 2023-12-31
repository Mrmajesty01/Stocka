package com.example.stocka.SettingScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.BottomNavItem
import com.example.stocka.Navigation.BottomNavMenu
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController:NavController,viewModel: AuthViewModel){

    var isLoading = viewModel.inProgress.value

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp)
            ){
                Text(
                    text = "Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier.weight(1f)
                    .padding(top = 20.dp, start = 20.dp)
            ){

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Person,
                        contentDescription = "personIcon",
                        tint = ListOfColors.lightBlue
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Personal Details",
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Divider(thickness = 1.dp, color = ListOfColors.orange,
                    modifier = Modifier.padding(start = 30.dp))

                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Storefront,
                        contentDescription = "businessIcon",
                        tint = ListOfColors.orange
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Business Details",
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Divider(thickness = 1.dp, color = ListOfColors.orange,
                    modifier = Modifier.padding(start = 30.dp))

                Spacer(modifier = Modifier.padding(5.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Default.Security,
                        contentDescription ="accountIcon",
                        tint = ListOfColors.lightRed
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Account Security",
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Divider(thickness = 1.dp, color = ListOfColors.orange,
                    modifier = Modifier.padding(start = 30.dp))

                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Default.ReceiptLong,
                        contentDescription ="transactionsIcon",
                        tint = ListOfColors.blue
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Transactions",
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Divider(thickness = 1.dp, color = ListOfColors.orange,
                    modifier = Modifier.padding(start = 30.dp))

                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Icon(imageVector = Icons.Default.DarkMode,
                        contentDescription = "themeIcon",
                        tint = ListOfColors.lightGrey
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Theme",
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))

                Divider(thickness = 1.dp, color = ListOfColors.orange,
                    modifier = Modifier.padding(start = 30.dp))

                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.ManageAccounts,
                        contentDescription = "manageIcon",
                        tint = ListOfColors.mintGreen
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Manage Staff",
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Divider(thickness = 1.dp, color = ListOfColors.orange,
                    modifier = Modifier.padding(start = 30.dp))

                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Default.SupportAgent,
                        contentDescription = "supportIcon",
                        tint = ListOfColors.orange
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Contact Us",
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Divider(thickness = 1.dp, color = ListOfColors.orange,
                    modifier = Modifier.padding(start = 30.dp))


                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            viewModel.logOut(){
                                navController.navigate(Destination.Login.routes)
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Default.Logout,
                        contentDescription = "logoutIcon",
                        tint = ListOfColors.orange
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Logout",
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Divider(thickness = 1.dp, color = ListOfColors.orange,
                    modifier = Modifier.padding(start = 30.dp))



            }




            BottomNavMenu(selectedItem = BottomNavItem.Settings, navController = navController )

        }
       if(isLoading){
           CircularProgressIndicator()
       }
    }
}




//@Preview (showBackground = true)
//@Composable
//fun prevSetting(){
//    SettingsScreen()
//}