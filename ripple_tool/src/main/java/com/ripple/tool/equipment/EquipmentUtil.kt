package com.ripple.tool.equipment

import android.content.Context
import android.provider.Settings
import android.util.Log
import java.lang.StringBuilder
import java.net.NetworkInterface

/**
 * Author: fanyafeng
 * Data: 2020/6/19 17:08
 * Email: fanyafeng@live.cn
 * Description: 获取android设备信息
 */
object EquipmentUtil {

    private val TAG = EquipmentUtil::class.java.simpleName
    private const val ERROR_MESSAGE = "请检查context是否为空"

    /**
     * 获取android id
     */
    fun getAndroidID(context: Context?): String? {
        context?.let {
            return Settings.System.getString(it.contentResolver, Settings.Secure.ANDROID_ID)
        }
        Log.e(TAG, ERROR_MESSAGE)
        return null
    }

    /**
     * 获取mac地址
     */
    fun getMacAddress(): String {
        val buf = StringBuilder()
        var networkInterface = NetworkInterface.getByName("eth1")
        if (networkInterface == null) {
            networkInterface = NetworkInterface.getByName("wlan0")
            if (networkInterface == null) {
                return ""
            }
        }
        val address = networkInterface.hardwareAddress
        address.forEach {
            buf.append(String.format("%02X:", it))
        }
        if (buf.isNotEmpty()) {
            buf.deleteCharAt(buf.length - 1)
        }
        return buf.toString()
    }

}