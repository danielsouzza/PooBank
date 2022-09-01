package com.danielsouzza.poobank.model.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danielsouzza.poobank.App
import com.danielsouzza.poobank.R

@Entity
data class DataKeysPix(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "email_key") val emailKey: String,
    @ColumnInfo(name = "cpf_key") val cpfKey: String,
    @ColumnInfo(name = "phone_key") val phoneKey: String,
    @ColumnInfo(name = "random_key") val randomKey: String
)

fun KeysPix.toDataKeysPix(): DataKeysPix {
    return DataKeysPix(
        id = this.getAccountId(),
        accountId = this.getAccountId(),
        emailKey = this.getEmailKey(),
        cpfKey = this.getCpfKey(),
        phoneKey = this.getPhoneKey(),
        randomKey = this.getRandomKey()
    )
}

fun DataKeysPix.toKeysPix() = KeysPix(
    accountId = accountId,
    emailKey = emailKey,
    cpfKey = cpfKey,
    phoneKey = phoneKey,
    randomKey = randomKey
)

fun DataKeysPix.toList() = listOf(
    emailKey, cpfKey, phoneKey, randomKey
)

fun DataKeysPix.toMap() = mapOf(
    App.getRes().getString(R.string.text_email) to emailKey,
    App.getRes().getString(R.string.text_cpf) to cpfKey,
    App.getRes().getString(R.string.text_phone) to phoneKey,
    App.getRes().getString(R.string.text_random) to randomKey
)
