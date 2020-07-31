package com.ripple.tool.extend

import com.ripple.tool.kttypelians.QuadraLambda
import com.ripple.tool.kttypelians.SuccessLambda
import com.ripple.tool.kttypelians.TripleLambda
import java.lang.reflect.Method

/**
 * Author: fanyafeng
 * Data: 2020/6/29 20:03
 * Email: fanyafeng@live.cn
 * Description: 为Int添加foreach扩展
 */


/**
 * Int的foreach封装
 * 需要int值大于0
 */
fun Int.forEach(successLambda: SuccessLambda<Int>) {
    (0 until this).forEach {
        successLambda?.invoke(it)
    }
}

/**
 * 带有首尾锚点的forEach
 */
fun Int.forEachAnchor(lambda: TripleLambda<Int, Boolean, Boolean>) {
    (0 until this).forEach {
        when (it) {
            0 -> {
                lambda?.invoke(it, true, false)
            }
            this - 1 -> {
                lambda?.invoke(it, false, true)
            }
            else -> {
                lambda?.invoke(it, false, false)
            }
        }
    }
}

/**
 * 遍历string所有元素
 */
fun String.forEach(successLambda: SuccessLambda<Char>) {
    (0 until length).forEach {
        successLambda?.invoke(this[it])
    }
}

/**
 * 带有锚点的遍历string所有元素的foreach
 */
fun String.forEachAnchor(lambda: TripleLambda<Char, Boolean, Boolean>) {
    (0 until length).forEach {
        when (it) {
            0 -> {
                lambda?.invoke(this[it], true, false)
            }
            length - 1 -> {
                lambda?.invoke(this[it], false, true)
            }
            else -> {
                lambda?.invoke(this[it], false, false)
            }
        }
    }
}

/**
 * 通过Iterable获取首尾锚点
 * 第三个返回值为True时则为首位置
 * 第四个返回值为True时则为尾位置
 */
fun <T> List<T>.forEachAnchor(lambda: QuadraLambda<Int, T, Boolean, Boolean>) {
    this.forEachIndexed { index, item ->
        when (index) {
            0 -> {
                if (size == 1) {
                    lambda?.invoke(index, item, true, true)
                } else {
                    lambda?.invoke(index, item, true, false)
                }
            }
            size - 1 -> {
                lambda?.invoke(index, item, false, true)
            }
            else -> {
                lambda?.invoke(index, item, false, false)
            }
        }
    }
}

