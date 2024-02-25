package com.example.stocka.main


import android.os.Parcelable
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.Viemodel.CustomerViewModel
import com.example.stocka.Viemodel.StockViewModel

@Composable
fun NotificationMessage(vm:AuthViewModel){
    val notifState = vm.popupNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if(notifMessage!=null){
        Toast.makeText(LocalContext.current,notifMessage, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun NotificationMessage(vm: StockViewModel){
    val notifState = vm.popupNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if(notifMessage!=null){
        Toast.makeText(LocalContext.current,notifMessage, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun NotificationMessage(vm: CustomerViewModel){
    val notifState = vm.popupNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if(notifMessage!=null){
        Toast.makeText(LocalContext.current,notifMessage, Toast.LENGTH_LONG).show()
    }

}

@Composable
fun CommonProgressSpinner(){

    Row(
        Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) { }
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        CircularProgressIndicator()
    }

}


data class NavPram(
    val name:String,
    val value: Parcelable
)

fun navigateTo(navController: NavController,dest:Destination, vararg params: NavPram){
    for(param in params){
        navController.currentBackStackEntry?.arguments?.putParcelable(param.name,param.value)
    }
    navController.navigate(dest.routes){
        popUpTo(dest.routes)
        launchSingleTop = true
    }

}

//@Composable
//fun CheckSignedIn(navController: NavController, viewModel:AuthViewModel){
//    val alreadySignedIn = remember { mutableStateOf(false) }
//    val signedIn = viewModel.signedIn.value
//    if(signedIn && !alreadySignedIn.value){
//        alreadySignedIn.value = true
//        navController.navigate(Destination.Home.routes){
//            popUpTo(0)
//        }
//    }
//
//}