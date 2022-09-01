package com.danielsouzza.poobank.repository.account

import android.provider.ContactsContract
import com.danielsouzza.poobank.App
import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.model.account.DataKeysPix
import com.danielsouzza.poobank.model.account.toAccount
import com.danielsouzza.poobank.model.account.toKeysPix
import com.danielsouzza.poobank.model.client.toClient
import com.danielsouzza.poobank.repository.database.AccountWithClient

class RepositoryKeysPixDB {

    private val app: App = App.getContext()
    private val userDao = app.db.accountWithClientDoa()
    private val keysPixDao = app.db.keysPixDao()

    suspend fun insertKeys(keys:DataKeysPix){
        keysPixDao.insert(keys)
    }

    suspend fun update(keys:DataKeysPix){
        keysPixDao.updateKeys(keys)
    }

    suspend fun getAccount(key:String): Account{
        val id = keysPixDao.searchKeyPix(key)
        val user = userDao.getAccountWithClient(id)
        return creatorAccount(user)
    }

    suspend fun getAllKeys(accountId: Int): DataKeysPix{
        val keys = keysPixDao.getAccount(accountId = accountId)
        if(keys.isNotEmpty()){
            return keys[0]
        }
        throw KeyPixNotRegisteredException()
    }

    suspend fun removeKey(keys: DataKeysPix){
        keysPixDao.updateKeys(keys)
    }

    private fun creatorAccount(user: List<AccountWithClient>): Account {
        if (user.isNotEmpty()) {
            val client = user[0].client.toClient()
            val keys = user[0].keysPix.toKeysPix()
            val account = user[0].account.toAccount(client)
            account.setKeysPix(keys)
            return account
        }
        throw KeyPixNotRegisteredException()
    }
}