package com.example.basedemo.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.di.NetworkSDKModule
import com.example.basedemo.di.NetworkSdkModuleImpl
import okhttp3.OkHttpClient
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.logging.Handler

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private var rootView: View? = null
    var binding: T? = null
    private var dialog: Dialog? = null
    private lateinit var mContainerActivity: BaseActivity
    private lateinit var webSocketListener: WebSocketListener
    lateinit var mainHandler: Handler
    var runnable: Runnable? = null
    var delay = 5000
    private val okHttpClient = OkHttpClient()

    lateinit var dataStoreManager1: DataStoreManager
    var webSocket: WebSocket? = null
    private lateinit var networkSDKModule: NetworkSDKModule
    private lateinit var dataStoreManager: DataStoreManager

    @LayoutRes
    abstract fun getLayoutId(): Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate<T>(inflater, getLayoutId(), container, false)
        binding?.lifecycleOwner = this
        rootView = binding!!.root
        networkSDKModule = NetworkSdkModuleImpl(requireContext())
        dataStoreManager = DataStoreManager(requireContext())
        return rootView
    }

    open fun binding(): T {
        return binding!!
    }

    fun showProgressDialog() {
        (activity as? BaseActivity)?.setLoadingDialog()
    }

    fun hideDialog() {
        (activity as? BaseActivity)?.hideProgressDialog()

    }

    fun getContainerActivity(): BaseActivity {
        return mContainerActivity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContainerActivity = context as BaseActivity
    }
}
