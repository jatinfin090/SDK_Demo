package com.example.basedemo

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basedemo.base.BaseFragment
import com.example.basedemo.databinding.LayoutFragmentDemoBinding


class LoanDashboardFragment : BaseFragment<LayoutFragmentDemoBinding>() {

    private var currentPosition = 0
    override fun getLayoutId(): Int {
        return R.layout.layout_fragment_demo
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

val imageList = listOf(
                R.drawable.offer1,
                R.drawable.offer2
            )


            val adapter = ExclusiveOfferPagerAdapter(imageList)
            binding?.viewPagerOffers?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding?.viewPagerOffers?.adapter = ExclusiveOfferPagerAdapter(imageList)

        }
    }


}