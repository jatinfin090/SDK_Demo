package com.example.basedemo.eligibilty

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.basedemo.R
import com.example.basedemo.base.BaseFragment
import com.example.basedemo.databinding.LayoutFragmentCheckEligibilityBinding
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.di.NetworkSdkModuleImpl


class CheckEligibilityFragment : BaseFragment<LayoutFragmentCheckEligibilityBinding>() {

    private var onSaveButtonClickListener: OnSaveButtonClickListener? = null

    interface OnSaveButtonClickListener {
        fun onSaveButtonClicked(data: String)
    }

    fun setOnSaveButtonClickListener(listener: OnSaveButtonClickListener) {
        onSaveButtonClickListener = listener
    }


    companion object {
        fun newInstance() = CheckEligibilityFragment()
    }

    private lateinit var dataStoreManager: DataStoreManager


    override fun getLayoutId(): Int {
        return R.layout.layout_fragment_check_eligibility
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        var viewModelFactory =
            CheckEligibilityViewModelFactory(NetworkSdkModuleImpl(requireContext()))

        val vm = ViewModelProvider(this, viewModelFactory)[CheckEligibilityViewModel::class.java]

        dataStoreManager = DataStoreManager(requireContext())

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            vm.getTimeStampDigest(dataStoreManager)
            binding?.btnContinue?.setOnClickListener {

              //  onSaveButtonClickListener?.onSaveButtonClicked("Name: " + binding?.edtName?.text.toString() + "\n" + "Token:SDK ")


                vm.loanSdkRegisterUSer(
                    "8080998080",
                    "abcd",
                    "abcd",
                    "uuu",
                    "abcd",
                    "4",
                    "0.0.4",
                    "Android",
                    true,
                    "80.65",
                    "78.92"
                )

                vm.usertoken.observe(viewLifecycleOwner) {
                    Log.e("k_response+", it)
                    onSaveButtonClickListener?.onSaveButtonClicked("Name: " + binding?.edtName?.text.toString() + "\n" + "Token: " + it)
                }

            }

        }
    }
}