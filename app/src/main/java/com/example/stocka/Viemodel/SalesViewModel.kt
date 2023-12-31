package com.example.stocka.Viemodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.stocka.Util.Constants
import com.example.stocka.data.Event
import com.example.stocka.data.Sales
import com.example.stocka.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    val auth:FirebaseAuth,
    val fireStore: FirebaseFirestore
): ViewModel(){

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val userData = mutableStateOf<User?>(null)
    val salesDetail = mutableStateOf<Sales?>(null)
    val popupNotification = mutableStateOf<Event<String>?>(null)
    val refreshSalesProgress = mutableStateOf(false)
    val salesData = mutableStateOf<List<Sales>>(listOf())

//    init {
//        val currentUser = auth.currentUser
//        signedIn.value = currentUser!=null
//        currentUser?.uid?.let {uid->
//            refreshSales()
//        }
//
//    }




    fun logOut(){
        auth.signOut()
        signedIn.value = false
        userData.value = null
    }

    fun handleException(exception:Exception? = null, customMessage:String =""){
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if(customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)


    }
}