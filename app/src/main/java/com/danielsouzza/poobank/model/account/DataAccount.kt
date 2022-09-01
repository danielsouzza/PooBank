package com.danielsouzza.poobank.model.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danielsouzza.poobank.model.client.Client

@Entity
data class DataAccount(
    @PrimaryKey(autoGenerate = true) val id :Int = 0,
    @ColumnInfo(name = "number_account") val numberAccount: Int,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "balance") val balance: Double = 0.0,
)

fun DataAccount.toAccount(client: Client): Account{
    val account = SavingsAccount(
        holder = client,
        email = this.email,
        password = this.password,
    )
    account.setBalance(value= this.balance)
    account.setNumber(number= numberAccount)
    return account
}

fun Account.toDataAccount(): DataAccount{
    return DataAccount(
        id= this.getNumberAccount(),
        numberAccount = this.getNumberAccount(),
        email = this.getEmail(),
        password = this.getPassword(),
        balance = this.getBalance()
    )
}