package com.example.stocka.SettingScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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

    var openDialogEdit by rememberSaveable {
        mutableStateOf(false)
    }
    var pin by remember { mutableStateOf(TextFieldValue()) }

    val userData = viewModel.userData.value

    val context = LocalContext.current

    if(openDialogEdit){
        AlertDialog(
            onDismissRequest = { openDialogEdit = false },

            title = {
                Text(text = "Enter Pin")
            },

            text = {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN TO VIEW") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if(pin.text.toInt() == userData?.pin?.toInt()) {
                        viewModel.getTotalSaleReceiptToday()
                        viewModel.getTotalCreditReceiptToday()
                        viewModel.getMostPurchasedProductsToday()
                        openDialogEdit = false
                        navController.navigate(Destination.DailyReport.routes)
                    }
                    else{
                        Toast.makeText(context, "wrong pin try again", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Yes")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    openDialogEdit = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "Settings",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 20.dp, start = 20.dp)
                ) {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Destination.PersonalDetail.routes)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
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

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Destination.BusinessDetails.routes)
                            },
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

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Destination.AccountSecurity.routes)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "accountIcon",
                            tint = ListOfColors.lightRed
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Account Security",
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                       viewModel.pay()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.BarChart,
                            contentDescription = "rankingIcon",
                            tint = ListOfColors.gold
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Ranking",
                            fontSize = 15.sp
                        )
                    }

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Destination.Invoices.routes)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = "invoicesIcon",
                            tint = ListOfColors.blue
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Invoices",
                            fontSize = 15.sp
                        )
                    }

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Destination.ExpenseScreen.routes)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Receipt,
                            contentDescription = "expenseIcon",
                            tint = ListOfColors.red
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Expenses",
                            fontSize = 15.sp
                        )
                    }

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )



                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                openDialogEdit = true
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "infoIcon",
                            tint = ListOfColors.lightRed
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Daily Report",
                            fontSize = 15.sp
                        )
                    }

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )



                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Insights,
                            contentDescription = "insightIcon",
                            tint = ListOfColors.lightRed
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Insights",
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.DarkMode,
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

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ManageAccounts,
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

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Destination.ContactUs.routes)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.SupportAgent,
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

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )


                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.logOut() {
                                    navController.navigate(Destination.Login.routes){
                                        popUpTo(Destination.Home.routes){
                                            inclusive = true
                                        }
                                    }
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "logoutIcon",
                            tint = ListOfColors.red
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Logout",
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    Divider(
                        thickness = 1.dp, color = ListOfColors.orange,
                        modifier = Modifier.padding(start = 30.dp)
                    )


                }

            BottomNavMenu(selectedItem = BottomNavItem.Settings, navController = navController)

            }
            if (isLoading) {
                CircularProgressIndicator()
            }
    }
}




//@Preview (showBackground = true)
//@Composable
//fun prevSetting(){
//    SettingsScreen()
//}