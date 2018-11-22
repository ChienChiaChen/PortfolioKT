package com.example.jason.portfoliokt.activities

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import com.example.jason.portfoliokt.R
import com.example.jason.portfoliokt.fragments.RuntimePermissionFragment
import com.example.jason.portfoliokt.utils.PermissionCase
import com.example.jason.portfoliokt.utils.extensions.*
import com.example.jason.portfoliokt.utils.view.dialogs.ManualEnablePermissionDialog
import com.example.jason.portfoliokt.utils.view.dialogs.WritePermissionDialog
import kotlinx.android.synthetic.main.activity_runtime_permission.*

class RuntimePermissionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runtime_permission)

        askRuntimePermission.setOnClickListener {
            handlePermission(PERMISSION_WRITE_STORAGE) { permissionCase ->
                when (permissionCase) {
                    PermissionCase.GRANTED -> toast("Granted !!")
                    PermissionCase.NEVER_ASK_AGAIN -> showManualEnablePermissionDialog()
                    PermissionCase.DENIED -> showAskPermissionDialog()
                }
            }
        }

        askRuntimePermissionInFragment.setOnClickListener {
            hideButton()
            val runtimePermissionFragment = RuntimePermissionFragment.newInstance()
            replaceFragmentInActivity(
                R.id.frameContainer,
                runtimePermissionFragment,
                RuntimePermissionFragment::class.java.simpleName
            )
        }
    }

    private fun hideButton() {
        askRuntimePermissionInFragment.visibility = View.INVISIBLE
        askRuntimePermission.visibility = View.INVISIBLE
    }

    private fun showButton() {
        askRuntimePermissionInFragment.visibility = View.VISIBLE
        askRuntimePermission.visibility = View.VISIBLE
    }

    private fun showManualEnablePermissionDialog() {
        ManualEnablePermissionDialog(this@RuntimePermissionActivity, 0)
    }

    private fun showAskPermissionDialog() {
        WritePermissionDialog(this, 0) { isPositive ->
            if (isPositive) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(getPermissionString(PERMISSION_WRITE_STORAGE)),
                    PERMISSION_REQUEST
                )
            } else {
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showButton()
    }
}
