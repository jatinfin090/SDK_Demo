package com.example.basedemo.eligibilty

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import com.example.basedemo.R
import com.example.basedemo.base.BaseFragment
import com.example.basedemo.databinding.LayoutFragmentCheckEligibilityBinding
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.di.NetworkSdkModuleImpl
import com.example.basedemo.model.LoanSdkSaveRequest
import com.example.basedemo.utils.SupportedDatePickerDialog
import com.example.basedemo.utils.Utility
import com.example.basedemo.utils.hideKeyboard
import com.example.basedemo.utils.observeInLifecycle
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CheckEligibilityFragmentww : BaseFragment<LayoutFragmentCheckEligibilityBinding>(),
    SupportedDatePickerDialog.OnDateSetListener {

    private var onSaveButtonClickListener: OnSaveButtonClickListener? = null
private lateinit var viewModel: CheckEligibilityViewModel
    enum class Gender {
        Male, Female, Other
    }

    private var selectedGender: Gender? = null

    interface OnSaveButtonClickListener {
        fun onSaveButtonClicked(data: String)
    }

    fun setOnSaveButtonClickListener(listener: OnSaveButtonClickListener) {
        onSaveButtonClickListener = listener
    }


    companion object {
        fun newInstance() = CheckEligibilityFragmentww()
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

        viewModel = ViewModelProvider(this, viewModelFactory)[CheckEligibilityViewModel::class.java]

        dataStoreManager = DataStoreManager(requireContext())
        viewModel.getTimeStampDigest(dataStoreManager)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            binding?.ivCalender?.setOnClickListener {
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

                    if (binding?.edtName?.text.toString()
                            .isNotEmpty() && binding?.edtMobileNo?.text.toString().isNotEmpty()
                        && binding?.edtEmail?.text.toString()
                            .isNotEmpty() && binding?.edtPAN?.text.toString().isNotEmpty()
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

                val userSaveRequest = LoanSdkSaveRequest(
                    binding?.edtName?.text.toString(),
                    selectedGender.toString(),
                    binding?.edtPAN?.text.toString(),
                    edtEmail.text.toString(),
                    edtMobileNo.text.toString(),
                    edtDOB.text.toString(),
                    1
                )


            /*    val loanSdkRegisterRequest = LoanSdkRegisterUserRequest(
                    edtMobileNo.text.toString(),
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
                )*/

                viewModel?.loanSdkRegisterUSer(
                    edtMobileNo.text.toString(),
                    "abcd",
                    "abcd",
                    "uuu",
                    "abcd",
                    "4",
                    "0.0.4",
                    "Android",
                    true,
                    "80.65",
                    "78.92",
                    dataStoreManager
                )

                viewModel?.userToken?.observe(viewLifecycleOwner) {
                    viewModel?.loanSdkSaveUser(userSaveRequest)
                }

                viewModel?.userSaveResponse?.observe(viewLifecycleOwner) {
                    Log.e("k_responseSave", it)
                    onSaveButtonClickListener?.onSaveButtonClicked(it)
                }

            }


            viewModel?.eventFlow?.onEach {
                when (it) {

                    is CheckEligibilityViewModel.Event.ResponseToken -> {
                        Log.e("k_responseEvent", it.token)

                    }

                    else -> {}
                }
            }?.observeInLifecycle(viewLifecycleOwner)

        }
    }


    fun showCalendar() {
        hideKeyboard()
        val supportedDatePickerDialog =
            SupportedDatePickerDialog(
                context = requireContext(),
                this@CheckEligibilityFragmentww,
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