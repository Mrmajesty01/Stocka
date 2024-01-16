package com.example.stocka.CreditInfoScreen


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Navigation.Destination
import com.example.stocka.SalesInfoScreen.SalesItemsDetails
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreditInfoHomeScreen(navController: NavController, viewModel: AuthViewModel) {

    val creditItem = viewModel.salesDetail.value
    val sales = viewModel.salesData.value
    val customer = viewModel.customerSelected.value
    val context = LocalContext.current
    val focus = LocalFocusManager.current

    val formattedDate = creditItem?.salesDate?.let {
        SimpleDateFormat("dd MMM yyyy").format(Date(it))
    } ?: ""

    if(creditItem?.sales.isNullOrEmpty()){
        navigateTo(navController,Destination.Home)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "ArrowBack",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(15.dp)
                    .clickable {
                        navigateTo(navController, Destination.Home)
                    },
                tint = ListOfColors.black
            )

            Text(
                text = "Credit Info",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }

        Divider(thickness = 1.dp, color = ListOfColors.lightGrey)


        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 5.dp, end = 5.dp)
            ) {

                Text(
                    text = "Name of Customer",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart)
                )

                Text(
                    text = creditItem?.customerName.toString(),
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = "Customer balance",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = customer?.customerBalance.toString(),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )

            }
            
            Spacer(modifier = Modifier.padding(5.dp))
            

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 5.dp, end = 5.dp)
            ) {

                Text(
                    text = "Invoice Number",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart)
                )

                Text(
                    text = creditItem?.type.toString() + creditItem?.salesNo.toString(),
                    modifier = Modifier.align(Alignment.TopEnd)
                )



                Text(
                    text = "Date",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = formattedDate,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )

            }

            Spacer(modifier = Modifier.padding(20.dp))

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
                items(creditItem?.sales.orEmpty()) { sale ->
                    SalesItemsDetails(sales = sale) {
                        if (sale != null) {
                            viewModel.onSaleSelected(creditItem!!)
                            viewModel.getStockSelected(sale)
                            viewModel.getSingleSale(sale,creditItem!!)
                        }
                        navController.navigate(Destination.EditSales.routes)
                    }
                }
             }

            Spacer(modifier = Modifier.padding(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 5.dp, end = 5.dp)
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
                    text = creditItem?.totalQuantity.toString(),
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                Text(
                    text = creditItem?.totalPrice.toString(),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )

            }


            Spacer(modifier = Modifier.padding(5.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 5.dp, end = 5.dp)
            ) {

                Text(
                    text = "Amount Paid",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = creditItem?.amountPaid.toString(),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )

            }

            Spacer(modifier = Modifier.padding(5.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 5.dp, end = 5.dp)
            ) {


                Text(
                    text = "Balance",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Text(
                    text = creditItem?.balance.toString(),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )

            }


            Spacer(modifier = Modifier.padding(20.dp))


            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .align(Alignment.CenterHorizontally),
            ) {
                Button(
                    onClick = {

                        if (creditItem?.sales.isNullOrEmpty() || (creditItem?.sales?.size ?: 0) < 5) {
                            viewModel.onSaleSelected(creditItem!!)
                            viewModel.fromPage("home")
                            navigateTo(navController,Destination.AddCredit)
                        }

                        else{
                            Toast.makeText(context,"Limit exceeded for adding sale", Toast.LENGTH_LONG).show()
                        }

                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)
                ) {
                    Text(

                        text = "Add Goods",
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = {
                        viewModel.onSaleSelected(creditItem!!)
                        navController.navigate(Destination.CreditReceipt.routes)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Generate Receipt",
                        textAlign = TextAlign.Center
                    )
                }

            }

            Spacer(modifier = Modifier.padding(10.dp))


            Button(
                onClick = {
                    navController.navigate(Destination.PayCredit.routes)
                    viewModel.onSaleSelected(creditItem!!)
                    viewModel.onCustomerSelectedHome(creditItem.customerId.toString())
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Pay Credit"
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {

                    focus.clearFocus(force = true)
                    viewModel.deleteEntireDocument(customerId = creditItem?.customerId.toString(), salesId = creditItem?.salesId.toString()){
                        navigateTo(navController,Destination.Home)
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(ListOfColors.orange),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Delete"
                )
            }

        }
    }

}


//@Preview (showBackground = true)
//@Composable
//fun CreditInfoPrev(){
//    CreditInfoScreen()
//}