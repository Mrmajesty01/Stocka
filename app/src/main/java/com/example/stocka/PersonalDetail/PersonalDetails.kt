package com.example.stocka.PersonalDetail

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonalDetailsScreen(navController: NavController, viewModel: AuthViewModel) {
    
    var userData = viewModel.userData.value

    var number = userData!!.number.toString()


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(imageVector = Icons.Default.ArrowBackIos ,
                contentDescription ="BackIcon",
                modifier = Modifier.padding(start = 5.dp)
                    .size(15.dp)
                    .clickable {
                       navController.navigate(Destination.Settings.routes)
                    },
                tint = ListOfColors.black
            )

            Text(
                text = "Personal Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(all = 20.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(45.dp)
            ) {

                Text(
                    text = "Name",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart)
                )


                Text(
                    text = "Number",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopEnd)
                )


                Text(
                    text = userData!!.fullName.toString(),
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = (if(number==null) null else number).toString(),
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )

            }

            Spacer(modifier = Modifier.padding(5.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(45.dp)
            ) {

                Text(
                    text = "Email Address",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopCenter)
                )


                Text(
                    text = userData.email.toString(),
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Button(
                onClick = {
                    viewModel.retrieveUser()
                    navController.navigate(Destination.EditPersonalDetail.routes)
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange)
            ) {
                Text(
                    text = "Edit"
                )
            }


        }
    }

}



//@Preview (showBackground = true)
//@Composable
//fun PersonalDetailsPrev(){
//    PersonalDetailsScreen()
//}