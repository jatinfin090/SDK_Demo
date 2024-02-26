package com.example.basedemo.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.RequiresApi
import java.nio.charset.StandardCharsets
import java.util.Base64
import java.util.Calendar
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Utility {

    private var utilityWindow: Window? = null
    fun fullParentWindow(window: Window) {
        utilityWindow = window
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            @Suppress("DEPRECATION")
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            @Suppress("DEPRECATION")
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = utilityWindow
        val winParams = win?.attributes
        if (on) {
            winParams!!.flags = winParams.flags or bits
        } else {
            winParams!!.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    val calender: Calendar? by lazy {
        Calendar.getInstance()
    }


    fun deviceId(ctx: Context): String {
        return Settings.Secure.getString(ctx.contentResolver, Settings.Secure.ANDROID_ID)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateHmacSha256Signature(secretKey: String, data: String): String {
        val secretKeySpec =
            SecretKeySpec(secretKey.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(secretKeySpec)
        val rawHmac = mac.doFinal(data.toByteArray(StandardCharsets.UTF_8))
        val base64Hmac = Base64.getEncoder().encodeToString(rawHmac)
        return base64Hmac
    }

}
