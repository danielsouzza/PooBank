package com.danielsouzza.poobank.repository.account

import com.danielsouzza.poobank.App
import com.danielsouzza.poobank.model.account.*
import com.danielsouzza.poobank.model.client.DataClient
import com.danielsouzza.poobank.model.client.toClient
import com.danielsouzza.poobank.model.client.toDataClient
import com.danielsouzza.poobank.repository.database.AccountWithClient


class RepositoryRoomDB : RepositoryDB {

    private val app: App = App.getContext()
    private val accountDao = app.db.accountDao()
    private val clientDao = app.db.clientDao()
    private val extractDao = app.db.extractDao()
    private val keysPixDao = app.db.keysPixDao()
    private val accountClientDao = app.db.accountWithClientDoa()


    override suspend fun insertAccount(account: DataAccount, client: DataClient): Account {
        val user = account.toAccount(client.toClient())
        try {
            searchAccountWithEmail(account.email)
            throw AccountAlreadyRegistered()
        } catch (ex: AccountNotRegisteredException) {
            accountDao.insert(account)
            clientDao.insert(client)
            user.setKeysPix(KeysPix(user.getNumberAccount()))
            keysPixDao.insert(user.getKeysPix().toDataKeysPix())
        }
        return user
    }

    override suspend fun updateAccount(account: Account) {
        val extract = account.getExtract().getExtract()
        accountDao.updateAccount(account.toDataAccount())
        clientDao.update(account.getHolder().toDataClient())
        keysPixDao.updateKeys(account.getKeysPix().toDataKeysPix())
        if (extract.isNotEmpty()) {
            extract.forEach {
                extractDao.insert(it.toDataExtract())
            }
        }
    }

    override suspend fun deleteAccount(account: Account) {
        // Delete Account
    }

    override suspend fun searchAccount(numberAccount: Int): Account {
        val user: List<AccountWithClient> =
            accountClientDao.getAccountWithClient(numberAccount = numberAccount)
        return creatorAccount(user)

    }

    private suspend fun searchAccountWithEmail(email: String) {
        val user: List<DataAccount> =
            accountDao.getAccount(email = email)
        if(user.isEmpty()){
            throw AccountNotRegisteredException()
        }

    }


    override suspend fun login(email: String, password: String): Account {
        val user: List<AccountWithClient> =
            accountClientDao.login(email = email, password = password)
        if(user.isEmpty()){
            throw AccountNotRegisteredException()
        }
        return creatorAccount(user)
    }

    suspend fun getExtract(accountID: Int): List<DataExtract> {
        return extractDao.getExtract(accountID)
    }

    private fun creatorAccount(user: List<AccountWithClient>): Account {
        if (user.isNotEmpty()) {
            val client = user[0].client.toClient()
            val account = user[0].account.toAccount(client)
            account.setKeysPix(user[0].keysPix.toKeysPix())
            return account
        }
        throw AccountNotRegisteredException()
    }

    override suspend fun getNumberOfAccounts(): Int {
        return accountDao.getNumberOfAccounts()
    }
}