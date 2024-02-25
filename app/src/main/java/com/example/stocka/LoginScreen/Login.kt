package com.example.stocka.LoginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stocka.Navigation.Destination
import com.example.stocka.R
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.CommonProgressSpinner
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors


@Composable
fun LoginScreen(navController: NavHostController, vieModel:AuthViewModel) {
//    CheckSignedIn(navController = navController, viewModel = vieModel)
    val focus = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var email by rememberSaveable {
                mutableStateOf("")
            }
            var password by rememberSaveable {
                mutableStateOf("")
            }

            Image(
                painter = painterResource(id = R.drawable.stockaimg), contentDescription = "logo",
                modifier = Modifier
                    .padding(10.dp)
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = ListOfColors.lightBlue)) {
                        append("Sto")
                    }
                    withStyle(style = SpanStyle(color = ListOfColors.orange)) {
                        append("cka")
                    }

                },
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center

            )

            Text(
                text = "Making your business thrive...",
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )


            Spacer(Modifier.padding(10.dp))

            Text(
                text = "Login",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.padding(5.dp))

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

            OutlinedTextField(
                value = password,
                modifier = Modifier.height(60.dp),
                onValueChange = { value -> password = value },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter your password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = "pswIcon",
                        tint = ListOfColors.matteBlack
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = "seePassword",
                        tint = ListOfColors.black
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true

            )

            Spacer(Modifier.padding(20.dp))

            Button(
                onClick = {
                    focus.clearFocus(force=true)
                    vieModel.login(email,password){
                        navController.navigate(Destination.Home.routes)
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
                    text = "Login",
                    fontSize = 15.sp
                )


            }

            Spacer(Modifier.padding(10.dp))


            Button(
                onClick = {
                    navigateTo(navController, Destination.SignUp)
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            )
            {
                Text(
                    text = "Don't have an account ? Sign Up!!!",
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
            }

        }

        val isLoading = vieModel.inProgress.value
        if(isLoading){
            CommonProgressSpinner()
        }

    }
    
}


//@Preview(showBackground = true)
//@Composable
//fun PrevLogin() {
//    LoginScreen()
//    }