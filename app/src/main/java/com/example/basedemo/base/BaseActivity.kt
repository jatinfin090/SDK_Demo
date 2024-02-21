package com.example.basedemo.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.basedemo.R
import com.example.basedemo.datastore.DataStoreManager
import okhttp3.OkHttpClient

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        var progressDialog: Dialog? = null
        var dialog: Dialog? = null
    }


    lateinit var mainHandler: Handler
    var runnable: Runnable? = null
    var delay = 5000
    private val okHttpClient = OkHttpClient()
    private val permissionsRequestCode = 123
    private var permissionCheck = 0

    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }


    fun setLoadingDialog() {
        progressDialog = Dialog(this)
        progressDialog?.show()
        if (progressDialog?.window != null) {
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog?.setContentView(R.layout.loader)
        progressDialog?.setCancelable(false)
        progressDialog?.setCanceledOnTouchOutside(false)

    }

    fun hideProgressDialog() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
            progressDialog = null
        }
    }
}
