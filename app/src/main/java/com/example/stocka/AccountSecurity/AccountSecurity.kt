package com.example.stocka.AccountSecurity

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountSecurityScreen(
    navController: NavController, viewModel: AuthViewModel
){

    val context = LocalContext.current

    val userData = viewModel.userData.value

    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var openDialogRemove by rememberSaveable {
        mutableStateOf(false)
    }

    var openDialogChange by rememberSaveable {
        mutableStateOf(false)
    }


    var pin by remember { mutableStateOf(TextFieldValue()) }

    var newPin by remember { mutableStateOf("") }
    var confirmNewPin by remember { mutableStateOf("") }


    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },

            title = {
                Text(text = "Add Pin")
            },

            text = {
                Column {
                    Text(text = "Enter a four digit pin to secure your business")
                    Spacer(modifier = Modifier.height(16.dp))

                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if (pin.text.length == 4) {
                        openDialog = false
                       viewModel.addPin(pin.text){
                           navController.popBackStack()
                       }
                    } else {
                        Toast.makeText(context, "pin should be of length 4", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Add")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    openDialog = false
                }) {
                    Text(text = "Cancel")
                }
            },
        )
    }

    if (openDialogRemove) {
        AlertDialog(
            onDismissRequest = { openDialogRemove = false },

            title = {
                Text(text = "Remove Pin")
            },

            text = {
                Column {
                    Text(text = "Are you sure you want to remove pin")
                    Spacer(modifier = Modifier.height(16.dp))

                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN To Remove") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if (pin.text == userData?.pin.toString()) {
                        openDialogRemove = false
                        viewModel.deletePin{
                            navController.popBackStack()
                        }
                    } else {
                        Toast.makeText(context, "pin is incorrect", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Yes")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    openDialogRemove = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }

    if (openDialogChange) {
        AlertDialog(
            onDismissRequest = { openDialogChange = false },

            title = {
                Text(text = "Change Pin")
            },

            text = {
                Column {
                    Text(text = "Enter your current PIN and set a new one.")
                    Spacer(modifier = Modifier.height(16.dp))

                    // Add an EditText for current PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Current PIN") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Add an EditText for new PIN input
                    OutlinedTextField(
                        value = newPin,
                        onValueChange = {
                            newPin = it
                        },
                        label = { Text("New PIN") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Add an EditText to confirm new PIN
                    OutlinedTextField(
                        value = confirmNewPin,
                        onValueChange = {
                            confirmNewPin = it
                        },
                        label = { Text("Confirm New PIN") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if (pin.text == userData?.pin.toString()) {
                        if (newPin == confirmNewPin) {
                            openDialogChange = false
                            viewModel.changePin(newPin) {
                                navController.popBackStack()
                            }
                        } else {
                            Toast.makeText(context, "New PINs do not match", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(context, "Current PIN is incorrect", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Change Pin")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    openDialogChange = false
                }) {
                    Text(text = "Cancel")
                }
            },
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(15.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = ListOfColors.black
            )


            Text(
                text = "Account Security",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }

        Divider(thickness = 1.dp, color = ListOfColors.lightGrey)


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {


            Button(
                onClick = {
                    if(userData?.pin!!.isEmpty()) {
                        openDialog = true
                    }
                    else{
                        Toast.makeText(context,"Pin already exist",Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange)
            ) {

                Text(
                    text = "Add Pin",
                    color = ListOfColors.black
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    openDialogChange = true
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange)
            ) {

                Text(
                    text = "Change Pin",
                    color = ListOfColors.black
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    openDialogRemove = true
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange)
            ) {

                Text(
                    text = "Remove Pin",
                    color = ListOfColors.black
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    navController.navigate(Destination.FeaturesToPassword.routes)
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange)
            ) {

                Text(
                    text = "Select Features to Password ",
                    color = ListOfColors.black
                )
            }
        }
    }

}




//@Preview (showBackground = true)
//@Composable
//fun AccountSecurityPrev(){
//    AccountSecurityScreen()
//}

