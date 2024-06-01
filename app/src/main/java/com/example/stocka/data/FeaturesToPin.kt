package com.example.stocka.data

data class FeaturesToPin(
    var TotalSales:Boolean = false,
    val TotalExpenses:Boolean = false,
    val TotalProfit:Boolean = false,
    val DailyReport:Boolean = false,
    val StockTotalValue:Boolean = false,
    val TotalAmountOwingCustomers:Boolean = false,
    val EditStocks:Boolean = false,
    val EditCustomers:Boolean = false,
    val DeleteStocks:Boolean = false,
    val DeleteCustomers:Boolean = false,
    val EditSales:Boolean = false,
    val DeleteSales:Boolean = false,
    val PayCredit:Boolean = false,
)
