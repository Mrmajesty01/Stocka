package com.example.stocka

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stocka.AddCustomerScreen.AddCustomerScreen
import com.example.stocka.AddExpenseScreen.AddExpenseScreen
import com.example.stocka.AddStockScreen.AddStockScreen
import com.example.stocka.BottomSheet.BSheet
import com.example.stocka.CustomerInfoScreen.CustomerInfoScreen
import com.example.stocka.CustomerScreen.CustomersScreen
import com.example.stocka.CustomerStockSearch.CustomerSearch
import com.example.stocka.CustomerStockSearch.StockSearch
import com.example.stocka.EditSalesScreen.EditSalesScreen
import com.example.stocka.EditSalesScreen.EditTrial
import com.example.stocka.ExpenseInfoScreen.ExpenseInfoScreen
import com.example.stocka.HomeScreen.HomeScreen
import com.example.stocka.InvoiceScreen.InvoiceScreen
import com.example.stocka.LoginScreen.LoginScreen
import com.example.stocka.MakeSalesScreen.MakeSalesScreen
import com.example.stocka.Navigation.Destination
import com.example.stocka.RegistrationScreen.RegistrationScreen
import com.example.stocka.SalesInfoScreen.SalesInfoHome
import com.example.stocka.SalesInfoScreen.SalesInfoScreen
import com.example.stocka.SettingScreen.SettingsScreen
import com.example.stocka.StockInfoTopBar.StockInfoScreen
import com.example.stocka.StockScreen.StockScreen
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.Viemodel.SalesViewModel
import com.example.stocka.data.Customer
import com.example.stocka.data.Expense
import com.example.stocka.data.Sales
import com.example.stocka.data.SingleSale
import com.example.stocka.data.Stock
import com.example.stocka.main.NotificationMessage
import com.example.stocka.ui.theme.StockaTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                BSheet(navController)
            }

            composable(Destination.Invoices.routes){
                InvoiceScreen(navController,authViewModel)
            }

            composable(Destination.Settings.routes){
                SettingsScreen(navController,authViewModel)
            }

            composable(Destination.MakeSales.routes){
                MakeSalesScreen(navController,authViewModel)
            }


            composable(Destination.SalesInfo.routes){
                SalesInfoScreen(navController, authViewModel)
            }

            composable(Destination.SalesInfoHome.routes){
                val salesData = navController
                    .previousBackStackEntry
                    ?.arguments
                    ?.getParcelable<Sales>("sales")
                salesData.let {
                    SalesInfoHome(
                        navController = navController,
                        viewModel = authViewModel,
                        sales = salesData)
                }
            }

            composable(Destination.AddCustomer.routes){
                AddCustomerScreen(navController,authViewModel)
            }


            composable(Destination.AddStock.routes){
                AddStockScreen(navController,authViewModel)
            }

            composable(Destination.SearchCustomer.routes){
                CustomerSearch(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.SearchStock.routes){
                StockSearch(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.EditSalesTrial.routes){
                EditTrial(authViewModel)
            }

            composable(Destination.StockInfo.routes){
                val stockData = navController
                    .previousBackStackEntry
                    ?.arguments
                    ?.getParcelable<Stock>("stock")
                stockData.let {
                    StockInfoScreen(
                        navController = navController,
                        viewModel = authViewModel,
                        stock = stockData
                    )
                }
            }

            composable(Destination.CustomerInfo.routes){
                val customerData = navController
                    .previousBackStackEntry
                    ?.arguments
                    ?.getParcelable<Customer>("customer")
                customerData.let {
                    CustomerInfoScreen(
                        navController = navController,
                        viewModel = authViewModel,
                        customer = customerData
                    )
                }
            }

            composable(Destination.ExpenseInfo.routes){
                val expenseData = navController
                    .previousBackStackEntry
                    ?.arguments
                    ?.getParcelable<Expense>("expense")
                expenseData.let {
                    ExpenseInfoScreen(
                        navController = navController,
                        viewModel = authViewModel ,
                        expense = expenseData
                    )
                }
            }

            composable(Destination.EditSales.routes){
                val salesData = navController
                    .previousBackStackEntry
                    ?.arguments
                    ?.getParcelable<SingleSale>("sale")
                salesData.let {
                    EditSalesScreen(
                        navController = navController,
                        viewModel = authViewModel,
                        singleSale = salesData
                    )
                }
            }


            }


        }
    }



