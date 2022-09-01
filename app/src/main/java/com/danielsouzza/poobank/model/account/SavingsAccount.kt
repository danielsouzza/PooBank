package com.danielsouzza.poobank.model.account

import com.danielsouzza.poobank.model.client.Client


class SavingsAccount(holder: Client, email: String, password: String) :
    Account(holder, email, password) {

    fun earnInterest(tax: Double) {
        this.deposit(this.getBalance() * tax)
    }

    override fun getType(): String = "Conta poupança"

}