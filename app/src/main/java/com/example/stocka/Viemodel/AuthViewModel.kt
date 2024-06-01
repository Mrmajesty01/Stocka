package com.example.stocka.Viemodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stocka.R
import com.example.stocka.Util.Constants
import com.example.stocka.data.Customer
import com.example.stocka.data.DailyReport
import com.example.stocka.data.Event
import com.example.stocka.data.Expense
import com.example.stocka.data.FeaturesToPin
import com.example.stocka.data.Sales
import com.example.stocka.data.SingleSale
import com.example.stocka.data.Stock
import com.example.stocka.data.StockHistory
import com.example.stocka.data.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObjects
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    private val context: Context

): ViewModel() {

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val userData = mutableStateOf<User?>(null)
    val dailyReport = mutableStateOf<DailyReport?>(null)
    val featuresToPin = mutableStateOf(FeaturesToPin())
    val inProgressFeaturesToPin = mutableStateOf(false)
    val popupNotification = mutableStateOf<Event<String>?>(null)
    val refreshSalesProgress = mutableStateOf(false)
    val getStockProgress = mutableStateOf(false)
    val getCustomerProgress = mutableStateOf(false)
    val updatedSaleProgress = mutableStateOf(false)
    val customerProgress = mutableStateOf(false)
    val deleteStockProgress = mutableStateOf(false)
    val deleteCustomerProgress = mutableStateOf(false)
    val deleteSaleProgress = mutableStateOf(false)
    val onMultipleSoldProgress = mutableStateOf(false)
    val addExpenseProgress = mutableStateOf(false)
    val expenseSelected = mutableStateOf<Expense?>(null)
    val expenseProgress = mutableStateOf(false)
    val getSaleProgress = mutableStateOf(false)
    val getSingleSaleProgress = mutableStateOf(false)
    val expenseData = mutableStateOf<List<Expense>>(listOf())
    val expenseHomeData = mutableStateOf<List<Expense>>(listOf())
    val salesData = mutableStateOf<List<Sales>>(listOf())
    val salesDetail = mutableStateOf<Sales?>(null)
    val salesHomeData = mutableStateOf<List<Sales>>(listOf())
    val invoiceData = mutableStateOf<List<Sales>>(listOf())
    val customerHistoryData = mutableStateOf<List<Sales>>(listOf())
    val stockHistoryData = mutableStateOf<List<StockHistory>>(listOf())
    val salesHomeDetail = mutableStateOf<Sales?>(null)
    val customerData = mutableStateOf<List<Customer>>(listOf())
    val searchedCustomer = mutableStateOf<List<Customer>>(listOf())
    val customerSearchProgress = mutableStateOf(false)
    val searchedStocks = mutableStateOf<List<Stock>>(listOf())
    val stocks = mutableStateOf<List<Stock>>(listOf())
    val stockSearchProgress = mutableStateOf(false)
    val invoiceSearchProgress = mutableStateOf(false)
    val dailyReportProgress = mutableStateOf(false)
    val customerSelected = mutableStateOf<Customer?>(null)
    val salesSelected = mutableStateOf<Sales?>(null)
    val stockSelected = mutableStateOf<Stock?>(null)
    val singleSaleSelected = mutableStateOf<SingleSale?>(null)
    val unmodifiedSingle = mutableStateOf<SingleSale?>(null)
    val unmodifiedexpense = mutableStateOf<Expense?>(null)
    val totalAmountOwed = mutableStateOf(0.0)
    val totalStockValue = mutableStateOf(0.0)
    val fromPageValue = mutableStateOf("")


    val AllsalesTotalToday = mutableStateOf(0.0)
    val saleReceiptTotalToday = mutableStateOf(0.0)
    val creditReceiptTotalToday = mutableStateOf(0.0)
    val totalProfitToday =  mutableStateOf(0.0)
    val totalExpenseToday =  mutableStateOf(0.0)
    val mostBoughtGoodToday = mutableStateOf("")
    val goodsSold = mutableStateOf("")
    val mostBoughtGoodQuantity = mutableStateOf("")

    val totalSalesFilter = mutableStateOf(0.0)
    val totalCreditReceiptFilter = mutableStateOf(0.0)
    val totalSaleReceiptFilter = mutableStateOf(0.0)
    val totalExpenseFilter = mutableStateOf(0.0)
    val totalProfitFilter = mutableStateOf(0.0)
    val totalProfitAfterExpenseFilter = mutableStateOf(0.0)
    val mostBoughtGoodFilter = mutableStateOf("")
    val mostBoughtGoodQuantityFilter = mutableStateOf("")

    val paymentDue = mutableStateOf(false)
    val paymentSuccesful = mutableStateOf(false)


    private val _payTrigger = MutableLiveData<Unit>()
    val payTrigger: LiveData<Unit> get() = _payTrigger






    init {
        auth.signOut()
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        currentUser?.uid?.let { uid ->
            getUserData(uid)

        }
    }



    fun onSignUp(fullName:String,businessName:String,email:String,password:String,confirmPassword:String, onSuccess: () -> Unit){
        if(fullName.isEmpty() or businessName.isEmpty() or email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Please fill in all fields")
            return
        }

        if(password != confirmPassword){
            handleException(customMessage = "Passwords don't match")
            return
        }

        inProgress.value = true
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
            if(task.isSuccessful){
                signedIn.value = true
                createOrUpdateProfile(fullName=fullName,businessName=businessName,email=email,password=password,confirmPassword = confirmPassword, onSuccess)
            }
            else{
                handleException(task.exception,"Signup failed")
            }
            inProgress.value = false
        }

    }

    fun login(email:String, password:String, onSuccess: () -> Unit){
        if(email.isEmpty() or password.isEmpty()){
            handleException(customMessage = "Please fill all fields")
            return
        }
        inProgress.value = true
        auth.signInWithEmailAndPassword(email.trim(),password.trim()).addOnCompleteListener{task->
            if(task.isSuccessful){
                signedIn.value = true
                inProgress.value = false
                auth.currentUser?.uid?.let {uid->
                    getUserData(uid)
                }
                onSuccess.invoke()
            }
            else{
                handleException(customMessage = "login failed")
                inProgress.value = false
            }
        }
            .addOnFailureListener{exc->
                handleException(exc,"login failed")
                inProgress.value = false
            }

    }

    private fun createOrUpdateProfile(
        fullName: String? = null,
        businessName: String? = null,
        email: String? = null,
        password:String? = null,
        confirmPassword:String? = null,
        onSuccess: () -> Unit
    ){

        val userId = auth.currentUser?.uid

        val currentDate = LocalDate.now()
        val paymentDueDate = currentDate.plusDays(30).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        val user = User(
            userId = userId,
            fullName = fullName ?:userData.value?.fullName,
            businessName = businessName ?:userData.value?.businessName,
            email = email ?:userData.value?.email,
            currentDate = System.currentTimeMillis(),
            dateCreated = System.currentTimeMillis(),
            paymentDate = paymentDueDate,
            password = password ?:userData.value?.password,
            confirmPassword = confirmPassword ?:userData.value?.confirmPassword,
            totalSales = "0.0",
            totalExpenses = "0.0",
            totalProfit = "0.0",
            salesReceiptTotalToday = "0.0",
            creditReceiptTotalToday = "0.0",
            mostSoldGoodToday = "",
            mostSoldGoodQuantity = "",
        )

        val feature = FeaturesToPin(
            TotalSales = false,
            TotalExpenses = false,
            TotalProfit = false,
            DailyReport = false,
            StockTotalValue = false,
            TotalAmountOwingCustomers = false,
            EditStocks = false,
            EditCustomers = false,
            DeleteStocks = false,
            DeleteCustomers = false,
            EditSales = false,
            DeleteSales = false,
        )

        userId?.let {uid->
            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_USERS).document(uid).get().addOnSuccessListener {
                if(it.exists()){
                    it.reference.update(user.toMap())
                        .addOnSuccessListener {
                            this.userData.value = user
                            inProgress.value = false
                            onSuccess.invoke()
                        }
                        .addOnFailureListener{
                            handleException(it,"Cannot Update User")
                            inProgress.value = false
                        }
                }
                else{
                    db.collection(Constants.COLLECTION_NAME_USERS).document(userId).set(user)
                    getUserData(userId)
                    inProgress.value = false
                    onSuccess.invoke()
                }
            }
                .addOnFailureListener{exc->
                    handleException(exc,"Cannot create user")
                    inProgress.value = false
                }

            db.collection(Constants.COLLECTION_NAME_FEATURESTOPIN).document(uid).set(feature)
                .addOnSuccessListener {

                }

                .addOnFailureListener {exc->
                    handleException(exc)
                }

        }
    }

    fun resetPassword(email: String,onSuccess: () -> Unit) {
        if (email.isEmpty()) {
            popupNotification.value = Event("please fill in email address")
        }
        else {
            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                popupNotification.value = Event("Password reset email sent")
                onSuccess.invoke()

            }
                .addOnFailureListener { exc ->
                    handleException(exc)
                }
        }
    }

    fun logOut(onSuccess: () -> Unit){
        auth.signOut()
        signedIn.value = false
        userData.value = null
        onSuccess.invoke()
    }

    fun checkForPayment(){
        val currentDate = System.currentTimeMillis()
        val userPaymentDate = userData.value?.paymentDate
        if (userPaymentDate != null && currentDate > userPaymentDate){
          paymentDue.value = true
        }
    }

    fun updatePaymentDate(){
        val userid = auth.currentUser?.uid

        val currentDate = LocalDate.now()
        val paymentDueDate = currentDate.plusDays(30).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        val updateDate = mapOf(
            "paymentDate" to paymentDueDate
        )
        db.collection(Constants.COLLECTION_NAME_USERS).document(userid.toString()).update(updateDate).addOnSuccessListener {
            getUserData(userid.toString())
            popupNotification.value = Event("payment date updated successfully")
        }
        .addOnFailureListener {exc->
                handleException(exc)
            }
    }

    fun retrieveUser() {
        val uid = auth.currentUser?.uid
        db.collection(Constants.COLLECTION_NAME_USERS).whereEqualTo("userId", uid).get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                        val user = querySnapshot.documents[0].toObject(User::class.java)
                        this.userData.value = user
                }
            }
            .addOnFailureListener {

            }
    }


    private fun getUserData(userId:String){
        inProgress.value = true
        db.collection(Constants.COLLECTION_NAME_USERS).document(userId).get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                userData.value = user
                inProgress.value = false
                checkForPayment()
                checkExpiryDate()
                retrievePasswordFeatures()
                retrieveSales()
                retrieveInvoices()
                retrieveCustomer()
                retrieveStocks()
                retrieveHomeExpense()
                retrieveExpense()
                refreshSales()
                getTotalStockValue()
                getTotalAmountOwingCustomer()
                getAllSaleReceiptToday()
                getTotalSaleReceiptToday()
                getTotalProfitToday()
                getTotalExpenseToday()
                getTotalCreditReceiptToday()
                getMostPurchasedProductsToday()
                getAllPurchasedProductsToday()


            }
            .addOnFailureListener{exc->
                handleException(exc,"Cannot retrieve user data")
                inProgress.value = false
            }

    }

    // home sales backend
    private fun retrieveSales() {

        val userId = auth.currentUser?.uid

        val date = System.currentTimeMillis()

        val startOfDay = date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000).toLong()
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000).toLong()

        if (userId != null) {
            inProgress.value = true

            db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("salesDate", startOfDay)
                .whereLessThan("salesDate", endOfDay)
                .orderBy("salesDate", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnSuccessListener {document->
                    val isCurrentDate = isDateCurrent(userData.value!!.currentDate!!.toLong())
                    if (!isCurrentDate) {
                        val update = mapOf(
                            "totalSales" to "0.0",
                            "totalExpenses" to "0.0",
                            "totalProfit" to "0.0",
                            "currentDate" to date,
                            "salesReceiptTotalToday" to "0.0",
                            "creditReceiptTotalToday" to "0.0",
                            "mostSoldGoodToday" to "",
                            "mostSoldGoodQuantity" to "",
                            "goodsSold" to ""
                        )

                        val dailyReport =  DailyReport(
                            date = userData.value!!.currentDate!!.toLong(),
                            userId = userId,
                            totalSalesToday = userData.value!!.totalSales.toString(),
                            totalProfitToday = userData.value!!.totalProfit.toString(),
                            salesReceiptTotal = userData.value!!.salesReceiptTotalToday.toString(),
                            creditReceiptTotal = userData.value!!.creditReceiptTotalToday.toString(),
                            totalExpensesToday = userData.value!!.totalExpenses.toString(),
                            profitAfterExpense = (userData.value!!.totalProfit!!.toDouble()- userData.value!!.totalExpenses!!.toDouble()).toString(),
                            mostSoldGood = userData.value!!.mostSoldGoodToday.toString(),
                            mostSoldGoodQty = userData.value!!.mostSoldGoodQuantity,
                            goodsSold = userData.value!!.goodsSold
                        )

                        db.collection(Constants.COLLECTION_NAME_DAILYREPORT).document(UUID.randomUUID().toString()).set(dailyReport)
                            .addOnSuccessListener {

                        }
                            .addOnFailureListener {

                            }

                        db.collection(Constants.COLLECTION_NAME_USERS).document(userId.toString()).update(update).
                        addOnSuccessListener {
                            getAllSaleReceiptToday()
                            getTotalSaleReceiptToday()
                            getTotalProfitToday()
                            getTotalExpenseToday()
                            getTotalCreditReceiptToday()
                            getMostPurchasedProductsToday()
                            getAllPurchasedProductsToday()
                            retrieveUser()
                        }
                            .addOnFailureListener {exc->
                                handleException(exc)
                            }
                    }
                    convertHomeSales(document,salesHomeData)
                    inProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch sales")
                    inProgress.value = false
                }
        }
        else{
            handleException(customMessage = "Error: Unable to fetch sales")
        }

    }

    private fun isDateCurrent(date: Long): Boolean {
        // Implement your logic to check if the date is the current date
        // You may compare it with the current date or use other conditions based on your data structure
        // For simplicity, let's consider it's not the current date if it's not within the same day
        val currentDateTime = System.currentTimeMillis()
        return currentDateTime / (24 * 60 * 60 * 1000) == date / (24 * 60 * 60 * 1000)
    }

    private fun convertHomeSales(documents: QuerySnapshot, outState: MutableState<List<Sales>>){
        val newSales = mutableListOf<Sales>()
        documents.forEach{doc->
            val sale = doc.toObject(Sales::class.java)
            newSales.add(sale)
        }
        val sortedSales = newSales.sortedByDescending { it.salesDate }
        outState.value = sortedSales

    }


// add customer backend
    fun createCustomer(
        name:String?=null,
        number:String?=null,
        address:String?=null,
        balance:String?="0", onSuccess: ()->Unit
    ){
        val userId = auth.currentUser?.uid
        inProgress.value = true
        if(userId!=null){
            val customerId = UUID.randomUUID().toString()

            val customer = Customer(
                customerId = customerId,
                userId = userId,
                customerName = name?.toLowerCase(),
                customerNumber = number,
                customerAddress = address,
                customerBalance = balance,
            )
            checkCustomerExistence(name.toString(), customer, customerId, userId, onSuccess)
        }
        else{
            handleException(customMessage = "Error:Failed to add customer")
            inProgress.value = false
        }

    }

    fun checkCustomerExistence(name: String, customer: Customer, customerId:String,userId: String, onSuccess: () -> Unit) {
        db.collection(Constants.COLLECTION_NAME_CUSTOMERS)
            .whereEqualTo("userId",userId)
            .whereEqualTo("customerName", name.trim().toLowerCase())
            .get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    // Customer exists in customers list
                    inProgress.value = false
                    popupNotification.value = Event("Customer already exists.")
                } else {
                    // Customer does not exist add one
                    db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customerId).set(customer)
                        .addOnSuccessListener {
                            popupNotification.value = Event("Customer Added Successfully")
                            retrieveCustomer()
                            onSuccess.invoke()
                            inProgress.value = false
                        }
                        .addOnFailureListener {exc->
                            handleException(exc,"Error:Failed to add customer")
                            inProgress.value = false
                        }

                }
            }
            .addOnFailureListener { exc ->
                handleException(exc, "Failed to check customer existence")
            }
    }

    fun retrieveCustomer() {

        val userId = auth.currentUser?.uid

        if (userId != null) {
            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_CUSTOMERS).whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener {document->
                    convertCustomer(document,customerData)
                    inProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch customers")
                    inProgress.value = false
                }
        }
        else{
            handleException(customMessage = "Error: Unable to fetch customers")
        }
    }

    private fun convertCustomer(documents:QuerySnapshot,outState:MutableState<List<Customer>>){
        val newCustomer = mutableListOf<Customer>()
        documents.forEach{doc->
            val customer = doc.toObject(Customer::class.java)
            newCustomer.add(customer)
        }
        val sortedCustomer = newCustomer.sortedBy { it.customerName }
        outState.value = sortedCustomer

    }

    fun retrieveCustomerWithHighestBalance() {

        val userId = auth.currentUser?.uid

        if (userId != null) {
            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_CUSTOMERS).whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener {document->
                    convertCustomerWithHighestBalance(document,customerData)
                    inProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch customers")
                    inProgress.value = false
                }
        }
        else{
            handleException(customMessage = "Error: Unable to fetch customers")
        }
    }

    private fun convertCustomerWithHighestBalance(documents:QuerySnapshot,outState:MutableState<List<Customer>>){
        val newCustomer = mutableListOf<Customer>()
        documents.forEach{doc->
            val customer = doc.toObject(Customer::class.java)
            newCustomer.add(customer)
        }
        val sortedCustomer = newCustomer.sortedByDescending { it.customerBalance?.toDouble() }
        outState.value = sortedCustomer

    }

    fun retrieveCustomerWithLowestBalance() {

        val userId = auth.currentUser?.uid

        if (userId != null) {
            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_CUSTOMERS).whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener {document->
                    convertCustomerWithLowestBalance(document,customerData)
                    inProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch customers")
                    inProgress.value = false
                }
        }
        else{
            handleException(customMessage = "Error: Unable to fetch customers")
        }
    }

    private fun convertCustomerWithLowestBalance(documents:QuerySnapshot,outState:MutableState<List<Customer>>){
        val newCustomer = mutableListOf<Customer>()
        documents.forEach{doc->
            val customer = doc.toObject(Customer::class.java)
            newCustomer.add(customer)
        }
        val sortedCustomer = newCustomer.sortedBy { it.customerBalance?.toDouble() }
        outState.value = sortedCustomer

    }




    // add stock backend
    @RequiresApi(Build.VERSION_CODES.O)
    fun createStock(name:String, purchasePrice:String, sellingPrice:String, expiryDate:String, quantity:String, monthToExpire:String, twoWeekToExpire:String, oneWeekToExpire:String, onSuccess:()->Unit){
        val stockId = UUID.randomUUID().toString()
        val userId = auth.currentUser?.uid
        val stock = Stock(
            stockId = stockId,
            userId = userId.toString(),
            stockName = name.toLowerCase(),
            stockPurchasePrice = purchasePrice,
            stockSellingPrice = sellingPrice,
            stockFixedSellingPrice = sellingPrice,
            stockTotalPrice = sellingPrice,
            stockExpiryDate = expiryDate,
            stockQuantity = quantity,
            stockQuantitySold = "0"
        )

        val stockHistory = StockHistory(
            stockId = stockId,
            userId = userId,
            stockName = name.toLowerCase(),
            stockPurchasePrice = purchasePrice,
            stockSellingPrice = sellingPrice,
            stockQuantityAdded = quantity,
            stockDateAdded = System.currentTimeMillis(),
            stockExpiryDate = expiryDate,
            stockOneMonthToExpire = monthToExpire,
            stockTwoWeeksToExpire = twoWeekToExpire,
            stockOneWeekToExpire = oneWeekToExpire

        )

        userId?.let {
            inProgress.value = true
            checkStockExistence(name,stock,stockHistory,stockId,userId, onSuccess)
        }

    }

    fun checkStockExistence(name: String, stock: Stock,stockHistory: StockHistory, stockId:String,userId: String, onSuccess: () -> Unit) {
        db.collection(Constants.COLLECTION_NAME_STOCKS)
            .whereEqualTo("userId",userId)
            .whereEqualTo("stockName", name.trim().toLowerCase())
            .get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    // Stock exists in stocks list
                    inProgress.value = false
                    popupNotification.value = Event("Stock already exists.")
                } else {
                    val randomUid = UUID.randomUUID()
                    // Stock does not exist add one
                    db.collection(Constants.COLLECTION_NAME_STOCKS).document(stockId).set(stock)
                        .addOnSuccessListener {
                            db.collection(Constants.COLLECTION_NAME_STOCKHISTORY).document(randomUid.toString()).set(stockHistory).addOnSuccessListener {
                                stockSelected.value = stock
                                popupNotification.value = Event("Stock added successfully")
                                retrieveStocks()
                                getTotalStockValue()
                                getAllSaleReceiptToday()
                                getTotalSaleReceiptToday()
                                getTotalProfitToday()
                                getTotalExpenseToday()
                                getTotalCreditReceiptToday()
                                getMostPurchasedProductsToday()
                                getAllPurchasedProductsToday()
                                onSuccess.invoke()
                                inProgress.value = false
                            }
                                .addOnFailureListener {exc->
                                    handleException(exc, "failed to generate stock history")
                                }

                        }
                        .addOnFailureListener{exc->
                            handleException(exc,"failed to add stock")
                        }



                }
            }
            .addOnFailureListener { exc ->
                handleException(exc, "Failed to check stock existence")
            }
    }
     fun retrieveStocks(){

        val userId = auth.currentUser?.uid

        if(userId!=null){

            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_STOCKS).whereEqualTo("userId",userId).get()
                .addOnSuccessListener {document->
                    convertStocks(document,stocks)
                    inProgress.value = false
                }
                .addOnFailureListener{exc->
                    handleException(exc,"unable fetch stocks")
                    inProgress.value = false
                }
        }

        else{
            handleException(customMessage = "Error: unable to fetch stocks")
        }

    }

    private fun convertStocks(document:QuerySnapshot,outState:MutableState<List<Stock>>){
        val newStock = mutableListOf<Stock>()
        document.forEach{doc->
            val stock = doc.toObject(Stock::class.java)
            newStock.add(stock)
        }
        val sortedStock = newStock.sortedBy { it.stockName }
        outState.value = sortedStock
    }

    fun filterStocksByMostSold(){

        val userId = auth.currentUser?.uid

        if(userId != null){

            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_STOCKS)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { document ->
                    convertStockByMostSold(document, stocks)
                    inProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "unable to fetch stocks")
                    inProgress.value = false
                }
        } else {
            handleException(customMessage = "Error: unable to fetch stocks")
        }
    }

    private fun convertStockByMostSold(document:QuerySnapshot,outState:MutableState<List<Stock>>){
        val newStock = mutableListOf<Stock>()
        document.forEach{doc->
            val stock = doc.toObject(Stock::class.java)
            newStock.add(stock)
        }
        val sortedStock = newStock.sortedByDescending { it.stockQuantitySold.toInt() }
        outState.value = sortedStock
    }

    fun filterStocksByLeastSold(){

        val userId = auth.currentUser?.uid

        if(userId != null){

            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_STOCKS)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { document ->
                    convertStockByLeastSold(document, stocks)
                    inProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "unable to fetch stocks")
                    inProgress.value = false
                }
        } else {
            handleException(customMessage = "Error: unable to fetch stocks")
        }
    }


    private fun convertStockByLeastSold(document:QuerySnapshot,outState:MutableState<List<Stock>>){
        val newStock = mutableListOf<Stock>()
        document.forEach{doc->
            val stock = doc.toObject(Stock::class.java)
            newStock.add(stock)
        }
        val sortedStock = newStock.sortedBy { it.stockQuantitySold.toInt() }
        outState.value = sortedStock
    }


    fun filterStocksByHighestQuantity(){

        val userId = auth.currentUser?.uid

        if(userId != null){

            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_STOCKS)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { document ->
                    convertStockByHighestQty(document, stocks)
                    inProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "unable to fetch stocks")
                    inProgress.value = false
                }
        } else {
            handleException(customMessage = "Error: unable to fetch stocks")
        }
    }

    private fun convertStockByHighestQty(document:QuerySnapshot,outState:MutableState<List<Stock>>){
        val newStock = mutableListOf<Stock>()
        document.forEach{doc->
            val stock = doc.toObject(Stock::class.java)
            newStock.add(stock)
        }
        val sortedStock = newStock.sortedByDescending { it.stockQuantity.toInt() }
        outState.value = sortedStock
    }


    fun filterStocksByLowestQuantity(){

        val userId = auth.currentUser?.uid

        if(userId != null){

            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_STOCKS)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { document ->
                    convertStockByLowestQty(document, stocks)
                    inProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "unable to fetch stocks")
                    inProgress.value = false
                }
        } else {
            handleException(customMessage = "Error: unable to fetch stocks")
        }
    }

    private fun convertStockByLowestQty(document:QuerySnapshot,outState:MutableState<List<Stock>>){
        val newStock = mutableListOf<Stock>()
        document.forEach{doc->
            val stock = doc.toObject(Stock::class.java)
            newStock.add(stock)
        }
        val sortedStock = newStock.sortedBy { it.stockQuantity.toInt() }
        outState.value = sortedStock
    }





//add expense backend
    fun AddExpense(name:String,description:String,category:String,amount:String,onSuccess:()->Unit){
        addExpenseProgress.value = true
        val currentUid = auth.currentUser?.uid
        if(currentUid!=null){
            val expenseUid = UUID.randomUUID().toString()
            var expense = Expense(
                expenseId = expenseUid,
                expenseName = name,
                userId = currentUid,
                expenseDescription = description,
                expenseDate = System.currentTimeMillis(),
                expenseCategory = category,
                expenseAmount = amount
            )

            db.collection(Constants.COLLECTION_NAME_EXPENSE).document(expenseUid).set(expense)
                .addOnSuccessListener {
                    userData.value?.let { user ->
                        try {
                            val updatedTotalExpenses = user.totalExpenses?.toDouble()!! + amount.toDouble()
                            user.totalExpenses = updatedTotalExpenses.toString()
                            db.collection(Constants.COLLECTION_NAME_USERS).document(currentUid).update("totalExpenses",updatedTotalExpenses.toString())
                        } catch (e: NumberFormatException) {
                            // Handle the case where totalPrice or totalSales is not a valid number
                        }
                    }
                    popupNotification.value = Event("Expense added successfully")
                    addExpenseProgress.value = false
                    onSuccess.invoke()
                    getUserData(currentUid.toString())
                    retrieveSales()
                    retrieveCustomer()
                    retrieveHomeExpense()
                    retrieveExpense()
                    retrieveStocks()
                }
                .addOnFailureListener{exc->
                    addExpenseProgress.value = false
                    handleException(exc,"Unable to add expense")
                }
        }
        else{
            addExpenseProgress.value = false
            handleException(customMessage = "Unable to add expense")
        }

    }

    fun retrieveHomeExpense(){

        val currentUid  = auth.currentUser?.uid
        val date = System.currentTimeMillis()

        val startOfDay = date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000).toLong()
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000).toLong()

        if(currentUid!=null){
            expenseProgress.value = true
            db.collection(Constants.COLLECTION_NAME_EXPENSE).whereEqualTo("userId",currentUid)
                .whereGreaterThanOrEqualTo("expenseDate", startOfDay)
                .whereLessThan("expenseDate", endOfDay)
                .orderBy("expenseDate", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnSuccessListener {document->
                    convertHomeExpense(document,expenseHomeData)
                    expenseProgress.value = false
                }
                .addOnFailureListener{exc->
                    handleException(exc,"Unable to retrieve expenses")
                    expenseProgress.value = false
                }
        }

        else{
            handleException(customMessage = "Unable to retrieve expense")
            expenseProgress.value = false
        }
    }

    fun convertHomeExpense(documents: QuerySnapshot,outState: MutableState<List<Expense>>){
        val newExpense = mutableListOf<Expense>()
        documents.forEach{doc->
            val expense = doc.toObject(Expense::class.java)
            newExpense.add(expense)
        }
        val sortedExpense = newExpense.sortedByDescending { it.expenseDate }
        outState.value = sortedExpense
    }

    //add sale backend
    fun onAddSale(
        customerName: String,
        customerId: String,
        totalPrice: String,
        totalProfit: String,
        totalQuantity: String,
        sales: List<SingleSale>,
        sale: Sales,
        stockQuantityList: List<Pair<Stock, Int>>,
        exist: Boolean,
        onSalesSuccess: () -> Unit
    ) {
        inProgress.value = true
        val currentUid = auth.currentUser?.uid
        if (currentUid != null) {
            try {
                userData.value?.let { user ->
                    val updatedTotalSales = user.totalSales?.toDouble()!! + totalPrice.toDouble()
                    val updatedTotalProfit = user.totalProfit?.toDouble()!! + totalProfit.toDouble()
                    user.totalSales = updatedTotalSales.toString()
                    user.totalProfit = updatedTotalProfit.toString()
                    val updates = mapOf(
                        "totalSales" to updatedTotalSales.toString(),
                        "totalProfit" to updatedTotalProfit.toString()
                    )
                    db.collection(Constants.COLLECTION_NAME_USERS).document(currentUid).update(updates)
                }

                // add the sales list to the existing list of sales

                for(singleSale in sales) {
                    sale.sales = sale.sales.orEmpty() + singleSale


                    // Calculate updated total amount and total quantity
                    val updatedTotalAmount =
                        sale.sales?.sumByDouble { it.totalPrice?.toDoubleOrNull() ?: 0.0 }
                    val updatedTotalQuantity =
                        sale.sales?.sumByDouble { it.quantity?.toDoubleOrNull() ?: 0.0 }
                    val updatedTotalProfit =
                        sale.sales?.sumByDouble { it.profit?.toDoubleOrNull() ?: 0.0 }
                    val updatedAmountPayed =
                        sale.sales?.sumByDouble { it.totalPrice?.toDoubleOrNull() ?: 0.0 }

                    // Update the total amount and total quantity in the Sales object
                    sale.totalPrice = updatedTotalAmount.toString()
                    sale.totalQuantity = updatedTotalQuantity.toString()
                    sale.totalProfit = updatedTotalProfit.toString()
                    sale.amountPaid = updatedAmountPayed.toString()
                }

                // Save the entire sale object to the database
                db.collection(Constants.COLLECTION_NAME_SALES).document(sale.salesId.toString()).set(sale)
                    .addOnSuccessListener {
                        if(exist) {
                            onMultipleStocksSold(stockQuantityList, onSalesSuccess)
                            this.salesDetail.value = sale
                            popupNotification.value = Event("Sales created successfully")
                            inProgress.value = false
                            retrieveInvoices()
                            refreshSales()
                            retrieveSales()
                            getTotalAmountOwingCustomer()
                            getMostPurchasedProductsToday()
                            getAllPurchasedProductsToday()
                            getAllSaleReceiptToday()
                            getTotalSaleReceiptToday()
                            getTotalProfitToday()
                            getTotalExpenseToday()
                        }
                        else{
                            this.salesDetail.value = sale
                            popupNotification.value = Event("Sales created successfully")
                            inProgress.value = false
                            retrieveInvoices()
                            refreshSales()
                            retrieveSales()
                            getTotalAmountOwingCustomer()
                            getMostPurchasedProductsToday()
                            getAllPurchasedProductsToday()
                            getAllSaleReceiptToday()
                            getTotalSaleReceiptToday()
                            getTotalProfitToday()
                            getTotalExpenseToday()
                            onSalesSuccess.invoke()
                        }
                    }
                    .addOnFailureListener { exc ->
                        handleException(exc, customMessage = "Unable to make sale")
                        inProgress.value = false
                    }
            } catch (e: NumberFormatException) {
                // Handle the case where totalPrice or totalSales is not a valid number
                handleException(e, customMessage = "Error: Unable to make sale")
                inProgress.value = false
            }
        } else {
            handleException(customMessage = "Error: Unable to make sale")
            inProgress.value = false
        }
    }


    fun onAddCredit(
        customerName: String,
        customerId: String,
        totalPrice: String,
        totalProfit: String,
        totalQuantity: String,
        sales: List<SingleSale>,
        sale: Sales,
        stockQuantityList: List<Pair<Stock, Int>>,
        exist:Boolean,
        onSalesSuccess: () -> Unit
    ) {
        inProgress.value = true
        val currentUid = auth.currentUser?.uid
        if (currentUid != null) {
            try {
                userData.value?.let { user ->
                    val updatedTotalSales = user.totalSales?.toDouble()!! + totalPrice.toDouble()
                    val updatedTotalProfit = user.totalProfit?.toDouble()!! + totalProfit.toDouble()
                    user.totalSales = updatedTotalSales.toString()
                    user.totalProfit = updatedTotalProfit.toString()
                    val updates = mapOf(
                        "totalSales" to updatedTotalSales.toString(),
                        "totalProfit" to updatedTotalProfit.toString()
                    )
                    db.collection(Constants.COLLECTION_NAME_USERS).document(currentUid).update(updates)
                }

                // add the sales list to the existing list of sales



                for(singleSale in sales) {
                    sale.sales = sale.sales.orEmpty() + singleSale


                    // Calculate updated total amount and total quantity
                    val updatedTotalAmount =
                        sale.sales?.sumByDouble { it.totalPrice?.toDoubleOrNull() ?: 0.0 }
                    val updatedTotalQuantity =
                        sale.sales?.sumByDouble { it.quantity?.toDoubleOrNull() ?: 0.0 }
                     val updatedBalance =
                        updatedTotalAmount!! - sale.amountPaid?.toDouble()!!
                    val updatedTotalProfit =
                        sale.sales?.sumByDouble { it.profit?.toDoubleOrNull() ?: 0.0 }


                    // Update the total amount and total quantity in the Sales object
                    sale.totalPrice = updatedTotalAmount.toString()
                    sale.totalQuantity = updatedTotalQuantity.toString()
                    sale.totalProfit = updatedTotalProfit.toString()
                    sale.balance = updatedBalance.toString()
                }

                // update customer balance
                db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customerId).get()
                    .addOnSuccessListener {documentSnapshot->
                        if(exist){
                            onMultipleStocksSold(stockQuantityList,onSalesSuccess)
                            val customer = documentSnapshot.toObject(Customer::class.java)

                            customer.let {

                                val updatedbalance = customer?.customerBalance!!.toDouble() + totalPrice.toDouble()

                                var update = mapOf(
                                    "customerBalance" to updatedbalance.toString()
                                )

                                db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customerId).update(update)
                            }
                        }
                        else{
                            val customer = documentSnapshot.toObject(Customer::class.java)

                            customer.let {

                                val updatedbalance = customer?.customerBalance!!.toDouble() + totalPrice.toDouble()

                                var update = mapOf(
                                    "customerBalance" to updatedbalance.toString()
                                )

                                db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customerId).update(update)
                            }
                            onSalesSuccess.invoke()
                        }
                    }
                    .addOnFailureListener {

                    }
                // Save the entire sale object to the database
                db.collection(Constants.COLLECTION_NAME_SALES).document(sale.salesId.toString()).set(sale)
                    .addOnSuccessListener {
                        this.salesDetail.value = sale
                        popupNotification.value = Event("Sales created successfully")
                        inProgress.value = false
                        retrieveInvoices()
                        refreshSales()
                        retrieveSales()
                        getTotalAmountOwingCustomer()
                        getMostPurchasedProductsToday()
                        getAllPurchasedProductsToday()
                        getAllSaleReceiptToday()
                        getTotalSaleReceiptToday()
                        getTotalProfitToday()
                        getTotalExpenseToday()
                        getTotalCreditReceiptToday()
                        retrieveCustomer()
                    }
                    .addOnFailureListener { exc ->
                        handleException(exc, customMessage = "Unable to make sale")
                        inProgress.value = false
                    }
            } catch (e: NumberFormatException) {
                // Handle the case where totalPrice or totalSales is not a valid number
                handleException(e, customMessage = "Error: Unable to make sale")
                inProgress.value = false
            }
        } else {
            handleException(customMessage = "Error: Unable to make sale")
            inProgress.value = false
        }
    }




    // make sales  backend
    @RequiresApi(Build.VERSION_CODES.O)
    fun onNewSales(customerName:String, sales:List<SingleSale>, customerId:String, totalPrice:String, totalProfit:String, totalQuantity:String, stockQuantityList: List<Pair<Stock, Int>>, exist:Boolean, onSalesSuccess:() -> Unit){
        inProgress.value = true
        val currentUid = auth.currentUser?.uid
        if(currentUid!=null){
            val salesUid = UUID.randomUUID().toString()
            val salesNumber = userData.value?.saleNo!!.toInt()+1


            val sales = Sales(
                userId = currentUid,
                salesId =  salesUid,
                salesNo = "SR$salesNumber",
                sales = sales,
                type = "SR",
                salesDate = System.currentTimeMillis(),
                customerName = customerName,
                customerId = customerId,
                totalPrice = totalPrice,
                totalProfit = totalProfit,
                amountPaid = totalPrice,
                balance = "0.0",
                totalQuantity = totalQuantity
            )

            db.collection(Constants.COLLECTION_NAME_SALES).document(salesUid).set(sales)
                .addOnSuccessListener {
                    if(exist) {
                        onMultipleStocksSold(stockQuantityList, onSalesSuccess)
                        val updatedTotalSales = userData.value?.totalSales?.toDouble()!! + totalPrice.toDouble()
                        val updatedTotalProfit = userData.value?.totalProfit?.toDouble()!! + totalProfit.toDouble()
                        userData.value?.totalSales = updatedTotalSales.toString()
                        userData.value?.totalProfit = updatedTotalProfit.toString()
                        val updates = mapOf(
                            "totalSales" to updatedTotalSales.toString(),
                            "totalProfit" to updatedTotalProfit.toString(),
                            "saleNo" to salesNumber.toString()
                        )
                        db.collection(Constants.COLLECTION_NAME_USERS).document(currentUid).update(updates)

                        this.salesDetail.value = sales
                        popupNotification.value = Event("Sales created successfully")
                        inProgress.value = false
                        retrieveInvoices()
                        refreshSales()
                        retrieveSales()
                        retrieveUser()
                        getMostPurchasedProductsToday()
                        getAllPurchasedProductsToday()
                        getTotalCreditReceiptToday()
                        getAllSaleReceiptToday()
                        getTotalSaleReceiptToday()
                        getTotalProfitToday()
                        getTotalExpenseToday()
                    }
                    else{
                        val updatedTotalSales = userData.value?.totalSales?.toDouble()!! + totalPrice.toDouble()
                        val updatedTotalProfit = userData.value?.totalProfit?.toDouble()!! + totalProfit.toDouble()
                        userData.value?.totalSales = updatedTotalSales.toString()
                        userData.value?.totalProfit = updatedTotalProfit.toString()
                        val updates = mapOf(
                            "totalSales" to updatedTotalSales.toString(),
                            "totalProfit" to updatedTotalProfit.toString(),
                            "saleNo" to salesNumber.toString()
                        )
                        db.collection(Constants.COLLECTION_NAME_USERS).document(currentUid).update(updates)

                        this.salesDetail.value = sales
                        popupNotification.value = Event("Sales created successfully")
                        inProgress.value = false
                        retrieveInvoices()
                        refreshSales()
                        retrieveSales()
                        retrieveUser()
                        getMostPurchasedProductsToday()
                        getAllPurchasedProductsToday()
                        getTotalCreditReceiptToday()
                        getAllSaleReceiptToday()
                        getTotalSaleReceiptToday()
                        getTotalProfitToday()
                        getTotalExpenseToday()
                        onSalesSuccess.invoke()
                    }

                }
                .addOnFailureListener{exc->
                    handleException(exc, customMessage = "Unable to make sale")
                    inProgress.value = false
                }
        }
        else{
            handleException(customMessage = "Error: Unable to make sales")
            inProgress.value = false
        }
    }


    // add credit backend

    @RequiresApi(Build.VERSION_CODES.O)
    fun onNewCredit(customerName:String, sales:List<SingleSale>, customerId:String, totalPrice:String, totalProfit:String, totalQuantity:String, customer: Customer, stockQuantityList: List<Pair<Stock, Int>>, exist: Boolean, onSalesSuccess:() -> Unit){
        inProgress.value = true
        val currentUid = auth.currentUser?.uid
        if(currentUid!=null){
            val salesUid = UUID.randomUUID().toString()
            val salesNumber = userData.value?.saleNo!!.toInt()+1

            val sales = Sales(
                userId = currentUid,
                salesId =  salesUid,
                salesNo = "CR$salesNumber",
                sales = sales,
                type = "CR",
                salesDate = System.currentTimeMillis(),
                customerName = customerName,
                customerId = customerId,
                totalPrice = totalPrice,
                totalProfit = totalProfit,
                amountPaid = "0.0",
                balance = totalPrice,
                totalQuantity = totalQuantity
            )

            db.collection(Constants.COLLECTION_NAME_SALES).document(salesUid).set(sales)
                .addOnSuccessListener {
                    if(exist) {
                        onMultipleStocksSold(stockQuantityList,onSalesSuccess)
                        userData.value?.let { user ->
                            try {
                                val updatedTotalSales = user.totalSales?.toDouble()!! + totalPrice.toDouble()
                                val updatedTotalProfit = user.totalProfit?.toDouble()!! + totalProfit.toDouble()
                                user.totalSales = updatedTotalSales.toString()
                                user.totalProfit = updatedTotalProfit.toString()
                                val updates = mapOf(
                                    "totalSales" to updatedTotalSales.toString(),
                                    "totalProfit" to updatedTotalProfit.toString(),
                                    "saleNo" to salesNumber.toString()
                                )
                                db.collection(Constants.COLLECTION_NAME_USERS).document(currentUid).update(updates)
                            } catch (e: NumberFormatException) {
                                // Handle the case where totalPrice or totalSales is not a valid number
                            }
                        }
                        customer.let {
                            try{
                                val updatedCustomerBalance = customer.customerBalance?.toDouble()!! + totalPrice.toDouble()
                                val update = mapOf(
                                    "customerBalance" to updatedCustomerBalance.toString()
                                )

                                db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customer.customerId.toString()).update(update)
                            }
                            catch (_: NumberFormatException){

                            }
                        }
                        this.salesDetail.value = sales
                        popupNotification.value = Event("Sales created successfully")
                        inProgress.value = false
                        retrieveInvoices()
                        refreshSales()
                        retrieveSales()
                        retrieveCustomer()
                        getTotalAmountOwingCustomer()
                        getMostPurchasedProductsToday()
                        getAllPurchasedProductsToday()
                        getTotalCreditReceiptToday()
                        getAllSaleReceiptToday()
                        getTotalSaleReceiptToday()
                        getTotalProfitToday()
                        getTotalExpenseToday()
                        retrieveUser()
                    }
                    else{
                        userData.value?.let { user ->
                            try {
                                val updatedTotalSales = user.totalSales?.toDouble()!! + totalPrice.toDouble()
                                val updatedTotalProfit = user.totalProfit?.toDouble()!! + totalProfit.toDouble()
                                user.totalSales = updatedTotalSales.toString()
                                user.totalProfit = updatedTotalProfit.toString()
                                val updates = mapOf(
                                    "totalSales" to updatedTotalSales.toString(),
                                    "totalProfit" to updatedTotalProfit.toString(),
                                    "saleNo" to salesNumber.toString()
                                )
                                db.collection(Constants.COLLECTION_NAME_USERS).document(currentUid).update(updates)
                            } catch (e: NumberFormatException) {
                                // Handle the case where totalPrice or totalSales is not a valid number
                            }
                        }
                        customer.let {
                            try{
                                val updatedCustomerBalance = customer.customerBalance?.toDouble()!! + totalPrice.toDouble()
                                val update = mapOf(
                                    "customerBalance" to updatedCustomerBalance.toString()
                                )

                                db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customer.customerId.toString()).update(update)
                            }
                            catch (_: NumberFormatException){

                            }
                        }
                        this.salesDetail.value = sales
                        popupNotification.value = Event("Sales created successfully")
                        inProgress.value = false
                        retrieveInvoices()
                        refreshSales()
                        retrieveSales()
                        retrieveCustomer()
                        getTotalAmountOwingCustomer()
                        getMostPurchasedProductsToday()
                        getAllPurchasedProductsToday()
                        getTotalCreditReceiptToday()
                        getAllSaleReceiptToday()
                        getTotalSaleReceiptToday()
                        getTotalProfitToday()
                        getTotalExpenseToday()
                        retrieveUser()
                        onSalesSuccess.invoke()
                    }
                }
                .addOnFailureListener{exc->
                    handleException(exc, customMessage = "Unable to make sale")
                    inProgress.value = false
                }
        }
        else{
            handleException(customMessage = "Error: Unable to make sales")
            inProgress.value = false
        }
    }

    private fun refreshSales(){

        val currentUid = auth.currentUser?.uid
        val salesId = salesDetail.value?.salesId

        if(currentUid!=null){
            refreshSalesProgress.value = true
            db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("userId",currentUid).whereEqualTo("salesId",salesId)
                .get()
                .addOnSuccessListener {document->
                    convertSales(document,salesData)
                    refreshSalesProgress.value = false
                }
                .addOnFailureListener{exc->
                    handleException(exc,"Cannot fetch posts")
                    refreshSalesProgress.value = false
                }

        }
        else{
            handleException(customMessage = "Error:Unable to refresh sales")
            refreshSalesProgress.value = false
        }

    }

    private fun convertSales(documents: QuerySnapshot, outState: MutableState<List<Sales>>){
        val newSales = mutableListOf<Sales>()
        documents.forEach{doc->
            val sale = doc.toObject(Sales::class.java)
            newSales.add(sale)
        }
        val sortedSales = newSales.sortedBy {it.customerName}
        outState.value = sortedSales

    }

    //search for customers
    fun customerSearch(name:String){
        if(name.isNotEmpty()){
            var userId = auth.currentUser?.uid
            customerSearchProgress.value = true
            db.collection(Constants.COLLECTION_NAME_CUSTOMERS)
                .whereEqualTo("userId",userId)
                .whereEqualTo("customerName",name.trim().toLowerCase())
                .get()
                .addOnSuccessListener {
                    convertSearchedCustomer(it,searchedCustomer)
                    customerSearchProgress.value = false
                }
                .addOnFailureListener{exc->
                    handleException(exc,"failed to search for customer")
                    customerSearchProgress.value = false
                }
        }
        else{
            retrieveCustomer()
        }
    }


    // search for customers as user type

    fun customerSearchWhenTyping(name: String) {
        if (name.isNotEmpty()) {
            var userId = auth.currentUser?.uid
            customerSearchProgress.value = true
            val searchQuery = name.trim().toLowerCase()
            val endQuery = searchQuery + '\uf8ff' // Unicode character that is higher than any other character

            db.collection(Constants.COLLECTION_NAME_CUSTOMERS)
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("customerName", searchQuery)
                .whereLessThan("customerName", endQuery)
                .get()
                .addOnSuccessListener { snapshot ->
                    convertSearchedCustomer(snapshot, searchedCustomer)
                    customerSearchProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to search for customer")
                    customerSearchProgress.value = false
                }
        } else {
            retrieveCustomer()
        }
    }


    private fun convertSearchedCustomer(documents:QuerySnapshot,outState:MutableState<List<Customer>>){
        val newCustomer = mutableListOf<Customer>()
        documents.forEach{doc->
            val customer = doc.toObject(Customer::class.java)
            newCustomer.add(customer)
        }
        val sortedCustomer = newCustomer.sortedBy { it.customerName }
        outState.value = sortedCustomer

    }

    //search for stocks
    fun stockSearch(name:String){
        if(name.isNotEmpty()){
            var userId = auth.currentUser?.uid
            stockSearchProgress.value = true
            db.collection(Constants.COLLECTION_NAME_STOCKS)
                .whereEqualTo("userId",userId)
                .whereEqualTo("stockName",name.trim().toLowerCase())
                .get()
                .addOnSuccessListener {
                    convertSearchedStocks(it,searchedStocks)
                    stockSearchProgress.value = false
                }
                .addOnFailureListener{exc->
                    handleException(exc,"failed to search for stock")
                    stockSearchProgress.value = false
                }
        }
        else{
            retrieveStocks()
        }
    }

    // search for stock as user type
    fun stockSearchWhenTyping(name: String) {
        if (name.isNotEmpty()) {
            var userId = auth.currentUser?.uid
            stockSearchProgress.value = true
            val searchQuery = name.trim().toLowerCase()
            val endQuery = searchQuery + '\uf8ff' // Unicode character that is higher than any other character

            db.collection(Constants.COLLECTION_NAME_STOCKS)
                .whereEqualTo("userId",userId)
                .whereGreaterThanOrEqualTo("stockName", searchQuery)
                .whereLessThan("stockName", endQuery)
                .get()
                .addOnSuccessListener { snapshot ->
                    convertSearchedStocks(snapshot, searchedStocks)
                    stockSearchProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to search for stock")
                    stockSearchProgress.value = false
                }
        } else {
            retrieveStocks()
        }
    }

    private fun convertSearchedStocks(document:QuerySnapshot,outState:MutableState<List<Stock>>){
        val newStock = mutableListOf<Stock>()
        document.forEach{doc->
            val stock = doc.toObject(Stock::class.java)
            newStock.add(stock)
        }
        val sortedStock = newStock.sortedBy { it.stockName }
        outState.value = sortedStock
    }


    //retrieves all expenses
    fun retrieveExpense(){

        val currentUid  = auth.currentUser?.uid

        if(currentUid!=null){
            expenseProgress.value = true
            db.collection(Constants.COLLECTION_NAME_EXPENSE).whereEqualTo("userId",currentUid)
                .orderBy("expenseDate", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {document->
                    convertExpense(document,expenseData)
                    expenseProgress.value = false
                }
                .addOnFailureListener{exc->
                    handleException(exc,"Unable to retrieve expenses")
                    expenseProgress.value = false
                }
        }

        else{
            handleException(customMessage = "Unable to retrieve expense")
            expenseProgress.value = false
        }
    }

    fun convertExpense(documents: QuerySnapshot,outState: MutableState<List<Expense>>){
        val newExpense = mutableListOf<Expense>()
        documents.forEach{doc->
            val expense = doc.toObject(Expense::class.java)
            newExpense.add(expense)
        }
        val sortedExpense = newExpense.sortedByDescending { it.expenseDate }
        outState.value = sortedExpense
    }

    fun expenseSearchByName(name:String){
        val userId = auth.currentUser?.uid
        if(name.isNotEmpty()){
            expenseProgress.value = true
            db.collection(Constants.COLLECTION_NAME_EXPENSE).whereEqualTo("userId", userId)
                .whereEqualTo("expenseName",name.toLowerCase())
                .get()
                .addOnSuccessListener {document->
                    convertExpenseSearch(document,expenseData)
                    expenseProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch sales")
                    print(exc)
                    expenseProgress.value = false
                }
        }
        else{
            retrieveExpense()
        }
    }

    fun expenseSearchByCategory(name:String){
        val userId = auth.currentUser?.uid
        if(name.isNotEmpty()){
            expenseProgress.value = true
            db.collection(Constants.COLLECTION_NAME_EXPENSE).whereEqualTo("userId", userId)
                .whereEqualTo("expenseCategory",name.toLowerCase())
                .get()
                .addOnSuccessListener {document->
                    convertExpenseSearch(document,expenseData)
                    expenseProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch sales")
                    print(exc)
                    expenseProgress.value = false
                }
        }
        else{
            retrieveExpense()
        }
    }

    fun expenseSearchByDescription(name:String){
        val userId = auth.currentUser?.uid
        if(name.isNotEmpty()){
            expenseProgress.value = true
            db.collection(Constants.COLLECTION_NAME_EXPENSE).whereEqualTo("userId", userId)
                .whereEqualTo("expenseDescription",name.toLowerCase())
                .get()
                .addOnSuccessListener {document->
                    convertExpenseSearch(document,expenseData)
                    expenseProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch sales")
                    print(exc)
                    expenseProgress.value = false
                }
        }
        else{
            retrieveExpense()
        }
    }

    fun expenseSearchByAmount(name:String){
        val userId = auth.currentUser?.uid
        if(name.isNotEmpty()){
            expenseProgress.value = true
            db.collection(Constants.COLLECTION_NAME_EXPENSE).whereEqualTo("userId", userId)
                .whereEqualTo("expenseAmount",name.toLowerCase())
                .get()
                .addOnSuccessListener {document->
                    convertExpenseSearch(document,expenseData)
                    expenseProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch sales")
                    print(exc)
                    expenseProgress.value = false
                }
        }
        else{
            retrieveExpense()
        }
    }

    fun convertExpenseSearch(documents: QuerySnapshot,outState: MutableState<List<Expense>>){
        val newExpense = mutableListOf<Expense>()
        documents.forEach{doc->
            val expense = doc.toObject(Expense::class.java)
            newExpense.add(expense)
        }
        val sortedExpense = newExpense.sortedBy { it.expenseName }
        outState.value = sortedExpense
    }


    fun expenseSearchWhenTypingByName(name: String) {
        val userId = auth.currentUser?.uid
        if (name.isNotEmpty()) {
            expenseProgress.value = true
            val searchQuery = name.trim().toLowerCase()
            val endQuery = searchQuery + '\uf8ff' // Unicode character that is higher than any other character

            db.collection(Constants.COLLECTION_NAME_EXPENSE)
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("expenseName", searchQuery)
                .whereLessThan("expenseName", endQuery)
                .get()
                .addOnSuccessListener { snapshot ->
                    convertExpenseSearchWhenTyping(snapshot, expenseData)
                    expenseProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to search for stock")
                    expenseProgress.value = false
                }
        } else {
            retrieveExpense()
        }
    }

    fun expenseSearchWhenTypingByCategory(name: String) {
        val userId = auth.currentUser?.uid
        if (name.isNotEmpty()) {
            expenseProgress.value = true
            val searchQuery = name.trim().toLowerCase()
            val endQuery = searchQuery + '\uf8ff' // Unicode character that is higher than any other character

            db.collection(Constants.COLLECTION_NAME_EXPENSE)
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("expenseCategory", searchQuery)
                .whereLessThan("expenseCategory", endQuery)
                .get()
                .addOnSuccessListener { snapshot ->
                    convertExpenseSearchWhenTyping(snapshot, expenseData)
                    expenseProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to search for stock")
                    expenseProgress.value = false
                }
        } else {
            retrieveExpense()
        }
    }

    fun expenseSearchWhenTypingByAmount(name: String) {
        val userId = auth.currentUser?.uid
        if (name.isNotEmpty()) {
            expenseProgress.value = true
            val searchQuery = name.trim().toLowerCase()
            val endQuery = searchQuery + '\uf8ff' // Unicode character that is higher than any other character

            db.collection(Constants.COLLECTION_NAME_EXPENSE)
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("expenseAmount", searchQuery)
                .whereLessThan("expenseAmount", endQuery)
                .get()
                .addOnSuccessListener { snapshot ->
                    convertExpenseSearchWhenTyping(snapshot, expenseData)
                    expenseProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to search for stock")
                    expenseProgress.value = false
                }
        } else {
            retrieveExpense()
        }
    }

    fun expenseSearchWhenTypingByDescription(name: String) {
        val userId = auth.currentUser?.uid
        if (name.isNotEmpty()) {
            expenseProgress.value = true
            val searchQuery = name.trim().toLowerCase()
            val endQuery = searchQuery + '\uf8ff' // Unicode character that is higher than any other character

            db.collection(Constants.COLLECTION_NAME_EXPENSE)
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("expenseDescription", searchQuery)
                .whereLessThan("expenseDescription", endQuery)
                .get()
                .addOnSuccessListener { snapshot ->
                    convertExpenseSearchWhenTyping(snapshot, expenseData)
                    expenseProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to search for stock")
                    expenseProgress.value = false
                }
        } else {
            retrieveExpense()
        }
    }

    fun convertExpenseSearchWhenTyping(documents: QuerySnapshot,outState: MutableState<List<Expense>>){
        val newExpense = mutableListOf<Expense>()
        documents.forEach{doc->
            val expense = doc.toObject(Expense::class.java)
            newExpense.add(expense)
        }
        val sortedExpense = newExpense.sortedBy { it.expenseName }
        outState.value = sortedExpense
    }



    fun retrieveInvoices(){
        val userId = auth.currentUser?.uid
        invoiceSearchProgress.value = true
        db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("userId", userId)
            .orderBy("salesNo", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {document->
                convertInvoiceSales(document,invoiceData)
                invoiceSearchProgress.value = false
            }
            .addOnFailureListener {exc->
                handleException(exc,"Unable to fetch sales")
                invoiceSearchProgress.value = false
            }
    }

    private fun convertInvoiceSales(documents: QuerySnapshot, outState: MutableState<List<Sales>>){
        val newSales = mutableListOf<Sales>()
        documents.forEach{doc->
            val sale = doc.toObject(Sales::class.java)
            newSales.add(sale)
        }
        val sortedSales = newSales.sortedByDescending { it.salesDate }
        outState.value = sortedSales

    }


    fun invoiceSearchByInvoiceNumber(name:String){
        val userId = auth.currentUser?.uid
        if(name.isNotEmpty()){
            invoiceSearchProgress.value = true
            db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("userId", userId)
                .whereIn("salesNo", listOf(name.lowercase(), name.uppercase()))
                .get()
                .addOnSuccessListener {document->
                    convertInvoiceSearch(document,invoiceData)
                    invoiceSearchProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch sales")
                    print(exc)
                    invoiceSearchProgress.value = false
                }
        }
        else{
            retrieveInvoices()
        }
    }

    fun invoiceSearchByCustomerName(name:String){
        val userId = auth.currentUser?.uid
        if(name.isNotEmpty()){
            invoiceSearchProgress.value = true
            db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("userId", userId)
                .whereIn("customerName", listOf(name.lowercase(), name.uppercase()))
                .get()
                .addOnSuccessListener {document->
                    convertInvoiceSearch(document,invoiceData)
                    invoiceSearchProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch sales")
                    print(exc)
                    invoiceSearchProgress.value = false
                }
        }
        else{
            retrieveInvoices()
        }
    }

    fun invoiceSearchByTotalAmount(name:String){
        val userId = auth.currentUser?.uid
        if(name.isNotEmpty()){
            invoiceSearchProgress.value = true
            db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("userId", userId)
                .whereIn("amountPaid", listOf(name.lowercase(), name.uppercase()))
                .get()
                .addOnSuccessListener {document->
                    convertInvoiceSearch(document,invoiceData)
                    invoiceSearchProgress.value = false
                }
                .addOnFailureListener {exc->
                    handleException(exc,"Unable to fetch sales")
                    print(exc)
                    invoiceSearchProgress.value = false
                }
        }
        else{
            retrieveInvoices()
        }
    }

    private fun convertInvoiceSearch(documents: QuerySnapshot, outState: MutableState<List<Sales>>){
        val newSales = mutableListOf<Sales>()
        documents.forEach{doc->
            val sale = doc.toObject(Sales::class.java)
            newSales.add(sale)
        }
        val sortedSales = newSales.sortedBy { it.salesNo }
        outState.value = sortedSales

    }


    // search for invoice as user type
    fun invoiceSearchByInvoiceNumberWhenTyping(name: String) {
        val userId = auth.currentUser?.uid
        if (name.isNotEmpty()) {
            invoiceSearchProgress.value = true
            val searchQuery = name.trim().toUpperCase()
            val endQuery = searchQuery + '\uf8ff' // Unicode character that is higher than any other character

            db.collection(Constants.COLLECTION_NAME_SALES)
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("salesNo", searchQuery)
                .whereLessThan("salesNo", endQuery)
                .get()
                .addOnSuccessListener { snapshot ->
                    convertSearchedInvoiceSales(snapshot, invoiceData)
                    invoiceSearchProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to search for stock")
                    invoiceSearchProgress.value = false
                }
        } else {
            retrieveInvoices()
        }
    }


    fun invoiceSearchByTotalAmountWhenTyping(name: String) {
        val userId = auth.currentUser?.uid
        if (name.isNotEmpty()) {
            invoiceSearchProgress.value = true
            val searchQuery = name.trim().toUpperCase()
            val endQuery = searchQuery + '\uf8ff' // Unicode character that is higher than any other character

            db.collection(Constants.COLLECTION_NAME_SALES)
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("amountPaid", searchQuery)
                .whereLessThan("amountPaid", endQuery)
                .get()
                .addOnSuccessListener { snapshot ->
                    convertSearchedInvoiceSales(snapshot, invoiceData)
                    invoiceSearchProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to search for stock")
                    invoiceSearchProgress.value = false
                }
        } else {
            retrieveInvoices()
        }
    }


    fun invoiceSearchByCustomerNameWhenTyping(name: String) {
        val userId = auth.currentUser?.uid
        if (name.isNotEmpty()) {
            invoiceSearchProgress.value = true
            val searchQuery = name.trim().toUpperCase()
            val endQuery = searchQuery + '\uf8ff' // Unicode character that is higher than any other character

            db.collection(Constants.COLLECTION_NAME_SALES)
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("customerName", searchQuery)
                .whereLessThan("customerName", endQuery)
                .get()
                .addOnSuccessListener { snapshot ->
                    convertSearchedInvoiceSales(snapshot, invoiceData)
                    invoiceSearchProgress.value = false
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "Failed to search for stock")
                    invoiceSearchProgress.value = false
                }
        } else {
            retrieveInvoices()
        }
    }

    private fun convertSearchedInvoiceSales(documents: QuerySnapshot, outState: MutableState<List<Sales>>){
        val newSales = mutableListOf<Sales>()
        documents.forEach{doc->
            val sale = doc.toObject(Sales::class.java)
            newSales.add(sale)
        }
        val sortedSales = newSales.sortedBy { it.salesNo }
        outState.value = sortedSales

    }



    //when a customer is clicked when searching the customers
    fun onCustomerSelected(customer: Customer) {
        customerSelected.value = customer
    }

    //when a stock is clicked when searching the stocks
    fun onStockSelected(stock: Stock) {
        stockSelected.value = stock
    }



    // Update stock quantities for multiple stocks
    fun onMultipleStocksSold(stockQuantityList: List<Pair<Stock, Int>>,onSalesSuccess:() -> Unit) {
        // Combine quantities for stocks with the same ID
        onMultipleSoldProgress.value = true
        val combinedStockQuantityList = combineQuantities(stockQuantityList)

        val batch = db.batch()

        for ((stock, quantityToDeduct) in combinedStockQuantityList) {
            val stockRef = db.collection(Constants.COLLECTION_NAME_STOCKS)
                .document(stock.stockId.toString())

            batch.update(
                stockRef,
                "stockQuantity",
                stock.stockQuantity?.toInt()?.minus(quantityToDeduct).toString()
            )
            batch.update(
                stockRef,
                "stockQuantitySold",
                stock.stockQuantitySold?.toInt()?.plus(quantityToDeduct).toString()
            )
        }

        batch.commit()
            .addOnSuccessListener {
                onMultipleSoldProgress.value = false
                // Successfully updated stock quantities
                onSalesSuccess.invoke()
                retrieveStocks()
                getTotalStockValue()
            }
            .addOnFailureListener { e ->
                onMultipleSoldProgress.value = false
                // Handle failure
            }
    }

    // Function to combine quantities for stocks with the same ID
    fun combineQuantities(stockQuantityList: List<Pair<Stock, Int>>): List<Pair<Stock, Int>> {
        val combinedStockQuantityMap = mutableMapOf<String, Int>()

        for ((stock, quantity) in stockQuantityList) {
            val stockId = stock.stockId.toString()
            combinedStockQuantityMap[stockId] = combinedStockQuantityMap.getOrDefault(stockId, 0) + quantity
        }

        return combinedStockQuantityMap.entries.map { (stockId, quantity) ->
            val stock = stockQuantityList.first { it.first.stockId.toString() == stockId }.first
            Pair(stock, quantity)
        }
    }


    //delete a singleSale
    fun deleteSingleSale(salesId: String, singleSale: String, exist:String, type:String, stockId:String, onSuccess: () -> Unit) {
        deleteSaleProgress.value = true
        db.collection(Constants.COLLECTION_NAME_SALES)
            .document(salesId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val sales = document.toObject(Sales::class.java)
                    sales?.let {
                        val updatedSalesList = it.sales?.filterNot { sale -> sale.saleId == singleSale }

                        val salesListDeleted = it.sales?.filter { sale-> sale.saleId == singleSale }
                        val salesProductName = salesListDeleted?.firstOrNull()
                        val deletedListProfit = salesListDeleted?.sumByDouble { sale ->
                            (sale.profit?.toDoubleOrNull() ?: 0.0) * (sale.quantity?.toDoubleOrNull() ?: 0.0)
                        }
                        val deletedListSales  = salesListDeleted?.sumByDouble { sale-> sale.totalPrice?.toDoubleOrNull() ?: 0.0 }

                        if (sales.type == "CR") {
                            db.collection(Constants.COLLECTION_NAME_CUSTOMERS)
                                .document(sales.customerId.toString()).get()
                                .addOnSuccessListener { document ->
                                    val customer = document.toObject(Customer::class.java)
                                    customer?.let {
                                        val updatedCustomerBalance = customer.customerBalance!!.toDouble()
                                        val balanceUpdate = mapOf(
                                            "customerBalance" to (updatedCustomerBalance - deletedListSales!!.toDouble()).toString()
                                        )

                                        db.collection(Constants.COLLECTION_NAME_CUSTOMERS)
                                            .document(sales.customerId.toString())
                                            .update(balanceUpdate)
                                    }
                                }
                                .addOnFailureListener {
                                    // Handle failure
                                }
                        }

                        db.collection(Constants.COLLECTION_NAME_USERS)
                            .document(it.userId.toString())
                            .get()
                            .addOnSuccessListener { userDocument ->
                                if (userDocument.exists()) {
                                    val user = userDocument.toObject(User::class.java)
                                    user?.let { currentUser ->
                                        // Decrement totalSales and totalProfit in user
                                        val updatedTotalSales =
                                            (currentUser.totalSales?.toDoubleOrNull()
                                                ?: 0.0) - deletedListSales!!
                                        val updatedTotalProfit =
                                            (currentUser.totalProfit?.toDoubleOrNull()
                                                ?: 0.0) - deletedListProfit!!

                                        // Update User document
                                        db.collection(Constants.COLLECTION_NAME_USERS)
                                            .document(currentUser.userId.toString())
                                            .update(
                                                "totalSales", updatedTotalSales.toString(),
                                                "totalProfit", updatedTotalProfit.toString()
                                            )
                                            .addOnSuccessListener {
                                                //update stock
                                                db.collection(Constants.COLLECTION_NAME_STOCKS)
                                                    .whereEqualTo("stockName", salesProductName?.productName.toString())
                                                    .whereEqualTo("stockId",stockId)
                                                    .get()
                                                    .addOnSuccessListener { stockDocuments ->
                                                        if(exist=="true") {
                                                            if (!stockDocuments.isEmpty) {
                                                                val stockDocument =
                                                                    stockDocuments.documents[0]
                                                                val stock =
                                                                    stockDocument.toObject(Stock::class.java)
                                                                stock?.let {
                                                                    // Update the stock quantities based on the saleToDelete
                                                                    stock.stockQuantitySold = ((stock.stockQuantitySold?.toIntOrNull() ?: 0) - (salesProductName?.quantity?.toIntOrNull() ?: 0)).toString()
                                                                    stock.stockQuantity = ((stock.stockQuantity?.toIntOrNull() ?: 0) + (salesProductName?.quantity?.toIntOrNull() ?: 0)).toString()
                                                                    continueWithDeleteSale(salesId, singleSale, exist, onSuccess, stock)
                                                                }
                                                            } else {
                                                                deleteSaleProgress.value = false
                                                                handleException(null, "Stock document not found for deletion")
                                                            }
                                                        }
                                                        else {
                                                            continueWithDeleteSale(salesId, singleSale, exist, onSuccess, Stock())
                                                        }
                                                    }
                                                    .addOnFailureListener { e ->
                                                        deleteSaleProgress.value = false
                                                        handleException(e, "Failed to load stock for deletion")
                                                    }

                                            }
                                            .addOnFailureListener { e ->

                                            }

                                    }
                                } else {
                                    // Log that the User document does not exist
                                    handleException(customMessage = "User document does not exist")
                                }
                            }
                            .addOnFailureListener { e ->
                                // Log the error
                                handleException(e, "Failed to fetch User document")
                            }

                    }
                } else {
                    deleteSaleProgress.value = false
                    handleException(customMessage = "Sales does not exist")
                }
            }
            .addOnFailureListener { e ->
                deleteSaleProgress.value = false
                handleException(e, "Failed to delete sale")
            }
    }
    private fun continueWithDeleteSale(salesId: String, singleSale: String, exist:String, onSuccess: () -> Unit, stock: Stock) {
        // Fetch the sales document again
        db.collection(Constants.COLLECTION_NAME_SALES)
            .document(salesId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val sales = document.toObject(Sales::class.java)
                    sales?.let {
                        // Filter and update the sales list

                        val updatedSalesList = it.sales?.filterNot { sale -> sale.saleId == singleSale }
                        val sale = updatedSalesList?.sumByDouble { sale -> sale.totalPrice?.toDoubleOrNull() ?: 0.0 }
                        val profit = updatedSalesList?.sumByDouble { sale -> sale.profit?.toDoubleOrNull() ?: 0.0 }
                        val quantity = updatedSalesList?.sumByDouble { sale -> sale.quantity?.toDoubleOrNull() ?: 0.0 }
                        it.sales = updatedSalesList
                        it.totalPrice = sale?.toString() ?: "0.0"
                        it.totalProfit = profit?.toString() ?: "0.0"
                        it.totalQuantity = quantity?.toString() ?: "0.0"
                        if(sales.type=="SR") {
                            it.amountPaid = sale?.toString() ?: "0.0"
                        }
                        else{
                            it.balance = sale?.toString() ?: "0.0"
                        }

                        deleteSales(salesId, it, onSuccess)
                        if(exist == "true") {
                            updateStock(stock)
                        }

                    }
                } else {
                    deleteSaleProgress.value = false
                    handleException(customMessage = "Sales does not exist")
                }
            }
            .addOnFailureListener { e ->
                deleteSaleProgress.value = false
                handleException(e, "Failed to delete sale")
            }
    }

    fun deleteSales(salesId: String, updatedSales: Sales,onSuccess: () -> Unit) {
        val userId = auth.currentUser!!.uid
        db.collection(Constants.COLLECTION_NAME_SALES)
            .document(salesId)
            .update(
                "sales", updatedSales.sales,
                "totalPrice", updatedSales.totalPrice,
                "totalProfit", updatedSales.totalProfit,
                "totalQuantity", updatedSales.totalQuantity,
                "amountPaid", updatedSales.amountPaid

                )
            .addOnSuccessListener {
                if (updatedSales.sales.isNullOrEmpty()) {
                    // Delete the sales document when the sales list is empty
                    deleteSalesDocument(salesId, onSuccess)
                } else {
                    popupNotification.value = Event("Sales deleted successfully")
                    deleteSaleProgress.value = false
                    retrieveInvoices()
                    retrieveSales()
                    retrieveStocks()
//                    getTotalStockValue()
                    getAllSaleReceiptToday()
                    getTotalSaleReceiptToday()
                    getTotalProfitToday()
                    getTotalExpenseToday()
                    getTotalCreditReceiptToday()
                    getMostPurchasedProductsToday()
                    getAllPurchasedProductsToday()
//                    getTotalAmountOwingCustomer()
                    retrieveCustomer()
                    onSuccess.invoke()
                }
            }
            .addOnFailureListener { e ->
                deleteSaleProgress.value = false
                handleException(customMessage = "failed to delete sale")
            }
    }

     fun deleteSalesDocument(salesId: String, onSuccess: () -> Unit) {
        val uid = auth.currentUser?.uid
        db.collection(Constants.COLLECTION_NAME_SALES)
            .document(salesId)
            .delete()
            .addOnSuccessListener {
                getSaleProgress.value = false
                deleteSaleProgress.value = false
                popupNotification.value = Event("Sales deleted successfully")
                retrieveInvoices()
                retrieveSales()
                retrieveStocks()
                retrieveCustomer()
                getAllSaleReceiptToday()
                getTotalSaleReceiptToday()
                getTotalProfitToday()
                getTotalExpenseToday()
                getTotalCreditReceiptToday()
                onSuccess.invoke()

            }
            .addOnFailureListener { e ->
                deleteSaleProgress.value = false
                handleException(customMessage = "Failed to delete sale")
            }
    }

    fun deleteEntireDocument(customerId: String,salesId: String, onSuccess: () -> Unit) {
        getSaleProgress.value = true
        db.collection(Constants.COLLECTION_NAME_SALES)
            .document(salesId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val sales = document.toObject(Sales::class.java)

                    val totalBalance = sales?.balance

                    sales?.let {
                        val updatedSalesList = it.sales


                        db.collection(Constants.COLLECTION_NAME_USERS)
                            .document(it.userId.toString())
                            .get()
                            .addOnSuccessListener { userDocument ->
                                if (userDocument.exists()) {
                                    val user = userDocument.toObject(User::class.java)
                                    user?.let { currentUser ->
                                        // Decrement totalSales and totalProfit in user
                                        var updatedTotalSales = currentUser.totalSales?.toDoubleOrNull() ?: 0.0
                                        var updatedTotalProfit = currentUser.totalProfit?.toDoubleOrNull() ?: 0.0

                                        updatedSalesList?.forEach { sale ->
                                            // Update the totalSales, totalProfit
                                            updatedTotalSales -= sale.totalPrice?.toDoubleOrNull() ?: 0.0
                                            updatedTotalProfit -= (sale.profit?.toDoubleOrNull() ?: 0.0) * (sale.quantity?.toDoubleOrNull()?: 0.0)
                                        }


                                        if(sales.type == "CR") {
                                            db.collection(Constants.COLLECTION_NAME_CUSTOMERS)
                                                .document(customerId).get()
                                                .addOnSuccessListener { document ->
                                                    val customer = document.toObject(Customer::class.java)
                                                    customer.let {
                                                        val updatedCustomerBalance =
                                                            customer?.customerBalance!!.toDouble()
                                                        val balanceUpdate = mapOf(
                                                            "customerBalance" to (updatedCustomerBalance - totalBalance!!.toDouble()).toString()
                                                        )

                                                        db.collection(Constants.COLLECTION_NAME_CUSTOMERS)
                                                            .document(customerId)
                                                            .update(balanceUpdate)

                                                    }

                                                }
                                                .addOnFailureListener {

                                                }

                                        }

                                        val updates = mapOf(
                                            "totalSales" to updatedTotalSales.toString(),
                                            "totalProfit" to updatedTotalProfit.toString()
                                        )


                                        // Update User document
                                        db.collection(Constants.COLLECTION_NAME_USERS)
                                            .document(currentUser.userId.toString())
                                            .update(updates)
                                            .addOnSuccessListener {
                                                //update stock

                                                val stockQuantitiesMap = mutableMapOf<String, Int>()

                                                var salesProcessedCount = 0

                                                updatedSalesList?.forEach { sale ->
                                                    val quantitySold = sale.quantity?.toIntOrNull() ?: 0

                                                    if(sale.exist.toString()=="true") {

                                                        db.collection(Constants.COLLECTION_NAME_STOCKS)
                                                            .whereEqualTo("stockName", sale.productName.toString())
                                                            .whereEqualTo("userId",sale.userId.toString())
                                                            .get()
                                                            .addOnSuccessListener { stockDocuments ->
                                                                if (!stockDocuments.isEmpty) {
                                                                    stockDocuments.forEach { stockDocument ->
                                                                        val stock = stockDocument.toObject(Stock::class.java)
                                                                        val stockId = stock.stockId.toString()

                                                                        // Combine quantities for stocks with the same name
                                                                        stockQuantitiesMap[stockId] = stockQuantitiesMap.getOrDefault(stockId, 0) + quantitySold
                                                                    }
                                                                    salesProcessedCount++

                                                                    // Continue with the deletion of the sale document for the last sale
                                                                    if (salesProcessedCount == updatedSalesList.size) {
                                                                        updateStockQuantities(stockQuantitiesMap, salesId, onSuccess)
                                                                    }
                                                                } else {
                                                                    getSaleProgress.value = false
                                                                    handleException(null, "Stock document not found for deletion")
                                                                }
                                                            }
                                                            .addOnFailureListener { e ->
                                                                handleException(e, "Failed to load stock for deletion")
                                                            }
                                                    }
                                                    else{
                                                        deleteSalesDocument(salesId, onSuccess)
                                                    }
                                                }


                                                }

                                            .addOnFailureListener { e ->


                                            }

                                    }
                                } else {
                                    // Log that the User document does not exist
                                    handleException(customMessage = "User document does not exist")
                                }
                            }
                            .addOnFailureListener { e ->
                                // Log the error
                                handleException(e, "Failed to fetch User document")
                            }

                    }
                } else {
                    getSaleProgress.value = false
                    handleException(customMessage = "Sales does not exist")
                }
            }
            .addOnFailureListener { e ->
                getSaleProgress.value = false
                handleException(e, "Failed to delete sale")
            }
    }

    fun updateStockQuantities(stockQuantitiesMap: Map<String, Int>, salesId: String, onSuccess: () -> Unit) {
        val tasks = mutableListOf<Task<Void>>()
        for ((stockId, quantitySold) in stockQuantitiesMap) {
            db.collection(Constants.COLLECTION_NAME_STOCKS)
                .whereEqualTo("stockId", stockId)
                .get()
                .addOnSuccessListener { stockDocuments ->
                    if (!stockDocuments.isEmpty) {
                        // Combine quantities for stocks with the same name
                        val totalQuantitySold = quantitySold
                        val totalQuantity = quantitySold

                        // Update stock quantities for each document
                        for (stockDocument in stockDocuments.documents) {
                            val stockRef = db.collection(Constants.COLLECTION_NAME_STOCKS).document(stockDocument.id)

                            val currentQuantitySold = stockDocument.getString("stockQuantitySold")?.toIntOrNull() ?: 0
                            val currentQuantity = stockDocument.getString("stockQuantity")?.toIntOrNull() ?: 0

                            stockRef.update("stockQuantitySold", (currentQuantitySold - totalQuantitySold).toString())
                            stockRef.update("stockQuantity", (currentQuantity + totalQuantity).toString())
                        }
                    } else {
                        handleException(customMessage = "stock not found ")
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure to fetch stock documents
                    handleException(e, "Failed to fetch stock documents for updating quantities")
                }
        }

        // Wait for all update tasks to complete
        Tasks.whenAll(tasks)
            .addOnSuccessListener {
                // Successfully updated stock quantities for all stocks
                // Continue with the deletion of the sale document
                deleteSalesDocument(salesId, onSuccess)
            }
            .addOnFailureListener { e ->
                handleException(e, "Failed to update stock quantities")
            }
    }


    // update stock after deleting
    fun updateStock(stock: Stock) {
        val stockId = stock.stockId
        if (stockId != null) {
            db.collection(Constants.COLLECTION_NAME_STOCKS)
                .whereEqualTo("stockId", stockId)
                .get()
                .addOnSuccessListener { stockDocument ->
                    if (!stockDocument.isEmpty) {
                        val stockRef = stockDocument.documents.first().reference
                        stockRef.update("stockQuantity", stock.stockQuantity, "stockQuantitySold", stock.stockQuantitySold)
                            .addOnSuccessListener {
                                deleteSaleProgress.value = false
                            }
                            .addOnFailureListener { e ->
                                handleException(e,"error:")
                                deleteSaleProgress.value = false
                            }
                    } else {
                        deleteSaleProgress.value = false
                    }
                }
                .addOnFailureListener { e ->
                    handleException(e,"error:")
                    deleteSaleProgress.value = false

                }
        }
        else {
            handleException(customMessage = "error: stock id is null")
            deleteSaleProgress.value = false
        }
    }


    fun updateSale(customerName: String, customerId:String, stockName:String, unitPrice:String, quantity: String, totalPrice: String, profit:String,
                   saleId: String, singleSaleId: String,stockId: String,
                   saleDiff: String, profitDiff: String, sale: Sales, singleSale: SingleSale, exist:String, onSuccess: () -> Unit){

        updatedSaleProgress.value = true
        val saleRef = db.collection(Constants.COLLECTION_NAME_SALES).document(saleId)

        saleRef.get()
            .addOnSuccessListener {saleDocument->
                if(saleDocument.exists()){

                    val sales = saleDocument.toObject(Sales::class.java)

                    val updateSingleSale = sales?.sales?.find { it.saleId == singleSaleId }

                    updateSingleSale.apply {
                        this?.saleId = singleSaleId
                        this?.customerName = customerName
                        this?.productName = stockName
                        this?.price = unitPrice
                        this?.quantity = quantity
                        this?.totalPrice = totalPrice
                        this?.profit = profit
                    }

                    val previousTotalAmount = sales?.totalPrice!!.toDouble()

                    // Update total amount and total quantity
                    val updatedTotalAmount =
                        sales?.sales?.sumByDouble { it.totalPrice?.toDoubleOrNull() ?: 0.0 }
                    val updatedTotalQuantity =
                        sales?.sales?.sumByDouble { it.quantity?.toDoubleOrNull() ?: 0.0 }
                    val updatedTotalProfit =
                        sales?.sales?.sumByDouble { (it.profit?.toDoubleOrNull()?: 0.0) * (it.quantity?.toDoubleOrNull()?: 0.0) }

                    // Update total quantity, total sale, and total amount in the Sales object
                    sales?.totalQuantity = updatedTotalQuantity.toString()
                    sales?.totalPrice = updatedTotalAmount.toString()
                    sales?.totalProfit = updatedTotalProfit.toString()
                    sales?.amountPaid = updatedTotalAmount.toString()

                    if(sale.type=="CR"){
                        sales?.balance = updatedTotalAmount.toString()
                        db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customerId).get()
                            .addOnSuccessListener {documentSnapshot->

                            val customer = documentSnapshot.toObject(Customer::class.java)

                            customer.let {
                                val customerBalance = customer?.customerBalance.toString()
                                val diffBalance = updatedTotalAmount!! - previousTotalAmount
                                val updatedBalance = customerBalance.toDouble()+diffBalance

                                val update = mapOf(
                                    "customerBalance" to updatedBalance.toString()
                                )

                                db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customerId).update(update)
                                    .addOnSuccessListener {
                                        popupNotification.value = Event("Customer Balance Updated")
                                        retrieveCustomer()
                                        getTotalAmountOwingCustomer()
                                    }
                                    .addOnFailureListener {exc->
                                        handleException(exc,"failed to update customer balance")

                                    }
                            }

                            }
                            .addOnFailureListener {

                            }
                    }

                    sales?.sales = sales?.sales?.map {
                        (if(it.saleId == singleSaleId) updateSingleSale else it)!!
                    }

                    saleRef.set(sales!!)
                        .addOnSuccessListener {
                            if (exist == "true") {
                                if (sales.sales?.size == 1) {
                                    updateSingleStock(
                                        stockId,
                                        quantity.toInt() - unmodifiedSingle.value?.quantity!!.toInt(),
                                        saleDiff,
                                        (updatedTotalProfit!!.toDouble() - (unmodifiedSingle.value!!.quantity!!.toInt() * unmodifiedSingle.value!!.profit!!.toDouble())).toString(),
                                        sale,
                                        singleSale,
                                        onSuccess
                                    )
                                }
                                else {
                                    updateSingleStock(
                                        stockId,
                                        quantity.toInt() - unmodifiedSingle.value?.quantity!!.toInt(),
                                        saleDiff,
                                        ((updateSingleSale?.profit!!.toDouble() * updateSingleSale.quantity!!.toInt()) - (unmodifiedSingle.value!!.quantity!!.toInt() * unmodifiedSingle.value!!.profit!!.toDouble())).toString(),
                                        sale,
                                        singleSale,
                                        onSuccess
                                    )
                                }
                            }
                            else {
                                popupNotification.value = Event("Sale updated successfully")
                                retrieveInvoices()
                                refreshSales()
                                retrieveUser()
                                retrieveSales()
                                retrieveStocks()
                                getTotalStockValue()
                                getAllSaleReceiptToday()
                                getTotalSaleReceiptToday()
                                getTotalProfitToday()
                                getTotalExpenseToday()
                                getTotalCreditReceiptToday()
                                getMostPurchasedProductsToday()
                                getAllPurchasedProductsToday()
                                getSingleSale(singleSale, sale)
                                onSuccess.invoke()
                            }
                        }
                        .addOnFailureListener{exc->
                            handleException(exc,"Failed to update sale")
                            updatedSaleProgress.value = false
                        }
                }
                else{
                    handleException(customMessage = "Sale document does not exist")
                    updatedSaleProgress.value = false
                }
            }
            .addOnFailureListener{
                    handleException(customMessage = "Failed to fetch sale document")
                    updatedSaleProgress.value = false
            }

    }

    fun updateSingleStock(
        stockId: String,
        quantity: Int,
        saleDiff: String,
        profitDiff: String,
        sale: Sales,
        single: SingleSale,
        onSuccess: () -> Unit
    ) {
        val currentUid = auth.currentUser?.uid

        if (currentUid == null) {
            updatedSaleProgress.value = false
            handleException(customMessage = "Current user ID is null")
            return
        }

        // Query to find the document with both stockId and userId
        db.collection(Constants.COLLECTION_NAME_STOCKS)
            .whereEqualTo("stockId", stockId)
            .whereEqualTo("userId", currentUid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val stockDocument = querySnapshot.documents[0]
                    val stock = stockDocument.toObject(Stock::class.java)

                    stock?.let { stock ->
                        val updateQuantityRemaining = stock.stockQuantity.toInt() - quantity
                        val updatedQuantitySold = stock.stockQuantitySold.toInt() + quantity

                        stock.stockQuantity = updateQuantityRemaining.toString()
                        stock.stockQuantitySold = updatedQuantitySold.toString()

                        // Update the stock document with the new values
                        stockDocument.reference.set(stock).addOnSuccessListener {
                            val userDocRef = db.collection(Constants.COLLECTION_NAME_USERS).document(currentUid)

                            // Check if the user document exists
                            userDocRef.get().addOnSuccessListener { userDocument ->
                                if (userDocument.exists()) {
                                    val user = userDocument.toObject(User::class.java)

                                    user?.let { user ->
                                        val updatedTotalSales = user.totalSales?.toDouble()!! + saleDiff.toDouble()
                                        val updatedTotalProfit = user.totalProfit?.toDouble()!! + profitDiff.toDouble()

                                        user.totalSales = updatedTotalSales.toString()
                                        user.totalProfit = updatedTotalProfit.toString()

                                        // Update the user document with the new values
                                        userDocRef.set(user).addOnSuccessListener {
                                            popupNotification.value = Event("Sale updated successfully")
                                            updatedSaleProgress.value = false
                                            retrieveInvoices()
                                            refreshSales()
                                            retrieveUser()
                                            retrieveSales()
                                            retrieveStocks()
                                            getTotalStockValue()
                                            getAllSaleReceiptToday()
                                            getTotalSaleReceiptToday()
                                            getTotalProfitToday()
                                            getTotalExpenseToday()
                                            getTotalCreditReceiptToday()
                                            getMostPurchasedProductsToday()
                                            getAllPurchasedProductsToday()
                                            getSingleSale(single, sale)
                                            onSuccess.invoke()
                                        }.addOnFailureListener { exc ->
                                            handleException(exc, "Failed to update total sale and profit")
                                            updatedSaleProgress.value = false
                                        }
                                    }
                                } else {
                                    handleException(customMessage = "User document does not exist")
                                    updatedSaleProgress.value = false
                                }
                            }.addOnFailureListener {
                                handleException(customMessage = "Failed to fetch user document")
                                updatedSaleProgress.value = false
                            }
                        }.addOnFailureListener { exc ->
                            handleException(exc, "Failed to update stock")
                            updatedSaleProgress.value = false
                        }
                    }
                } else {
                    handleException(customMessage = "Stock document does not exist")
                    updatedSaleProgress.value = false
                }
            }
            .addOnFailureListener {
                handleException(customMessage = "Failed to fetch stock document")
                updatedSaleProgress.value = false
            }
    }


    fun getSale(saleId:String){
        getSaleProgress.value = true
        val uid = auth.currentUser?.uid
        db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("userId",uid).whereEqualTo("salesId",saleId)
            .get()
            .addOnSuccessListener {querySnapshot->
                if(!querySnapshot.isEmpty){
                    val sales = querySnapshot.documents[0].toObject(Sales::class.java)
                    this.salesDetail.value = sales
                    getSaleProgress.value = false
                }
                else{
                    this.salesDetail.value = null
                    getSaleProgress.value = false
                }

            }
            .addOnFailureListener{
                getSaleProgress.value = false
            }
    }


    fun onSaleSelected(sale: Sales) {
        salesSelected.value = sale
    }

    fun onExpenseSelected(expense: Expense) {
        expenseSelected.value = expense
        unmodifiedexpense.value = expense
    }

    //get total amount owing customer

    fun getTotalAmountOwingCustomer() {

        val userId = auth.currentUser?.uid
        totalAmountOwed.value = 0.0
        db.collection(Constants.COLLECTION_NAME_CUSTOMERS)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {

                    val customers = querySnapshot.toObjects<Customer>()


                    customers.forEach { customer ->
                        // Ensure customerBalance is not null before adding to totalBalance
                        customer.customerBalance?.let { balance ->
                            totalAmountOwed.value += balance.toDouble()
                        }
                    }

                }
            }
            .addOnFailureListener { exc ->
                handleException(exc,"failed to retrieve total amount owing customers")
            }
    }


    fun getTotalStockValue(){

        val userId = auth.currentUser?.uid
        totalStockValue.value = 0.0
        db.collection(Constants.COLLECTION_NAME_STOCKS).whereEqualTo("userId",userId).get()
            .addOnSuccessListener {querySnapshot->

                if(!querySnapshot.isEmpty){

                    val stocks = querySnapshot.toObjects<Stock>()

                    stocks.forEach{stock->
                        val purchasePrice = stock.stockPurchasePrice?.toDoubleOrNull() ?: 0.0
                        val quantity = stock.stockQuantity?.toDoubleOrNull() ?: 0.0

                        totalStockValue.value += purchasePrice * quantity
                    }


                }


            }
            .addOnFailureListener{exc->
                handleException(exc,"failed to retrieve total stock value")
            }
    }


    //get stock selected to update sale

     fun getStockSelected(productName:SingleSale){
         val userId = auth.currentUser?.uid
         getStockProgress.value = true
         db.collection(Constants.COLLECTION_NAME_STOCKS).whereEqualTo("stockName",productName.productName).whereEqualTo("userId",userId).get()
             .addOnSuccessListener {querySnapshot->
                 if(!querySnapshot.isEmpty){

                     val stock = querySnapshot.documents[0].toObject(Stock::class.java)
                     stockSelected.value = stock
                 }
                 getStockProgress.value = false
             }
             .addOnFailureListener{exc->
                 handleException(exc,"error:")
                 getStockProgress.value = false
             }
     }

    fun getSingleSale(single: SingleSale, sale: Sales) {
        getSingleSaleProgress.value = true
        db.collection(Constants.COLLECTION_NAME_SALES).document(sale.salesId.toString()).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val sales = document.toObject(Sales::class.java)
                    sales?.let {
                        val updatedSale = it.sales?.find { it.saleId == single.saleId }

                        updatedSale?.let {
                            unmodifiedSingle.value = it
                            singleSaleSelected.value = it
                        }
                    }
                }
                getSingleSaleProgress.value = false
            }
            .addOnFailureListener {exc->
                handleException(exc,"error:")
                getSingleSaleProgress.value = false
            }
    }

    // select a customer on home to see its details on credit info
    fun onCustomerSelectedHome(customerId: String){
        customerProgress.value = true
        db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customerId).get()
            .addOnSuccessListener { documentSnapshot->
                customerProgress.value = false
                val customer = documentSnapshot.toObject(Customer::class.java)
                customerSelected.value = customer

            }
            .addOnFailureListener{
                customerProgress.value = false
            }
    }


    // for customer to pay credit
    fun payCredit(amountPaid: String, customer: Customer, creditId: String, onSuccess: () -> Unit) {
        inProgress.value = true
        val updatedCustomerBalance = (customer.customerBalance?.toDoubleOrNull() ?: 0.0) - (amountPaid.toDoubleOrNull()!! ?: 0.0)

        db.collection(Constants.COLLECTION_NAME_SALES).document(creditId).get()
            .addOnSuccessListener { documentSnapshot ->
                val sale = documentSnapshot.toObject(Sales::class.java)

                sale?.let {
                    val updatedAmountPaid = (sale.amountPaid?.toDoubleOrNull() ?: 0.0) + (amountPaid.toDoubleOrNull() ?: 0.0)
                    val updatedBalance = (sale.totalPrice?.toDoubleOrNull() ?: 0.0) - updatedAmountPaid

                    val update = mapOf(
                        "amountPaid" to updatedAmountPaid.toString(),
                        "balance" to updatedBalance.toString()
                    )

                    val customerBalanceUpdate = mapOf(
                        "customerBalance" to updatedCustomerBalance.toString()
                    )

                    // Use batched write for atomicity
                    val batch = db.batch()
                    val salesDocRef = db.collection(Constants.COLLECTION_NAME_SALES).document(sale.salesId.toString())
                    val customerDocRef = db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customer.customerId.toString())

                    batch.update(salesDocRef, update)
                    batch.update(customerDocRef, customerBalanceUpdate)

                    batch.commit()
                        .addOnSuccessListener {
                            inProgress.value = false
                            popupNotification.value = Event("Amount paid successfully")
                            onSuccess.invoke()
                            retrieveSales()
                            refreshSales()
                            getTotalAmountOwingCustomer()
                            retrieveCustomer()
                        }
                        .addOnFailureListener { exc ->
                            inProgress.value = false
                            handleException(exc, "Error:")
                        }
                }
            }
            .addOnFailureListener {
                inProgress.value = false
                // Handle failure
            }
    }

    fun getStock(stock: Stock){
        getStockProgress.value = true
        db.collection(Constants.COLLECTION_NAME_STOCKS).document(stock.stockId.toString()).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val stock = documentSnapshot.toObject(Stock::class.java)
                    stockSelected.value = stock
                    getStockProgress.value = false
                }
                else{
                    popupNotification.value = Event("stock does not exist")
                    getStockProgress.value = false
                }
            }
            .addOnFailureListener {exc->
                handleException(exc,"error:")
                getStockProgress.value = false
            }

    }

    fun getCustomer(customer: Customer){
        getCustomerProgress.value = true
        db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customer.customerId.toString()).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val customer = documentSnapshot.toObject(Customer::class.java)
                    customerSelected.value = customer
                    getCustomerProgress.value = false
                }
                else{
                    popupNotification.value = Event("customer does not exist")
                    getCustomerProgress.value = false
                }
            }
            .addOnFailureListener {exc->
                handleException(exc,"error:")
                getCustomerProgress.value = false
            }

    }

    fun updateStock(stock: Stock, productName:String, purchasePrice: String, sellingPrice: String, qtySold:String, qtyRemain:String, expiryDate: String ,oneMonthToExpire:String, twoWeeksToExpires:String, oneWeekToExpire:String, onSuccess: () -> Unit){
           inProgress.value = true
           var updatedQuantity = qtyRemain
            db.collection(Constants.COLLECTION_NAME_STOCKS).document(stock.stockId.toString()).get()
                .addOnSuccessListener {

                    val update = mapOf(
                        "stockName" to productName,
                        "stockPurchasePrice" to purchasePrice,
                        "stockSellingPrice" to sellingPrice,
                        "stockSellingPrice" to sellingPrice,
                        "stockFixedSellingPrice" to sellingPrice,
                        "stockTotalPrice" to sellingPrice,
                        "stockQuantity" to updatedQuantity,
                        "stockQuantitySold" to qtySold,
                        "stockExpiryDate" to expiryDate
                    )

                    //implement later
//                    val stockHistoryUpdate = mapOf(
//                        "stockExpiryDate" to expiryDate,
//                        "stockOneMonthToExpire" to oneMonthToExpire,
//                        "stockTwoWeeksToExpire" to twoWeeksToExpires,
//                        "stockOneWeekToExpire" to oneWeekToExpire
//                    )

                    db.collection(Constants.COLLECTION_NAME_STOCKS)
                        .document(stock.stockId.toString()).update(update)
                        .addOnSuccessListener {
                            popupNotification.value = Event("Stock Updated Successfully")
                            inProgress.value = false
                            retrieveStocks()
                            getTotalStockValue()
                            onSuccess.invoke()
                        }
                        .addOnFailureListener { exc ->
                            inProgress.value = false
                            handleException(exc, "Failed to update stock")
                        }

                }
                .addOnFailureListener {exc->
                    inProgress.value = false
                    handleException(exc, "failed to retrieve stock for update")
                }
    }

    fun addToStock(stock: Stock, productName:String, purchasePrice: String, sellingPrice: String, qtyRemain:String, expiryDate: String, newQty:String,oneMonthToExpire:String, twoWeeksToExpires:String, oneWeekToExpire:String, onSuccess: () -> Unit){
        inProgress.value = true
        db.collection(Constants.COLLECTION_NAME_STOCKS).document(stock.stockId.toString()).get()
            .addOnSuccessListener {
                    val update = mapOf(
                        "stockName" to productName,
                        "stockPurchasePrice" to if(newQty.toInt()>0) purchasePrice else stock.stockPurchasePrice.toString(),
                        "stockSellingPrice" to if(newQty.toInt()>0) sellingPrice else stock.stockSellingPrice.toString(),
                        "stockFixedSellingPrice" to if(newQty.toInt()>0) sellingPrice else stock.stockSellingPrice.toString(),
                        "stockTotalPrice" to if(newQty.toInt()>0) sellingPrice else stock.stockSellingPrice.toString(),
                        "stockQuantity" to if(newQty.toInt()>0) (qtyRemain.toInt() + newQty.toInt()).toString() else qtyRemain,
                        "stockQuantitySold" to stock.stockQuantitySold.toString(),
                        "stockExpiryDate" to if(newQty.toInt()>0) expiryDate else stock.stockExpiryDate.toString()
                    )


                    //implement later
//                    val stockHistoryUpdate = mapOf(
//                        "stockExpiryDate" to expiryDate,
//                        "stockOneMonthToExpire" to oneMonthToExpire,
//                        "stockTwoWeeksToExpire" to twoWeeksToExpires,
//                        "stockOneWeekToExpire" to oneWeekToExpire
//                    )

                    db.collection(Constants.COLLECTION_NAME_STOCKS)
                        .document(stock.stockId.toString()).update(update)
                        .addOnSuccessListener {

                            if(newQty.toInt()>0){
                                val updatedQuantity =  (qtyRemain.toInt() + newQty.toInt()).toString()
                                val randomUid = UUID.randomUUID()
                                val stockHistory = StockHistory(
                                    stockId = stock.stockId,
                                    userId = stock.userId,
                                    stockName = productName,
                                    stockPurchasePrice = purchasePrice,
                                    stockSellingPrice = sellingPrice,
                                    stockQuantityAdded = newQty,
                                    stockDateAdded = System.currentTimeMillis(),
                                    stockExpiryDate = expiryDate,
                                    stockOneMonthToExpire = oneMonthToExpire,
                                    stockTwoWeeksToExpire = twoWeeksToExpires,
                                    stockOneWeekToExpire = oneWeekToExpire

                                )

                                db.collection(Constants.COLLECTION_NAME_STOCKHISTORY).document(randomUid.toString()).set(stockHistory).addOnSuccessListener {
                                    popupNotification.value = Event("Stock added successfully")
                                    retrieveStocks()
                                    getTotalStockValue()
                                    getAllSaleReceiptToday()
                                    getTotalSaleReceiptToday()
                                    getTotalProfitToday()
                                    getTotalExpenseToday()
                                    getTotalCreditReceiptToday()
                                    getMostPurchasedProductsToday()
                                    getAllPurchasedProductsToday()
                                    inProgress.value = false
                                    onSuccess.invoke()
                                }
                                    .addOnFailureListener {exc->
                                        handleException(exc, "failed to generate stock history")
                                    }
                            }
                        }
                        .addOnFailureListener { exc ->
                            inProgress.value = false
                            handleException(exc, "Failed to update stock")
                        }
                    }
    }


    fun editCustomer(customer:Customer, customerName:String, customerNumber:String, customerAddress: String, customerBalance:String,addAmount:String, onSuccess: () -> Unit ){

        inProgress.value = true
        db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customer.customerId.toString()).get()
            .addOnSuccessListener {
                val updatedBalance = if (addAmount.isEmpty()) customerBalance else (customerBalance.toDouble() + addAmount.toDouble()).toString()
                val update = mapOf(
                    "customerName" to customerName,
                    "customerAddress" to customerAddress,
                    "customerNumber" to customerNumber,
                    "customerBalance" to updatedBalance
                )

                db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customer.customerId.toString()).update(update)
                    .addOnSuccessListener {
                        popupNotification.value = Event("Customer Updated Successfully")
                        inProgress.value = false
                        retrieveCustomer()
                        getTotalAmountOwingCustomer()
                        onSuccess.invoke()
                    }
                    .addOnFailureListener {exc->
                        inProgress.value = false
                        handleException(exc,"failed to update customer")

                    }

            }
            .addOnFailureListener { exc->
                inProgress.value = false
                handleException(exc, "failed to retrieve customer for update")

            }

    }

    fun editExpense(expenseId:String, expenseName:String, expenseDescription:String, expenseCategory:String, expenseAmount:String, onSuccess:()->Unit){

        val userId = auth.currentUser?.uid
        expenseProgress.value = true

        db.collection(Constants.COLLECTION_NAME_EXPENSE).document(expenseId).get()
            .addOnSuccessListener {document->

                val expense = document.toObject(Expense::class.java)

                val update = mapOf(
                    "expenseName" to expenseName,
                    "expenseDescription" to expenseDescription,
                    "expenseCategory" to expenseCategory,
                    "expenseAmount" to (expense!!.expenseAmount!!.toDouble()+expenseAmount.toDouble()).toString()
                )

                db.collection(Constants.COLLECTION_NAME_EXPENSE).document(expenseId).update(update)
                    .addOnSuccessListener {
                        userData.value?.let { user ->
                            try {
                                val updatedTotalExpenses = user.totalExpenses?.toDouble()!! + expenseAmount.toDouble()
                                user.totalExpenses = updatedTotalExpenses.toString()
                                db.collection(Constants.COLLECTION_NAME_USERS).document(userId.toString()).update("totalExpenses",updatedTotalExpenses.toString())
                            } catch (e: NumberFormatException) {
                                // Handle the case where totalPrice or totalSales is not a valid number
                            }
                        }
                        popupNotification.value = Event("Expense updated successfully")
                        retrieveExpense()
                        retrieveHomeExpense()
                        retrieveUser()
                        onSuccess.invoke()
                        expenseProgress.value = false
                    }
                    .addOnFailureListener {exc->
                        handleException(exc,"failed to edit expense")
                        expenseProgress.value = false
                    }

            }
            .addOnFailureListener {exc->
                    handleException(exc, "expense does not exist")
                    expenseProgress.value = false
            }




    }

// customer history for sales
     fun retrieveCustomerHistory(customerId: String) {

        inProgress.value = true
        db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("customerId", customerId)
            .get()
            .addOnSuccessListener {document->
                convertHomeSales(document,customerHistoryData)
                inProgress.value = false
            }
            .addOnFailureListener {exc->
                handleException(exc,"Unable to fetch customer history")
                inProgress.value = false
            }
    }

    // stock history for stocks
    fun retrieveStockHistory(stockId: String){

        inProgress.value = true

        db.collection(Constants.COLLECTION_NAME_STOCKHISTORY).whereEqualTo("stockId", stockId)
            .get()
            .addOnSuccessListener { document->
                convertStockHistory(document,stockHistoryData)
                inProgress.value = false
            }
            .addOnFailureListener {exc->
                handleException(exc,"Unable to fetch stock history")
                inProgress.value = false
            }

    }

    private fun convertStockHistory(document:QuerySnapshot, mutableState:MutableState<List<StockHistory>>){

        val newStockHistory = mutableListOf<StockHistory>()
        document.forEach{doc->
            val stockHistory = doc.toObject(StockHistory::class.java)
            newStockHistory.add(stockHistory)
        }

        val sortedStockHistory = newStockHistory.sortedByDescending { it.stockDateAdded }
        mutableState.value = sortedStockHistory

    }

    fun updateUserPersonalData(name: String, email: String, number: String, onSuccess: () -> Unit ){

        inProgress.value = true

        var userId = auth.currentUser!!.uid
        val update = mapOf(
            "fullName" to name,
            "email" to email,
            "number" to number
        )
        db.collection(Constants.COLLECTION_NAME_USERS).document(userId).update(update)
            .addOnSuccessListener {
                inProgress.value = false
                popupNotification.value = Event("Personal Details Updated Successfully")
                retrieveUser()
                onSuccess.invoke()
             }
            .addOnFailureListener {exc->
                inProgress.value = false
                handleException(exc, "failed to update user personal detail")
            }

    }

    fun updateUserBusinessData(businessName: String, businessDescription: String, businessAddress: String,
                               number: String, addNumber:String, businessEmail:String,onSuccess: () -> Unit ){

        inProgress.value = true

        var userId = auth.currentUser!!.uid
        val update = mapOf(
            "businessName" to businessName,
            "businessDescription" to businessDescription,
            "businessAddress" to businessAddress,
            "number" to number,
            "additionalNumber" to addNumber,
            "businessEmailAddress" to businessEmail,

        )
        db.collection(Constants.COLLECTION_NAME_USERS).document(userId).update(update)
            .addOnSuccessListener {
                inProgress.value = false
                popupNotification.value = Event("Business Details Updated Successfully")
                retrieveUser()
                onSuccess.invoke()
            }
            .addOnFailureListener {exc->
                inProgress.value = false
                handleException(exc, "failed to update user business detail")
            }

    }

    fun fromPage(name:String){
        fromPageValue.value = name
    }


    fun getExpense(expense:Expense){
        expenseProgress.value = true
        db.collection(Constants.COLLECTION_NAME_EXPENSE).document(expense.expenseId.toString()).get()
            .addOnSuccessListener {document->
            if(document.exists()){
                val retrievedExpense = document.toObject(Expense::class.java)
                expenseSelected.value = retrievedExpense
                expenseProgress.value = false
            }
            }
            .addOnFailureListener {exc->
                handleException(exc, "failed to retrieve expense")
                expenseProgress.value = false
            }
    }





    fun getTotalProfitToday(){

        val userId = auth.currentUser?.uid

        val date = System.currentTimeMillis()

        val startOfDay = date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000).toLong()
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000).toLong()

        db.collection(Constants.COLLECTION_NAME_SALES)
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("salesDate", startOfDay)
            .whereLessThan("salesDate", endOfDay)
            .get()
            .addOnSuccessListener { documents ->

                var profitToday = 0.0

                for (document in documents) {

                    val sales = document.toObject(Sales::class.java)

                    for (singleSale in sales.sales!!) {
                        val singleProfit = singleSale.profit?.toDoubleOrNull() ?: 0.0
                        val singleQuantity = singleSale.quantity?.toDoubleOrNull() ?: 0.0

                        val saleProfitAmount = singleProfit * singleQuantity
                        profitToday += saleProfitAmount.toString().toDouble()
                    }

                }

                totalProfitToday.value = profitToday

                val updates = mapOf(
                    "totalProfit" to totalProfitToday.value.toString(),
                )
                db.collection(Constants.COLLECTION_NAME_USERS).document(userId.toString()).update(updates)
            }
            .addOnFailureListener { exception ->
                handleException(exception, "Unable to fetch sales")
            }
    }


    fun getTotalExpenseToday(){

        val userId = auth.currentUser?.uid

        val date = System.currentTimeMillis()

        val startOfDay = date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000).toLong()
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000).toLong()

        db.collection(Constants.COLLECTION_NAME_EXPENSE)
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("expenseDate", startOfDay)
            .whereLessThan("expenseDate", endOfDay)
            .get()
            .addOnSuccessListener { documents ->

                var expenseToday = 0.0

                for (document in documents) {

                    val expense = document.toObject(Expense::class.java)

                    val expenseAmount = expense.expenseAmount ?: 0.0

                    expenseToday += expenseAmount.toString().toDouble()

                }

                totalExpenseToday.value = expenseToday
                val updates = mapOf(
                    "totalExpenses" to totalExpenseToday.value.toString(),
                )
                db.collection(Constants.COLLECTION_NAME_USERS).document(userId.toString()).update(updates)
            }
            .addOnFailureListener { exception ->
                handleException(exception, "Unable to fetch sales")
            }
    }



    fun getAllSaleReceiptToday() {

        val userId = auth.currentUser?.uid

        val date = System.currentTimeMillis()

        val startOfDay = date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000).toLong()
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000).toLong()

        db.collection(Constants.COLLECTION_NAME_SALES)
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("salesDate", startOfDay)
            .whereLessThan("salesDate", endOfDay)
            .get()
            .addOnSuccessListener { documents ->

                var totalSaleToday = 0.0

                for (document in documents) {
                    val sales = document.toObject(Sales::class.java)


                    val saleAmount = sales.totalPrice ?: 0.0

                    totalSaleToday += saleAmount.toString().toDouble()
                }

                AllsalesTotalToday.value = totalSaleToday
                val updates = mapOf(
                    "totalSales" to totalSaleToday.toString(),
                )
                db.collection(Constants.COLLECTION_NAME_USERS).document(userId.toString()).update(updates)
            }
            .addOnFailureListener { exception ->
                handleException(exception, "Unable to fetch sales")
            }
    }

    fun getTotalSaleReceiptToday() {

        val userId = auth.currentUser?.uid

        val date = System.currentTimeMillis()

        val startOfDay = date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000).toLong()
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000).toLong()

        db.collection(Constants.COLLECTION_NAME_SALES)
            .whereEqualTo("userId", userId)
            .whereEqualTo("type", "SR")
            .whereGreaterThanOrEqualTo("salesDate", startOfDay)
            .whereLessThan("salesDate", endOfDay)
            .get()
            .addOnSuccessListener { documents ->

                var totalSaleToday = 0.0

                for (document in documents) {
                    val sales = document.toObject(Sales::class.java)


                    val saleAmount = sales.totalPrice ?: 0.0

                    totalSaleToday += saleAmount.toString().toDouble()
                }

                saleReceiptTotalToday.value = totalSaleToday
                val updates = mapOf(
                    "salesReceiptTotalToday" to totalSaleToday.toString(),
                )
                db.collection(Constants.COLLECTION_NAME_USERS).document(userId.toString()).update(updates)
            }
            .addOnFailureListener { exception ->
                handleException(exception, "Unable to fetch sales")
            }
    }




    fun getTotalCreditReceiptToday() {

        val userId = auth.currentUser?.uid

        val date = System.currentTimeMillis()

        val startOfDay = date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000).toLong()
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000).toLong()

        db.collection(Constants.COLLECTION_NAME_SALES)
            .whereEqualTo("userId", userId)
            .whereEqualTo("type", "CR")
            .whereGreaterThanOrEqualTo("salesDate", startOfDay)
            .whereLessThan("salesDate", endOfDay)
            .get()
            .addOnSuccessListener { documents ->

                var totalSaleToday = 0.0

                for (document in documents) {
                    val sales = document.toObject(Sales::class.java)


                    val saleAmount = sales.totalPrice ?: 0.0

                    totalSaleToday += saleAmount.toString().toDouble()
                }

                creditReceiptTotalToday.value = totalSaleToday
                val updates = mapOf(
                    "creditReceiptTotalToday" to totalSaleToday.toString(),
                )
                db.collection(Constants.COLLECTION_NAME_USERS).document(userId.toString()).update(updates)

            }
            .addOnFailureListener { exception ->
                handleException(exception, "Unable to fetch sales")
            }
    }


    fun getMostPurchasedProductsToday() {

        val userId = auth.currentUser?.uid

        val date = System.currentTimeMillis()

        val startOfDay = date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000).toLong()
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000).toLong()

        db.collection(Constants.COLLECTION_NAME_SALES)
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("salesDate", startOfDay)
            .whereLessThan("salesDate", endOfDay)
            .get()
            .addOnSuccessListener { documents ->

                // Map to store product names and their corresponding quantities
                val productQuantityMap = mutableMapOf<String, Int>()

                for (document in documents) {
                    val sales = document.toObject(Sales::class.java)

                    // Iterate through SingleSales in each Sale
                    sales?.sales?.forEach { singleSale ->
                        val productName = singleSale.productName
                        val quantity = singleSale.quantity?.toInt() ?: 0

                        // Update quantity for the product in the map
                        val currentQuantity = productQuantityMap.getOrDefault(productName, 0)
                        productQuantityMap[productName.toString()] = currentQuantity + quantity
                    }
                }

                // Find the maximum quantity
                val maxQuantity = productQuantityMap.values.maxOrNull()

                if (maxQuantity != null) {
                    // Filter products with the maximum quantity
                    val mostPurchasedProducts = productQuantityMap.filter { it.value == maxQuantity }.keys.toList()

                    // Now 'mostPurchasedProducts' contains the list of products with the maximum quantity
                    if (mostPurchasedProducts.isNotEmpty()) {
                        userData.value!!.mostSoldGoodToday = mostPurchasedProducts.toString()
                        userData.value!!.mostSoldGoodQuantity = maxQuantity.toString()
                        mostBoughtGoodToday.value = mostPurchasedProducts.toString()
                        mostBoughtGoodQuantity.value = maxQuantity.toString()
                        val updates = mapOf(
                            "mostSoldGoodQuantity" to mostBoughtGoodQuantity.value,
                            "mostSoldGoodToday" to mostBoughtGoodToday.value
                        )
                        db.collection(Constants.COLLECTION_NAME_USERS).document(userId.toString()).update(updates)

                    } else {
                        mostBoughtGoodToday.value = "no data yet"
                        mostBoughtGoodQuantity.value = "no data yet"
                    }
                } else {
                    mostBoughtGoodToday.value = "no data yet"
                    mostBoughtGoodQuantity.value = "no data yet"
                }
            }
            .addOnFailureListener { exception ->
                handleException(exception, "Unable to fetch sales")
            }
    }


    fun getAllPurchasedProductsToday() {
        val userId = auth.currentUser?.uid

        val date = System.currentTimeMillis()

        val startOfDay = date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000).toLong()
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000).toLong()

        db.collection(Constants.COLLECTION_NAME_SALES)
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("salesDate", startOfDay)
            .whereLessThan("salesDate", endOfDay)
            .get()
            .addOnSuccessListener { documents ->

                // Map to store product names and their corresponding quantities
                val productQuantityMap = mutableMapOf<String, Int>()

                for (document in documents) {
                    val sales = document.toObject(Sales::class.java)

                    // Iterate through SingleSales in each Sale
                    sales?.sales?.forEach { singleSale ->
                        val productName = singleSale.productName
                        val quantity = singleSale.quantity?.toInt() ?: 0

                        // Update quantity for the product in the map
                        val currentQuantity = productQuantityMap.getOrDefault(productName, 0)
                        productQuantityMap[productName.toString()] = currentQuantity + quantity
                    }
                }

                // Convert the map to a string to store or display
                val allPurchasedProducts = productQuantityMap.entries.joinToString { "${it.key}: ${it.value}" }

                // Update userData and relevant UI components
                if (productQuantityMap.isNotEmpty()) {
                    userData.value!!.goodsSold = allPurchasedProducts
                    goodsSold.value = allPurchasedProducts

                    val updates = mapOf(
                        "goodsSold" to goodsSold.value
                    )
                    db.collection(Constants.COLLECTION_NAME_USERS).document(userId.toString()).update(updates)
                } else {
                    goodsSold.value = "no data yet"
                }
            }
            .addOnFailureListener { exception ->
                handleException(exception, "Unable to fetch sales")
            }
    }



    fun deleteExpense(amount:String, expenseId:String, userId: String, onSuccess: () -> Unit){
        expenseProgress.value = true
        db.collection(Constants.COLLECTION_NAME_EXPENSE).document(expenseId).delete()
            .addOnSuccessListener {
                db.collection(Constants.COLLECTION_NAME_USERS).document(userId).get()
                    .addOnSuccessListener { document->
                        val user = document.toObject(User::class.java)

                        val expenseAmount = user!!.totalExpenses!!.toDouble()

                        val updatedUser = mapOf(
                            "totalExpenses" to (expenseAmount-amount.toDouble()).toString()
                        )

                        db.collection(Constants.COLLECTION_NAME_USERS).document(userId).update(updatedUser)
                            .addOnSuccessListener {
                                expenseProgress.value = false
                                popupNotification.value = Event("expense deleted successfully")
                                retrieveExpense()
                                retrieveHomeExpense()
                                getUserData(userId)
                                onSuccess.invoke()
                            }
                            .addOnFailureListener {

                            }
                    }
                    .addOnFailureListener {

                    }

            }
            .addOnFailureListener {exc->
                expenseProgress.value = false
                handleException(exc, "failed to delete expense")
            }

    }

    fun deleteStock(stockId:String, onSuccess: () -> Unit){

        deleteStockProgress.value = true
        db.collection(Constants.COLLECTION_NAME_STOCKS).document(stockId).delete()
            .addOnSuccessListener {
                deleteStockProgress.value = false
                popupNotification.value = Event("stock deleted successfully")
                retrieveStocks()
                getTotalStockValue()
                retrieveUser()
                onSuccess.invoke()
            }
            .addOnFailureListener {exc->
                deleteStockProgress.value = false
                handleException(exc, "failed to delete stock")
            }

    }


    fun deleteCustomer(customerId:String, onSuccess: () -> Unit){

        deleteCustomerProgress.value = true
        db.collection(Constants.COLLECTION_NAME_CUSTOMERS).document(customerId).delete()
            .addOnSuccessListener {
                deleteCustomerProgress.value = false
                popupNotification.value = Event("customer deleted successfully")
                retrieveCustomer()
                getTotalAmountOwingCustomer()
                retrieveUser()
                onSuccess.invoke()
            }
            .addOnFailureListener {exc->
                deleteCustomerProgress.value = false
                handleException(exc, "failed to delete customer")
            }
    }

    fun checkExpiryDate(){

        val userId = auth.currentUser!!.uid
        db.collection(Constants.COLLECTION_NAME_STOCKHISTORY).whereEqualTo("userId",userId)
            .get()
            .addOnSuccessListener {querySnapshot->

                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        popupNotification.value = Event("Fetching FCM registration token failed")
                        return@addOnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result

                    // Loop through the documents
                    for (document in querySnapshot.documents) {
                        val stockHistory = document.toObject(StockHistory::class.java)
                        db.collection(Constants.COLLECTION_NAME_STOCKS).whereEqualTo("stockName", stockHistory?.stockName.toString()).whereEqualTo("userId",userId).get()
                            .addOnSuccessListener { querySnapshot ->
                                if (!querySnapshot.isEmpty) {
                                    val stock = querySnapshot.documents[0].toObject(Stock::class.java)

                                    if (stock?.stockQuantity!!.toInt() > 0) {

                                        val date = userData.value?.currentDate?.let { timestamp ->
                                            val date = Date(timestamp)
                                            SimpleDateFormat("MMM dd yyyy").format(date)
                                        } ?: ""


                                        if (stockHistory?.stockExpiryDate == date) {
                                            val notificationId = stockHistory.stockId.hashCode()
                                            showNotification(
                                                "${stockHistory.stockName} is about to expire today",
                                                "Stock Expiry Date Update",
                                                notificationId
                                            )
                                        }


                                        if (stockHistory?.stockOneMonthToExpire == date) {
                                            val notificationId = stockHistory.stockId.hashCode()
                                            showNotification(
                                                "${stockHistory.stockName} is about to expire within a month",
                                                "Stock Expiry Date Update",
                                                notificationId
                                            )
                                        }

                                        if (stockHistory?.stockOneWeekToExpire == date) {
                                            val notificationId = stockHistory.stockId.hashCode()
                                            showNotification(
                                                "${stockHistory.stockName} is about to expire within a week",
                                                "Stock Expiry Date Update",
                                                notificationId
                                            )
                                        }

                                        if (stockHistory?.stockTwoWeeksToExpire == date) {
                                            val notificationId = stockHistory.stockId.hashCode()
                                            showNotification(
                                                "${stockHistory.stockName} is about to expire within two weeks",
                                                "Stock Expiry Date Update",
                                                notificationId
                                            )
                                        }

                                    }
                                }
                            }

                            .addOnFailureListener {

                            }



                        }
                    }
                }
            .addOnFailureListener {

            }

    }

    fun getDailyReport(option: String) {

        val userId = auth.currentUser?.uid
        val currentTimestamp = System.currentTimeMillis()

        val query = db.collection(Constants.COLLECTION_NAME_DAILYREPORT)


        when (option) {

            "This Week" -> {
                dailyReportProgress.value = true

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = currentTimestamp

// Calculate the start of the week (assuming the week starts on Monday)

                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    // If today is Sunday, set the start of the week to the previous Monday
                    calendar.add(Calendar.DAY_OF_WEEK, -6)
                }
                else {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                }

                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                val startOfWeekTimestamp = calendar.timeInMillis


                val mostSoldGoodQuantityMap = mutableMapOf<String, Int>()
                query.whereEqualTo("userId",userId)
                    .whereGreaterThanOrEqualTo("date", startOfWeekTimestamp)
                    .whereLessThanOrEqualTo("date", currentTimestamp)
                    .get()
                    .addOnSuccessListener { document ->
                        val dailyReports = document.toObjects(DailyReport::class.java)
                        // Calculate totals
                        val totalSalesSum = dailyReports.sumOf { it.totalSalesToday?.toDoubleOrNull() ?: 0.0 }
                        val creditTotalSum = dailyReports.sumOf { it.creditReceiptTotal?.toDoubleOrNull() ?: 0.0 }
                        val salesReceiptTotalSum = dailyReports.sumOf { it.salesReceiptTotal?.toDoubleOrNull() ?: 0.0 }
                        val expensesTotalSum = dailyReports.sumOf { it.totalExpensesToday?.toDoubleOrNull() ?: 0.0 }
                        val profit = dailyReports.sumOf { it.totalProfitToday?.toDoubleOrNull() ?: 0.0 }
                        val profitAfterExpenseSum = dailyReports.sumOf { it.profitAfterExpense?.toDoubleOrNull() ?: 0.0 }


                        val userMostSoldGood = userData.value?.mostSoldGoodToday ?: ""
                        val userMostSoldGoodQty = userData.value?.mostSoldGoodQuantity?.toIntOrNull() ?: 0


                        val userSoldGoods = userData.value?.goodsSold ?: ""
                        val userSoldGoodsMap = mutableMapOf<String, Int>()

                        // Parse userSoldGoods string into a map
                        if (userSoldGoods.isNotEmpty()) {
                            userSoldGoods.split(",").forEach {
                                val parts = it.split(":")
                                if (parts.size == 2) {
                                    val productName = parts[0].trim()
                                    val quantity = parts[1].trim().toIntOrNull() ?: 0
                                    userSoldGoodsMap[productName] = quantity
                                }
                            }
                        }

                        for (report in dailyReports) {
                            val soldGoodsString = report.goodsSold ?: ""
                            // Parse soldGoodsString into a map and update userSoldGoodsMap
                            soldGoodsString.split(",").forEach {
                                val parts = it.split(":")
                                if (parts.size == 2) {
                                    val productName = parts[0].trim()
                                    val quantity = parts[1].trim().toIntOrNull() ?: 0
                                    val currentQuantity = userSoldGoodsMap.getOrDefault(productName, 0)
                                    userSoldGoodsMap[productName] = currentQuantity + quantity
                                }
                            }
                        }

                        val goodsSoldString = userSoldGoodsMap.entries.joinToString { "${it.key}: ${it.value}" }
                        goodsSold.value = goodsSoldString

                        for (report in dailyReports) {
                            val mostSoldGoodsString = report.mostSoldGood ?: ""

                            // Remove square brackets and split the string into a list
                            val mostSoldGoods = mostSoldGoodsString
                                .removeSurrounding("[", "]")
                                .split(",")
                                .map { it.trim() }
                            val mostSoldGoodQuantity = report.mostSoldGoodQty?.toIntOrNull() ?: 0

                            // Update quantity for the most sold good in the map
                            for (mostSoldGood in mostSoldGoods) {
                                val currentQuantity = mostSoldGoodQuantityMap.getOrDefault(mostSoldGood, 0)
                                mostSoldGoodQuantityMap[mostSoldGood] = currentQuantity + mostSoldGoodQuantity
                            }

                            if (mostSoldGoods.contains(userMostSoldGood)) {
                                val currentQuantity = mostSoldGoodQuantityMap.getOrDefault(userMostSoldGood, 0)
                                mostSoldGoodQuantityMap[userMostSoldGood] = currentQuantity + userMostSoldGoodQty
                            }

                            if (userMostSoldGood.isNotEmpty() && !mostSoldGoodQuantityMap.containsKey(userMostSoldGood)) {
                                mostSoldGoodQuantityMap[userMostSoldGood] = userMostSoldGoodQty
                            }

                        }




                        val maxSoldGoodQuantity = mostSoldGoodQuantityMap.values.maxOrNull()

                        if (maxSoldGoodQuantity != null) {
                            // Filter most sold goods with the maximum quantity
                            val mostSoldGoods = mostSoldGoodQuantityMap.filter { it.value == maxSoldGoodQuantity }.keys.toList()

                            // Now 'mostSoldGoods' contains the list of most sold goods with the maximum quantity
                            if (mostSoldGoods.isNotEmpty()) {
                                val mostSoldGood = mostSoldGoods.toString()
                                val mostSoldGoodQuantitySum = maxSoldGoodQuantity.toString()
                                mostBoughtGoodToday.value = mostSoldGood
                                mostBoughtGoodQuantity.value = mostSoldGoodQuantitySum.toString()

                            } else {
                                // Handle the case where there are no most sold goods yet
                                // For example, set default values or log a message
                            }
                        } else {
                            // Handle the case where there are no most sold goods yet
                            // For example, set default values or log a message
                        }
                        // Handle most sold good and quantity


                        totalSalesFilter.value = totalSalesSum + userData.value!!.totalSales?.toDouble()!!
                        totalCreditReceiptFilter.value = creditTotalSum + userData.value!!.creditReceiptTotalToday?.toDouble()!!
                        totalSaleReceiptFilter.value = salesReceiptTotalSum + userData.value!!.salesReceiptTotalToday?.toDouble()!!
                        totalExpenseFilter.value = expensesTotalSum + userData.value!!.totalExpenses?.toDouble()!!
                        totalProfitFilter.value = profit + userData.value!!.totalProfit?.toDouble()!!
                        totalProfitAfterExpenseFilter.value = profitAfterExpenseSum + userData.value!!.totalProfit?.toDouble()!! - userData.value!!.totalExpenses?.toDouble()!!
                        dailyReportProgress.value = false
                        // Handle the retrieved daily reports and totals
                    }
                    .addOnFailureListener { exception ->
                        dailyReportProgress.value = false
                      handleException(exception,"error:")
                    }
            }

            "This Month" -> {

                dailyReportProgress.value = true
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = currentTimestamp

// Set the calendar to the first day of the current month
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                val startOfMonthTimestamp = calendar.timeInMillis


                val mostSoldGoodQuantityMap = mutableMapOf<String, Int>()
                query.whereEqualTo("userId",userId)
                    .whereGreaterThanOrEqualTo("date", startOfMonthTimestamp)
                    .whereLessThanOrEqualTo("date", currentTimestamp)
                    .get()
                    .addOnSuccessListener { document ->

                        val dailyReports = document.toObjects(DailyReport::class.java)

                        // Calculate totals
                        val totalSalesSum = dailyReports.sumOf { it.totalSalesToday?.toDoubleOrNull() ?: 0.0 }
                        val creditTotalSum = dailyReports.sumOf { it.creditReceiptTotal?.toDoubleOrNull() ?: 0.0 }
                        val salesReceiptTotalSum = dailyReports.sumOf { it.salesReceiptTotal?.toDoubleOrNull() ?: 0.0 }
                        val expensesTotalSum = dailyReports.sumOf { it.totalExpensesToday?.toDoubleOrNull() ?: 0.0 }
                        val profit = dailyReports.sumOf { it.totalProfitToday?.toDoubleOrNull() ?: 0.0 }
                        val profitAfterExpenseSum = dailyReports.sumOf { it.profitAfterExpense?.toDoubleOrNull() ?: 0.0 }

                        val userMostSoldGood = userData.value?.mostSoldGoodToday ?: ""
                        val userMostSoldGoodQty = userData.value?.mostSoldGoodQuantity?.toIntOrNull() ?: 0

                        val userSoldGoods = userData.value?.goodsSold ?: ""
                        val userSoldGoodsMap = mutableMapOf<String, Int>()

                        // Parse userSoldGoods string into a map
                        if (userSoldGoods.isNotEmpty()) {
                            userSoldGoods.split(",").forEach {
                                val parts = it.split(":")
                                if (parts.size == 2) {
                                    val productName = parts[0].trim()
                                    val quantity = parts[1].trim().toIntOrNull() ?: 0
                                    userSoldGoodsMap[productName] = quantity
                                }
                            }
                        }

                        for (report in dailyReports) {
                            val soldGoodsString = report.goodsSold ?: ""
                            // Parse soldGoodsString into a map and update userSoldGoodsMap
                            soldGoodsString.split(",").forEach {
                                val parts = it.split(":")
                                if (parts.size == 2) {
                                    val productName = parts[0].trim()
                                    val quantity = parts[1].trim().toIntOrNull() ?: 0
                                    val currentQuantity = userSoldGoodsMap.getOrDefault(productName, 0)
                                    userSoldGoodsMap[productName] = currentQuantity + quantity
                                }
                            }
                        }

                        val goodsSoldString = userSoldGoodsMap.entries.joinToString { "${it.key}: ${it.value}" }
                        goodsSold.value = goodsSoldString

                        for (report in dailyReports) {
                            val mostSoldGoodsString = report.mostSoldGood ?: ""

                            // Remove square brackets and split the string into a list
                            val mostSoldGoods = mostSoldGoodsString
                                .removeSurrounding("[", "]")
                                .split(",")
                                .map { it.trim() }
                            val mostSoldGoodQuantity = report.mostSoldGoodQty?.toIntOrNull() ?: 0

                            // Update quantity for the most sold good in the map
                            for (mostSoldGood in mostSoldGoods) {
                                val currentQuantity = mostSoldGoodQuantityMap.getOrDefault(mostSoldGood, 0)
                                mostSoldGoodQuantityMap[mostSoldGood] = currentQuantity + mostSoldGoodQuantity
                            }

                            if (mostSoldGoods.contains(userMostSoldGood)) {
                                val currentQuantity = mostSoldGoodQuantityMap.getOrDefault(userMostSoldGood, 0)
                                mostSoldGoodQuantityMap[userMostSoldGood] = currentQuantity + userMostSoldGoodQty
                            }

                            if (userMostSoldGood.isNotEmpty() && !mostSoldGoodQuantityMap.containsKey(userMostSoldGood)) {
                                mostSoldGoodQuantityMap[userMostSoldGood] = userMostSoldGoodQty
                            }
                        }

                        val maxSoldGoodQuantity = mostSoldGoodQuantityMap.values.maxOrNull()

                        if (maxSoldGoodQuantity != null) {
                            // Filter most sold goods with the maximum quantity
                            val mostSoldGoods = mostSoldGoodQuantityMap.filter { it.value == maxSoldGoodQuantity }.keys.toList()

                            // Now 'mostSoldGoods' contains the list of most sold goods with the maximum quantity
                            if (mostSoldGoods.isNotEmpty()) {
                                val mostSoldGood = mostSoldGoods.toString()
                                val mostSoldGoodQuantitySum = maxSoldGoodQuantity.toString()
                                mostBoughtGoodToday.value = mostSoldGood
                                mostBoughtGoodQuantity.value = mostSoldGoodQuantitySum.toString()

                            } else {
                                // Handle the case where there are no most sold goods yet
                                // For example, set default values or log a message
                            }
                        } else {
                            // Handle the case where there are no most sold goods yet
                            // For example, set default values or log a message
                        }

                        totalSalesFilter.value = totalSalesSum + userData.value!!.totalSales?.toDouble()!!
                        totalCreditReceiptFilter.value = creditTotalSum + userData.value!!.creditReceiptTotalToday?.toDouble()!!
                        totalSaleReceiptFilter.value = salesReceiptTotalSum + userData.value!!.salesReceiptTotalToday?.toDouble()!!
                        totalExpenseFilter.value = expensesTotalSum + userData.value!!.totalExpenses?.toDouble()!!
                        totalProfitFilter.value = profit + userData.value!!.totalProfit?.toDouble()!!
                        totalProfitAfterExpenseFilter.value = profitAfterExpenseSum + userData.value!!.totalProfit?.toDouble()!! - userData.value!!.totalExpenses?.toDouble()!!

                        dailyReportProgress.value = false

                            // Handle the retrieved daily reports and totals
                        }
                        .addOnFailureListener { exception ->
                            dailyReportProgress.value = false
                            handleException(exception,"error:")
                        }

                // Calculate the start of the month (1 month ago from today)

            }

            // No additional date filter for "All Time"
            "All Time" -> {
                dailyReportProgress.value = true
                val mostSoldGoodQuantityMap = mutableMapOf<String, Int>()
                query.whereEqualTo("userId",userId)
                    .get()
                    .addOnSuccessListener { document ->
                        val dailyReports = document.toObjects(DailyReport::class.java)

                        // Calculate totals
                        val totalSalesSum = dailyReports.sumOf { it.totalSalesToday?.toDoubleOrNull() ?: 0.0 }
                        val creditTotalSum = dailyReports.sumOf { it.creditReceiptTotal?.toDoubleOrNull() ?: 0.0 }
                        val salesReceiptTotalSum = dailyReports.sumOf { it.salesReceiptTotal?.toDoubleOrNull() ?: 0.0 }
                        val expensesTotalSum = dailyReports.sumOf { it.totalExpensesToday?.toDoubleOrNull() ?: 0.0 }
                        val profit = dailyReports.sumOf { it.totalProfitToday?.toDoubleOrNull() ?: 0.0 }
                        val profitAfterExpenseSum = dailyReports.sumOf { it.profitAfterExpense?.toDoubleOrNull() ?: 0.0 }

                        // Handle most sold good and quantity
                        val userMostSoldGood = userData.value?.mostSoldGoodToday ?: ""
                        val userMostSoldGoodQty = userData.value?.mostSoldGoodQuantity?.toIntOrNull() ?: 0

                        val userSoldGoods = userData.value?.goodsSold ?: ""
                        val userSoldGoodsMap = mutableMapOf<String, Int>()

                        // Parse userSoldGoods string into a map
                        if (userSoldGoods.isNotEmpty()) {
                            userSoldGoods.split(",").forEach {
                                val parts = it.split(":")
                                if (parts.size == 2) {
                                    val productName = parts[0].trim()
                                    val quantity = parts[1].trim().toIntOrNull() ?: 0
                                    userSoldGoodsMap[productName] = quantity
                                }
                            }
                        }

                        for (report in dailyReports) {
                            val soldGoodsString = report.goodsSold ?: ""
                            // Parse soldGoodsString into a map and update userSoldGoodsMap
                            soldGoodsString.split(",").forEach {
                                val parts = it.split(":")
                                if (parts.size == 2) {
                                    val productName = parts[0].trim()
                                    val quantity = parts[1].trim().toIntOrNull() ?: 0
                                    val currentQuantity = userSoldGoodsMap.getOrDefault(productName, 0)
                                    userSoldGoodsMap[productName] = currentQuantity + quantity
                                }
                            }
                        }

                        val goodsSoldString = userSoldGoodsMap.entries.joinToString { "${it.key}: ${it.value}" }
                        goodsSold.value = goodsSoldString

                        for (report in dailyReports) {
                            val mostSoldGoodsString = report.mostSoldGood ?: ""

                            // Remove square brackets and split the string into a list
                            val mostSoldGoods = mostSoldGoodsString
                                .removeSurrounding("[", "]")
                                .split(",")
                                .map { it.trim() }
                            val mostSoldGoodQuantity = report.mostSoldGoodQty?.toIntOrNull() ?: 0

                            // Update quantity for the most sold good in the map
                            for (mostSoldGood in mostSoldGoods) {
                                val currentQuantity = mostSoldGoodQuantityMap.getOrDefault(mostSoldGood, 0)
                                mostSoldGoodQuantityMap[mostSoldGood] = currentQuantity + mostSoldGoodQuantity
                            }

                            if (mostSoldGoods.contains(userMostSoldGood)) {
                                val currentQuantity = mostSoldGoodQuantityMap.getOrDefault(userMostSoldGood, 0)
                                mostSoldGoodQuantityMap[userMostSoldGood] = currentQuantity + userMostSoldGoodQty
                            }

                            if (userMostSoldGood.isNotEmpty() && !mostSoldGoodQuantityMap.containsKey(userMostSoldGood)) {
                                mostSoldGoodQuantityMap[userMostSoldGood] = userMostSoldGoodQty
                            }
                        }

                        val maxSoldGoodQuantity = mostSoldGoodQuantityMap.values.maxOrNull()

                        if (maxSoldGoodQuantity != null) {
                            // Filter most sold goods with the maximum quantity
                            val mostSoldGoods = mostSoldGoodQuantityMap.filter { it.value == maxSoldGoodQuantity }.keys.toList()

                            // Now 'mostSoldGoods' contains the list of most sold goods with the maximum quantity
                            if (mostSoldGoods.isNotEmpty()) {
                                val mostSoldGood = mostSoldGoods.toString()
                                val mostSoldGoodQuantitySum = maxSoldGoodQuantity.toString()
                                mostBoughtGoodToday.value = mostSoldGood
                                mostBoughtGoodQuantity.value = mostSoldGoodQuantitySum.toString()

                            } else {
                                // Handle the case where there are no most sold goods yet
                                // For example, set default values or log a message
                            }
                        } else {
                            // Handle the case where there are no most sold goods yet
                            // For example, set default values or log a message
                        }

                        totalSalesFilter.value = totalSalesSum + userData.value!!.totalSales?.toDouble()!!
                        totalCreditReceiptFilter.value = creditTotalSum + userData.value!!.creditReceiptTotalToday?.toDouble()!!
                        totalSaleReceiptFilter.value = salesReceiptTotalSum + userData.value!!.salesReceiptTotalToday?.toDouble()!!
                        totalExpenseFilter.value = expensesTotalSum + userData.value!!.totalExpenses?.toDouble()!!
                        totalProfitFilter.value = profit + userData.value!!.totalProfit?.toDouble()!!
                        totalProfitAfterExpenseFilter.value = profitAfterExpenseSum + userData.value!!.totalProfit?.toDouble()!! - userData.value!!.totalExpenses?.toDouble()!!


                        // Handle the retrieved daily reports and totals
                        dailyReportProgress.value = false
                    }
                    .addOnFailureListener { exception ->
                        dailyReportProgress.value = false
                        // Handle failure
                    }
            }

            "Custom" -> {
                // Implement custom date logic based on your requirements
                // For example, you can prompt the user to input a custom date and use that in the query
            }
        }


    }


   fun showNotification(content: String, title: String, notificationId: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel for Android Oreo and above
            val channel = NotificationChannel(
                "channel_id",
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentText(content)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.shop)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    fun addPin(pinNumber:String, onSuccess: () -> Unit){

        val userId = auth.currentUser?.uid
        val update = mapOf("pin" to pinNumber)
        db.collection(Constants.COLLECTION_NAME_USERS)
            .document(userId.toString()).update(update)
            .addOnSuccessListener {
                getUserData(userId.toString())
                onSuccess.invoke()
                popupNotification.value = Event("Pin Updated Successfully")

            }
            .addOnFailureListener {exc->
                handleException(exc)
            }
    }

    fun deletePin(onSuccess: () -> Unit){

        val userId = auth.currentUser?.uid
        val update = mapOf("pin" to "")
        db.collection(Constants.COLLECTION_NAME_USERS)
            .document(userId.toString()).update(update)
            .addOnSuccessListener {
                getUserData(userId.toString())
                onSuccess.invoke()
                popupNotification.value = Event("Pin Removed Successfully")
            }
            .addOnFailureListener {exc->
                handleException(exc)
            }

    }

    fun changePin(newPin:String, onSuccess: () -> Unit){

        val userId = auth.currentUser?.uid
        val update = mapOf("pin" to newPin)
        db.collection(Constants.COLLECTION_NAME_USERS)
            .document(userId.toString()).update(update)
            .addOnSuccessListener {
                getUserData(userId.toString())
                onSuccess.invoke()
                popupNotification.value = Event("Pin Changed Successfully")
            }
            .addOnFailureListener {exc->
                handleException(exc)
            }

    }

    fun print(businessName: String, businessDescription: String, businessAddress: String, businessNumber: String, customerName: String, invoiceType:String,
              invoiceNumber:String, invoiceDate:String, totalQuantity: String, totalAmount: String, salesList:List<SingleSale>,sale: Sales){
//        initListenersEvent.postValue(Unit)
//        (context as? MainActivity)?.printDetails(userData.value?.businessName.toString(),userData.value?.businessDescription.toString(),userData.value?.businessAddress.toString(),userData.value?.number.toString()+userData.value?.additionalNumber.toString(),sale.customerName.toString(),
//            sale.type.toString(), sale.salesNo.toString(), sale.salesDate.toString(), sale.totalQuantity.toString(), sale.totalPrice.toString(),salesList)
    }

    fun pay (){
        _payTrigger.value = Unit
    }


    fun featuresToPassWord(TotalSales:String, TotalExpenses:String, TotalProfit:String,
                           DailyReport:String, StockTotalValue:String, TotalAmountOwingCustomers:String,
                           EditStock:String, EditCustomers:String, DeleteStocks:String, DeleteCustomers:String,
                           EditSales:String, DeleteSale:String){

        inProgressFeaturesToPin.value = true

        val userId = auth.currentUser?.uid

        val update = mapOf(
            "totalSales" to TotalSales,
            "totalExpenses" to TotalExpenses,
            "totalProfit" to TotalProfit,
            "dailyReport" to DailyReport,
            "stockTotalValue" to StockTotalValue,
            "totalAmountOwingCustomers" to TotalAmountOwingCustomers,
            "editStocks" to EditStock,
            "editCustomers" to EditCustomers,
            "deleteStocks" to DeleteStocks,
            "deleteCustomers" to DeleteCustomers,
            "editSales" to EditSales,
            "deleteSales" to DeleteSale
        )

        db.collection(Constants.COLLECTION_NAME_FEATURESTOPIN).document(userId.toString()).update(update)
            .addOnSuccessListener {
                inProgressFeaturesToPin.value = false
                popupNotification.value = Event("Account Security Updated")
                retrievePasswordFeatures()
            }
            .addOnFailureListener { exc->
                inProgressFeaturesToPin.value = false
                handleException(exc)
            }
    }
    fun updateFeaturePassword(featureName: String, value: Boolean) {
        inProgressFeaturesToPin.value = true

        val userId = auth.currentUser?.uid

        val update = mapOf(
            featureName to value
        )

        db.collection(Constants.COLLECTION_NAME_FEATURESTOPIN)
            .document(userId.toString())
            .update(update)
            .addOnSuccessListener {
                inProgressFeaturesToPin.value = false
                popupNotification.value = Event("$featureName Security Updated")
                retrievePasswordFeatures()
            }
            .addOnFailureListener { exc ->
                inProgressFeaturesToPin.value = false
                handleException(exc)
            }
    }

    fun retrievePasswordFeatures() {
        inProgressFeaturesToPin.value = true
        val userId = auth.currentUser?.uid
        db.collection(Constants.COLLECTION_NAME_FEATURESTOPIN)
            .document(userId.toString())
            .get()
            .addOnSuccessListener { document ->
                val features = document.toObject(FeaturesToPin::class.java)
                if (features != null) {
                    featuresToPin.value = features
                }
                inProgressFeaturesToPin.value = false
            }
            .addOnFailureListener { exc ->
                handleException(exc)
                inProgressFeaturesToPin.value = false
            }
    }



    // function to handle erros
    fun handleException(exception:Exception? = null, customMessage:String =""){
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if(customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)

    }


}

