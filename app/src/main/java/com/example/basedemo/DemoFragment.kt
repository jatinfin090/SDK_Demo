package com.example.basedemo

import com.example.basedemo.base.BaseFragment
import com.example.basedemo.databinding.LayoutFragmentDemoBinding


class DemoFragment : BaseFragment<LayoutFragmentDemoBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.layout_fragment_demo
    }
}