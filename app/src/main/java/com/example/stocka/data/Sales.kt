package com.example.stocka.data

import android.os.Parcel
import android.os.Parcelable
data class Sales(
    val salesId:String? = null,
    val userId:String? = null,
    val salesNo:String? = null,
    val customerName:String?=null,
    val customerId:String?=null,
    val salesDate:Long? = null,
    var sales:List<SingleSale>?= listOf(),
    var totalPrice:String? = null,
    var totalProfit:String? = null,
    val amountPaid:String?=null,
    val balance:String?=null,
    var totalQuantity:String?=null,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        mutableListOf<SingleSale>().apply {
            parcel.readList(this, SingleSale::class.java.classLoader)
        },
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(salesId)
        parcel.writeString(userId)
        parcel.writeString(salesNo)
        parcel.writeString(customerName)
        parcel.writeString(customerId)
        parcel.writeValue(salesDate)
        parcel.writeList(sales)
        parcel.writeString(totalPrice)
        parcel.writeString(totalProfit)
        parcel.writeString(amountPaid)
        parcel.writeString(balance)
        parcel.writeString(totalQuantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sales> {
        override fun createFromParcel(parcel: Parcel): Sales {
            return Sales(parcel)
        }

        override fun newArray(size: Int): Array<Sales?> {
            return arrayOfNulls(size)
        }
    }
}