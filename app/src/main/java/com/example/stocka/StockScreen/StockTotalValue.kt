package com.example.stocka.StockScreen


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.HomeScreen.formatNumberWithDelimiter
import com.example.stocka.Viemodel.AuthViewModel

@Composable
fun TotalStockValue(
    amount: Double,
    viewModel: AuthViewModel
){

    val features = viewModel.featuresToPin.value

    val context = LocalContext.current
    val userData = viewModel.userData.value

    var totalStockValueHidden by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(features){
        totalStockValueHidden = viewModel.featuresToPin.value.StockTotalValue
    }

    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var pin by remember { mutableStateOf(TextFieldValue()) }

    if(openDialog){
        AlertDialog(
            onDismissRequest = {
                openDialog = false
                pin = TextFieldValue("")
            },

            title = {
                Text(text = "View Total Stock Value")
            },

            text = {
                Column {
                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN To View Total Stock Value") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if(pin.text.toInt() == userData?.pin?.toInt()) {
                        openDialog = false
                        totalStockValueHidden = false
                        pin = TextFieldValue("")
                    }
                    else{
                        Toast.makeText(context, "wrong pin try again", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Yes")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    openDialog = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }

    val totalValue = amount
    val formattedAmount = formatNumberWithDelimiter(totalValue)
    Card(
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth()
            .height(50.dp)
            .clickable {

                if (totalStockValueHidden) {
                    openDialog = true
                }

                else {
                    totalStockValueHidden = true
                }
            },
        shape = RoundedCornerShape(15.dp)

    ) {
        Box(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp)
        ) {


            Text(
                text ="Total Stock Value",
                modifier = Modifier.align(Alignment.TopCenter),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = if(totalStockValueHidden) "*****" else formattedAmount,
                modifier = Modifier.align(Alignment.BottomCenter),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }

}



//@Preview
//@Composable
//fun PrevTotalStockValue(){
//    TotalStockValue()
//}