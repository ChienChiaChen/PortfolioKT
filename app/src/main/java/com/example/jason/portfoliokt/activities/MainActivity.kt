package com.example.jason.portfoliokt.activities

import android.content.Intent
import android.os.Bundle
import com.example.jason.portfoliokt.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runtimePermission.setOnClickListener {
            startActivity(Intent(this, RuntimePermissionActivity::class.java))
        }
    }
}
