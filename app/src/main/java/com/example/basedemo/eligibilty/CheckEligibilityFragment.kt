package com.example.basedemo.eligibilty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.basedemo.R
import com.example.basedemo.base.BaseFragment
import com.example.basedemo.databinding.LayoutFragmentCheckEligibilityBinding
import com.example.basedemo.datastore.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheckEligibilityFragment : BaseFragment<LayoutFragmentCheckEligibilityBinding>() {

    private val vm: CheckEligibilityViewModel by viewModels()
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

    @Inject
    lateinit var dataStoreManager: DataStoreManager


    override fun getLayoutId(): Int {
        return R.layout.layout_fragment_check_eligibility
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            vm.getTimeStampDigest()
            binding?.btnContinue?.setOnClickListener {
                //   onSaveButtonClickListener?.onSaveButtonClicked("Name: " + binding.edtName.text.toString() + "\n Comes from Loan SDK")


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
                      "78.92")

            }

        }
    }
}