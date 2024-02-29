package com.example.basedemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.basedemo.base.BaseActivity
import com.example.basedemo.eligibilty.CheckEligibilityFragment
import com.example.basedemo.utils.Utility
import com.example.basedemo.utils.addFragment

class MainActivity : BaseActivity(),
    CheckEligibilityFragment.OnSaveButtonClickListener {

    val layoutResId: Int = R.layout.activity_main
    val fragment = CheckEligibilityFragment()
    val loanDashboardFragment = LoanDashboardFragment()
    lateinit var latitude : String
    lateinit var longitude : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utility.fullParentWindow(window)
        setContentView(layoutResId)
        fragment.setOnSaveButtonClickListener(this)
        init()
    }

    private fun init() {

        val intent = intent
        if (intent != null) {
             latitude = intent.getStringExtra("lat").toString()
             longitude = intent.getStringExtra("long").toString()
 }

        openFragment(
            loanDashboardFragment,
            R.id.mainActivityFrameLayout,
            "tag"
        )
    }


    private fun openFragment(fragment: Fragment, mainActivityFrameLayout: Int, s: String) {
        val fragment = fragment
        val args = Bundle()
        args.putString("lat",latitude )
        args.putString("long",longitude )
        fragment.arguments = args

        addFragment(
            fragment,
            mainActivityFrameLayout,
            "tag"
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else
            finish()
    }

    override fun onSaveButtonClicked(data: String) {
        println("KData received: $data")
        Log.e("k_data", data)
        val intent = Intent()
        intent.putExtra("saveClicked", true)
        intent.putExtra("datafromsdk", data)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
