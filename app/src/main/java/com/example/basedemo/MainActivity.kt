package com.example.basedemo

import android.os.Bundle
import android.util.Log
import com.example.basedemo.base.BaseActivity
import com.example.basedemo.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutResId: Int = R.layout.activity_main
    // Additional implementation for MainActivity

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Additional implementation for MainActivity
        //  printValues()
    }

    public fun printValues() {
        Log.e("SDK_CALL", "Value from SDK")
    }
}
