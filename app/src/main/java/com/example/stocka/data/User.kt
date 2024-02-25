package com.example.stocka.data

data class User(
    var userId: String? = null,
    var fullName:String? = null,
    var businessName:String? = null,
    var email:String? = null,
    var password:String? = null,
    var currentDate:Long? = null,
    var saleNo:String? = "0",
    var confirmPassword:String? = null,
    var totalSales:String? = null,
    var totalExpenses:String? = null,
    var totalProfit:String? = null,
    var salesReceiptTotalToday:String? = null,
    var creditReceiptTotalToday:String? = null,
    var mostSoldGoodToday:String? = null,
    var mostSoldGoodQuantity:String? = null,
    var number:String? = null,
    var additionalNumber:String? = null,
    var businessDescription:String? = null,
    var businessAddress:String? = null,
    var businessEmailAddress:String? = null,
    var pin:String? = null

){

    fun toMap() = mapOf(
        "userId" to userId,
        "fullName" to fullName,
        "businessName" to businessName,
        "email" to email,
        "saleNo" to saleNo,
        "password" to password,
        "currentDate" to currentDate,
        "confirmPassword" to confirmPassword,
        "totalSales" to totalSales,
        "totalExpenses" to totalExpenses,
        "totalProfit" to totalProfit,
        "salesReceiptTotalToday" to salesReceiptTotalToday,
        "creditReceiptTotalToday" to creditReceiptTotalToday,
        "mostSoldGoodToday" to mostSoldGoodToday,
        "mostSoldGoodQuantity" to mostSoldGoodQuantity,
        "number" to number,
        "additionalNumber" to additionalNumber,
        "businessDescription" to businessDescription,
        "businessAddress" to businessAddress,
        "businessEmailAddress" to businessEmailAddress,
        "pin" to pin

    )
}