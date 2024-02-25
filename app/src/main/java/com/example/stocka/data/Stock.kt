package com.example.stocka.data

data class Stock(
    var stockId: String = "",
    var userId: String = "",
    var stockName: String = "",
    var stockPurchasePrice: String = "",
    var stockSellingPrice: String = "",
    var stockFixedSellingPrice: String = "",
    var stockTotalPrice: String = "",
    var stockExpiryDate: String = "",
    var stockQuantity: String = "",
    var stockDateAdded: String?= null,
    var stockQuantitySold: String = ""
)