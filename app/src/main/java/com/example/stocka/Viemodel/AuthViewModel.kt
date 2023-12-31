package com.example.stocka.Viemodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.stocka.Util.Constants
import com.example.stocka.data.Customer
import com.example.stocka.data.Event
import com.example.stocka.data.Expense
import com.example.stocka.data.Sales
import com.example.stocka.data.SingleSale
import com.example.stocka.data.Stock
import com.example.stocka.data.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore
): ViewModel() {

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val userData = mutableStateOf<User?>(null)
    val popupNotification = mutableStateOf<Event<String>?>(null)
    val refreshSalesProgress = mutableStateOf(false)
    val expenseProgress = mutableStateOf(false)
    val expenseData = mutableStateOf<List<Expense>>(listOf())
    val salesData = mutableStateOf<List<Sales>>(listOf())
    val salesDetail = mutableStateOf<Sales?>(null)
    val salesHomeData = mutableStateOf<List<Sales>>(listOf())
    val salesHomeDetail = mutableStateOf<Sales?>(null)
    val customerData = mutableStateOf<List<Customer>>(listOf())
    val searchedCustomer = mutableStateOf<List<Customer>>(listOf())
    val customerSearchProgress = mutableStateOf(false)
    val searchedStocks = mutableStateOf<List<Stock>>(listOf())
    val stocks = mutableStateOf<List<Stock>>(listOf())
    val stockSearchProgress = mutableStateOf(false)
    val customerSelected = mutableStateOf<Customer?>(null)
    val salesSelected = mutableStateOf<Sales?>(null)
    val stockSelected = mutableStateOf<Stock?>(null)
    val singleSaleSelected = mutableStateOf<SingleSale?>(null)
    val totalAmountOwed = mutableStateOf(0.0)
    val totalStockValue = mutableStateOf(0.0)


    init {
        auth.signOut()
        val currentUser = auth.currentUser
        signedIn.value = currentUser!=null
        currentUser?.uid?.let {uid->
            getUserData(uid)
        }
    }

    fun onSignUp(fullName:String,businessName:String,email:String,password:String,confirmPassword:String){
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
                createOrUpdateProfile(fullName=fullName,businessName=businessName,email=email,password=password,confirmPassword = confirmPassword)
            }
            else{
                handleException(task.exception,"Signup failed")
            }
            inProgress.value = false
        }

    }

    fun login(email:String, password:String){
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
        confirmPassword:String? = null){

        val userId = auth.currentUser?.uid

        val user = User(
            userId = userId,
            fullName = fullName ?:userData.value?.fullName,
            businessName = businessName ?:userData.value?.businessName,
            email = email ?:userData.value?.email,
            password = password ?:userData.value?.password,
            confirmPassword = confirmPassword ?:userData.value?.confirmPassword,
            totalSales = "0.0",
            totalExpenses = "0.0",
            totalProfit = "0.0"
        )

        userId?.let {uid->
            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_USERS).document(uid).get().addOnSuccessListener {
                if(it.exists()){
                    it.reference.update(user.toMap())
                        .addOnSuccessListener {
                            this.userData.value = user
                            inProgress.value = false
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
                }
            }
                .addOnFailureListener{exc->
                    handleException(exc,"Cannot create user")
                    inProgress.value = false
                }

        }
    }

    fun logOut(onSuccess: () -> Unit){
        auth.signOut()
        signedIn.value = false
        userData.value = null
        onSuccess.invoke()
    }

    fun retrieveUser() {
        val uid = auth.currentUser?.uid
        db.collection(Constants.COLLECTION_NAME_USERS).whereEqualTo("userId", uid).get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                        val user = querySnapshot.documents[0].toObject<User>()
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
                val user = it.toObject<User>()
                userData.value = user
                inProgress.value = false
                retrieveSales()
                retrieveCustomer()
                retrieveStocks()
                retrieveExpense()
                refreshSales()
                getTotalStockValue()
                getTotalAmountOwingCustomer()

            }
            .addOnFailureListener{exc->
                handleException(exc,"Cannot retrieve user data")
                inProgress.value = false
            }

    }

    // home sales backend
    private fun retrieveSales() {

        val userId = auth.currentUser?.uid

        if (userId != null) {
            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener {document->
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

    private fun convertHomeSales(documents: QuerySnapshot, outState: MutableState<List<Sales>>){
        val newSales = mutableListOf<Sales>()
        documents.forEach{doc->
            val sale = doc.toObject<Sales>()
            newSales.add(sale)
        }
        val sortedSales = newSales.sortedBy { it.salesDate }
        outState.value = sortedSales

    }


// add customer backend
    fun createCustomer(
        name:String?=null,
        number:String?=null,
        address:String?=null,
        balance:String?="₦0", onSuccess: ()->Unit
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
        else{
            handleException(customMessage = "Error:Failed to add customer")
            inProgress.value = false
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
            val customer = doc.toObject<Customer>()
            newCustomer.add(customer)
        }
        val sortedCustomer = newCustomer.sortedBy { it.customerName }
        outState.value = sortedCustomer

    }




    // add stock backend
    fun createStock(name:String,purchasePrice:String,sellingPrice:String,expiryDate:String,quantity:String,onSuccess:()->Unit){
        val stockId = UUID.randomUUID().toString()
        val userId = auth.currentUser?.uid
        val stock = Stock(
            stockId = stockId,
            userId = userId,
            stockName = name.toLowerCase(),
            stockPurchasePrice = purchasePrice,
            stockSellingPrice = sellingPrice,
            stockExpiryDate = expiryDate,
            stockQuantity = quantity,
            stockQuantitySold = "0"
        )

        userId?.let {
            inProgress.value = true
            db.collection(Constants.COLLECTION_NAME_STOCKS).document(stockId).set(stock)
                .addOnSuccessListener {
                    popupNotification.value = Event("Stock added successfully")
                    retrieveStocks()
                    onSuccess.invoke()
                    inProgress.value = false

                }
                .addOnFailureListener{exc->
                    handleException(exc,"failed to add stock")
                }

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
            val stock = doc.toObject<Stock>()
            newStock.add(stock)
        }
        val sortedStock = newStock.sortedBy { it.stockName }
        outState.value = sortedStock
    }



//add expense backend
    fun AddExpense(name:String,description:String,category:String,amount:String,onSuccess:()->Unit){
        expenseProgress.value = true
        val currentUid = auth.currentUser?.uid
        if(currentUid!=null){
            val expenseUid = UUID.randomUUID().toString()
            var expense = Expense(
                expenseId = expenseUid,
                expenseName = name,
                userId = currentUid,
                expenseDescription = description,
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
                    retrieveExpense()
                    popupNotification.value = Event("Expense added successfully")
                    expenseProgress.value = false
                    onSuccess.invoke()
                    retrieveSales()
                    retrieveCustomer()
                    retrieveExpense()
                    retrieveStocks()
                }
                .addOnFailureListener{exc->
                    handleException(exc,"Unable to add expense")
                }
        }
        else{
            handleException(customMessage = "Unable to add expense")
        }

    }

    fun retrieveExpense(){
        val currentUid  = auth.currentUser?.uid

        if(currentUid!=null){
            expenseProgress.value = true
            db.collection(Constants.COLLECTION_NAME_EXPENSE).whereEqualTo("userId",currentUid).get()
                .addOnSuccessListener {document->
                    convertExpense(document,expenseData)
                }
                .addOnFailureListener{exc->
                    handleException(exc,"Unable to retrieve expenses")
                }
        }

        else{
            handleException(customMessage = "Unable to retrieve expense")
        }
    }

    fun convertExpense(documents: QuerySnapshot,outState: MutableState<List<Expense>>){
        val newExpense = mutableListOf<Expense>()
        documents.forEach{doc->
            val expense = doc.toObject<Expense>()
            newExpense.add(expense)
        }
        val sortedExpense = newExpense.sortedBy { it.expenseName }
        outState.value = sortedExpense
    }



// add sales  backend
    fun onNewSales(customerName:String,sales:List<SingleSale>,customerId:String,totalPrice:String,totalProfit:String,totalQuantity:String,onSalesSuccess:() -> Unit){
        inProgress.value = true
        val currentUid = auth.currentUser?.uid
        if(currentUid!=null){
            val salesUid = UUID.randomUUID().toString()
            var salesNumber = 0

            val sales = Sales(
                userId = currentUid,
                salesId =  salesUid,
                salesNo = salesNumber.toString(),
                sales = sales,
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
                    userData.value?.let { user ->
                        try {
                            val updatedTotalSales = user.totalSales?.toDouble()!! + totalPrice.toDouble()
                            val updatedTotalProfit = user.totalProfit?.toDouble()!! + totalProfit.toDouble()
                            user.totalSales = updatedTotalSales.toString()
                            user.totalProfit = updatedTotalProfit.toString()
                            val updates = mapOf(
                                "totalSales" to updatedTotalSales.toString(),
                                "totalProfit" to updatedTotalProfit.toString()
                            )
                            db.collection(Constants.COLLECTION_NAME_USERS).document(currentUid).update(updates)
                        } catch (e: NumberFormatException) {
                            // Handle the case where totalPrice or totalSales is not a valid number
                        }
                    }
                    this.salesDetail.value = sales
                    popupNotification.value = Event("Sales created successfully")
                    inProgress.value = false
                    refreshSales()
                    retrieveSales()
                    onSalesSuccess.invoke()
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
        }

    }

    private fun convertSales(documents: QuerySnapshot, outState: MutableState<List<Sales>>){
        val newSales = mutableListOf<Sales>()
        documents.forEach{doc->
            val sale = doc.toObject<Sales>()
            newSales.add(sale)
        }
        val sortedSales = newSales.sortedBy {it.customerName}
        outState.value = sortedSales

    }

    //search for customers
    fun customerSearch(name:String){
        if(name.isNotEmpty()){
            customerSearchProgress.value = true
            db.collection(Constants.COLLECTION_NAME_CUSTOMERS).whereEqualTo("customerName",name.trim().toLowerCase())
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

    private fun convertSearchedCustomer(documents:QuerySnapshot,outState:MutableState<List<Customer>>){
        val newCustomer = mutableListOf<Customer>()
        documents.forEach{doc->
            val customer = doc.toObject<Customer>()
            newCustomer.add(customer)
        }
        val sortedCustomer = newCustomer.sortedBy { it.customerName }
        outState.value = sortedCustomer

    }

    //search for stocks
    fun stockSearch(name:String){
        if(name.isNotEmpty()){
            stockSearchProgress.value = true
            db.collection(Constants.COLLECTION_NAME_STOCKS).whereEqualTo("stockName",name.trim().toLowerCase())
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

    private fun convertSearchedStocks(document:QuerySnapshot,outState:MutableState<List<Stock>>){
        val newStock = mutableListOf<Stock>()
        document.forEach{doc->
            val stock = doc.toObject<Stock>()
            newStock.add(stock)
        }
        val sortedStock = newStock.sortedBy { it.stockName }
        outState.value = sortedStock
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
    fun onMultipleStocksSold(stockQuantityList: List<Pair<Stock, Int>>) {
        // Combine quantities for stocks with the same ID
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
                // Successfully updated stock quantities
                retrieveStocks()
                getTotalStockValue()
            }
            .addOnFailureListener { e ->
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
    fun deleteSingleSale(salesId: String, singleSale: String, onSuccess: () -> Unit) {
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
                                                    .get()
                                                    .addOnSuccessListener { stockDocuments ->
                                                        if (!stockDocuments.isEmpty) {
                                                            val stockDocument = stockDocuments.documents[0]
                                                            val stock = stockDocument.toObject(Stock::class.java)
                                                            stock?.let {
                                                                // Update the stock quantities based on the saleToDelete
                                                                stock.stockQuantitySold = ((stock.stockQuantitySold?.toIntOrNull() ?: 0) - (salesProductName?.quantity?.toIntOrNull() ?: 0)).toString()
                                                                stock.stockQuantity =  ((stock.stockQuantity?.toIntOrNull() ?: 0) + (salesProductName?.quantity?.toIntOrNull() ?: 0)).toString()

                                                                continueWithDeleteSale(
                                                                    salesId,
                                                                    singleSale,
                                                                    onSuccess,stock)
                                                            }
                                                        } else {
                                                            handleException(null, "Stock document not found for deletion")
                                                        }
                                                    }
                                                    .addOnFailureListener { e ->
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
                    handleException(customMessage = "Sales does not exist")
                }
            }
            .addOnFailureListener { e ->
                handleException(e, "Failed to delete sale")
            }
    }
    private fun continueWithDeleteSale(salesId: String, singleSale: String, onSuccess: () -> Unit, stock: Stock) {
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
                        val sales = updatedSalesList?.sumByDouble { sale -> sale.totalPrice?.toDoubleOrNull() ?: 0.0 }
                        val profit = updatedSalesList?.sumByDouble { sale -> sale.profit?.toDoubleOrNull() ?: 0.0 }
                        val quantity = updatedSalesList?.sumByDouble { sale -> sale.quantity?.toDoubleOrNull() ?: 0.0 }
                        it.sales = updatedSalesList
                        it.sales = updatedSalesList
                        it.totalPrice = sales?.toString() ?: "0.0"
                        it.totalProfit = profit?.toString() ?: "0.0"
                        it.totalQuantity = quantity?.toString() ?: "0.0"


                        deleteSales(salesId, it, onSuccess)
                        updateStock(stock)

                    }
                } else {
                    handleException(customMessage = "Sales does not exist")
                }
            }
            .addOnFailureListener { e ->
                handleException(e, "Failed to delete sale")
            }
    }
    fun deleteSales(salesId: String, updatedSales: Sales,onSuccess: () -> Unit) {
        db.collection(Constants.COLLECTION_NAME_SALES)
            .document(salesId)
            .update(
                "sales", updatedSales.sales,
                "totalPrice", updatedSales.totalPrice,
                "totalProfit", updatedSales.totalProfit,
                "totalQuantity", updatedSales.totalQuantity

                )
            .addOnSuccessListener {
                if (updatedSales.sales.isNullOrEmpty()) {
                    // Delete the sales document when the sales list is empty
                    deleteSalesDocument(salesId, onSuccess)
                } else {
                    popupNotification.value = Event("Sales deleted successfully")
                    retrieveSales()
                    retrieveStocks()
                    getTotalStockValue()
                    retrieveUser()
                    retrieveCustomer()
                    onSuccess.invoke()
                }
            }
            .addOnFailureListener { e ->
                handleException(customMessage = "failed to delete sale")
            }
    }

     fun deleteSalesDocument(salesId: String, onSuccess: () -> Unit) {
        val uid = auth.currentUser?.uid
        db.collection(Constants.COLLECTION_NAME_SALES)
            .document(salesId)
            .delete()
            .addOnSuccessListener {
                popupNotification.value = Event("Sales deleted successfully")
                retrieveSales()
                retrieveStocks()
                retrieveUser()
                retrieveCustomer()
                getTotalStockValue()
                onSuccess.invoke()

            }
            .addOnFailureListener { e ->
                handleException(customMessage = "Failed to delete sale")
            }
    }

    fun deleteEntireDocument(salesId: String, onSuccess: () -> Unit) {

        db.collection(Constants.COLLECTION_NAME_SALES)
            .document(salesId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val sales = document.toObject(Sales::class.java)
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

                                                    db.collection(Constants.COLLECTION_NAME_STOCKS)
                                                        .whereEqualTo("stockName", sale.productName.toString())
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
                                                            }else {
                                                                handleException(null, "Stock document not found for deletion")
                                                            }
                                                        }
                                                        .addOnFailureListener { e ->
                                                            handleException(e, "Failed to load stock for deletion")
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
                    handleException(customMessage = "Sales does not exist")
                }
            }
            .addOnFailureListener { e ->
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
                            }
                            .addOnFailureListener { e ->

                            }
                    } else {

                    }
                }
                .addOnFailureListener { e ->

                }
        } else {

        }
    }


    fun updateSale(customerName: String, stockName:String, unitPrice:String, quantity: String, totalPrice: String, profit:String,
                   saleId: String, singleSaleId: String){

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

                    sales?.sales = sales?.sales?.map {
                        (if(it.saleId == singleSaleId) updateSingleSale else it)!!
                    }

                    saleRef.set(sales!!)
                        .addOnSuccessListener {
                            popupNotification.value = Event("Sale updated successfully")
                        }
                        .addOnFailureListener{exc->
                                handleException(exc,"Failed to update sale")
                        }
                }
                else{
                    handleException(customMessage = "Sale document does not exist")
                }
            }
            .addOnFailureListener{
                    handleException(customMessage = "Failed to fetch sale document")
            }

    }


    fun getSale(saleId:String){
        val uid = auth.currentUser?.uid
        db.collection(Constants.COLLECTION_NAME_SALES).whereEqualTo("userId",uid).whereEqualTo("salesId",saleId)
            .get()
            .addOnSuccessListener {querySnapshot->
                if(!querySnapshot.isEmpty){
                    val sales = querySnapshot.documents[0].toObject<Sales>()
                    this.salesDetail.value = sales

                }
                else{
                    this.salesDetail.value = null
                }

            }
            .addOnFailureListener{

            }
    }


    fun onSaleSelected(sale: Sales) {
        salesSelected.value = sale
    }

    //get total amount owing customer

    fun getTotalAmountOwingCustomer() {

        val userId = auth.currentUser?.uid
        totalAmountOwed.value
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

         db.collection(Constants.COLLECTION_NAME_STOCKS).whereEqualTo("stockName",productName.productName).get()
             .addOnSuccessListener {querySnapshot->
                 if(!querySnapshot.isEmpty){

                     val stock = querySnapshot.documents[0].toObject<Stock>()
                     stockSelected.value = stock
                 }
             }
             .addOnFailureListener{

             }
     }

    fun getSingleSale(single: SingleSale, sale: Sales) {
        db.collection(Constants.COLLECTION_NAME_SALES).document(sale.salesId.toString()).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val sales = document.toObject<Sales>()
                    sales?.let {
                        val updatedSale = it.sales?.find { it.saleId == single.saleId }

                        updatedSale?.let {
                            singleSaleSelected.value = it
                        }
                    }
                }
            }
            .addOnFailureListener {

            }
    }


// function to handle erros
    fun handleException(exception:Exception? = null, customMessage:String =""){
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if(customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)

    }


}


