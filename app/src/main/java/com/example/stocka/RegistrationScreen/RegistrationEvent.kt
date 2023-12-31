package com.example.stocka.RegistrationScreen



sealed class RegistrationEvent {

    object SignUp: RegistrationEvent()

    object GoToLogin: RegistrationEvent()

}
