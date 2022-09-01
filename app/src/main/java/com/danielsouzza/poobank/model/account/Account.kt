package com.danielsouzza.poobank.model.account

import com.danielsouzza.poobank.model.client.Client
import com.danielsouzza.poobank.stragy.GetDate

abstract class Account(holder: Client, email: String, password: String) {

    private var numberAccount: Int
    private var email: String
    private var password: String
    private var balance: Double
    private var holder: Client
    private var extract: ExtractAccount
    private lateinit var keysPix: KeysPix

    init {
        this.numberAccount = 0
        this.balance = 0.0
        this.holder = holder
        this.password = password
        this.email = email
        this.extract = ExtractAccount()
    }

    fun deposit(value: Double) {
        val previousBalance = getBalance()
        depositT(value)
        this.launch("Depósito", value, previousBalance, getBalance())

    }

    private fun depositT(value: Double) {
        this.balance += value
    }

    private fun toWithdraw(value: Double) {
        if (value <= this.getBalance()) {
            this.balance -= value
        } else {
            throw InsufficientFundsException()
        }

    }

    fun move(destination: Account, value: Double) {
        this.toWithdraw(value)
        destination.depositT(value)
    }


    fun transfer(destination: Account, value: Double) {
        val balanceOrigin = getBalance()
        val balanceDestination = destination.getBalance()
        move(destination, value)
        this.launch("Tranferência", value, balanceOrigin, getBalance())
        destination.launch("Recebido", value, balanceDestination, destination.getBalance())
    }

    private fun launch(
        historic: String,
        value: Double,
        previousBalance: Double,
        posteriorBalance: Double
    ) {
        val date = GetDate.getDate()
        val launch = ExtractItem(
            getNumberAccount(),
            date,
            historic,
            value,
            previousBalance,
            posteriorBalance
        )
        this.extract.addLaunch(launch)
    }


    fun getHolder() = this.holder
    fun setHolder(holder: Client) {
        this.holder = holder
    }

    fun getNumberAccount() = this.numberAccount
    fun setNumber(number: Int) {
        this.numberAccount = number
    }


    fun getBalance() = this.balance
    fun setBalance(value: Double) {
        this.balance = value
    }

    fun getPassword() = this.password
    fun setPassword(password: String) {
        this.password = password
    }

    fun getEmail() = this.email
    fun setEmail(email: String) {
        this.email = email
    }

    fun setExtract(extract: ExtractAccount) {
        this.extract = extract
    }

    fun getExtract() = this.extract

    fun getKeysPix() = this.keysPix

    abstract fun getType(): String
    fun setKeysPix(keys: KeysPix){
        this.keysPix = keys
    }

}