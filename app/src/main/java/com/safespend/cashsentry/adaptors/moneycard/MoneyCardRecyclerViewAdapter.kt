package com.safespend.cashsentry.adaptors.moneycard

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.safespend.cashsentry.R
import com.safespend.cashsentry.data.local_data_source.model.MoneyCardModel

class MoneyCardRecyclerViewAdapter(val moneycardList: List<MoneyCardModel>?, val context: Context) : RecyclerView.Adapter<MyViewHolderForMoneyCard>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderForMoneyCard {
        val layoutInflator = LayoutInflater.from(parent.context)
        val listItem = layoutInflator.inflate(R.layout.moneycard_carview, parent, false)
        return MyViewHolderForMoneyCard(listItem)
    }

    override fun getItemCount(): Int {
        return moneycardList!!.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolderForMoneyCard, position: Int) {
        val index_element = moneycardList!![position]
        when(index_element.name){
            "Master Card" -> {
                holder.etCardName.text = "Master Card"
                holder.etCardAmt.text = "$ " + index_element.totalAmt.toString()
                holder.etCardSerialNum.text = index_element.serialNum
            }
            "Visa" -> {
                holder.etCardName.text = "Visa"
                holder.etCardAmt.text = "$ " + index_element.totalAmt.toString()
                holder.etCardSerialNum.text = index_element.serialNum
            }
            "Amex" ->{
                holder.etCardName.text = "Amex"
                holder.etCardAmt.text = "$ " + index_element.totalAmt.toString()
                holder.etCardSerialNum.text = index_element.serialNum
            }
            else -> {}
        }
    }
}

class MyViewHolderForMoneyCard(val view: View): RecyclerView.ViewHolder(view){
    val etCardName = view.findViewById<TextView>(R.id.etCardName)
    val etCardAmt = view.findViewById<TextView>(R.id.etCardAmount)
    val etCardSerialNum = view.findViewById<TextView>(R.id.etCardSerialNumber)
}