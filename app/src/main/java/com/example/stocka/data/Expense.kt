package com.example.stocka.data

import android.os.Parcel
import android.os.Parcelable

data class Expense(
    val expenseId:String?=null,
    val userId:String?=null,
    val expenseName:String?=null,
    val expenseDescription:String?=null,
    val expenseCategory:String?=null,
    val expenseAmount:String?=null
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
        parcel.writeString(expenseId)
        parcel.writeString(userId)
        parcel.writeString(expenseName)
        parcel.writeString(expenseDescription)
        parcel.writeString(expenseCategory)
        parcel.writeString(expenseAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }
    }
}