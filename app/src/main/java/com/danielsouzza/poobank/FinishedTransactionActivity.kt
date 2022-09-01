package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FinishedTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_transaction)
        val btnHome: Button = findViewById(R.id.btn_home)

        if (!intent.extras?.getBoolean("condition")!!) {
            val imgView: ImageView = findViewById(R.id.trf_last_screen_img)
            val textView: TextView = findViewById(R.id.trf_last_screen_txt)
            textView.setText(R.string.transaction_fail)
            imgView.setImageResource(R.drawable.ic_close)
        }

        btnHome.setOnClickListener {
            val home = Intent(this, HomeActivity::class.java)
            finish()
            startActivity(home)
        }
    }
}