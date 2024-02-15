package com.example.basedemo.eligibilty

import android.os.Bundle
import android.view.View
import com.example.basedemo.R
import com.example.basedemo.base.BaseFragment
import com.example.basedemo.databinding.LayoutFragmentCheckEligibilityBinding


class CheckEligibilityFragment : BaseFragment<LayoutFragmentCheckEligibilityBinding>() {

    private var onSaveButtonClickListener: OnSaveButtonClickListener? = null

    interface OnSaveButtonClickListener {
        fun onSaveButtonClicked(data: String)
    }

    fun setOnSaveButtonClickListener(listener: OnSaveButtonClickListener) {
        onSaveButtonClickListener = listener
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_fragment_check_eligibility
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.btnContinue.setOnClickListener {
                onSaveButtonClickListener?.onSaveButtonClicked("Name: " + binding.edtName.text.toString() + "\n Comes from Loan SDK")
            }

        }
    }
}