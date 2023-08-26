package com.safespend.cashsentry.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.textfield.TextInputEditText
import com.safespend.cashsentry.R
import com.safespend.cashsentry.data.local_data_source.model.UserProfile
import com.safespend.cashsentry.databinding.FragmentProfileBinding
import com.safespend.cashsentry.util.Constants
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
        showPieChartResult()

        binding.btnEditProfile.setOnClickListener{
            showProfileEditDialog()
        }

        showProfileDetails()

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
        profileViewModel.getAdmin()
        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.adminProfile.collect{state ->
                if (state.isLoading) {
                    Log.i("UI load", "lol")
                }else if(state.error.isNotEmpty()){
                    Log.i("UI error", "lol")
                }else if(state.userProfile != null){
                    binding.etUserEmail.text = state.userProfile.email
                    binding.etUserName.text = state.userProfile.name
                }
            }
        }
    }

    private fun showProfileEditDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog_box_editprofile)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val enteredProfileName = dialog.findViewById<TextInputEditText>(R.id.enteredProfileName)
        val enteredProfileEmail = dialog.findViewById<TextInputEditText>(R.id.enteredProfileEmail)
        val btnEditProfile = dialog.findViewById<Button>(R.id.btnProfileUpdate)

        dialog.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show()

        btnEditProfile?.let{
            it.setOnClickListener {
                if(Constants.ADMIN_EMAIL.isNotEmpty() or Constants.ADMIN_EMAIL.isNotBlank()) {
                    profileViewModel.deletePreviousAdmin(Constants.ADMIN_EMAIL)
                }
                profileViewModel.upsertAdmin(
                    UserProfile(
                        id = 1,
                        email = enteredProfileEmail.text.toString(),
                        name = enteredProfileName.text.toString()
                    )
                )
                profileViewModel.upsertSuccessEvent.observe(viewLifecycleOwner) {
                    if (it) {
                        showProfileDetails()
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            showProfileDetails()
        }
    }

}