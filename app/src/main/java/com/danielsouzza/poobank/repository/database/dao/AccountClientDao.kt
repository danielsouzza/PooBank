package com.danielsouzza.poobank.repository.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.danielsouzza.poobank.repository.database.AccountWithClient

@Dao
interface AccountClientDao {
    @Transaction
    @Query("SELECT * FROM DataAccount WHERE email = :email and password = :password")
    fun login(email:String, password:String): List<AccountWithClient>

    @Transaction
    @Query("SELECT * FROM DataAccount WHERE number_account = :numberAccount")
    fun getAccountWithClient(numberAccount:Int): List<AccountWithClient>

}