package com.danielsouzza.poobank.model.client

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataClient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "cpf") val cpf: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "birth_date") val birthDate: String,
    @ColumnInfo(name = "phone") val phone: String
)

fun DataClient.toClient(): Client {
    return Client(
        accountId = accountId,
        cpf = this.cpf,
        name = this.name,
        birthDate = this.birthDate,
        phone = this.phone
    )
}

fun Client.toDataClient(): DataClient {
    return DataClient(
        accountId = this.getAccountId(),
        cpf = this.getCpf(),
        name = this.getName(),
        birthDate = this.getBirthDate(),
        phone = this.getPhone()
    )
}