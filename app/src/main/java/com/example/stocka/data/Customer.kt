package com.example.stocka.data

import android.os.Parcel
import android.os.Parcelable

data class Customer(
    var userId:String?=null,
    var customerId:String?=null,
    var customerName:String?=null,
    var customerNumber:String?=null,
    var customerAddress:String?=null,
    var customerBalance:String?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(customerId)
        parcel.writeString(customerName)
        parcel.writeString(customerNumber)
        parcel.writeString(customerAddress)
        parcel.writeString(customerBalance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun createFromParcel(parcel: Parcel): Customer {
            return Customer(parcel)
        }

        override fun newArray(size: Int): Array<Customer?> {
            return arrayOfNulls(size)
        }
    }
}