package com.danielsouzza.poobank.repository.database

import androidx.room.Embedded
import androidx.room.Relation
import com.danielsouzza.poobank.model.account.DataAccount
import com.danielsouzza.poobank.model.account.DataExtract
import com.danielsouzza.poobank.model.account.DataKeysPix
import com.danielsouzza.poobank.model.client.DataClient

data class AccountWithClient(
    @Embedded val account: DataAccount,
    @Relation(parentColumn = "id",entityColumn = "account_id") val client: DataClient,
    @Relation(parentColumn = "id",entityColumn = "account_id") val extract: List<DataExtract>,
    @Relation(parentColumn = "id",entityColumn = "account_id") val keysPix: DataKeysPix
)
