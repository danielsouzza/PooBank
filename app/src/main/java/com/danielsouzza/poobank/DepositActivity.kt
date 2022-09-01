package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class DepositActivity : AppCompatActivity() {

    private val controller = BankController()
    private lateinit var edittextValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit)

        val userCurrent = controller.getUserCurrent()
        edittextValue = findViewById(R.id.edit_deposit)

        val btnDeposit: Button = findViewById(R.id.btn_deposit)

        btnDeposit.setOnClickListener {
            if (edittextValue.text.toString().isEmpty()) {
                App.snackBar(it, R.string.fields_input)
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).async {
                controller.deposit(
                    edittextValue.text.toString().toDouble()
                )
                runOnUiThread {
                    val endScreen =
                        Intent(this@DepositActivity, FinishedTransactionActivity::class.java)
                    endScreen.putExtra("condition", true)
                    startActivity(endScreen)
                    finish()
                }
            }
        }
    }
}