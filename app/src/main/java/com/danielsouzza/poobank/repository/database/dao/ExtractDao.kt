package com.danielsouzza.poobank.repository.database.dao

import androidx.room.*
import com.danielsouzza.poobank.model.account.DataExtract

@Dao
interface ExtractDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(extract: DataExtract)

    @Query("SELECT * FROM DataExtract WHERE account_id= :accountId")
    suspend fun getExtract(accountId:Int): List<DataExtract>

}