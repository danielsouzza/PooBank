package com.danielsouzza.poobank.repository.account

import com.danielsouzza.poobank.R
import com.danielsouzza.poobank.App
import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.regex.Regx

class RepositoryKeysPixList {
    private val keysPix = mutableMapOf<String, Account>()
    val regx = Regx()

    fun registerNewKey(key: String, id: Account) {
        try {
            searchKeyPix(key)
            throw KeyPixAlreadyRegisteredException()
        } catch (ex: KeyPixNotRegisteredException) {
            keysPix[key] = id
        }
    }

    fun searchKeyPix(key: String): Account {
        val keys = keysPix.keys
        keys.forEach {
            if (it == key) {
                return keysPix.getValue(key)
            }
        }
        throw KeyPixNotRegisteredException()
    }

    fun removeKeyPix(key: String){
        keysPix.remove(key)
    }

    fun getKeys(numberAccount:Int) : MutableMap<String,String> {
        val keys = mutableMapOf<String,String>()

        for((key,value) in keysPix){
            if(value.getNumberAccount() == numberAccount){
                when{
                    regx.checkPattern(key,regx.emailRegx) -> keys[App.getRes().getString(R.string.text_email)] = key
                    regx.checkPattern(key,regx.cpfRegx) -> keys[App.getRes().getString(R.string.text_cpf)] = key
                    regx.checkPattern(key,regx.phoneRegx) -> keys[App.getRes().getString(R.string.text_phone)] = key
                    else -> keys[App.getRes().getString(R.string.text_random)] = key
                }
            }
        }
        return keys
    }

}