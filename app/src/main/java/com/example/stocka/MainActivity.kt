package com.example.stocka

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stocka.AccountSecurity.AccountSecurityScreen
import com.example.stocka.AddCredit.AddCreditScreen
import com.example.stocka.AddCustomerScreen.AddCustomerScreen
import com.example.stocka.AddExpenseScreen.AddExpenseScreen
import com.example.stocka.AddStockScreen.AddStockScreen
import com.example.stocka.AddToStock.AddToStockScreen
import com.example.stocka.BottomSheet.BSheet
import com.example.stocka.BusinessDetail.BusinessDetailsScreen
import com.example.stocka.BusinessDetail.EditBusinessDetailsScreen
import com.example.stocka.CheckOutPayment.CheckOutPayment
import com.example.stocka.ContactUs.ContactUsScreen
import com.example.stocka.CreditInfoScreen.CreditInfoHomeScreen
import com.example.stocka.CreditInfoScreen.CreditInfoScreen
import com.example.stocka.CustomerHistoryScreen.CustomerHistoryScreen
import com.example.stocka.CustomerInfoScreen.CustomerInfoScreen
import com.example.stocka.CustomerScreen.CustomersScreen
import com.example.stocka.CustomerStockSearch.CustomerSearch
import com.example.stocka.CustomerStockSearch.StockSearch
import com.example.stocka.DailyReportScreen.DailyReportScreen
import com.example.stocka.EditCustomerScreen.EditCustomerInfoScreen
import com.example.stocka.EditExpenseScreen.EditExpenseScreen
import com.example.stocka.EditSalesScreen.EditSalesScreen
import com.example.stocka.EditStockScreen.EditStockInfoScreen
import com.example.stocka.ExpenseInfoScreen.ExpenseInfoScreen
import com.example.stocka.ExpenseScreen.ExpenseScreen
import com.example.stocka.FeaturesToPin.FeaturesToPin
import com.example.stocka.ForgotPassword.ForgetPassword
import com.example.stocka.GenerateCreditReceipt.GenerateCreditReceiptScreen
import com.example.stocka.GenerateReceiptScreen.GenerateReceiptScreen
import com.example.stocka.HomeScreen.HomeScreen
import com.example.stocka.InvoiceScreen.InvoiceScreen
import com.example.stocka.LoginScreen.LoginScreen
import com.example.stocka.MakeCreditScreen.MakeCreditScreen
import com.example.stocka.MakeSalesScreen.AddSalesScreen
import com.example.stocka.MakeSalesScreen.MakeSalesScreen
import com.example.stocka.Navigation.Destination
import com.example.stocka.PayCredit.PayCreditScreen
import com.example.stocka.PersonalDetail.EditPersonalDetailsScreen
import com.example.stocka.PersonalDetail.PersonalDetailsScreen
import com.example.stocka.RegistrationScreen.RegistrationScreen
import com.example.stocka.SalesInfoScreen.SalesInfoHome
import com.example.stocka.SalesInfoScreen.SalesInfoScreen
import com.example.stocka.SettingScreen.SettingsScreen
import com.example.stocka.StockHistoryScreen.StockHistoryScreen
import com.example.stocka.StockInfo.StockInfoScreen
import com.example.stocka.StockScreen.StockScreen
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.Viemodel.SalesViewModel
import com.example.stocka.data.SingleSale
import com.example.stocka.main.NotificationMessage
import com.example.stocka.ui.theme.StockaTheme
import com.flutterwave.raveandroid.RavePayActivity
import com.flutterwave.raveandroid.RaveUiManager
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.ui.ScanningActivity
import com.mazenrashed.printooth.utilities.Printing
import com.mazenrashed.printooth.utilities.PrintingCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity @Inject constructor(): ComponentActivity(), NotificationHandler, PrintCallBackListener {
    var printing: Printing? = null
    private val viewModelPay: AuthViewModel by viewModels()



    val viewModel: AuthViewModel by viewModels()
        @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Printooth.init(this)
        if (Printooth.hasPairedPrinter())
            printing = Printooth.printer()
        requestNotificationPermission()
        requestBluetoothNotification()



        viewModelPay.payTrigger.observe(this, {
            makePayment() // Call the makePayment() method when pay action is triggered
        })






        setContent {
            StockaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Stocka()
                }
            }
        }


        /* callback from printooth to get printer process */
        printing?.printingCallback =
            object : PrintingCallback {
                override fun connectingWithPrinter() {
                    Toast.makeText(this@MainActivity, "Connecting with printer", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun printingOrderSentSuccessfully() {
                    Toast.makeText(this@MainActivity, "Order sent to printer", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun connectionFailed(error: String) {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to connect printer",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onError(error: String) {
                    Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                }

                override fun onMessage(message: String) {
                    Toast.makeText(this@MainActivity, "Message: $message", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun disconnected() {
                    Toast.makeText(this@MainActivity, "Disconnected Printer", Toast.LENGTH_SHORT)
                        .show()
                }

            }
    }

    fun makePayment(){

       RaveUiManager(this)
           .setAmount("2000".toDouble())
           .setCountry("NG")
           .setCurrency("NGN")
           .setfName(viewModel.userData.value?.fullName)
           .setlName(viewModel.userData.value?.fullName)
           .setEmail(viewModel.userData.value?.email)
           .setNarration("monthly subscription for stocka")
           .setPublicKey("FLWPUBK_TEST-c3f52b8bc51db846f096c24ff2ca7fc1-X")
           .setEncryptionKey("FLWSECK_TEST1b98ecbd5457")
           .setTxRef(System.currentTimeMillis().toString() + "Ref")
           .acceptAccountPayments(true)
           .acceptCardPayments(true)
           .acceptUssdPayments(true)
           .acceptBankTransferPayments(true)
           .onStagingEnv(true)
           .shouldDisplayFee(true)
           .showStagingLabel(true)
           .initialize()



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /*
         *  We advise you to do a further verification of transaction's details on your server to be
         *  sure everything checks out before providing service or goods.
        */
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            val message = data.getStringExtra("response")
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
                viewModel.updatePaymentDate()
                viewModel.paymentSuccesful.value = true
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "An error occurred ,please try again", Toast.LENGTH_SHORT).show()
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }





    fun printDetails(businessName: String, businessDescription: String, businessAddress: String, businessNumber: String, customerName: String, invoiceType:String,
                     invoiceNumber:String, invoiceDate:String, totalQuantity: String, totalAmount: String, salesList:List<SingleSale>) {
        val printables = getSomePrintables(businessName,businessDescription,businessAddress,businessNumber,customerName,invoiceType,invoiceNumber,invoiceDate,totalQuantity,totalAmount,salesList)
        printing?.print(printables)
    }


    fun getSomePrintables(businessName: String, businessDescription: String, businessAddress: String, businessNumber: String, customerName: String, invoiceType:String,
                          invoiceNumber:String, invoiceDate:String, totalQuantity: String, totalAmount: String, salesList: List<SingleSale>): ArrayList<Printable> {
        val printables = ArrayList<Printable>()

        printables.add(
            TextPrintable.Builder()
                .setText(businessName)
                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_OFF)
                .setNewLinesAfter(1)
                .build())

        printables.add(
            TextPrintable.Builder()
                .setText(businessDescription)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)  // Align at the center
                .setNewLinesAfter(1)
                .build()
        )

        printables.add(
            TextPrintable.Builder()
                .setText(businessAddress)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())

        printables.add(
            TextPrintable.Builder()
                .setText(businessNumber)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(2)
                .build())


        printables.add(
            TextPrintable.Builder()
                .setText("customerName")  // Replace with your actual customerName variable
                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_OFF)
                .build()
        )

        printables.add(
            TextPrintable.Builder()
                .setText(invoiceType)
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)  // Align at the right end
                .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_OFF)
                .build()
        )


        printables.add(
            TextPrintable.Builder()
                .setText(customerName)
                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)  // Align at the left end
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_OFF)
                .build()
        )


        printables.add(
            TextPrintable.Builder()
                .setText("Invoice Number: $invoiceNumber")
                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build())

        printables.add(
            TextPrintable.Builder()
                .setText("InvoiceDate: $invoiceDate")
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build())

        // Add header line
        printables.add(
            TextPrintable.Builder()
                .setText("Quantity | Item | Unit Price | Total")
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(1)
                .build())

        for (sale in salesList) {
            // Create Printable for each sale
            printables.add(
                TextPrintable.Builder()
                    .setText("${sale.quantity} | ${sale.productName} | ${sale.price} | ${sale.totalPrice}")
                    .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                    .setNewLinesAfter(1)
                    .build())
        }


        printables.add(
            TextPrintable.Builder()
                .setText("TotalQuantity")
                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .build())

        printables.add(
            TextPrintable.Builder()
                .setText("TotalAmount")
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build())

        printables.add(
            TextPrintable.Builder()
                .setText(totalQuantity)
                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .build())

        printables.add(
            TextPrintable.Builder()
                .setText(totalAmount)
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .build())

        // Add other printables as needed
        printables.add(RawPrintable.Builder(byteArrayOf(27, 100, 4)).build())

        // Add logo or any other printables

        return printables
    }

    /* Inbuilt activity to pair device with printer or select from list of pair bluetooth devices */
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == ScanningActivity.SCANNING_FOR_PRINTER &&  result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
//            val intent = result.data
            printDetails(viewModel.userData.value?.businessName.toString(),viewModel.userData.value?.businessDescription.toString(),viewModel.userData.value?.businessAddress.toString(),viewModel.userData.value?.number.toString()+viewModel.userData.value?.additionalNumber.toString(),viewModel.salesSelected.value?.customerName.toString(),viewModel.salesSelected.value?.type.toString(),viewModel.salesSelected.value?.salesNo.toString(),viewModel.salesSelected.value?.salesDate.toString(),
                viewModel.salesSelected.value?.totalQuantity.toString(),viewModel.salesSelected.value?.totalPrice.toString(),viewModel.salesSelected.value?.sales.orEmpty())

        }
    }

    fun initListeners() {

        if (!Printooth.hasPairedPrinter())
            resultLauncher.launch(Intent(this@MainActivity, ScanningActivity::class.java))
        else printDetails(viewModel.userData.value?.businessName.toString(),viewModel.userData.value?.businessDescription.toString(),viewModel.userData.value?.businessAddress.toString(),viewModel.userData.value?.number.toString()+viewModel.userData.value?.additionalNumber.toString(),viewModel.salesSelected.value?.customerName.toString(),viewModel.salesSelected.value?.type.toString(),viewModel.salesSelected.value?.salesNo.toString(),viewModel.salesSelected.value?.salesDate.toString(),
            viewModel.salesSelected.value?.totalQuantity.toString(),viewModel.salesSelected.value?.totalPrice.toString(),viewModel.salesSelected.value?.sales.orEmpty())

    }





    override fun showNotification(content:String, title:String){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext,"channel_id")
            .setContentText(content)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.shop)
            .build()
        notificationManager.notify(1, notification)
    }

    override fun onPrintButtonClick() {
           if (Printooth.hasPairedPrinter()) {
            printDetails(
                viewModel.userData.value?.businessName.toString(),
                viewModel.userData.value?.businessDescription.toString(),
                viewModel.userData.value?.businessAddress.toString(),
  viewModel.userData.value?.number.toString() + viewModel.userData.value?.additionalNumber.toString(),
                viewModel.salesSelected.value?.customerName.toString(),
                viewModel.salesSelected.value?.type.toString(),
                viewModel.salesSelected.value?.salesNo.toString(),
                viewModel.salesSelected.value?.salesDate.toString(),
                viewModel.salesSelected.value?.totalQuantity.toString(),
                viewModel.salesSelected.value?.totalPrice.toString(),
                viewModel.salesSelected.value?.sales.orEmpty()
            )
        } else {
            // Prompt the user to connect a printer
            Toast.makeText(this, "Please connect to a printer to print", Toast.LENGTH_SHORT).show()
            // Optionally, you can launch the activity to pair a printer
            resultLauncher.launch(Intent(this, ScanningActivity::class.java))
        }
    }

    companion object {
        const val PERMISSION_BLUETOOTH = 1
        const val PERMISSION_BLUETOOTH_ADMIN = 2
        const val PERMISSION_BLUETOOTH_CONNECT = 3
        const val PERMISSION_BLUETOOTH_SCAN = 4
    }

  fun requestBluetoothNotification(){
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S &&
          ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
      ) {
          ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), PERMISSION_BLUETOOTH)
      } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S &&
          ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
      ) {
          ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_ADMIN), PERMISSION_BLUETOOTH_ADMIN)
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
          ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
      ) {
          ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), PERMISSION_BLUETOOTH_CONNECT)
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
          ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
      ) {
          ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_SCAN), PERMISSION_BLUETOOTH_SCAN)
      } else {
          // Your code HERE
      }
  }



    private fun requestNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if(!hasPermission){
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }



    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Stocka(){
        val navController = rememberNavController()
        val authViewModel : AuthViewModel = hiltViewModel()
        val mainActivity = LocalContext.current as MainActivity


        val salesViewModel:SalesViewModel = hiltViewModel()
//        val customerViewModel: CustomerViewModel = hiltViewModel()
//        val stockViewModel: StockViewModel = hiltViewModel()
        NotificationMessage(vm = authViewModel)
//        NotificationMessage(vm = customerViewModel)
//        NotificationMessage(vm = stockViewModel)
        NavHost(navController = navController, startDestination = Destination.Login.routes ){

            composable(Destination.SignUp.routes){
                RegistrationScreen(navController,authViewModel)
            }


            composable(Destination.Login.routes){
                LoginScreen(navController,authViewModel)
            }


            composable(Destination.Home.routes){
                HomeScreen(navController,authViewModel)
            }

            composable(Destination.Customers.routes){
                CustomersScreen(navController,authViewModel)
            }

            composable(Destination.Stocks.routes){
                StockScreen(navController,authViewModel)
            }

            composable(Destination.AddExpense.routes){
                AddExpenseScreen(navController,authViewModel)
            }

            composable(Destination.BottomSheet.routes){
                BSheet(navController,authViewModel)
            }

            composable(Destination.Invoices.routes){
                InvoiceScreen(navController,authViewModel)
            }

            composable(Destination.Settings.routes){
                SettingsScreen(navController,authViewModel)
            }

            composable(Destination.AddSale.routes){
                AddSalesScreen(navController,authViewModel)
            }


            composable(Destination.SalesInfo.routes){
                SalesInfoScreen(navController, authViewModel)
            }

            composable(Destination.MakeSales.routes){
                MakeSalesScreen(navController, authViewModel)
            }

            composable(Destination.SalesInfoHome.routes){
                SalesInfoHome(navController = navController, viewModel = authViewModel)
            }


            composable(Destination.AddCustomer.routes){
                AddCustomerScreen(navController,authViewModel)
            }


            composable(Destination.AddStock.routes){
                AddStockScreen(navController,authViewModel)
            }

            composable(Destination.SearchCustomer.routes){
                CustomerSearch(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.SearchStock.routes){
                StockSearch(navController = navController, viewModel = authViewModel)
            }


            composable(Destination.StockInfo.routes){
                    StockInfoScreen(navController = navController, viewModel = authViewModel)
            }


            composable(Destination.StockHistory.routes){
                StockHistoryScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.EditStock.routes){
                EditStockInfoScreen(navController = navController, viewModel =  authViewModel)
            }

            composable(Destination.CustomerInfo.routes){
                    CustomerInfoScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.EditCustomer.routes){
                EditCustomerInfoScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.CustomerHistory.routes){
                CustomerHistoryScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.ExpenseInfo.routes){
                    ExpenseInfoScreen(navController = navController, viewModel = authViewModel)
            }

            composable(Destination.EditSales.routes){
                EditSalesScreen(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.MakeCredit.routes){
                MakeCreditScreen(navController, authViewModel)
            }

            composable(Destination.AddCredit.routes){
                AddCreditScreen(navController, authViewModel)
            }

            composable(Destination.CreditInfo.routes){
                CreditInfoScreen(navController, authViewModel)
            }

            composable(Destination.CreditInfoHome.routes){
                CreditInfoHomeScreen(navController, authViewModel)
            }

            composable(Destination.PayCredit.routes){
                PayCreditScreen(navController,authViewModel)
            }

            composable(Destination.AccountSecurity.routes){
                AccountSecurityScreen(navController, authViewModel)
            }

            composable(Destination.ContactUs.routes){
                ContactUsScreen(navController)
            }

            composable(Destination.PersonalDetail.routes){
                PersonalDetailsScreen(navController, authViewModel)
            }

            composable(Destination.EditPersonalDetail.routes){
                EditPersonalDetailsScreen(navController, authViewModel)
            }

            composable(Destination.BusinessDetails.routes){
                BusinessDetailsScreen(navController, authViewModel)
            }

            composable(Destination.EditBusinessDetail.routes){
                EditBusinessDetailsScreen(navController, authViewModel)
            }

            composable(Destination.SalesReceipt.routes){
                GenerateReceiptScreen(navController, authViewModel, mainActivity)
            }

            composable(Destination.CreditReceipt.routes){
                GenerateCreditReceiptScreen(navController, authViewModel)
            }

            composable(Destination.ExpenseScreen.routes){
                ExpenseScreen(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.DailyReport.routes){
                DailyReportScreen(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.EditExpense.routes){
                EditExpenseScreen(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.AddToStock.routes){
                AddToStockScreen(navController = navController, viewModel = authViewModel )
            }

            composable(Destination.CheckOutPayment.routes){
                CheckOutPayment(navController,authViewModel)
            }

            composable(Destination.ForgotPassword.routes){
                ForgetPassword(navController,authViewModel)
            }

            composable(Destination.FeaturesToPassword.routes){
                FeaturesToPin(navController,authViewModel)
            }

            }


        }




}



