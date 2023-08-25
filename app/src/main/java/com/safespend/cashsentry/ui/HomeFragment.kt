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

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var moneycardRecyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel

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
            showDialogBox()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.userWallet.collect{state ->
                val userWallet = state.userWallet
                if (state.isLoading) {
                    Log.i("UI load", "lol")
                }else if(state.error.isNotEmpty()) {
                    Log.i("UI error", "lol")
                }else if(userWallet != null){
                    moneycardRecyclerView.adapter = MoneyCardRecyclerViewAdapter(userWallet, requireContext())
                }
            }
        }

        return binding.root
    }

    private fun showDialogBox(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val moneyCards = resources.getStringArray(R.array.moneycards)
        val arrayadapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, moneyCards)
        val selectedCard = dialog.findViewById<AutoCompleteTextView>(R.id.selectedMoneyCard)
        val selectedCardAmt = dialog.findViewById<TextInputEditText>(R.id.enteredAmount)
        val btnAdd = dialog.findViewById<Button>(R.id.btnAdd)

        selectedCard.setAdapter(arrayadapter)
        dialog.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show()

        btnAdd?.let {
            it.setOnClickListener {
                val moneycardInstance = MoneyCardModel(selectedCard.text.toString(), selectedCardAmt.text.toString())
                homeViewModel.upsertWallet(moneycardInstance)
                homeViewModel.upsertSuccessEvent.observe(viewLifecycleOwner){
                    if(it){
                        dialog.dismiss()
                    }
                }
            }
        }
    }

}