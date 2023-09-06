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
        if(index_element.isCreated){
            holder.notifyContent.text = "You have succesfully created your Card with ${index_element.serialNum} serial number of amount $ ${index_element.amt}"
            holder.alertTitle.text = "Created Alert!!"
            holder.alertLogo.setImageResource(R.drawable.creation)
        }else if(index_element.isUpdated){
            holder.notifyContent.text = "You have successfully updated in your ${index_element.serialNum} Card with amount $ ${index_element.amt}."
            holder.alertTitle.text = "Updated Alert!!"
            holder.alertLogo.setImageResource(R.drawable.update)
        }else{
            holder.notifyContent.text = "You have succesfully deleted your Card with ${index_element.serialNum} serial number."
            holder.alertTitle.text = "Deletion Alert!!"
            holder.alertLogo.setImageResource(R.drawable.deletion)
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