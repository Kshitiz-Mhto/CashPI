package com.safespend.cashsentry.adaptors.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.safespend.cashsentry.R
import com.safespend.cashsentry.data.local_data_source.model.HistoryModel
import de.hdodenhof.circleimageview.CircleImageView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryRecylerViewAdapter(val notificationList: List<HistoryModel>?, val context: Context) : RecyclerView.Adapter<MyViewHolderForNotification>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderForNotification {
        val layoutInflator = LayoutInflater.from(parent.context)
        val listItem = layoutInflator.inflate(R.layout.notification_cardview, parent, false)
        return MyViewHolderForNotification(listItem)
    }

    override fun getItemCount(): Int {
        return notificationList!!.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolderForNotification, position: Int) {
        val index_element = notificationList!![position]
        if(index_element.isDesposit){
            holder.notifyContent.text = "You have successfully deposited in your Card-I of amount ${index_element.amt}"
            holder.alertTitle.text = "Deposit Alert!!"
            holder.alertLogo.setImageResource(R.drawable.deposit)
        }else{
            holder.notifyContent.text = "You have successfully withdrawl from your Card-I of amount ${index_element.amt}"
            holder.alertTitle.text = "Withdrawl Alert!!"
            holder.alertLogo.setImageResource(R.drawable.withdrawl)
        }
        holder.alertDate.text = (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).toString()
    }
}
class MyViewHolderForNotification(val view: View): RecyclerView.ViewHolder(view){
    val notifyContent = view.findViewById<TextView>(R.id.etNotificationContent)
    val alertTitle = view.findViewById<TextView>(R.id.alertTitle)
    val alertLogo = view.findViewById<CircleImageView>(R.id.alertNotificationLogo)
    val alertDate = view.findViewById<TextView>(R.id.alertDate)
}