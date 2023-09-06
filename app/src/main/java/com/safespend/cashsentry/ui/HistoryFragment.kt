package com.safespend.cashsentry.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.safespend.cashsentry.adaptors.history.HistoryRecylerViewAdapter
import com.safespend.cashsentry.databinding.FragmentHistoryBinding
import com.safespend.cashsentry.util.Constants
import com.safespend.cashsentry.viewmodel.history.HistoryViewModel
import com.safespend.cashsentry.viewmodel.history.HistoryViewModelFactory
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val channelId = Constants.CHANNEL_ID
    private lateinit var notificationManager: NotificationManager
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var historyRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelId, "NotifyChannel", "This is a Notify channel.")

        historyViewModel = ViewModelProvider(requireActivity(), HistoryViewModelFactory(application = requireContext())).get(HistoryViewModel::class.java)
        historyRecyclerView = binding.historyRecyclerView
        historyRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            historyViewModel.getHistoryDemo()
            historyViewModel.userHistory.collect{state ->
                val userHistory = state.userHistory
                if (state.isLoading) {
                    Log.i("UI load", "lol")
                }else if(state.error.isNotEmpty()) {
                    Log.i("UI error", "lol")
                }else if(state.userHistory != null){
                    historyRecyclerView.adapter = HistoryRecylerViewAdapter(userHistory, requireContext())
                    for (history in userHistory!!) {
                        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                        val notifyId = history.id
                        if (history.isUpdated) {
                            notificationBuilder
                                    .setContentTitle("Update Alert!!")
                                    .setContentText("You have successfully updated in your ${history.serialNum} Card with amount ${history.amt}.")

                        }
                        if (history.isCreated) {
                            notificationBuilder
                                    .setContentTitle("Creation Alert!!")
                                    .setContentText("You have succesfully created your Card with ${history.serialNum} serial number of amount ${history.amt}.")

                        }
                        if (history.isDeleted) {
                            notificationBuilder
                                .setContentTitle("Deletion Alert!!")
                                .setContentText("You have succesfully deleted your Card with ${history.serialNum} serial number.")

                        }
                        val notification = notificationBuilder.build()
                        notificationManager.notify(notifyId, notification)
                    }
                }
            }
        }

        return binding.root
    }

    private fun createNotificationChannel(id: String, name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                setDescription(description)
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

}