package com.ripple.permission.callback

import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020-04-13 15:32
 * Email: fanyafeng@live.cn
 * Description:
 */
interface PermissionCallback: Serializable {

    /**
     * 授权结束回调
     *
     * @param allGranted 所有申请的权限全部通过授权
     * @param grant 通过授权的权限列表
     * @param deny 没有通过授权的权限列表
     */
    fun onFinish(allGranted: Boolean, grant: List<String>, deny: List<String>)
}