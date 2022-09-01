package com.danielsouzza.poobank.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danielsouzza.poobank.model.account.DataAccount
import com.danielsouzza.poobank.model.account.DataExtract
import com.danielsouzza.poobank.model.account.DataKeysPix
import com.danielsouzza.poobank.model.client.DataClient
import com.danielsouzza.poobank.repository.database.dao.*

@Database(entities = [DataAccount::class, DataClient::class, DataExtract::class,DataKeysPix::class],version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun clientDao(): ClientDao
    abstract fun accountWithClientDoa(): AccountClientDao
    abstract fun extractDao(): ExtractDao
    abstract fun keysPixDao(): KeysPixDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "poo_bank"
                    ).build()
                }
                INSTANCE as AppDatabase
            }else{
                INSTANCE as AppDatabase
            }
        }
    }
}