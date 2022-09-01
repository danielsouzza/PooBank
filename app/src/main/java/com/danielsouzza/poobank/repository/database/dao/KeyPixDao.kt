package com.danielsouzza.poobank.repository.database.dao

import androidx.room.*
import com.danielsouzza.poobank.model.account.DataKeysPix

@Dao
interface KeysPixDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: DataKeysPix)

    @Update
    suspend fun updateKeys(keys: DataKeysPix)

    @Query("SELECT * FROM DataKeysPix WHERE account_id= :accountId")
    suspend fun getAccount(accountId:Int): List<DataKeysPix>

    @Query("SELECT account_id FROM DataKeysPix WHERE email_Key = :key or cpf_Key =:key or phone_Key = :key or random_Key = :key")
    suspend fun searchKeyPix(key: String): Int
}