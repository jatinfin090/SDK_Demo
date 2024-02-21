package com.example.basedemo.eligibilty

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.basedemo.R
import com.example.basedemo.base.BaseFragment
import com.example.basedemo.databinding.LayoutFragmentCheckEligibilityBinding
import com.example.basedemo.datastore.DataStoreManager


class CheckEligibilityFragment : BaseFragment<LayoutFragmentCheckEligibilityBinding>() {
    /*

       private val vm: CheckEligibilityViewModel by viewModels {
            viewModelFactory {
                CheckEligibilityViewModel(
                    BaseApplication.networkModule.loanSdkLandingPageRepository
                )
            }
        }
    */


    private lateinit var viewModelFactory: CheckEligibilityViewModelFactory
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

    lateinit var dataStoreManager: DataStoreManager


    override fun getLayoutId(): Int {
        return R.layout.layout_fragment_check_eligibility
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModelFactory = CheckEligibilityViewModelFactory()

        val vm = ViewModelProvider(this, viewModelFactory)[CheckEligibilityViewModel::class.java]

        dataStoreManager = DataStoreManager(requireContext())



        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            vm.getTimeStampDigest(dataStoreManager)
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
                    "78.92"
                )

            }

        }
    }
}