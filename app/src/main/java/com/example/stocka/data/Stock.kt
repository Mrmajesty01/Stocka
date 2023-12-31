package com.example.stocka.data

import android.os.Parcel
import android.os.Parcelable

data class Stock(
    var stockId:String?=null,
    var userId:String?=null,
    var stockName:String?=null,
    var stockPurchasePrice:String?=null,
    var stockSellingPrice:String?=null,
    var stockExpiryDate:String?=null,
    var stockQuantity:String?=null,
    var stockDateAdded:String?=null,
    var stockQuantitySold:String?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
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
        parcel.writeString(stockId)
        parcel.writeString(userId)
        parcel.writeString(stockName)
        parcel.writeString(stockPurchasePrice)
        parcel.writeString(stockSellingPrice)
        parcel.writeString(stockExpiryDate)
        parcel.writeString(stockQuantity)
        parcel.writeString(stockDateAdded)
        parcel.writeString(stockQuantitySold)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stock> {
        override fun createFromParcel(parcel: Parcel): Stock {
            return Stock(parcel)
        }

        override fun newArray(size: Int): Array<Stock?> {
            return arrayOfNulls(size)
        }
    }
}