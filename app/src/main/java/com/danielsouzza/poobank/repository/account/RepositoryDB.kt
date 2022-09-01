package com.danielsouzza.poobank.repository.account

import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.model.account.DataAccount
import com.danielsouzza.poobank.model.client.DataClient

interface RepositoryDB {
    suspend fun insertAccount(account: DataAccount, client: DataClient):Account
    suspend fun updateAccount(account: Account)
    suspend fun deleteAccount(account: Account)
    suspend fun login(email: String, password:String): Account
    suspend fun getNumberOfAccounts(): Int
    suspend fun searchAccount(numberAccount: Int): Account

}