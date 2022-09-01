package com.danielsouzza.poobank.controler

import com.danielsouzza.poobank.model.account.*
import com.danielsouzza.poobank.model.client.DataClient
import com.danielsouzza.poobank.regex.Regx
import com.danielsouzza.poobank.repository.account.RepositoryKeysPixDB
import com.danielsouzza.poobank.repository.account.RepositoryRoomDB
import com.danielsouzza.poobank.repository.client.RepositoryClient
import com.danielsouzza.poobank.repository.client.RepositoryClientList
import com.danielsouzza.poobank.stragy.SequentialNumberStrategy

class BankController {

    companion object {
        private var repositoryClient: RepositoryClient = RepositoryClientList()
        private val repositoryDB: RepositoryRoomDB = RepositoryRoomDB()
        private val repositoryKeysPix: RepositoryKeysPixDB = RepositoryKeysPixDB()
        private val numberStrategy: SequentialNumberStrategy = SequentialNumberStrategy()
        private lateinit var userCurrent: Account
        private val regex = Regx()

    }

    suspend fun insertDatabase(account: DataAccount, client: DataClient): Account {
        return repositoryDB.insertAccount(account, client)
    }

    fun setUserCurrent(user: Account) {
        userCurrent = user
    }

    fun getUserCurrent(): Account {
        return userCurrent
    }

    suspend fun getNumberOfAccounts(): Int {
        return repositoryDB.getNumberOfAccounts()
    }

    suspend fun getAllKeys(id: Int): DataKeysPix {
        return repositoryKeysPix.getAllKeys(id)
    }

    suspend fun searchKeyPix(key: String): Account {
        val account =  repositoryKeysPix.getAccount(key)
        if(account.getHolder().getCpf()  != userCurrent.getHolder().getCpf()){
            return account
        }
        throw ControllerException("Não foi possivél realizar a transferência")
    }

    suspend fun removeKeyPix(key: String) {
        when {
            regex.checkPattern(
                key,
                regex.emailRegx
            ) -> userCurrent.getKeysPix().setEmailKey("")


            regex.checkPattern(
                key,
                regex.cpfRegx
            ) -> userCurrent.getKeysPix().setCpfKey("")

            regex.checkPattern(
                key,
                regex.phoneRegx
            ) -> userCurrent.getKeysPix().setPhoneKey("")

            else -> userCurrent.getKeysPix().setRandomKey("")
        }
        repositoryDB.updateAccount(userCurrent)
    }

    suspend fun insertKeyPix(keys: KeysPix) {
        userCurrent.setKeysPix(keys = keys)
        repositoryKeysPix.insertKeys(keys.toDataKeysPix())
    }

    suspend fun updateKeysPix() {
        repositoryKeysPix.update(keys = userCurrent.getKeysPix().toDataKeysPix())
    }

    suspend fun searchAccount(numberAccount: Int): Account {
        return repositoryDB.searchAccount(numberAccount)
    }

    suspend fun login(email: String, password: String): Account {
        return repositoryDB.login(email, password)
    }

    suspend fun updateAccount(account: Account) {
        repositoryDB.updateAccount(account)
    }


    suspend fun deleteAccount(account: Account) {
        if (account.getBalance() == 0.0) {
            repositoryDB.deleteAccount(account)
        } else {
            throw ControllerException("Conta com saldo não pode ser excluída.")
        }
    }

    suspend fun deposit(value: Double) {
        userCurrent.deposit(value)
        repositoryDB.updateAccount(userCurrent)
    }

    suspend fun transfer(destination: Int, value: Double) {
        val accountDestination = repositoryDB.searchAccount(destination)
        userCurrent.transfer(accountDestination, value)
        updateAccount(userCurrent)
        updateAccount(accountDestination)
    }

    suspend fun getExtractDB(): List<DataExtract> {
        return repositoryDB.getExtract(userCurrent.getNumberAccount())
    }

}