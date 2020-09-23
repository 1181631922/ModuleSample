package com.ripple.sdk.router.hepler


/**
 * Author: fanyafeng
 * Date: 2020/9/18 15:58
 * Email: fanyafeng@live.cn
 * Description:
 *
 * 路由页面注册
 * 会根据所有的注册信息生成相应的类，主要是string
 * 如果是首页跳转的话，防止一次性加入太多类的实例，而造成启动时间过长的问题
 */
annotation class PageRouterName(

    /**
     * 生成类的全部链接，不能为空
     * 因为默认值value，省去部分麻烦
     */
    val value: String,

    /**
     * 生成的为pageName
     * 如果为空则默认为类的全部大写字母
     * 否则取当前的注解值
     */
    val pageName: String = "",

    /**
     * 是否需要登录，一般现在都会获取用户信息，都需要登录，所以这里默认为true
     * 如果不设置此值那么默认此页面需要登录
     * 否则直接设置为false
     */
    val pageNeedLogin: Boolean = true,

    /**
     * 页面的note信息，主要是给自己或者同事看
     * 主要是解释此类的作用，或者是那个页面
     */
    val pageNote: String = ""

)