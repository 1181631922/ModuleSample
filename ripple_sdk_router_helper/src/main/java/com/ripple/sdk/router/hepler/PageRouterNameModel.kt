package com.ripple.sdk.router.hepler


/**
 * Author: fanyafeng
 * Date: 2020/9/18 17:15
 * Email: fanyafeng@live.cn
 * Description:
 *
 * 注解信息存放的实体类
 */
data class PageRouterNameModel(

    /**
     * packageName.className
     * 包名加类名的全称
     * 别名：pageClassName
     */
    val pageClassName: String,

    /**
     * 当前页面的名字
     */
    val pageName: String,

    /**
     * 跳转到当前页面需要用的名字
     * 一般来说当前页面的名字和跳转名字相同
     * 但是为了防止app升级或者业务变动，代码重构
     * 原有的页面还存在但是需要更改跳转名称的情况
     */
    val forwardName: String,

    /**
     * 是否需要登录
     */
    val needLogin: Boolean,

    /**
     * 生成在类字段上的注释
     */
    val note: String
)