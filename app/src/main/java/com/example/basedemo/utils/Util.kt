package com.example.basedemo.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.basedemo.R
import com.example.basedemo.base.BaseApplication
import java.text.SimpleDateFormat
import java.util.Date


private const val NULL = "NULL"
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun getCurrentDate(): String {
    // Get the current date
    val currentDate = Date()
    // Define the desired date format
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    // Format the current date using the specified format
    return dateFormat.format(currentDate)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyBoard(view: View) {
    (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
        showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}


fun AppCompatActivity.whenDialogOpenDismiss(
    tag: String,
) {
    supportFragmentManager.findFragmentByTag(tag)?.let {
        if (it is DialogFragment) it.dismiss()
    }
}


fun Context.getThemeColor(attributeColor: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(attributeColor, typedValue, true)
    return typedValue.data
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun validateEmail(email: String): String? {
    val context = BaseApplication.getContext()?.applicationContext
    return when {
        email.isEmpty() -> context?.getString(R.string.error_message_field_empty_email)
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
            .matches() -> context?.getString(R.string.your_e_mail_address_is_wrong)

        else -> ""
    }
}


fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, backStackTag: String? = null) {
    supportFragmentManager.inTransaction {
        add(frameId, fragment)
        backStackTag?.let {
            addToBackStack(it)
        }!!
    }

}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun Fragment.addFragment(fragment: Fragment, frameId: Int, backStackTag: String? = null) {
    parentFragmentManager.inTransaction {
        add(frameId, fragment)
        backStackTag?.let {
            addToBackStack(it)
        }!!
    }
}


fun popFragment(fragment: Fragment) {
    val manager: FragmentManager = fragment.parentFragmentManager
    val trans = manager.beginTransaction()
    trans.remove(fragment)
    trans.commit()
    manager.popBackStack()
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    parentFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int, backStackTag: String? = null) {
    parentFragmentManager.inTransaction {
        replace(frameId, fragment)
        backStackTag?.let {
            addToBackStack(it)
        }!!
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun setArguments(fragment: Fragment, bundle: Bundle): Fragment {
    fragment.arguments = bundle
    return fragment
}

fun isAndroidOOrHigher(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}
