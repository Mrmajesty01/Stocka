package com.example.stocka.HomeScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
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
import java.text.NumberFormat
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: AuthViewModel) {


    val context = LocalContext.current
    val userData = viewModel.userData.value
    val isLoading = viewModel.inProgress.value
    val isLoadingFeatures = viewModel.inProgressFeaturesToPin.value
    var sales = viewModel.salesHomeData.value
    var expenses = viewModel.expenseHomeData.value
    val businessName = if (userData?.businessName == null) "" else userData.businessName
    val lazyListState = rememberLazyListState()

    val features = viewModel.featuresToPin.value

    val paymentDue = viewModel.paymentDue.value

    val totalSales = viewModel.saleReceiptTotalToday.value+viewModel.creditReceiptTotalToday.value
    val totalExpenses = userData?.totalExpenses?.toDoubleOrNull() ?: 0.0
    val totalProfit = userData?.totalProfit?.toDoubleOrNull() ?: 0.0

    val profit = viewModel.totalProfitToday.value
    val expense = viewModel.totalExpenseToday.value

    var totalSalesHidden by remember {
        mutableStateOf(true)
    }

    var totalExpensesHidden by remember {
        mutableStateOf(true)
    }

    var totalProfitHidden by remember {
        mutableStateOf(true)
    }

    var dailyReportHidden by remember{
        mutableStateOf(true)
    }

//
//    // If features have not been updated from the default state, set a default value for safety
//    if (!features.TotalSales) {
//        totalSalesHidden = true // Default to hidden if TotalSales is false (initial state)
//    }

    LaunchedEffect(features){
        totalSalesHidden = viewModel.featuresToPin.value.TotalSales
        totalExpensesHidden = viewModel.featuresToPin.value.TotalExpenses
        totalProfitHidden = viewModel.featuresToPin.value.TotalProfit
        dailyReportHidden = viewModel.featuresToPin.value.DailyReport
    }

    // Adjust totalSalesHidden if features change (ensures updated state)



    var formattedTotalSales = formatNumberWithDelimiter(totalSales)

    var formattedTotalExpenses = formatNumberWithDelimiter(expense)
    var formattedTotalProfit = formatNumberWithDelimiter(profit)


    if(paymentDue){
        navController.navigate(Destination.CheckOutPayment.routes)
    }



    var openDialogSales by rememberSaveable {
        mutableStateOf(false)
    }

    var openDialogExpenses by rememberSaveable {
        mutableStateOf(false)
    }

    var openDialogProfit by rememberSaveable {
        mutableStateOf(false)
    }

    var openDialogReport by rememberSaveable {
        mutableStateOf(false)
    }

    var pin by remember { mutableStateOf(TextFieldValue()) }


    if(openDialogSales){
        AlertDialog(
            onDismissRequest = {
                openDialogSales = false
                pin = TextFieldValue("")
           },

            title = {
                Text(text = "View Total Sales Today")
            },

            text = {
                Column {
                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN To View Sales") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if(pin.text.toInt() == userData?.pin?.toInt()) {
                        openDialogSales = false
                        totalSalesHidden = false
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
                    openDialogSales = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }

    if(openDialogExpenses){
        AlertDialog(
            onDismissRequest = {
                openDialogExpenses = false
                pin = TextFieldValue("")
            },

            title = {
                Text(text = "View Total Expenses Today")
            },

            text = {
                Column {
                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN To View Expenses") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if(pin.text.toInt() == userData?.pin?.toInt()) {
                        openDialogExpenses = false
                        totalExpensesHidden = false
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
                    openDialogExpenses = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }

    if(openDialogProfit){
        AlertDialog(
            onDismissRequest = {
                openDialogProfit = false
                pin = TextFieldValue("")
            },

            title = {
                Text(text = "View Total Profit Today")
            },

            text = {
                Column {
                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN To View Profit") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if(pin.text.toInt() == userData?.pin?.toInt()) {
                        openDialogProfit = false
                        totalProfitHidden = false
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
                    openDialogProfit = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }

    if(openDialogReport){
        AlertDialog(
            onDismissRequest = {
                openDialogReport = false
                pin = TextFieldValue("")
            },

            title = {
                Text(text = "View Daily Report")
            },

            text = {
                Column {
                    // Add an EditText for PIN input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                        },
                        label = { Text("Enter PIN To View Daily Report") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    if(pin.text.toInt() == userData?.pin?.toInt()) {
                        viewModel.getTotalSaleReceiptToday()
                        viewModel.getTotalCreditReceiptToday()
                        viewModel.getMostPurchasedProductsToday()
                        navController.navigate(Destination.DailyReport.routes)
                        openDialogReport = false
                        dailyReportHidden = false
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
                    openDialogReport = false
                }) {
                    Text(text = "No")
                }
            },
        )
    }


    var state by remember {
        mutableStateOf(0)
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

                Card(
                    elevation = 16.dp,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(150.dp)
                        .fillMaxWidth()
                        .clickable {
                            if (!isLoading) {
                                if(dailyReportHidden) {
                                    openDialogReport = true
                                }
                                else{
                                    viewModel.getTotalSaleReceiptToday()
                                    viewModel.getTotalCreditReceiptToday()
                                    viewModel.getMostPurchasedProductsToday()
                                    navController.navigate(Destination.DailyReport.routes)
                                }
                            }
                        }
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
                        when (state) {
                            0 -> {
                                Text(
                                    text = "Total Sales Today",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack
                                )

                            }

                            1 -> {

                                Text(
                                    text = "Total Expenses Today",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack
                                )

                            }

                            2 -> {

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

                        when (state) {

                            0 -> {
                                Text(
                                    text = if (totalSalesHidden)  "*****"  else "₦$formattedTotalSales",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack,
                                    modifier = Modifier.clickable {
                                        if (totalSalesHidden) {
                                            openDialogSales = true
                                        }
                                        else {
                                            // If revealed, hide the total sales
                                            totalSalesHidden = true
                                        }
                                    }
                                )
                            }

                            1 -> {
                                Text(
                                    text = if (totalExpensesHidden) "*****" else "₦$formattedTotalExpenses",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack,
                                    modifier = Modifier.clickable{
                                        if (totalExpensesHidden) {
                                            openDialogExpenses = true
                                        }
                                        else {
                                            // If revealed, hide the total sales
                                            totalExpensesHidden = true
                                        }
                                    }
                                ) 
                            }

                            2 -> {
                                Text(
                                    text = if (totalProfitHidden) "*****" else "₦$formattedTotalProfit",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontStyle = FontStyle.Italic,
                                    color = ListOfColors.matteBlack,
                                    modifier = Modifier.clickable{
                                        if (totalProfitHidden) {
                                            openDialogProfit = true
                                        }
                                        else {
                                            // If revealed, hide the total sales
                                            totalProfitHidden = true
                                        }
                                    }
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
                            if (!isLoading) {
                                state = 0
                            }
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
                            if (!isLoading) {
                                state = 1
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(ListOfColors.lightRed),
                        modifier = Modifier.width(110.dp),
                    ) {
                        Text(
                            "Expenses"
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                            if (!isLoading) {
                                state = 2
                            }
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


                    when (state) {

                        0 -> {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "Recent Sales",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                            append("View All")
                                        }
                                    },
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            if (!isLoading) {
                                                navController.navigate(Destination.Invoices.routes)
                                            }
                                        },
                                    textAlign = TextAlign.End,
                                )
                            }
                        }

                        1 -> {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Recent Expense",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                            append("View All")
                                        }
                                    },
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            if (!isLoading) {
                                                navController.navigate(Destination.ExpenseScreen.routes)
                                            }
                                        },
                                    textAlign = TextAlign.End,
                                )
                            }
                        }

                        2 -> {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Recent Sales",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                            append("View All")
                                        }
                                    },
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            if (!isLoading) {
                                                navController.navigate(Destination.Invoices.routes)
                                            }
                                        },
                                    textAlign = TextAlign.End,
                                )

                            }
                        }

                    }


                    Spacer(modifier = Modifier.padding(7.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        when (state) {
                            0 -> {
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
                                        verticalArrangement = Arrangement.spacedBy(7.dp),
                                        state = lazyListState
                                    ) {
                                        items(sales) {
                                            CustomerSalesItem(sales = it) {
                                                if (it.type.equals("SR")) {
                                                    viewModel.getSale(it.salesId.toString())
                                                    navigateTo(
                                                        navController,
                                                        Destination.SalesInfoHome
                                                    )
                                                    viewModel.onCustomerSelectedHome(it.customerId.toString())
                                                } else {
                                                    viewModel.getSale(it.salesId.toString())
                                                    navigateTo(
                                                        navController,
                                                        Destination.CreditInfoHome
                                                    )
                                                    viewModel.onCustomerSelectedHome(it.customerId.toString())
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (expenses.isNullOrEmpty()) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "No expenses added today"
                                        )
                                    }
                                } else {
                                    LazyColumn(
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.spacedBy(7.dp),
                                        state = lazyListState
                                    ) {
                                        items(expenses) { expense ->
                                            ExpenseItem(expense = expense) {
                                                viewModel.getExpense(it)
                                                navigateTo(navController, Destination.ExpenseInfo)
                                            }
                                        }
                                    }
                                }
                            }

                            2 -> {
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
                                        verticalArrangement = Arrangement.spacedBy(7.dp),
                                        state = lazyListState
                                    ) {
                                        items(sales) {
                                            CustomerSalesItem(sales = it) {
                                                navigateTo(
                                                    navController,
                                                    Destination.SalesInfoHome,
                                                    NavPram("sales", it)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                        }

                    }


                }
            }
            if (!isLoading) {
                BottomNavMenu(selectedItem = BottomNavItem.Home, navController = navController)
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
                    .align(Alignment.Center)
            )
        }
    }
}
fun formatNumberWithDelimiter(number: Double): String {
    val numberFormat = NumberFormat.getInstance(Locale.getDefault())
    return numberFormat.format(number)
}









//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPrev(){
////    HomeScreen()
//}