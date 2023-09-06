package com.safespend.cashsentry.ui


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.safespend.cashsentry.R
import com.safespend.cashsentry.adaptors.moneycard.MoneyCardRecyclerViewAdapter
import com.safespend.cashsentry.data.local_data_source.model.MoneyCardModel
import com.safespend.cashsentry.databinding.FragmentHomeBinding
import com.safespend.cashsentry.viewmodel.wallet.HomeViewModel
import com.safespend.cashsentry.viewmodel.wallet.HomeViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment(),MoneyCardRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var moneycardRecyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var tempCardModel: List<MoneyCardModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory(application = requireContext())).get(HomeViewModel::class.java)
        moneycardRecyclerView = binding.recyclerCards
        moneycardRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.btnAddCard.setOnClickListener{
            showDialogBoxForCardCreation()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.getWalletDemo()
            homeViewModel.userWallet.collect{state ->
                val userWallet = state.userWallet
                if (state.isLoading) {
                    Log.i("UI load", "lol")
                }else if(state.error.isNotEmpty()) {
                    Log.i("UI error", "lol")
                }else if(userWallet != null){
                    tempCardModel = userWallet
                    moneycardRecyclerView.adapter = MoneyCardRecyclerViewAdapter(userWallet, requireContext(), this@HomeFragment)
                }
            }
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        // Handle the click event here, for example:
        val clickedItem = tempCardModel?.get(position)
        if (clickedItem != null) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.custom_dialog_box_cash_access)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val selectedAmtForAccess = dialog.findViewById<TextInputEditText>(R.id.enteredAmountForAccess)
            val btnWithdraw = dialog.findViewById<Button>(R.id.btnWithdrawl)
            val btnDeposit = dialog.findViewById<Button>(R.id.btnDeposit)
            val btnDelete = dialog.findViewById<Button>(R.id.btnDelete)
            dialog.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show()

            btnDeposit.setOnClickListener {
                homeViewModel.getUserData()
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.userData.collect { state ->
                        val userData = state.userProfile
                        if (state.isLoading) {
                            Log.i("UI load", "lol")
                        } else if (state.error.isNotEmpty()) {
                            Log.i("UI error", "lol")
                        } else if (userData != null) {
                            val moneycardInstance = MoneyCardModel(
                                name = clickedItem.name,
                                totalAmt = (clickedItem.totalAmt.toInt() + selectedAmtForAccess.text.toString().toInt()).toString(),
                                email = userData.email,
                                serialNum = clickedItem.serialNum
                            )
                            homeViewModel.updateWallet(moneycardInstance)
                            homeViewModel.updateSuccessEvent.observe(viewLifecycleOwner) {
                                if (it) {
                                    Toast.makeText(requireContext(), "Money Card Updated Successfully!!", Toast.LENGTH_LONG).show()
                                    dialog.dismiss()
                                }
                            }
                        }
                    }
                }
            }
            btnWithdraw.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.userData.collect { state ->
                        val userData = state.userProfile
                        if (state.isLoading) {
                            Log.i("UI load", "lol")
                        } else if (state.error.isNotEmpty()) {
                            Log.i("UI error", "lol")
                        } else if (userData != null) {
                            val moneycardInstance = MoneyCardModel(
                                name = clickedItem.name,
                                totalAmt = (clickedItem.totalAmt.toInt() - selectedAmtForAccess.text.toString().toInt()).toString(),
                                email = userData.email,
                                serialNum = clickedItem.serialNum
                            )
                            homeViewModel.updateWallet(moneycardInstance)
                            homeViewModel.updateSuccessEvent.observe(viewLifecycleOwner) {
                                if (it) {
                                    Toast.makeText(requireContext(), "Money Card Updated Successfully!!", Toast.LENGTH_LONG).show()
                                    dialog.dismiss()
                                }
                            }
                        }
                    }
                }
            }
            btnDelete.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.userData.collect { state ->
                        val userData = state.userProfile
                        if (state.isLoading) {
                            Log.i("UI load", "lol")
                        } else if (state.error.isNotEmpty()) {
                            Log.i("UI error", "lol")
                        } else if (userData != null) {
                            val moneycardInstance = MoneyCardModel(
                                name = clickedItem.name,
                                totalAmt = clickedItem.totalAmt,
                                email = clickedItem.email,
                                serialNum = clickedItem.serialNum
                            )
                            homeViewModel.deleteWallet(moneycardInstance)
                            homeViewModel.deleteSuccessEvent.observe(viewLifecycleOwner) {
                                if (it) {
                                    Toast.makeText(requireContext(), "Money Card Deletion Successfully!!", Toast.LENGTH_LONG).show()
                                    dialog.dismiss()
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private fun showDialogBoxForCardCreation(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_layout_addingcards)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val moneyCards = resources.getStringArray(R.array.moneycards)
        val arrayadapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, moneyCards)
        val selectedCard = dialog.findViewById<AutoCompleteTextView>(R.id.selectedMoneyCard)
        val selectedCardAmt = dialog.findViewById<TextInputEditText>(R.id.enteredAmount)
        val enteredSerialNum = dialog.findViewById<TextInputEditText>(R.id.enteredSerialNum)
        val btnAdd = dialog.findViewById<Button>(R.id.btnAdd)

        selectedCard.setAdapter(arrayadapter)
        dialog.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show()

        btnAdd?.let {
            it.setOnClickListener {
                homeViewModel.getUserData()
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.userData.collect { state ->
                        val userData = state.userProfile
                        if (state.isLoading) {
                            Log.i("UI load", "lol")
                        } else if (state.error.isNotEmpty()) {
                            Log.i("UI error", "lol")
                        } else if (userData != null) {
                            val moneycardInstance = MoneyCardModel(
                                name = selectedCard.text.toString(),
                                totalAmt = selectedCardAmt.text.toString(),
                                email = userData.email,
                                serialNum = addHyphensTo16DigitString(enteredSerialNum.text.toString())
                            )
                            homeViewModel.insertWallet(moneycardInstance)
                            homeViewModel.insertSuccessEvent.observe(viewLifecycleOwner) {
                                if (it) {
                                    Toast.makeText(requireContext(), "Money Card Created Successfully!!", Toast.LENGTH_LONG).show()
                                    dialog.dismiss()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private fun addHyphensTo16DigitString(input: String): String {
        val formattedString = StringBuilder()
        for (i in input.indices) {
            if (i > 0 && i % 4 == 0) {
                formattedString.append(" - ")
            }
            formattedString.append(input[i])
        }
        return formattedString.toString()
    }

}