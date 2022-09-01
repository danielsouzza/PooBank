package com.danielsouzza.poobank.model.client


data class Client(
    private val accountId: Int,
    private var cpf: String,
    private var name: String,
    private var birthDate: String,
    private var phone: String
) {

    fun getCpf(): String = this.cpf
    fun setCpf(cpf: String) {
        cpf.also { this.cpf = it }
    }

    fun getName(): String = this.name
    fun setName(name: String) {
        name.also { this.name = it }
    }

    fun getPhone(): String = this.phone
    fun setPhone(phone: String) {
        phone.also { this.phone = it }
    }

    fun getBirthDate(): String = this.birthDate
    fun setBirthDate(sexo: String) {
        sexo.also { this.birthDate = it }
    }

    fun getAccountId():Int = this.accountId
}