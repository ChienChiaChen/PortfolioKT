package com.example.jason.portfoliokt.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.example.jason.portfoliokt.fragments.BaseFragment
import com.example.jason.portfoliokt.utils.PermissionCase
import com.example.jason.portfoliokt.utils.extensions.PERMISSION_MANUAL
import com.example.jason.portfoliokt.utils.extensions.PERMISSION_REQUEST
import com.example.jason.portfoliokt.utils.extensions.getPermissionString
import com.example.jason.portfoliokt.utils.extensions.hasPermission

open class BaseActivity : AppCompatActivity() {
    var onPermissionCallback: ((case: PermissionCase) -> Unit)? = null
    var permissionId: Int = -1

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (PERMISSION_REQUEST != requestCode || grantResults.isEmpty()) {
            onPermissionCallback?.invoke(PermissionCase.DENIED)
            return
        }

        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            onPermissionCallback?.invoke(PermissionCase.GRANTED)
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, getPermissionString(permissionId))) {
                onPermissionCallback?.invoke(PermissionCase.DENIED)
            } else { // Checked "Do not ask me again"
                onPermissionCallback?.invoke(PermissionCase.NEVER_ASK_AGAIN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PERMISSION_MANUAL -> {
                onPermissionCallback?.invoke(if (hasPermission(permissionId)) PermissionCase.GRANTED else PermissionCase.DENIED)
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun handlePermission(permId: Int, callback: ((case: PermissionCase) -> Unit)) {
        onPermissionCallback = null
        if (hasPermission(permId)) {
            callback(PermissionCase.GRANTED)
        } else {
            onPermissionCallback = callback
            permissionId = permId
            ActivityCompat.requestPermissions(this, arrayOf(getPermissionString(permId)), PERMISSION_REQUEST)
        }
    }

    override fun onBackPressed() {
        val mutableList = supportFragmentManager.fragments
        var handled = false
        mutableList.forEach {
            if (it is BaseFragment) {
                handled = it.onBackPressed()
                if (handled) {
                    return
                }
            }

        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        onPermissionCallback = null
        permissionId = -1
    }
}