package com.example.stocka.ForgotPassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors

@Composable
fun ForgetPassword(navController:NavController,viewModel:AuthViewModel){

    var email by rememberSaveable {
        mutableStateOf("")
    }

    val focus = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(
            text = "Forgot Password",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.padding(10.dp))

        OutlinedTextField(
            value = email,
            modifier = Modifier.height(60.dp),
            onValueChange = { value -> email = value },
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Enter your email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    tint = ListOfColors.matteBlack,
                    contentDescription = "userIcon"
                )
            },
            singleLine = true
        )

        Spacer(Modifier.padding(10.dp))

        Button(
            onClick = {
                focus.clearFocus(force=true)
                viewModel.resetPassword(email){
                    email = ""
                }

            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
        )
        {
            Text(
                text = "Reset Password",
                fontSize = 15.sp
            )


        }

        Spacer(Modifier.padding(10.dp))


        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.DarkGray)) {
                    append("Go back to ")
                }
                withStyle(style = SpanStyle(color = ListOfColors.orange, fontWeight = FontWeight.Bold)) {
                    append("Login")
                }

            },
            color = Color.DarkGray,
            fontSize = 15.sp,
            modifier = Modifier
                .clickable {
                    navigateTo(navController, Destination.Login)
                }
                .padding(vertical = 12.dp) // Adjust padding as needed
        )




    }
}