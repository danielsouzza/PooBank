package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.Account

class HomeActivity : AppCompatActivity() {

    private val controller = BankController()
    private lateinit var viewBalance: TextView
    private lateinit var viewName: TextView
    private lateinit var btnExtract: Button
    private lateinit var userCurrent: Account
    private lateinit var rvHome: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // User current
        userCurrent = controller.getUserCurrent()

        // Get view
        viewBalance = findViewById(R.id.view_balance)
        viewName = findViewById(R.id.view_name)
        btnExtract = findViewById(R.id.btn_extract)

        // List of object HomeItem
        val homeItem = mutableListOf<HomeItem>()

        homeItem.add(
            HomeItem(
                id = 1,
                drawableId = R.drawable.ic_pix,
                textStringId = R.string.transfer_pix
            )
        )

        homeItem.add(
            HomeItem(
                id = 2,
                drawableId = R.drawable.ic_transfer,
                textStringId = R.string.transfer
            )
        )
        homeItem.add(
            HomeItem(
                id = 3,
                drawableId = R.drawable.ic_deposit,
                textStringId = R.string.deposit
            )
        )

        homeItem.add(
            HomeItem(
                id = 4,
                drawableId = R.drawable.ic_pay,
                textStringId = R.string.payments
            )
        )

        homeItem.add(
            HomeItem(
                id = 5,
                drawableId = R.drawable.ic_card,
                textStringId = R.string.card
            )
        )

        homeItem.add(
            HomeItem(
                id = 6,
                drawableId = R.drawable.ic_recarga,
                textStringId = R.string.recharge
            )
        )


        // Recycle View
        val adapter = HomeAdapter(homeItem) { id ->
            when (id) {
                1 -> {
                    val paymentActivity = Intent(this, PaymentActivity::class.java)
                    startActivity(paymentActivity)
                }
                2 -> {
                    val transferActivity = Intent(this, TransferStartActivity::class.java)
                    startActivity(transferActivity)
                }
                3 -> {
                    val depositActivity = Intent(this, DepositActivity::class.java)
                    startActivity(depositActivity)
                }
            }
        }
        rvHome = findViewById(R.id.rv_function)
        rvHome.adapter = adapter
        rvHome.layoutManager = GridLayoutManager(this, 3)

        // Show account information
        showDataAccount()

        btnExtract.setOnClickListener {
            val extractActivity = Intent(this, ExtractActivity::class.java)
            startActivity(extractActivity)
        }

    }

    override fun onResume() {
        super.onResume()
        showDataAccount()
    }

    private inner class HomeAdapter(
        private val homeItem: List<HomeItem>,
        private val onItemClickListener: (Int) -> Unit,
    ) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
            val view = layoutInflater.inflate(R.layout.home_item, parent, false)
            return HomeViewHolder(view)
        }

        override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
            holder.bind(homeItem[position])
        }

        override fun getItemCount(): Int {
            return homeItem.size
        }

        private inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: HomeItem) {
                val button: ImageView = itemView.findViewById(R.id.btn_item)
                val function: TextView = itemView.findViewById(R.id.text_item)

                button.setImageResource(item.drawableId)
                function.setText(item.textStringId)

                button.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }


    private fun showDataAccount() {
        viewName.text = getString(R.string.name_current_user, userCurrent.getHolder().getName())
        viewBalance.text = getString(R.string.text_balance, userCurrent.getBalance().toString())
    }
}