package com.ripple.tool.equipment

import android.content.Context

/**
 * Author: fanyafeng
 * Data: 2020/6/19 17:09
 * Email: fanyafeng@live.cn
 * Description: 获取android设备信息
 */

/**
 * 获取android id
 */
fun Context?.getAndroidID() = EquipmentUtil.getAndroidID(this)

/**
 * 获取android mac地址
 */
fun getMacAddress() = EquipmentUtil.getMacAddress()