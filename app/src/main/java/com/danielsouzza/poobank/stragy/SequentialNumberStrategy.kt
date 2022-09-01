package com.danielsouzza.poobank.stragy

import com.danielsouzza.poobank.controler.BankController

class SequentialNumberStrategy {
    private var nextNumberAccount: Int = 1
    private val controller = BankController()

    suspend fun nextNumber(): String {
        return (controller.getNumberOfAccounts() + 1).toString()
    }
}