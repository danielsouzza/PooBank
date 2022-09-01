package com.danielsouzza.poobank.model.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataExtract(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "historic") val historic: String,
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "previous_balance") val previousBalance: Double,
    @ColumnInfo(name = "posterior_balance")val posteriorBalance: Double
)

fun DataExtract.toExtractItem(): ExtractItem{
    return ExtractItem(
        accountId = accountId,
        date = date,
        historic = historic,
        value = value,
        previousBalance = previousBalance,
        posteriorBalance = posteriorBalance

    )
}

fun ExtractItem.toDataExtract(): DataExtract{
    return DataExtract(
        accountId = this.getAccountId(),
        date = this.getData(),
        historic = this.getHistoric(),
        value = this.getValue(),
        previousBalance = this.getPreviousBalance(),
        posteriorBalance = this.getPosteriorBalance()
    )
}



