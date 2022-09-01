package com.danielsouzza.poobank

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.model.account.toDataKeysPix
import com.danielsouzza.poobank.model.account.toList
import com.danielsouzza.poobank.model.account.toMap
import com.danielsouzza.poobank.regex.Regx
import com.danielsouzza.poobank.repository.account.KeyPixAlreadyRegisteredException
import com.danielsouzza.poobank.repository.account.KeyPixNotRegisteredException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RegisterKeyPixActivity : AppCompatActivity() {

    private lateinit var btnConfirm: Button
    private lateinit var editTextKey: EditText
    private lateinit var spinnerKey: Spinner
    private lateinit var currentUser: Account
    private val regex = Regx()
    private lateinit var typeKey: String
    private val controller = BankController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_key_pix)

        currentUser = controller.getUserCurrent() // User current


        try {
            // Get id of elements
            spinnerKey = findViewById(R.id.key_spinner)
            editTextKey = findViewById(R.id.edit_key)
            btnConfirm = findViewById(R.id.btn_confirm_key) //Button finish

            // Get all key related to user
            val keys = currentUser.getKeysPix().toDataKeysPix().toMap()

            val options = keys.keys.filter {
                keys[it] == ""
            }
            // Adapter Spinner
            spinnerOptions(options)


            btnConfirm.setOnClickListener {
                if (!validate() && verifyFieldType()) { // check if data is correct
                    App.snackBar(it, R.string.fields_input)
                    return@setOnClickListener
                }

                // Register key in repository
                CoroutineScope(Dispatchers.Default).launch {
                    try {
                        setTypeKey(editTextKey.text.toString())
                        controller.updateAccount(currentUser)

                    } catch (ex: KeyPixAlreadyRegisteredException) {
                        App.snackBar(it, R.string.keys_already_exists)
                        return@launch
                    }

                    finish()
                }
            }

        } catch (ex: KeyPixNotRegisteredException) {
            finish()
        }
    }


    private fun spinnerOptions(options: List<String>) {
        spinnerKey.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, options)
        spinnerKey.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // When change type key
                when {
                    options[position] == getString(R.string.text_random) -> {
                        editTextKey.isEnabled = false
                        editTextKey.setText(getString(R.string.key_random, App.getKeyRandom(16)))
                    }
                    options[position] == getString(R.string.text_cpf) || options[position] == getString(
                        R.string.text_phone
                    ) -> {
                        editTextKey.inputType = InputType.TYPE_CLASS_NUMBER
                    }
                    options[position] == getString(R.string.text_email) -> {
                        editTextKey.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT
                    }
                }
                // For enable again field
                if (options[position] != getString(R.string.text_random)) {
                    editTextKey.setText("")
                    editTextKey.isEnabled = true
                }

                typeKey = options[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                typeKey = options[0]
            }
        }
    }

    private fun validate(): Boolean {
        return editTextKey.text.toString().isNotEmpty()
    }

    private fun verifyFieldType(): Boolean {
        return when (typeKey) {
            getString(R.string.text_email) -> regex.checkPattern(
                editTextKey.text.toString(),
                regex.emailRegx
            )
            getString(R.string.text_phone) -> regex.checkPattern(
                editTextKey.text.toString(),
                regex.phoneRegx
            )
            getString(R.string.text_cpf) -> regex.checkPattern(
                editTextKey.text.toString(),
                regex.cpfRegx
            )
            else -> false
        }
    }

    private fun setTypeKey(key: String) {
        when {
            regex.checkPattern(
                key,
                regex.emailRegx
            ) -> currentUser.getKeysPix().setEmailKey(key)


            regex.checkPattern(
                key,
                regex.cpfRegx
            ) -> currentUser.getKeysPix().setCpfKey(key)

            regex.checkPattern(
                key,
                regex.phoneRegx
            ) -> currentUser.getKeysPix().setPhoneKey(key)
            else -> currentUser.getKeysPix().setRandomKey(key)
        }
    }
}