package com.danielsouzza.poobank.repository.database.dao

import androidx.room.*
import com.danielsouzza.poobank.model.account.DataAccount

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: DataAccount)

    @Update
    suspend fun updateAccount(account:DataAccount)

    @Query("SELECT COUNT(number_account) FROM DataAccount")
    suspend fun getNumberOfAccounts(): Int

    @Query("SELECT * FROM DataAccount WHERE email = :email")
    suspend fun getAccount(email:String): List<DataAccount>
}