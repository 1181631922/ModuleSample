package com.ripple.permission.annotation

/**
 * Author: fanyafeng
 * Data: 2020/4/13 18:09
 * Email: fanyafeng@live.cn
 * Description: 动态权限注解
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class NeedPermission(val permissions: Array<String>, val method: String)