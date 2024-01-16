package com.example.stocka.data

data class User(
    var userId: String? = null,
    var fullName:String? = null,
    var businessName:String? = null,
    var email:String? = null,
    var password:String? = null,
    var saleNo:String? = "0",
    var confirmPassword:String? = null,
    var totalSales:String? = null,
    var totalExpenses:String? = null,
    var totalProfit:String? = null,
    var number:String? = null,
    var additionalNumber:String? = null,
    var businessDescription:String? = null,
    var businessAddress:String? = null,
    var businessEmailAddress:String? = null

){

    fun toMap() = mapOf(
        "userId" to userId,
        "fullName" to fullName,
        "businessName" to businessName,
        "email" to email,
        "saleNo" to saleNo,
        "password" to password,
        "confirmPassword" to confirmPassword,
        "totalSales" to totalSales,
        "totalExpenses" to totalExpenses,
        "totalProfit" to totalProfit,
        "number" to number,
        "additionalNumber" to additionalNumber,
        "businessDescription" to businessDescription,
        "businessAddress" to businessAddress,
        "businessEmailAddress" to businessEmailAddress

    )
}