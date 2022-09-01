package com.danielsouzza.poobank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.model.account.DataExtract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ExtractActivity : AppCompatActivity() {

    private val controller = BankController()
    private lateinit var viewBalance: TextView
    private lateinit var currentUser: Account
    private lateinit var rvExtractItem: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)

        // Get user current
        currentUser = controller.getUserCurrent()

        // Get id views
        viewBalance = findViewById(R.id.extract_balance)

        // Show information
        showDataAccount()

        // Get item of DB

        CoroutineScope(Dispatchers.IO).async {
            val extract = controller.getExtractDB()
            runOnUiThread{
                creatorItemRecycle(extract)
            }
        }
    }

    private inner class ExtractAdapter(
        private val extractItem: List<DataExtract>
    ): RecyclerView.Adapter<ExtractViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtractViewHolder {
            val view = layoutInflater.inflate(R.layout.extract_item, parent, false)
            return ExtractViewHolder(view)
        }

        override fun onBindViewHolder(holder: ExtractViewHolder, position: Int) {
            holder.bind(extractItem[position])
        }

        override fun getItemCount(): Int = extractItem.size
    }


    private inner class ExtractViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: DataExtract){
            val textViewType: TextView = itemView.findViewById(R.id.text_extractType_item)
            val textViewValue: TextView = itemView.findViewById(R.id.text_extractValue_item)
            val textViewDate: TextView = itemView.findViewById(R.id.text_date_extract_item)

            textViewType.text = item.historic
            textViewValue.text = item.value.toString()
            textViewDate.text =item.date
        }
    }


    private fun creatorItemRecycle(extract: List<DataExtract>) {
        val itemExtract = mutableListOf<DataExtract>()
        extract.forEach{
            itemExtract.add(
                it
            )
        }

        val adapter = ExtractAdapter(itemExtract)
        rvExtractItem = findViewById(R.id.rv_extract)
        rvExtractItem.adapter = adapter
        rvExtractItem.layoutManager = LinearLayoutManager(this)
    }

    private fun showDataAccount() {
        viewBalance.text = getString(R.string.text_balance, currentUser.getBalance().toString())
    }
}