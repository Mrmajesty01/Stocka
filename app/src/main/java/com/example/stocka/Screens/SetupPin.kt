package com.example.stocka.Screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocka.ui.theme.ListOfColors


@Composable
fun SetupPinScreen(){

    var pin by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back, Muhammad",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = "Enter your 4 digit pin",
            fontWeight = FontWeight.Light,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(10.dp))

        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = pin,
            onValueChange = { value ->
                pin = value
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(4) { index ->
                        val char = when {
                            index >= pin.length -> ""
                            else -> pin[index].toString()
                        }
                        val isFocused = pin.length == index

                        Text(
                            modifier = Modifier
                                .width(40.dp)
                                .border(
                                    if (isFocused) 2.dp
                                    else 1.dp,
                                    if (isFocused) Color.DarkGray
                                    else Color.LightGray,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(2.dp),
                            text = char,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                    }
                }
            }
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "1",

                    )
            }


            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "2",

                    )
            }



            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "3",

                    )
            }

        }

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "4",

                    )
            }


            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "5",

                    )
            }


            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "6",

                    )
            }

        }

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "7",

                    )
            }


            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "8",

                    )
            }



            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "9",

                    )
            }

        }

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "SignOut",
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(ListOfColors.yellow),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "0",

                    )
            }


            Text(
                text = "Delete",
                fontWeight = FontWeight.Bold
            )


        }


    }

}

@Preview(showBackground = true)
@Composable
fun prevSetupPin(){
    SetupPinScreen()
}