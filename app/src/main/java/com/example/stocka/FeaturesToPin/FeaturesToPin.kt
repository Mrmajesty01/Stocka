package com.example.stocka.FeaturesToPin

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocka.Viemodel.AuthViewModel
import com.example.stocka.ui.theme.ListOfColors

@SuppressLint("UnrememberedMutableState")
@Composable
fun FeaturesToPin(navController: NavController, viewModel: AuthViewModel){

    val isLoading = viewModel.inProgressFeaturesToPin.value
    val features by viewModel.featuresToPin

    var totalSalesChecked = features.TotalSales
    var totalExpenseChecked = features.TotalExpenses
    var totalProfitChecked = features.TotalProfit
    var totalDailyReportChecked = features.DailyReport
    var totalStockValueChecked = features.StockTotalValue
    var totalOwingCustomersChecked = features.TotalAmountOwingCustomers
    var totalEditStockChecked = features.EditStocks
    var totalEditCustomerChecked = features.EditCustomers
    var totalDeleteStockChecked = features.DeleteStocks
    var totalDeleteCustomerChecked = features.DeleteCustomers
    var totalEditSalesChecked = features.EditSales
    var totalDeleteSalesChecked = features.DeleteSales
    var payCreditChecked = features.PayCredit


    val focus = LocalFocusManager.current

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
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackIcon",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(15.dp)
                        .clickable {
                            if (!isLoading) {
                                navController.popBackStack()
                            }
                        },
                    tint = ListOfColors.black
                )

                Text(
                    text = "Features to Password",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )

            }

            Divider(thickness = 1.dp, color = ListOfColors.lightGrey)

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Total Sales",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalSalesChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("TotalSales", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }


                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Total Expenses",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalExpenseChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("TotalExpenses", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }


                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Total Profit",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalProfitChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("TotalProfit", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }


                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Daily Report",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalDailyReportChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("DailyReport", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Stock Total Value",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalStockValueChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("StockTotalValue", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }


                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Total Owing Customers",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalOwingCustomersChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("TotalAmountOwingCustomers", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Edit Stocks",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalEditStockChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("EditStocks", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Edit Customers",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalEditCustomerChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("EditCustomers", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Delete Stocks",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalDeleteStockChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("DeleteStocks", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }


                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Delete Customers",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalDeleteCustomerChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("DeleteCustomers", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Edit Sales",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalEditSalesChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("EditSales", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Delete Sales",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = totalDeleteSalesChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("DeleteSales", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Pay Credit",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp)
                    )
                    Switch(
                        checked = payCreditChecked,
                        onCheckedChange = {
                            viewModel.updateFeaturePassword("PayCredit", it)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterEnd),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ListOfColors.orange, // Change thumb color when checked
                            uncheckedThumbColor = ListOfColors.lightGrey, // Change thumb color when unchecked
                            checkedTrackColor = ListOfColors.orange, // Change track color when checked
                            uncheckedTrackColor = ListOfColors.lightGrey // Change track color when unchecked
                        )
                    )
                }




            }


        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
            )
        }
    }

}

@Composable
fun FeatureRow(text: String, isDisabled: Boolean, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit, onSuccessListener: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 5.dp)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange(it)
            },
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterEnd),
            enabled = !isDisabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = ListOfColors.orange,
                uncheckedThumbColor = ListOfColors.lightGrey,
                checkedTrackColor = ListOfColors.orange,
                uncheckedTrackColor = ListOfColors.lightGrey
            )
        )
    }

}
