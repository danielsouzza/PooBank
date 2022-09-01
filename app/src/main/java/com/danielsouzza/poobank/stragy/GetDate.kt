package com.danielsouzza.poobank.stragy

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class GetDate {
    companion object{
        @SuppressLint("SimpleDateFormat")
        fun getDate(): String{
            val format = SimpleDateFormat("dd/MM/yyyy")
            val date = Date()
            return format.format(date)
        }
    }
}