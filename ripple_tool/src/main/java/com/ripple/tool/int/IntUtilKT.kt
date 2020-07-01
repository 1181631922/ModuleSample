package com.ripple.tool.int

/**
 * Author: fanyafeng
 * Data: 2020/6/30 17:54
 * Email: fanyafeng@live.cn
 * Description: Int值常用的工具方法
 */


/**
 * Int值取一半
 * 向下取整
 */
val Int.half: Int
    get() {
        return this shr 1
    }

val Int.halfF: Float
    get() {
        return this.toFloat() / 2
    }

/**
 * 三分之一
 */
val Int.oneTriple: Int
    get() {
        return this / 3
    }

/**
 * 三分之一
 */
val Int.oneTripleF: Float
    get() {
        return this.toFloat() / 3
    }

/**
 * 四分之一
 */
val Int.oneQuadra: Int
    get() {
        return this shr 2
    }

/**
 * 四分之一
 */
val Int.oneQuadraF: Float
    get() {
        return this.toFloat() / 4
    }

/**
 * 五分之一
 */
val Int.onePenta: Int
    get() {
        return this / 5
    }

/**
 * 五分之一
 */
val Int.onePentaF: Float
    get() {
        return this.toFloat() / 5
    }

/**
 * Int的二倍
 */
val Int.double: Int
    get() {
        return this shl 1
    }

/**
 * 三倍
 */
val Int.triple: Int
    get() {
        return this * 3
    }

/**
 * 四倍
 */
val Int.quadra: Int
    get() {
        return this shl 2
    }

/**
 * 五倍
 */
val Int.penta: Int
    get() {
        return this * 5
    }

