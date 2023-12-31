package com.example.stocka.Viemodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.ViewModel
import com.example.stocka.Util.Constants
import com.example.stocka.data.Event
import com.example.stocka.data.Stock
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val fireStore: FirebaseFirestore
): ViewModel(){

    val inProgress = mutableStateOf(false)
    val popupNotification = mutableStateOf<Event<String>?>(null)
    val stockData = mutableStateOf<Stock?>(null)
    val stocks = mutableStateOf<List<Stock>>(listOf())

    init {
//        val currentUser = auth.currentUser
//        currentUser?.uid?.let { uid ->
//            retrieveStocks()
//        }
    }



    fun handleException(exception:Exception? = null, customMessage:String =""){
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if(customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)


    }
}