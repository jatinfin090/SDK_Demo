package com.example.basedemo

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.viewpager2.widget.ViewPager2
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


            val adapter = ExclusiveOfferPagerAdapter(imageList,  binding?.viewPagerOffers!!)
            binding?.viewPagerOffers?.adapter = adapter
          binding?.viewPagerOffers?.clipToPadding = true

            binding?.viewPagerOffers?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPosition = position
                }
            })

           // startAutoScroll()

        }
    }

    private fun startAutoScroll() {
        val handler = Handler()
        val update = Runnable {
            currentPosition++
            binding?.viewPagerOffers?.setCurrentItem(currentPosition, true)
        }

        val delay = 1000L // Adjust the delay between scrolls as needed

        handler.postDelayed(object : Runnable {
            override fun run() {
                update.run()
                handler.postDelayed(this, delay)
            }
        }, delay)
    }

}