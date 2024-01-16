package com.example.stocka.BusinessDetail

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
fun BusinessDetailsScreen(navController: NavController, viewModel: AuthViewModel){

    val userData = viewModel.userData.value

    val number = userData!!.number.toString()
    val addNumber = userData!!.additionalNumber.toString()
    val businessEmail = userData!!.businessEmailAddress.toString()
    val businessDescription = userData!!.businessDescription.toString()
    val businessAddress = userData!!.businessAddress.toString()

    Column(
        modifier = Modifier
            .fillMaxSize()

    ){

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
                text = "Business Details",
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
                    text = "Business Name",
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
                    text = userData.businessName.toString(),
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
                    text = "Business Email Address",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart)
                )


                Text(
                    text = "Additional Number",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopEnd)
                )


                Text(
                    text = (if(businessEmail == null) null else businessEmail).toString(),
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = (if(addNumber==null) null else addNumber).toString(),
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
                    text = "Business Description",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopCenter)
                )


                Text(
                    text = (if(businessDescription==null) null else businessDescription).toString(),
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )


            }

            Spacer(modifier = Modifier.padding(5.dp))


            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(45.dp)
            ) {


                Text(
                    text = "Business Address",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopCenter)
                )


                Text(
                    text = (if(businessAddress==null) null else businessAddress).toString(),
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Button(
                onClick = {
                      navController.navigate(Destination.EditBusinessDetail.routes)
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
//fun BusinessDetailsPrev(){
//    BusinessDetailsScreen()
//}