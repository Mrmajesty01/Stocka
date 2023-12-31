package com.example.stocka.Navigation

sealed class Destination(val routes:String){

    object SignUp:Destination("signup")
    object Login:Destination("login")
    object Home:Destination("home")
    object Stocks:Destination("stocks")
    object Customers:Destination("customers")
    object Invoices:Destination("invoices")
    object Settings:Destination("settings")
    object MakeSales:Destination("makeSales")
    object SalesInfo:Destination("salesInfo")
    object SalesInfoHome:Destination("salesInfoHome")
    object AddCustomer:Destination("addCustomer")
    object AddStock:Destination("addStock")
    object AddExpense:Destination("addExpense")
    object BottomSheet:Destination("sheet")
    object CustomerInfo:Destination("customerInfo")
    object StockInfo:Destination("stockInfo")
    object SearchStock:Destination("searchStock")
    object SearchCustomer:Destination("searchCustomer")
    object ExpenseInfo:Destination("expenseInfo")
    object EditSales:Destination("editSales")
    object EditSalesTrial:Destination("editTrial")
}