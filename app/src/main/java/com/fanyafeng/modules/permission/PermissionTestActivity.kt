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
import com.ripple.permission.RipplePermission
import com.ripple.permission.RipplePermissionImpl
import com.ripple.permission.annotation.NeedPermission
import com.ripple.permission.callback.PermissionCallback
import kotlinx.android.synthetic.main.activity_permission.*
import java.util.*


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
            simpleRequestPermission("name")
        }

    }


    @NeedPermission(
        [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION],
        "result"
    )
    fun simpleRequestPermission(item: String) {
        Log.e("哈哈哈哈", "权限用户已经授权")
    }


    private fun requestMediaPermission() {
//        RipplePermission.doCheckPermission(
//            this, arrayListOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ), "methodName", "methodDesc", listOf("name"), "result"
//        )
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

    fun look() {
        Log.e("哭哭哭哭", "用户拒绝了某些权限")
    }

    fun onFail(per: List<String>) {
        per.forEach {
            Log.e("fail", it)
        }
    }

    private fun initData() {

    }

    fun result(success: List<String>, fail: List<String>) {
        Log.e("哭哭哭哭", "用户拒绝了某些权限")

        success.forEach {
            Log.e("success", it)
        }

        fail.forEach {
            Log.e("fail", it)
        }
    }
}
