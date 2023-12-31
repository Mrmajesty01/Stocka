package com.example.stocka.data

data class User(
    var userId: String? = null,
    var fullName:String? = null,
    var businessName:String? = null,
    var email:String? = null,
    var password:String? = null,
    var confirmPassword:String? = null,
    var totalSales:String? = null,
    var totalExpenses:String? = null,
    var totalProfit:String? = null
){

    fun toMap() = mapOf(
        "userId" to userId,
        "fullName" to fullName,
        "businessName" to businessName,
        "email" to email,
        "password" to password,
        "confirmPassword" to confirmPassword,
        "totalSales" to totalSales,
        "totalExpenses" to totalExpenses,
        "totalProfit" to totalProfit
    )
}