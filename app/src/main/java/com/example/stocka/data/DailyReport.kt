package com.example.stocka.data

data class DailyReport(
    val date:Long? = null,
    val userId:String? = null,
    val totalSalesToday:String? = null,
    val totalProfitToday:String? = null,
    val salesReceiptTotal:String? = null,
    val creditReceiptTotal:String? = null,
    val totalExpensesToday:String? = null,
    val profitAfterExpense:String? = null,
    val mostSoldGood:String? = null,
    val mostSoldGoodQty:String? = null,
    var goodsSold:String? = null,
)
