package com.example.stocka.HomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.AddExpenseScreen.ExpenseItem
import com.example.stocka.Navigation.BottomNavItem
import com.example.stocka.Navigation.BottomNavMenu
import com.example.stocka.Navigation.Destination
import com.example.stocka.R
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.main.NavPram
import com.example.stocka.main.navigateTo
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: AuthViewModel) {



    val userData = viewModel.userData.value
    val isLoading = viewModel.inProgress.value
    var sales = viewModel.salesHomeData.value
    var expenses = viewModel.expenseData.value
    val businessName = if(userData?.businessName==null) "" else userData.businessName

    var state by remember{
        mutableStateOf(0)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Image(
                    painter = painterResource(id = R.drawable.shop),
                    contentDescription = "shopIcon",
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(top = 10.dp, end = 10.dp)
                        .size(50.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp, end = 20.dp)
            ) {

                Text(
                    text = "Welcome back,",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = businessName.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "FilterIcon",
                        tint = ListOfColors.black
                    )

                }

                Spacer(modifier = Modifier.padding(5.dp))

                Card(
                    elevation = 16.dp,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(150.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ybb), contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        when(state){
                             0->{
                                Text(
                                    text = "Total Sales Today",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack
                                )

                            }
                             1->{

                                Text(
                                    text = "Total Expenses Today",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack
                                )

                            }
                            2->{

                                Text(
                                    text = "Total Profit Today",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack
                                )

                            }
                        }

                        Spacer(modifier = Modifier.padding(5.dp))

                        when(state){

                            0->{
                                Text(
                                    text = "₦${userData?.totalSales?:0.0}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack

                                )
                            }

                            1->{
                                Text(
                                    text = "₦${userData?.totalExpenses?:0.0}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack

                                )
                            }

                            2->{
                                Text(
                                    text = "₦${userData?.totalProfit?:0.0}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack

                                )

                            }

                        }


                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))


                Row(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .align(Alignment.CenterHorizontally),
                ) {
                    Button(
                        onClick = {
                              state = 0
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.lightBlue),
                        modifier = Modifier.width(80.dp)
                    ) {
                        Text(
                            "Sales"
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                              state = 1
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.lightRed),
                        modifier = Modifier.width(110.dp)
                    ) {
                        Text(
                            "Expenses"
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                              state = 2
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.lightGreen),
                        modifier = Modifier.width(80.dp)
                    ) {
                        Text(
                            "Profit"
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Column {


                    when(state){

                        0->{
                            Text(
                                text = "Recent Sales",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                            )
                        }

                        1->{
                            Text(
                                text = "Recent Expense",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                            )
                        }

                        2->{
                            Text(
                                text = "Recent Sales",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                            )

                        }

                    }

                    Spacer(modifier = Modifier.padding(7.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        when(state){
                             0-> {
                                 if (sales.isEmpty()) {
                                     Column(
                                         modifier = Modifier.fillMaxWidth()
                                             .fillMaxSize(),
                                         verticalArrangement = Arrangement.Center,
                                         horizontalAlignment = Alignment.CenterHorizontally
                                     ) {
                                         Text(
                                             text = "No sales added today"
                                         )
                                     }
                                 } else {
                                     LazyColumn(
                                         modifier = Modifier
                                             .wrapContentHeight()
                                             .fillMaxSize(),
                                         verticalArrangement = Arrangement.spacedBy(7.dp)
                                     ) {
                                         items(sales) {
                                             CustomerSalesItem(sales = it){
                                                 if(it.type.equals("SR")){
                                                     viewModel.getSale(it.salesId.toString())
                                                     navigateTo(navController,Destination.SalesInfoHome)
                                                     viewModel.onCustomerSelectedHome(it.customerId.toString())
                                                 }
                                                 else {
                                                     viewModel.getSale(it.salesId.toString())
                                                     navigateTo(navController,Destination.CreditInfoHome)
                                                     viewModel.onCustomerSelectedHome(it.customerId.toString())
                                                 }
                                                 }
                                             }
                                         }
                                     }
                                 }

                            1->{
                                if(expenses.isNullOrEmpty()) {
                                    Column (
                                        modifier = Modifier.fillMaxWidth()
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Text(
                                            text = "No expenses added today"
                                        )
                                    }
                                }
                                else{
                                    LazyColumn(
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.spacedBy(7.dp)
                                    ) {
                                        items(expenses) { expense ->
                                            ExpenseItem(expense = expense){
                                                navigateTo(navController,Destination.ExpenseInfo,
                                                    NavPram("expense",it))
                                            }
                                        }
                                    }
                                }
                            }

                            2->{
                                if (sales.isEmpty()) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "No sales added today"
                                        )
                                    }
                                } else {
                                    LazyColumn(
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.spacedBy(7.dp)
                                    ) {
                                        items(sales) {
                                            CustomerSalesItem(sales = it){
                                                navigateTo(navController,Destination.SalesInfoHome,NavPram("sales",it))
                                            }
                                        }
                                    }
                                }
                            }

                        }

                    }


                }
            }
            BottomNavMenu(selectedItem = BottomNavItem.Home, navController = navController )
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
//fun HomeScreenPrev(){
////    HomeScreen()
//}