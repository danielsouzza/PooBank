package com.danielsouzza.poobank
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.controler.ControllerException
import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.model.account.InsufficientFundsException
import com.danielsouzza.poobank.repository.account.KeyPixNotRegisteredException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class PaymentActivity : AppCompatActivity() {

    private val controller = BankController()
    private lateinit var editTextKey: EditText
    private lateinit var editTextValue: EditText
    private lateinit var userCurrent: Account
    private lateinit var btnContinue: Button
    private lateinit var btnMyKeys: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Get a user current
        userCurrent = controller.getUserCurrent()

        // Get id of view elements
        btnContinue = findViewById(R.id.btn_continue)
        btnMyKeys = findViewById(R.id.btn_my_key)
        editTextKey = findViewById(R.id.input_keyPix)
        editTextValue = findViewById(R.id.input_valuePix)

        btnMyKeys.setOnClickListener {
            val myKeys = Intent(this, ManageKeysPixActivity::class.java)
            startActivity(myKeys)
        }

        btnContinue.setOnClickListener {

            // Create/start and get elements of dialog
            val view = this.showDialogTrf(R.layout.dialog_password)
            val editPassword: EditText = view.findViewById(R.id.trfPassword)
            val btnFinish: Button = view.findViewById(R.id.trfBtnFinish)

            btnFinish.setOnClickListener {btnFinish->
                if (!checkPassword(editPassword.text.toString())) {
                    App.snackBar(btnFinish, R.string.fields_password)
                    return@setOnClickListener
                }

                // screen final
                val endScreen = Intent(this, FinishedTransactionActivity::class.java)

                CoroutineScope(Dispatchers.IO).async {
                    try {
                        // Get account destination
                        val destination = controller.searchKeyPix(editTextKey.text.toString())

                        // transaction
                        controller.transfer(
                            destination.getNumberAccount(),
                            editTextValue.text.toString().toDouble()
                        )
                        endScreen.putExtra("condition", true)  // screen success
                    } catch (ex: InsufficientFundsException ) {
                        endScreen.putExtra("condition", false) // screen fail
                    }catch (ex: ControllerException){
                        endScreen.putExtra("condition", false) // screen fail
                    }

                    runOnUiThread {
                        // Regardless of what happens the final screen will start
                        finish()
                        startActivity(endScreen)
                    }
                }
            }
        }
    }

    // Start Pop-Up of password
    private fun showDialogTrf(custom: Int): View {
        val view = layoutInflater.inflate(custom, null)
        val builder = AlertDialog.Builder(this, R.style.ThemeCustomDialog).setView(view)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setGravity(Gravity.BOTTOM)
        alertDialog.show()
        return view
    }

    // check if password is correct
    private fun checkPassword(password: String): Boolean {
        return password == userCurrent.getPassword()
    }

    // verify if key has a value
//    private fun validateKey(): Boolean {
//        return try {
//            controller.searchKeyPix(editTextKey.text.toString())
//            true
//        } catch (ex: KeyPixNotRegisteredException) {
//            false
//        }
//    }
}