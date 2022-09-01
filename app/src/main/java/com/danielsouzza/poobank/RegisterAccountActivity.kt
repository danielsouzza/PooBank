package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.DataAccount
import com.danielsouzza.poobank.model.client.DataClient
import com.danielsouzza.poobank.regex.Regx
import com.danielsouzza.poobank.repository.account.AccountAlreadyRegistered
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RegisterAccountActivity : AppCompatActivity() {

    private val regex: Regx = Regx()
    private val controller: BankController = BankController()
    private lateinit var editEmail: EditText
    private lateinit var editPassword1: EditText
    private lateinit var editPassword2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)

        val btnFinished: Button = findViewById(R.id.btn_finished)
        editEmail = findViewById(R.id.edit_email)
        editPassword1 = findViewById(R.id.edit_password1)
        editPassword2 = findViewById(R.id.edit_password2)

        btnFinished.setOnClickListener {
            if (!validate()) {
                snackBar(it,R.string.fields_input)
                return@setOnClickListener
            }

            // Get data from editView
            CoroutineScope(Dispatchers.IO).async {


                val account = DataAccount(
                    numberAccount= (controller.getNumberOfAccounts()+1),
                    email = editEmail.text.toString(),
                    password = editPassword1.text.toString(),
                )

                val client = DataClient(
                    accountId=account.numberAccount,
                    cpf = intent.extras?.getString("cpf")!!,
                    name = intent.extras?.getString("name")!!,
                    birthDate = intent.extras?.getString("birth")!!,
                    phone = intent.extras?.getString("phone")!!
                )

                // Register user
                try {
                    controller.insertDatabase(account,client)
                }catch (ex:AccountAlreadyRegistered){
                    snackBar(it,R.string.fields_account_already_exists)
                    return@async
                }

                val mainActivity = Intent(this@RegisterAccountActivity, MainActivity::class.java)
                mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(mainActivity)
            }
        }
    }

    private fun validate(): Boolean {
        return editEmail.text.toString().isNotEmpty()
                && regex.checkPattern(editEmail.text.toString(), regex.emailRegx)
                && editPassword1.text.toString().isNotEmpty()
                && regex.checkPattern(editPassword1.text.toString(), regex.passwordRegx)
                && editPassword2.text.toString().isNotEmpty()
                && regex.checkPattern(editPassword2.text.toString(), regex.passwordRegx)
                && editPassword1.text.toString() == editPassword2.text.toString()
    }

    private fun snackBar(view:View,resource:Int){
        Snackbar.make(view, resource, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.secondary)).show()
    }
}