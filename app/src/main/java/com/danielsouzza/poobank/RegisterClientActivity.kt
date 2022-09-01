package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.regex.Regx
import com.google.android.material.snackbar.Snackbar

class RegisterClientActivity : AppCompatActivity() {

    private val regex: Regx = Regx()
    private val controller: BankController = BankController()
    private lateinit var editCpf: EditText
    private lateinit var editName: EditText
    private lateinit var editBirth: EditText
    private lateinit var editPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_client)

        // Button
        val btnContinuation: Button = findViewById(R.id.btn_continue)

        // EditTexts inputs
        editCpf = findViewById(R.id.edit_cpf)
        editName = findViewById(R.id.edit_name)
        editBirth = findViewById(R.id.edit_birth)
        editPhone = findViewById(R.id.edit_phone)

        btnContinuation.setOnClickListener {

            // Validate data entries
            if (!validate()) {
                Snackbar.make(it, R.string.fields_input, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.secondary)).show()
                return@setOnClickListener
            }

            // Start Activity registerAccount
            val registerAccount = Intent(this, RegisterAccountActivity::class.java)
            registerAccount.putExtra("cpf", editCpf.text.toString())
            registerAccount.putExtra("name", editName.text.toString())
            registerAccount.putExtra("birth", editBirth.text.toString())
            registerAccount.putExtra("phone", editPhone.text.toString())
            startActivity(registerAccount)
        }

    }

    private fun validate(): Boolean {
        return editCpf.text.toString().isNotEmpty()
                && editCpf.text.toString().length == 5
                && editName.text.toString().isNotEmpty()
                && editBirth.text.toString().isNotEmpty()
                && regex.checkPattern(editBirth.text.toString(), regex.dateRegx)
                && editPhone.text.toString().isNotEmpty()
                && regex.checkPattern(editPhone.text.toString(), regex.phoneRegx)
    }
}