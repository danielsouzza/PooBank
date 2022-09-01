package com.danielsouzza.poobank

import android.app.Application
import android.content.res.Resources
import android.view.View
import com.danielsouzza.poobank.regex.Regx
import com.danielsouzza.poobank.repository.database.AppDatabase
import com.google.android.material.snackbar.Snackbar
import java.util.*

class App : Application() {
    companion object {
        private val regex = Regx()
        private lateinit var res: Resources
        private lateinit var context: App

        // Get Resource String
        fun getRes(): Resources = res
        fun getContext(): App = context

        // Creator Random key
        fun getKeyRandom(size: Int): String {
            val random = Random(System.nanoTime())
            val keyPix = StringBuilder()

            for (i in 0 until size) {
                val index = random.nextInt(regex.characters.length)
                keyPix.append(regex.characters[index])
            }
            return keyPix.toString()
        }

        // SnackBar for all Activity
        fun snackBar(view: View, resource: Int) {
            Snackbar.make(view, resource, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(res.getColor(R.color.secondary))
                .show()
        }

    }

    // Data base
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        // Database
        db = AppDatabase.getDatabase(this)
        //Resources to user outside onCreate
        res = resources
        // Context
        context = this
    }
}