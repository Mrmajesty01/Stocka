package com.example.stocka.HomeScreen
//

sealed class HomeEvent {

    object MakeSalesFb: HomeEvent()
    object SalesBtn: HomeEvent()
    object ExpensesBtn: HomeEvent()
    object ProfitBtn: HomeEvent()
    }
//    data class OnSalesClicked(val sales: Sales): HomeEvent()



