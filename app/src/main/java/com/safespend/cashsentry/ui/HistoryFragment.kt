package com.safespend.cashsentry.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.safespend.cashsentry.adaptors.history.HistoryRecylerViewAdapter
import com.safespend.cashsentry.databinding.FragmentHistoryBinding
import com.safespend.cashsentry.viewmodel.history.HistoryViewModel
import com.safespend.cashsentry.viewmodel.history.HistoryViewModelFactory
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
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
                }
            }
        }

        return binding.root
    }

}