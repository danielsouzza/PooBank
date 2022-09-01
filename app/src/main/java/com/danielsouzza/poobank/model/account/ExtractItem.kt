package com.danielsouzza.poobank.model.account


class ExtractItem(
    private val accountId: Int,
    private val date: String,
    private val historic: String,
    private val value: Double,
    private val previousBalance: Double,
    private val posteriorBalance: Double
) {
    fun getData(): String = this.date

    fun getHistoric(): String = this.historic

    fun getValue(): Double = this.value

    fun getPosteriorBalance(): Double = this.posteriorBalance

    fun getPreviousBalance(): Double = this.previousBalance

    fun getAccountId():Int = this.accountId
}