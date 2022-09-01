package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.model.account.InsufficientFundsException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TransferEndActivity : AppCompatActivity() {

    private val controller = BankController()
    private lateinit var currentUser: Account
    private lateinit var trfValue: TextView
    private lateinit var destination: Account
    private lateinit var rvTransfer: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_end)

        // Button finish
        val finishedButton: Button = findViewById(R.id.btn_transfer_finish)

        // Data transaction
        val numberDestine = intent.extras?.getInt("numberAccount")!!
        val type = intent.extras?.getString("typeAccount")!!
        val value = intent.extras?.getString("value")!! // value sent by previous view

        // List of object TrfItem
        var itemInfo: List<TrfItem>

        // Origin
        currentUser = controller.getUserCurrent()

        // Get data of database
        CoroutineScope(Dispatchers.IO).async {
            // Destination
            destination = controller.searchAccount(numberDestine)
            // items to recycle view
            itemInfo = addItems(destination)

            // Return to mainThread
            runOnUiThread {
                //Recycle View
                val adapter = TransferAdapter(itemInfo)
                rvTransfer = findViewById(R.id.rv_trf)
                rvTransfer.adapter = adapter
                rvTransfer.layoutManager = GridLayoutManager(this@TransferEndActivity, 2)
            }
        }

        // View statics
        showInfo(value)

        // Transfer confirmation
        finishedButton.setOnClickListener {

            // Create/start and get elements of dialog
            val view = this.showDialogTrf(R.layout.dialog_password)
            val editPassword: EditText = view.findViewById(R.id.trfPassword)
            val btnContinue: Button = view.findViewById(R.id.trfBtnFinish)

            btnContinue.setOnClickListener {
                if (!checkPassword(editPassword.text.toString())) {
                    App.snackBar(it, R.string.fields_password)
                    return@setOnClickListener
                }

                // screen final
                val endScreen = Intent(this, FinishedTransactionActivity::class.java)

                CoroutineScope(Dispatchers.IO).async {
                    try {
                        // transaction
                        controller.transfer(
                            destination.getNumberAccount(),
                            value.toDouble()
                        )
                        endScreen.putExtra("condition", true)
                    } catch (ex: InsufficientFundsException) {
                        endScreen.putExtra("condition", false)
                    }

                    // Regardless of what happens the final screen will start
                    runOnUiThread {
                        finish()
                    }
                    startActivity(endScreen)
                }
            }
        }
    }

    // Adapter of Recycle
    private inner class TransferAdapter(
        private val trfItem: List<TrfItem>
    ) : RecyclerView.Adapter<TransferViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferViewHolder {
            val view = layoutInflater.inflate(R.layout.transfer_item, parent, false)
            return TransferViewHolder(view)
        }

        override fun onBindViewHolder(holder: TransferViewHolder, position: Int) {
            holder.bind(trfItem[position])
        }

        override fun getItemCount(): Int {
            return trfItem.size
        }
    }

    private inner class TransferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TrfItem) {
            val name: TextView = itemView.findViewById(R.id.trf_item)
            name.text = item.textStringId
        }
    }

    // Verify password
    private fun checkPassword(password: String): Boolean {
        return password == currentUser.getPassword()
    }

    // create dialog
    private fun showDialogTrf(custom: Int): View {
        val view = layoutInflater.inflate(custom, null)
        val builder = AlertDialog.Builder(this, R.style.ThemeCustomDialog).setView(view)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setGravity(Gravity.BOTTOM)
        alertDialog.show()
        return view
    }

    // Show balance
    private fun showInfo(value: String) {
        trfValue = findViewById(R.id.trf_value)
        trfValue.text = getString(R.string.text_balance, value)
    }

    private fun addItems(destination: Account): List<TrfItem> {
        return listOf(
            TrfItem(id = 1, textStringId = getString(R.string.text_name)),
            TrfItem(
                id = 2,
                textStringId = getString(
                    R.string.name_current_user,
                    destination.getHolder().getName()
                )
            ),
            TrfItem(id = 3, textStringId = getString(R.string.text_cpf)),
            TrfItem(
                id = 4,
                textStringId = getString(R.string.trf_cpf, destination.getHolder().getCpf())
            ),
            TrfItem(id = 5, textStringId = getString(R.string.trf_inst)),
            TrfItem(id = 6, textStringId = getString(R.string.app_name)),
            TrfItem(id = 7, textStringId = getString(R.string.trf_account, destination.getType())),
            TrfItem(
                id = 8,
                textStringId = getString(
                    R.string.trf_account_number,
                    destination.getNumberAccount()
                )
            ),
        )
    }
}