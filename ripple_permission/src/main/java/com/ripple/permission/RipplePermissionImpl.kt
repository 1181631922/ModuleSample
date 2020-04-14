package com.ripple.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import com.ripple.permission.callback.PermissionCallback

/**
 * Author: fanyafeng
 * Data: 2020/4/13 17:25
 * Email: fanyafeng@live.cn
 * Description:
 */
object RipplePermissionImpl : IRipplePermission {

    private lateinit var context: Context
    internal var mCallback: PermissionCallback? = null

    fun init(context: Context) {
        this.context = context

    }

    override fun checkSelfPermissions(vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions.forEach {
                if (context.checkSelfPermission(it) == PackageManager.PERMISSION_DENIED)
                    return false
            }
        }
        return true
    }

    override fun requestPermissions(vararg permissions: String, callback: PermissionCallback) {
        if (checkSelfPermissions(*permissions)) {
            callback.onFinish(true, permissions.asList(), listOf())
        } else {
            mCallback = callback
            val intent = Intent(context, PermissionActivity::class.java)
            intent.putExtra(PermissionActivity.DATA_PERMISSIONS, permissions)
            context.startActivity(intent)
        }
    }


}