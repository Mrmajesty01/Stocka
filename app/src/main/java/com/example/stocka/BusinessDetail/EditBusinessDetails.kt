package com.example.stocka.BusinessDetail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun EditBusinessDetailsScreen(navController: NavController, viewModel: AuthViewModel){

    var businessName by remember{
        mutableStateOf("")
    }

    var businessAddress by remember{
        mutableStateOf("")
    }

    var businessEmail by remember{
        mutableStateOf("")
    }

    var businessDescription by remember{
        mutableStateOf("")
    }

    var businessNumber by remember{
        mutableStateOf("")
    }
    var businessAddNumber by remember{
        mutableStateOf("")
    }

    var focus = LocalFocusManager.current
    var context = LocalContext.current
    val isLoading = viewModel.inProgress.value

    var businessDetails = viewModel.userData.value
    
    if(businessDetails!=null){
        businessName = businessDetails.businessName.toString()
        businessAddress = businessDetails.businessAddress.toString()
        businessEmail = businessDetails.businessEmailAddress.toString()
        businessDescription = businessDetails.businessDescription.toString()
        businessNumber = businessDetails.number.toString()
        businessAddNumber = businessDetails.additionalNumber.toString()
    }

    Box( modifier = Modifier
        .fillMaxSize()) {

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.5f))
                    .clickable {}
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()

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
                                navController.navigate(Destination.BusinessDetails.routes)
                            }
                        },
                    tint = ListOfColors.black
                )

                Text(
                    text = "Edit Business Details",
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
                    value = viewModel.userData.value!!.businessName.toString(),
                    modifier = Modifier
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChange = {
                        viewModel.userData.value =
                            viewModel.userData.value!!.copy(businessName = it)
                    },
                    label = { Text(text = "Business Name") },
                    placeholder = { Text(text = "Business Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddBusiness,
                            contentDescription = "businessName",
                            tint = ListOfColors.black
                        )
                    },
                    singleLine = true,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(10.dp))


                OutlinedTextField(
                    value = viewModel.userData.value!!.businessDescription.toString(),
                    modifier = Modifier
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChange = {
                        viewModel.userData.value =
                            viewModel.userData.value!!.copy(businessDescription = it)
                    },
                    label = { Text(text = "Business Description") },
                    placeholder = { Text(text = "Business Description") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "businessInfo",
                            tint = ListOfColors.black
                        )
                    },
                    singleLine = false,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.padding(10.dp))



                OutlinedTextField(
                    value = viewModel.userData.value!!.businessAddress.toString(),
                    modifier = Modifier
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChange = {
                        viewModel.userData.value =
                            viewModel.userData.value!!.copy(businessAddress = it)
                    },
                    label = { Text(text = "Business Address") },
                    placeholder = { Text(text = "Business Address") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddLocation,
                            contentDescription = "businessAddress",
                            tint = ListOfColors.black
                        )
                    },
                    singleLine = false,
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
                    value = viewModel.userData.value!!.additionalNumber.toString(),
                    modifier = Modifier.height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChange = {
                        viewModel.userData.value =
                            viewModel.userData.value!!.copy(additionalNumber = it)
                    },
                    label = { Text(text = "Second Phone Number") },
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
                    value = viewModel.userData.value!!.businessEmailAddress.toString(),
                    modifier = Modifier.height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onValueChange = {
                        viewModel.userData.value =
                            viewModel.userData.value!!.copy(businessEmailAddress = it)
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
                            if (businessName.isEmpty() || businessDescription.isEmpty()
                                || businessAddress.isEmpty() || businessNumber.isEmpty()
                                || businessAddNumber.isEmpty() || businessEmail.isEmpty()
                            ) {
                                Toast.makeText(
                                    context,
                                    "fill all the fields to update business details",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            viewModel.updateUserBusinessData(
                                viewModel.userData.value!!.businessName.toString(),
                                viewModel.userData.value!!.businessDescription.toString(),
                                viewModel.userData.value!!.businessAddress.toString(),
                                viewModel.userData.value!!.number.toString(),
                                viewModel.userData.value!!.additionalNumber.toString(),
                                viewModel.userData.value!!.businessEmailAddress.toString()
                            ) {
                                navController.navigate(Destination.BusinessDetails.routes)
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
//fun EditBusinessDetailsPrev(){
//    EditBusinessDetailsScreen()
//}