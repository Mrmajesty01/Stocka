package com.example.stocka

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stocka.AccountSecurity.AccountSecurityScreen
import com.example.stocka.AddCredit.AddCreditScreen
import com.example.stocka.AddCustomerScreen.AddCustomerScreen
import com.example.stocka.AddExpenseScreen.AddExpenseScreen
import com.example.stocka.AddStockScreen.AddStockScreen
import com.example.stocka.AddToStock.AddToStockScreen
import com.example.stocka.BottomSheet.BSheet
import com.example.stocka.BusinessDetail.BusinessDetailsScreen
import com.example.stocka.BusinessDetail.EditBusinessDetailsScreen
import com.example.stocka.ContactUs.ContactUsScreen
import com.example.stocka.CreditInfoScreen.CreditInfoHomeScreen
import com.example.stocka.CreditInfoScreen.CreditInfoScreen
import com.example.stocka.CustomerHistoryScreen.CustomerHistoryScreen
import com.example.stocka.CustomerInfoScreen.CustomerInfoScreen
import com.example.stocka.CustomerScreen.CustomersScreen
import com.example.stocka.CustomerStockSearch.CustomerSearch
import com.example.stocka.CustomerStockSearch.StockSearch
import com.example.stocka.DailyReportScreen.DailyReportScreen
import com.example.stocka.EditCustomerScreen.EditCustomerInfoScreen
import com.example.stocka.EditExpenseScreen.EditExpenseScreen
import com.example.stocka.EditSalesScreen.EditSalesScreen
import com.example.stocka.EditStockScreen.EditStockInfoScreen
import com.example.stocka.ExpenseInfoScreen.ExpenseInfoScreen
import com.example.stocka.ExpenseScreen.ExpenseScreen
import com.example.stocka.GenerateCreditReceipt.GenerateCreditReceiptScreen
import com.example.stocka.GenerateReceiptScreen.GenerateReceiptScreen
import com.example.stocka.HomeScreen.HomeScreen
import com.example.stocka.InvoiceScreen.InvoiceScreen
import com.example.stocka.LoginScreen.LoginScreen
import com.example.stocka.MakeCreditScreen.MakeCreditScreen
import com.example.stocka.MakeSalesScreen.AddSalesScreen
import com.example.stocka.MakeSalesScreen.MakeSalesScreen
import com.example.stocka.Navigation.Destination
import com.example.stocka.PayCredit.PayCreditScreen
import com.example.stocka.PersonalDetail.EditPersonalDetailsScreen
import com.example.stocka.PersonalDetail.PersonalDetailsScreen
import com.example.stocka.RegistrationScreen.RegistrationScreen
import com.example.stocka.SalesInfoScreen.SalesInfoHome
import com.example.stocka.SalesInfoScreen.SalesInfoScreen
import com.example.stocka.SettingScreen.SettingsScreen
import com.example.stocka.StockHistoryScreen.StockHistoryScreen
import com.example.stocka.StockInfo.StockInfoScreen
import com.example.stocka.StockScreen.StockScreen
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.Viemodel.SalesViewModel
import com.example.stocka.main.NotificationMessage
import com.example.stocka.ui.theme.StockaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity @Inject constructor(): ComponentActivity(), NotificationHandler {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()

        setContent {
            StockaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Stocka()
                    }
                }
            }
        }

    override fun showNotification(content:String, title:String){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext,"channel_id")
            .setContentText(content)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.shop)
            .build()
        notificationManager.notify(1, notification)
    }


//  fun requestBluetoothNotification(){
//      if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
//          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, MainActivity.PERMISSION_BLUETOOTH);
//      } else if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
//          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, MainActivity.PERMISSION_BLUETOOTH_ADMIN);
//      } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, MainActivity.PERMISSION_BLUETOOTH_CONNECT);
//      } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, MainActivity.PERMISSION_BLUETOOTH_SCAN);
//      } else {
//          // Your code HERE
//      }
//  }



    private fun requestNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if(!hasPermission){
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }



    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Stocka(){
        val navController = rememberNavController()
        val authViewModel : AuthViewModel = hiltViewModel()


        val salesViewModel:SalesViewModel = hiltViewModel()
//        val customerViewModel: CustomerViewModel = hiltViewModel()
//        val stockViewModel: StockViewModel = hiltViewModel()
        NotificationMessage(vm = authViewModel)
//        NotificationMessage(vm = customerViewModel)
//        NotificationMessage(vm = stockViewModel)
        NavHost(navController = navController, startDestination = Destination.Login.routes ){

            composable(Destination.SignUp.routes){
                RegistrationScreen(navController,authViewModel)
            }


            composable(Destination.Login.routes){
                LoginScreen(navController,authViewModel)
            }


            composable(Destination.Home.routes){
                HomeScreen(navController,authViewModel)
            }

            composable(Destination.Customers.routes){
                CustomersScreen(navController,authViewModel)
            }

            composable(Destination.Stocks.routes){
                StockScreen(navController,authViewModel)
            }

            composable(Destination.AddExpense.routes){
                AddExpenseScreen(navController,authViewModel)
            }

            composable(Destination.BottomSheet.routes){
                BSheet(navController,authViewModel)
            }

            composable(Destination.Invoices.routes){
                InvoiceScreen(navController,authViewModel)
            }

            composable(Destination.Settings.routes){
                SettingsScreen(navController,authViewModel)
            }

            composable(Destination.AddSale.routes){
                AddSalesScreen(navController,authViewModel)
            }


            composable(Destination.SalesInfo.routes){
                SalesInfoScreen(navController, authViewModel)
            }

            composable(Destination.MakeSales.routes){
                MakeSalesScreen(navController, authViewModel)
            }

            composable(Destination.SalesInfoHome.routes){
                SalesInfoHome(navController = navController, viewModel = authViewModel)
            }


            composable(Destination.AddCustomer.routes){
                AddCustomerScreen(navController,authViewModel)
            }


            composable(Destination.AddStock.routes){
                AddStockScreen(navController,authViewModel)
            }

            composable(Destination.SearchCustomer.routes){
                CustomerSearch(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.SearchStock.routes){
                StockSearch(navController = navController, viewModel = authViewModel)
            }


            composable(Destination.StockInfo.routes){
                    StockInfoScreen(navController = navController, viewModel = authViewModel)
            }


            composable(Destination.StockHistory.routes){
                StockHistoryScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.EditStock.routes){
                EditStockInfoScreen(navController = navController, viewModel =  authViewModel)
            }

            composable(Destination.CustomerInfo.routes){
                    CustomerInfoScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.EditCustomer.routes){
                EditCustomerInfoScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.CustomerHistory.routes){
                CustomerHistoryScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.ExpenseInfo.routes){
                    ExpenseInfoScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.EditSales.routes){
                EditSalesScreen(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.MakeCredit.routes){
                MakeCreditScreen(navController, authViewModel)
            }

            composable(Destination.AddCredit.routes){
                AddCreditScreen(navController, authViewModel)
            }

            composable(Destination.CreditInfo.routes){
                CreditInfoScreen(navController, authViewModel)
            }

            composable(Destination.CreditInfoHome.routes){
                CreditInfoHomeScreen(navController, authViewModel)
            }

            composable(Destination.PayCredit.routes){
                PayCreditScreen(navController,authViewModel)
            }

            composable(Destination.AccountSecurity.routes){
                AccountSecurityScreen()
            }

            composable(Destination.ContactUs.routes){
                ContactUsScreen(navController)
            }

            composable(Destination.PersonalDetail.routes){
                PersonalDetailsScreen(navController, authViewModel)
            }

            composable(Destination.EditPersonalDetail.routes){
                EditPersonalDetailsScreen(navController, authViewModel)
            }

            composable(Destination.BusinessDetails.routes){
                BusinessDetailsScreen(navController, authViewModel)
            }

            composable(Destination.EditBusinessDetail.routes){
                EditBusinessDetailsScreen(navController, authViewModel)
            }

            composable(Destination.SalesReceipt.routes){
                GenerateReceiptScreen(navController, authViewModel)
            }

            composable(Destination.CreditReceipt.routes){
                GenerateCreditReceiptScreen(navController, authViewModel)
            }

            composable(Destination.ExpenseScreen.routes){
                ExpenseScreen(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.DailyReport.routes){
                DailyReportScreen(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.EditExpense.routes){
                EditExpenseScreen(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.AddToStock.routes){
                AddToStockScreen(navController = navController, viewModel = authViewModel )
            }

            }


        }
    }



