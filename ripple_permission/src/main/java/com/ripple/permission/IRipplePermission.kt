package com.ripple.permission

import com.ripple.permission.callback.PermissionCallback

/**
 * Author: fanyafeng
 * Data: 2020/4/13 17:12
 * Email: fanyafeng@live.cn
 * Description:
 */
interface IRipplePermission {

    /**
     * 判断列表[permissions]中的权限是否全部被授予。
     */
    fun checkSelfPermissions(vararg permissions: String): Boolean


    /**
     * 批量权限申请
     */
    fun requestPermissions(vararg permissions: String, callback: PermissionCallback)

}