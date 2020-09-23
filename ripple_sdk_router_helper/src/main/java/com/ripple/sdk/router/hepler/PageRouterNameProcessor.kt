package com.ripple.sdk.router.hepler

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement


/**
 * Author: fanyafeng
 * Date: 2020/9/18 17:24
 * Email: fanyafeng@live.cn
 * Description: 类注解解析器
 *
 * 核心功能是生成页面的键值对
 */
class PageRouterNameProcessor : AbstractProcessor() {

    @Synchronized
    override fun process(
        set: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {
        return true
    }
}