package com.safespend.cashsentry.ui

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.safespend.cashsentry.databinding.FragmentProfileBinding
import com.safespend.cashsentry.viewmodel.profile.ProfileViewModel
import com.safespend.cashsentry.viewmodel.profile.ProfileViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileViewModel = ViewModelProvider(requireActivity(),ProfileViewModelFactory(application = requireContext())).get(
            ProfileViewModel::class.java)
        pieChart = binding.pieChart

        showProfileDetails()
        showPieChartResult()
        return binding.root
    }

    private fun showPieChartResult(){
        val list:ArrayList<PieEntry> = ArrayList()

        list.add(PieEntry(30f,"Withdrawl"))
        list.add(PieEntry(70f,"Deposit"))

        val pieDataSet= PieDataSet(list,"")

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS,255)
        pieDataSet.valueTextColor= Color.GREEN
        pieDataSet.valueTextSize=20f

        val pieData= PieData(pieDataSet)
        pieData.setValueTextColor(Color.WHITE)

        pieChart.data= pieData
        pieChart.description.text = ""
        pieChart.centerText = ""
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.animateY(2000)

    }

    private fun showProfileDetails(){
        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.adminProfile.collect{state ->
                if (state.isLoading) {
                    Log.i("UI load", "lol")
                }else if(state.error.isNotEmpty()){
                    Log.i("UI error", "lol")
                }else if(state.userProfile != null){
                    binding.
                    etUserEmail.text = state.userProfile.email
                    binding.etUserName.text = state.userProfile.name
                }
            }
        }
    }

}