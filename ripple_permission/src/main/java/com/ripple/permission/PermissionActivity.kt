package com.ripple.permission

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.lang.Exception

class PermissionActivity : AppCompatActivity() {

    private lateinit var mPermissions: Array<String>
    private lateinit var mAppName: CharSequence

    private val mReapplyPermissions = mutableListOf<String>()

    companion object {
        const val DATA_PERMISSIONS = "data_permissions"
        const val DATA_CALLBACK = "data_callback"

        private const val REQUEST_SETTING = 1000

        private const val REQUEST_CODE_MUTI = 1001
        private const val REQUEST_CODE_MUTI_SINGLE = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        doApply(*mPermissions)

    }

    @Suppress("unchecked_cast")
    private fun initData() {
        mPermissions = intent.getStringArrayExtra(DATA_PERMISSIONS)
        mAppName = applicationInfo.loadLabel(packageManager)
    }

    private fun doApply(vararg permissions: String) {
        val requestCode = if (permissions.size == 1) REQUEST_CODE_MUTI_SINGLE else REQUEST_CODE_MUTI
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_MUTI -> {
                onMutiResult(permissions, grantResults)
            }
            REQUEST_CODE_MUTI_SINGLE -> {
                onSingleResult(permissions[0], grantResults[0])
            }
        }
    }

    private fun onMutiResult(permissions: Array<out String>, grantResults: IntArray) {
        grantResults.forEachIndexed { index, i ->
            if (i == PackageManager.PERMISSION_DENIED) {
                mReapplyPermissions.add(permissions[index])
            }
        }

        checkReapply()
    }

    private fun onSingleResult(permission: String, grantResults: Int) {
        mReapplyPermissions.remove(permission)
        if (grantResults == PackageManager.PERMISSION_DENIED) {
            gotoSetting(permission)
        } else {
            checkReapply()
        }
    }

    private fun checkReapply() {
        if (mReapplyPermissions.isEmpty()) {
            finish()
        } else {
            doApply(mReapplyPermissions[0])
        }
    }


    private fun gotoSetting(permission: String) {
        val msg = "由于${mAppName}无法获取${permission}权限, 不能正常运行, 请开启权限后再使用。\n设置路径: 应用管理->$mAppName->权限"
        showAlertDialog("申请权限", msg, "拒绝", "去设置", DialogInterface.OnClickListener { _, _ ->
            try {
                val packageURI = Uri.parse("package:$packageName")
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
                startActivityForResult(intent, REQUEST_SETTING)
            } catch (e: Exception) {
                finish()
            }
        })

    }

    private fun showAlertDialog(title: String, msg: String, cancelTxt: String, PosTxt: String, onClickListener: DialogInterface.OnClickListener) {
        val alertDialog = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton(cancelTxt) { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                .setPositiveButton(PosTxt, onClickListener).create()
        alertDialog.show()
    }

    override fun finish() {
        super.finish()
        postPermissionResult()
    }

    private fun postPermissionResult() {
        val deniedPermissions = mutableListOf<String>()
        val grantedPermissions = mutableListOf<String>()
        mPermissions.forEach {
            if (RipplePermissionImpl.checkSelfPermissions(it)) {
                grantedPermissions.add(it)
            } else {
                deniedPermissions.add(it)
            }
        }
        RipplePermissionImpl.mCallback?.onFinish(deniedPermissions.isEmpty(), grantedPermissions, deniedPermissions)
        RipplePermissionImpl.mCallback = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SETTING) {
            finish()
        }
    }
}
