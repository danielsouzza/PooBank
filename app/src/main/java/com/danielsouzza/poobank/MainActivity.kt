package com.danielsouzza.poobank


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.DataAccount
import com.danielsouzza.poobank.model.client.DataClient
import com.danielsouzza.poobank.regex.Regx
import com.danielsouzza.poobank.repository.account.AccountNotRegisteredException
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private val controller = BankController()
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private val regx = Regx()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Input data
        editEmail = findViewById(R.id.text_email)
        editPassword = findViewById(R.id.text_password)

        // Buttons
        val btnEnter: Button = findViewById(R.id.btn_enter)
        val btnOpenAccount: Button = findViewById(R.id.btn_open_account)

        //logical part
        btnEnter.setOnClickListener {
            // Validate credentials
            if (!validate()) {
                App.snackBar(it,R.string.fields_login)
                return@setOnClickListener
            }


            // Query on database
            CoroutineScope(Dispatchers.IO).async {
                try {
                    // User current
                    controller.setUserCurrent(
                        controller.login(
                            editEmail.text.toString(),
                            editPassword.text.toString()
                        )
                    )
                    // Start activity
                    val home = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(home)
                }catch (ex:AccountNotRegisteredException){
                    App.snackBar(it,R.string.fields_login)
                    return@async
                }
            }
            return@setOnClickListener
        }

        // OnClick
        btnOpenAccount.setOnClickListener {
            val openAccount = Intent(this, RegisterClientActivity::class.java)
            startActivity(openAccount)
        }
    }

    private fun validate(): Boolean {
        return editEmail.text.toString().isNotEmpty()
                && regx.checkPattern(editEmail.text.toString(), regx.emailRegx)
                && editPassword.text.toString().isNotEmpty()
                && editPassword.text.toString().length <= 8
    }

}