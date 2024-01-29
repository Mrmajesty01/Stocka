package com.example.stocka.PersonalDetail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditPersonalDetailsScreen(navController: NavController, viewModel: AuthViewModel){


    var focus = LocalFocusManager.current

    var context = LocalContext.current

    var personalDetail = viewModel.userData.value

    val isLoading = viewModel.inProgress.value

    var name by remember{
        mutableStateOf("")
    }

    var number by remember{
        mutableStateOf("")
    }

    var email by remember{
        mutableStateOf("")
    }



    if(personalDetail!=null){
        name = personalDetail.fullName.toString()
        number = personalDetail.number.toString()
        email = personalDetail.email.toString()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.5f))
                    .clickable {}
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackIcon",
                    modifier = Modifier.padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            if (!isLoading) {
                                navController.popBackStack()
                            }
                        },
                    tint = ListOfColors.black
                )

                Text(
                    text = "Edit Personal Details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)


            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(top = 20.dp)

            ) {


                OutlinedTextField(
                    value = viewModel.userData.value!!.fullName.toString(),
                    modifier = Modifier
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChange = {
                        viewModel.userData.value = viewModel.userData.value!!.copy(fullName = it)
                    },
                    label = { Text(text = "Full Name") },
                    placeholder = { Text(text = "Enter your FullName") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "userIcon",
                            tint = ListOfColors.black
                        )
                    },
                    singleLine = true,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = viewModel.userData.value!!.number.toString(),
                    modifier = Modifier.height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChange = {
                        viewModel.userData.value = viewModel.userData.value!!.copy(number = it)
                    },
                    label = { Text(text = "Phone Number") },
                    placeholder = { Text(text = "Enter your phone number") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "numberIcon",
                            tint = ListOfColors.black
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    enabled = !isLoading

                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = viewModel.userData.value!!.email.toString(),
                    modifier = Modifier.height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChange = {
                        viewModel.userData.value = viewModel.userData.value!!.copy(email = it)
                    },
                    label = { Text(text = "Email Address") },
                    placeholder = { Text(text = "Enter your Email Address") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "emailIcon",
                            tint = ListOfColors.black
                        )
                    },
                    singleLine = true,
                    enabled = !isLoading
                )
                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    onClick = {

                        focus.clearFocus(force = true)

                        if (!isLoading) {
                            if (name.isEmpty() || number.isEmpty() || email.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Fill all the fields to update",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            viewModel.updateUserPersonalData(
                                viewModel.userData.value!!.fullName.toString(),
                                viewModel.userData.value!!.email.toString(),
                                viewModel.userData.value!!.number.toString()
                            ) {
                                navController.navigate(Destination.PersonalDetail.routes)
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(ListOfColors.orange)
                ) {
                    Text(
                        text = "Update"
                    )
                }

            }
        }

        if(isLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
                    .align(Alignment.Center)
            )
        }

    }

}


//@Preview(showBackground = true)
//@Composable
//fun EditPersonalDetailsPrev(){
//    EditPersonalDetailsScreen()
//}