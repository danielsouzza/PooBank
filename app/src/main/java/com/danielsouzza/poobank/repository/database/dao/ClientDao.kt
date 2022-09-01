package com.danielsouzza.poobank.repository.database.dao

import androidx.room.*
import com.danielsouzza.poobank.model.client.DataClient

@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(client: DataClient)

    @Update
    suspend fun update(client: DataClient)

}