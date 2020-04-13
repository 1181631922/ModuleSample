package com.fanyafeng.modules.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fanyafeng.modules.R
import com.ripple.permission.RipplePermissionImpl
import com.ripple.permission.callback.PermissionCallback
import kotlinx.android.synthetic.main.activity_permission.*


/**
 * 动态权限测试activity
 */
class PermissionTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        title = "动态权限"

        initView()
        initData()
    }

    private fun initView() {

        permissionTest.setOnClickListener {

        }

    }



    private fun requestMediaPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    3
                )
            } else {
            }
        } else {
        }
    }


    fun requestPermission() {
        RipplePermissionImpl.requestPermissions(permissions = *arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
            callback = object : PermissionCallback {
                override fun onFinish(
                    allGranted: Boolean,
                    grant: List<String>,
                    deny: List<String>
                ) {
                    if (allGranted) {
//                        location(intArrayOf(1, 2))
                        grant.forEachIndexed { index, s ->
                            Log.d("允许", s)
                        }
                    } else {
                        onFail(deny)
                    }
                }

            })
    }

    fun location(a: IntArray) {
        val toast = Toast.makeText(this, "（[Int）:（$a）", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.show()
    }

    fun onFail(per: List<String>) {
        per.forEach {
            Log.e("fail", it)
        }
    }

    private fun initData() {

    }
}
