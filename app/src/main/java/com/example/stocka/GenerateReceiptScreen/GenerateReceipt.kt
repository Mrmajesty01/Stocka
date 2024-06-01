package com.example.stocka.GenerateReceiptScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.HomeScreen.formatNumberWithDelimiter
import com.example.stocka.PrintCallBackListener
import com.example.stocka.SalesInfoScreen.SalesItemsDetails
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors
import java.text.SimpleDateFormat
import java.util.Date


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GenerateReceiptScreen(navController: NavController, viewModel: AuthViewModel, printCallBackListener: PrintCallBackListener) {

    val user = viewModel.userData.value
    val saleInfo = viewModel.salesSelected.value

    val formattedDate = saleInfo?.salesDate?.let {
        SimpleDateFormat("dd MMM yyyy").format(Date(it))
    } ?: ""

    val formattedTotalAmount = formatNumberWithDelimiter(saleInfo?.totalPrice!!.toDouble())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "ArrowIcon",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(15.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Text(
                text = "Generate Receipt",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }

        Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        ) {

            Text(
                text = user!!.businessName.toString(),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(3.dp))

            if (user.businessDescription != null) {
                Text(
                    text = user.businessDescription.toString(),
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(3.dp))
            }

            if (user.businessAddress != null) {

                Text(
                    text = "Address: ${user.businessAddress.toString()}",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(3.dp))
            }

            if (user.number != null && user.additionalNumber != null) {

                Text(
                    text = "Mobile: ${user.number.toString()}, ${user.additionalNumber.toString()}",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(5.dp))
            }

            if (user.number == null && user.additionalNumber != null) {

                Text(
                    text = "Mobile: ${user.additionalNumber.toString()}",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(5.dp))
            }

            if (user.number != null && user.additionalNumber == null) {

                Text(
                    text = "Mobile: ${user.number.toString()}",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(5.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
            ) {

                Text(
                    text = "Customer's Name",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart)
                )

                Text(
                    text = "SALES INVOICE",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = saleInfo!!.customerName.toString(),
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                Text(
                    text = "Invoice Number: ${saleInfo.salesNo.toString()}",
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

                Text(
                    text = "Invoice Date: $formattedDate",
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Text(
                    text = "Quantity",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Item",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Unit Price",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Total",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))

            LazyColumn(
                modifier = Modifier.wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                items(saleInfo?.sales.orEmpty()) { singleSale ->
                    SalesItemsDetails(sales = singleSale, viewModel) {}
                }

                item {

                    Spacer(modifier = Modifier.padding(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                    ) {

                        Text(
                            text = "Total Quantity",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopStart)
                        )

                        Text(
                            text = "Total Amount",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )

                        Text(
                            text = saleInfo!!.totalQuantity.toString(),
                            modifier = Modifier.align(Alignment.BottomStart)
                        )

                        Text(
                            text = formattedTotalAmount,
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )

                    }

                    Spacer(modifier = Modifier.padding(20.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {

                                printCallBackListener.onPrintButtonClick()
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(ListOfColors.orange)
                        ) {

                            Text(
                                text = "Print",
                                color = ListOfColors.black
                            )
                        }
                    }

                }
            }


        }
    }
}






//@Preview (showBackground = true)
//@Composable
//fun GenerateReceiptPrev(){
//    GenerateReceiptScreen()
//}