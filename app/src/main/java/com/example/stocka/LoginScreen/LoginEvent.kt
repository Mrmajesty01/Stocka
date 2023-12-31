package com.example.stocka.LoginScreen


sealed class LoginEvent{

    object Login: LoginEvent()

    object GoToSignUp: LoginEvent()


}
