package com.ripple.permission

import com.ripple.permission.callback.PermissionCallback
import java.lang.reflect.InvocationTargetException

/**
 * Author: fanyafeng
 * Data: 2020/4/13 17:21
 * Email: fanyafeng@live.cn
 * Description: 注解辅助类
 */
object RipplePermission {

    /**
     *
     * 做权限申请
     *
     * @param host 需要申请权限的方法的实例对象
     * @param permissions 需要申请的权限
     *
     * @param methodName 需要申请权限方法的名字
     * @param methodDesc 需要申请权限方法的描述
     * @param args 当前运行时传递的参数
     *
     * @param failMethodName 权限申请失败调用的方法,可空。
     *
     */
    fun doCheckPermission(host: Any, permissions: List<String>,
                          methodName: String, methodDesc: String, args: List<Any>,
                          failMethodName: String?): Boolean {
        val permissionArray = permissions.toTypedArray()
        if (RipplePermissionImpl.checkSelfPermissions(*permissionArray)) {
            return true
        }

        RipplePermissionImpl.requestPermissions(*permissionArray, callback = object :
            PermissionCallback {
            override fun onFinish(allGranted: Boolean, grant: List<String>, deny: List<String>) {
                if (allGranted) {
                    val successMethod = Util.findMethod(host, methodName, methodDesc)
                    successMethod?.apply {
                        try {
                            if (args.isEmpty()){
                                this.invoke(host)
                            }else{
                                this.invoke(host, *args.toTypedArray())
                            }
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        }
                    }
                } else if (failMethodName != null){
                    val failMethod = Util.findMethod(host, failMethodName, "(Ljava/util/List;Ljava/util/List;)V")
                    failMethod?.apply {
                        try {
                            failMethod.invoke(host, grant, deny)
                        } catch (e: InvocationTargetException) {
                            e.printStackTrace()
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        }
                    }
                }
            }

        })

        return false
    }

}