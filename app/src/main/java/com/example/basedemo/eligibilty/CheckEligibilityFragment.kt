package com.example.basedemo.eligibilty

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import com.example.basedemo.R
import com.example.basedemo.base.BaseFragment
import com.example.basedemo.databinding.LayoutFragmentCheckEligibilityBinding
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.di.NetworkSdkModuleImpl
import com.example.basedemo.model.LoanSdkRegisterUserRequest
import com.example.basedemo.model.LoanSdkSaveRequest
import com.example.basedemo.utils.SupportedDatePickerDialog
import com.example.basedemo.utils.Utility
import com.example.basedemo.utils.hideKeyboard
import com.example.basedemo.utils.observeInLifecycle
import com.example.basedemo.utils.validateEmail
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CheckEligibilityFragment : BaseFragment<LayoutFragmentCheckEligibilityBinding>(),
    SupportedDatePickerDialog.OnDateSetListener {

    private var onSaveButtonClickListener: OnSaveButtonClickListener? = null

    enum class Gender {
        Male, Female, Other
    }

    private var selectedGender: Gender? = null
    private lateinit var userSaveRequest: LoanSdkSaveRequest

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
        val viewModelFactory =
            CheckEligibilityViewModelFactory(NetworkSdkModuleImpl(requireContext()))

        val vm = ViewModelProvider(this, viewModelFactory)[CheckEligibilityViewModel::class.java]

        dataStoreManager = DataStoreManager(requireContext())
        vm.getTimeStampDigest(dataStoreManager)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            binding?.edtDOB?.setOnClickListener {
                showCalendar()
            }

            binding?.llMale?.setOnClickListener {
                binding?.llMale?.isSelected = true
                binding?.llFemale?.isSelected = false
                binding?.llOther?.isSelected = false

                selectedGender = Gender.Male
            }
            binding?.llFemale?.setOnClickListener {
                binding?.llFemale?.isSelected = true
                binding?.llMale?.isSelected = false
                binding?.llOther?.isSelected = false
                selectedGender = Gender.Female

            }
            binding?.llOther?.setOnClickListener {

                binding?.llOther?.isSelected = true
                binding?.llFemale?.isSelected = false
                binding?.llMale?.isSelected = false
                selectedGender = Gender.Other
            }

            binding?.cbCondition?.setOnCheckedChangeListener { buttonView, isChecked ->



                if (isChecked) {
                    val emailStatus=  validateEmail(binding?.edtEmail?.text.toString())

                    if (binding?.edtName?.text.toString()
                            .isNotEmpty() && binding?.edtMobileNo?.text.toString().isNotEmpty()
                        && emailStatus?.isEmpty() == true && binding?.edtPAN?.text.toString().isNotEmpty()
                        && binding?.edtDOB?.text.toString().isNotEmpty() && selectedGender != null
                    ) {

                        binding?.btnContinue?.isEnabled = true
                        binding?.btnContinue?.setBackgroundResource(R.drawable.bg_yellow_btn)
                    } else {
                        binding?.btnContinue?.isEnabled = false
                        binding?.btnContinue?.setBackgroundResource(R.drawable.bg_grey_btn)
                    }
                } else {
                    binding?.btnContinue?.isEnabled = false
                    binding?.btnContinue?.setBackgroundResource(R.drawable.bg_grey_btn)
                }
            }


            binding?.btnContinue?.setOnClickListener {

                runBlocking {
                    dataStoreManager.saveAuthToken("")
                }


                val loanSdkRegisterRequest = LoanSdkRegisterUserRequest(
                    edtMobileNo.text.toString(),
                    "abcd",   /// build.model
                    "abcd",  ///
                    "uuu",  //// api issue for now  as device id deviceId(68)
                    "abcd",   /// firebase
                    "4",    //// same as jumpp 4
                    "0.0.4",  /// build.version.sdkint
                    "Android",  /////const
                    true,  //// no need
                    "80.65",
                    "78.92"   ///OnBoardingMobileNumberFragment
                )

                 userSaveRequest = LoanSdkSaveRequest(
                    binding?.edtName?.text.toString(),
                    selectedGender.toString(),
                    binding?.edtPAN?.text.toString(),
                     binding?.edtEmail?.text.toString(),
                     binding?.edtMobileNo?.text.toString(),
                     binding?.edtDOB?.text.toString(),
                    1
                )


                vm.loanSdkRegisterUSer(
                    loanSdkRegisterRequest,
                    dataStoreManager
                )

            }


            vm.eventFlow.onEach {
                when (it) {

                    is CheckEligibilityViewModel.Event.ResponseToken -> {
                        vm.loanSdkSaveUser(userSaveRequest)
                    }

                    is CheckEligibilityViewModel.Event.ResponseSave -> {
                        onSaveButtonClickListener?.onSaveButtonClicked(it.response)
                    }

                    else -> {}
                }
            }.observeInLifecycle(viewLifecycleOwner)

        }
    }


    private fun showCalendar() {
        hideKeyboard()
        val supportedDatePickerDialog =
            SupportedDatePickerDialog(
                context = requireContext(),
                this@CheckEligibilityFragment,
                Utility.calender!!
            )
        supportedDatePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        Utility.calender?.set(Calendar.YEAR, year)
        Utility.calender?.set(Calendar.MONTH, month)
        Utility.calender?.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateDateInView()
    }

    private fun updateDateInView() {
        val myFormat = getString(R.string.yyyy_MM_dd)
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val date = sdf.format(Utility.calender!!.time)

        binding?.edtDOB?.setText(date)

    }


}