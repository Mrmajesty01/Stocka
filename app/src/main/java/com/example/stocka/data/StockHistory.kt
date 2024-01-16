package com.example.stocka.data

data class StockHistory(
    val stockId:String?=null,
    val userId:String?=null,
    val stockName: String?=null,
    val stockPurchasePrice:String?=null,
    val stockSellingPrice:String?=null,
    val stockQuantityAdded:String?=null,
    val stockDateAdded: Long? =null,
    val stockExpiryDate:String?=null
)
