package com.example.stocka.data

import android.os.Parcel
import android.os.Parcelable

data class SingleSale(
    var saleId:String? = null,
    val userId:String? = null,
    var customerName:String? = null,
    var productName:String? = null,
    var quantity:String? = null,
    var price:String? = null,
    var totalPrice:String? = null,
    var profit:String? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(saleId)
        parcel.writeString(userId)
        parcel.writeString(customerName)
        parcel.writeString(productName)
        parcel.writeString(quantity)
        parcel.writeString(price)
        parcel.writeString(totalPrice)
        parcel.writeString(profit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SingleSale> {
        override fun createFromParcel(parcel: Parcel): SingleSale {
            return SingleSale(parcel)
        }

        override fun newArray(size: Int): Array<SingleSale?> {
            return arrayOfNulls(size)
        }
    }
}