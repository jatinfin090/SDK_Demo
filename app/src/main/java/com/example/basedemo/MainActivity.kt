package com.example.basedemo

import com.example.basedemo.base.BaseActivity
import com.example.basedemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutResId: Int = R.layout.activity_main
    // Additional implementation for MainActivity
}
