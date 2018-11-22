package com.example.jason.portfoliokt.utils.extensions

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

fun Activity.toast(id: Int, length: Int = Toast.LENGTH_SHORT) {
    if (isOnMainThread()) {
        showToast(this, id, length)
    } else {
        runOnUiThread {
            showToast(this, id, length)
        }
    }
}

fun Activity.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    if (isOnMainThread()) {
        showToast(this, msg, length)
    } else {
        runOnUiThread {
            showToast(this, msg, length)
        }
    }
}

fun AppCompatActivity.replaceFragmentInActivity(@IdRes containerId: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.transact {
        replace(containerId, fragment, tag)
        addToBackStack(tag)
    }
}

fun AppCompatActivity.switchMainFragment(@IdRes containerId: Int, targetFragment: Fragment, tag: String): Fragment? {
    val topFragment = supportFragmentManager.findFragmentById(containerId)
    if (topFragment == null || topFragment.tag?.equals(tag) == true) {
        return topFragment
    }

    supportFragmentManager.transact {
        add(containerId, targetFragment, tag)
        addToBackStack(tag)
        hide(topFragment)
    }

    return targetFragment
}

private fun showToast(activity: Activity, messageId: Int, length: Int) {
    if (!activity.isDestroyed) {
        showToast(activity, activity.getString(messageId), length)
    }
}

private fun showToast(activity: Activity, message: String, length: Int) {
    if (!activity.isDestroyed) {
        activity.applicationContext.toast(message, length)
    }
}
