package com.example.stocka.RegistrationScreen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.CommonProgressSpinner
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors


@Composable
fun RegistrationScreen(navController: NavHostController, viewModel:AuthViewModel){

//    CheckSignedIn(navController = navController, viewModel = viewModel)
    val focus = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var fullName by rememberSaveable {
                mutableStateOf("")
            }
            var businessName by rememberSaveable {
                mutableStateOf("")
            }
            var email by rememberSaveable {
                mutableStateOf("")
            }
            var password by rememberSaveable {
                mutableStateOf("")
            }
            var confirmPassword by rememberSaveable {
                mutableStateOf("")
            }
            var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
            var isPasswordVisibleConfirm by rememberSaveable { mutableStateOf(false) }

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
                textAlign = TextAlign.Center,

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
                text = "Create an Account",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.padding(5.dp))

            OutlinedTextField(
                value = fullName,
                modifier = Modifier.height(60.dp),
                onValueChange = { value -> fullName = value },
                label = { Text(text = "Full Name") },
                placeholder = { Text(text = "Enter your fullname") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "userIcon",
                        tint = ListOfColors.black
                    )
                },
                singleLine = true
            )

            Spacer(Modifier.padding(10.dp))

            OutlinedTextField(
                value = businessName,
                modifier = Modifier.height(60.dp),
                onValueChange = { value -> businessName = value },
                label = { Text(text = "Business Name") },
                placeholder = { Text(text = "Enter your business name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AddBusiness,
                        contentDescription = "businessInfo",
                        tint = ListOfColors.black
                    )
                },
                singleLine = true

            )

            Spacer(Modifier.padding(10.dp))


            OutlinedTextField(
                value = email,
                modifier = Modifier.height(60.dp),
                onValueChange = { value -> email = value },
                label = { Text(text = "Email Address") },
                placeholder = { Text(text = "Enter your Email Address") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "emailIcon",
                        tint = ListOfColors.black
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
                        contentDescription = "pswdIcon",
                        tint = ListOfColors.black
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "HidePassword" else "ShowPassword",
                            tint = ListOfColors.black
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )


            Spacer(Modifier.padding(10.dp))


            OutlinedTextField(
                value = confirmPassword,
                modifier = Modifier.height(60.dp),
                onValueChange = { value -> confirmPassword = value },
                label = { Text(text = "Confirm Password") },
                placeholder = { Text(text = "Enter your password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = "pswdIcon",
                        tint = ListOfColors.black
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { isPasswordVisibleConfirm = !isPasswordVisibleConfirm }
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisibleConfirm) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPasswordVisibleConfirm) "HidePassword" else "ShowPassword",
                            tint = ListOfColors.black
                        )
                    }
                },
                visualTransformation = if (isPasswordVisibleConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            Spacer(Modifier.padding(20.dp))

            Button(
                onClick = {
                    focus.clearFocus(force=true)
                    viewModel.onSignUp(fullName, businessName, email, password, confirmPassword){
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
                    text = "Register",
                    fontSize = 15.sp
                )


            }

            Spacer(Modifier.padding(10.dp))

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.DarkGray)) {
                        append("Already have an account? ")
                    }
                    withStyle(style = SpanStyle(color = ListOfColors.orange, fontWeight = FontWeight.Bold)) {
                        append("Login")
                    }

                },
                fontSize = 15.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        navigateTo(navController, Destination.Login)
                    }
                    .padding(vertical = 12.dp) // Adjust padding as needed
            )


//            Button(
//                onClick = {
//                    navigateTo(navController,Destination.Login)
//                },
//                shape = RoundedCornerShape(10.dp),
//                modifier = Modifier
//                    .fillMaxWidth(0.75f)
//                    .height(50.dp),
//                colors = ButtonDefaults.buttonColors(Color.Transparent)
//            )
//            {
//                Text(
//                    text = "Already have an account ? Login!!!",
//                    fontWeight = FontWeight.ExtraBold,
//                    fontSize = 12.sp,
//                    color = Color.DarkGray,
//                    textAlign = TextAlign.Center
//                )
//            }

        }

        val isLoading = viewModel.inProgress.value

        if (isLoading) {
            CommonProgressSpinner()
        }


    }
}

//@Preview(showBackground = true)
//@Composable
//fun PrevRegister() {
//    RegisterationScreen()
//}