package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.toDataKeysPix
import com.danielsouzza.poobank.model.account.toMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ManageKeysPixActivity : AppCompatActivity() {

    private val controller = BankController()
    private val currentUser = controller.getUserCurrent()
    private lateinit var rvKeyItem: RecyclerView
    private lateinit var btnNewKey: Button
    private lateinit var adapter: KeysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_keys_pix)

        // Button of register new key
        btnNewKey = findViewById(R.id.btn_new_key)


        // Recycle view
        creatorItemRecycle()

        btnNewKey.setOnClickListener {
            val registerKey = Intent(this, RegisterKeyPixActivity::class.java)
            startActivity(registerKey)
        }
    }

    private inner class KeysAdapter(
        private val keysItem: List<KeysItem>,
        private val onItemClickListener: (String) -> Unit
    ) : RecyclerView.Adapter<KeysAdapter.KeysViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeysViewHolder {
            val view = layoutInflater.inflate(R.layout.key_pix_item, parent, false)
            return KeysViewHolder(view)
        }

        override fun onBindViewHolder(holder: KeysViewHolder, position: Int) {
            holder.bind(keysItem[position])
        }

        override fun getItemCount(): Int = keysItem.size

        private inner class KeysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: KeysItem) {
                val textTypeKey: TextView = itemView.findViewById(R.id.text_typeKey_item)
                val textKey: TextView = itemView.findViewById(R.id.text_key_item)
                val imgButtonDel: ImageButton = itemView.findViewById(R.id.btn_delete_key)
                textTypeKey.text = item.textTypeKeyId
                textKey.text = item.textKeyId


                imgButtonDel.setOnClickListener {
                    onItemClickListener.invoke(item.textKeyId)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        creatorItemRecycle()
    }

    private  fun creatorItemRecycle() {
        val keys = currentUser.getKeysPix().toDataKeysPix().toMap()
        val itemKeys = mutableListOf<KeysItem>()
        for ((key, value) in keys) {
            if(value.isNotEmpty()) {
                itemKeys.add(
                    KeysItem(
                        textTypeKeyId = key,
                        textKeyId = value
                    )
                )
            }
        }

       adapter = KeysAdapter(itemKeys) { key ->
           CoroutineScope(Dispatchers.IO).launch{
               controller.removeKeyPix(key)
               runOnUiThread{
                   creatorItemRecycle()
               }
           }
        }
        rvKeyItem = findViewById(R.id.rv_keys_item)
        rvKeyItem.adapter = adapter
        rvKeyItem.layoutManager = LinearLayoutManager(this)
    }

}