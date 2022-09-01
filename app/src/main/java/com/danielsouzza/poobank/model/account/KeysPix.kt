package com.danielsouzza.poobank.model.account


class KeysPix(
    private var accountId: Int,
    private var emailKey: String = "",
    private var cpfKey: String = "",
    private var phoneKey: String = "",
    private var randomKey: String = "",
){

    fun getAccountId() = this.accountId
    fun setAccountId(id:Int){ this.accountId = id}

    fun getEmailKey() = this.emailKey
    fun setEmailKey(email:String){ this.emailKey = email}

    fun getCpfKey() = this.cpfKey
    fun setCpfKey(cpf:String){ this.cpfKey = cpf}

    fun getPhoneKey() = this.phoneKey
    fun setPhoneKey(phone: String){ this.phoneKey = phone}

    fun getRandomKey() = this.randomKey
    fun setRandomKey(random: String){ this.randomKey = random }



}