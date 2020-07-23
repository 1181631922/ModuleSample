/**
 * 处理empty
 * Created by imuto on 17/11/24.
 */
@file:JvmName("ArrayUtil")

package com.ripple.tool.check

import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import java.io.File

//fun isEmpty(vararg list: Any?): Boolean {
//    if (list.isEmpty()) {
//        return false
//    }
//    if (list.contains(null)) return false
//    var isEmpty = false
//    val lambda: (Boolean) -> Boolean = {
//        if (it) isEmpty = true
//        it
//    }
//    list.forEach {
//        when (it) {
//            is Array<*> -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is BooleanArray -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is CharArray -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is DoubleArray -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is FloatArray -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is IntArray -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is LongArray -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is ShortArray -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is Collection<*> -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is Map<*, *> -> if (lambda.invoke(it.isEmpty())) return@forEach
//            is CharSequence -> if (lambda.invoke(it.isEmpty())) return@forEach
//        }
//    }
//    return isEmpty
//}

inline fun isEmpty(list: Array<*>?) = list == null || list.isEmpty()
inline fun isEmpty(list: BooleanArray?) = list == null || list.isEmpty()
inline fun isEmpty(list: CharArray?) = list == null || list.isEmpty()
inline fun isEmpty(list: DoubleArray?) = list == null || list.isEmpty()
inline fun isEmpty(list: FloatArray?) = list == null || list.isEmpty()
inline fun isEmpty(list: IntArray?) = list == null || list.isEmpty()
inline fun isEmpty(list: LongArray?) = list == null || list.isEmpty()
inline fun isEmpty(list: ShortArray?) = list == null || list.isEmpty()
inline fun isEmpty(list: SparseIntArray?) = list == null || list.size() <= 0
inline fun isEmpty(list: SparseBooleanArray?) = list == null || list.size() <= 0
inline fun isEmpty(list: SparseArray<*>?) = list == null || list.size() <= 0
inline fun isEmpty(list: Collection<*>?) = list == null || list.isEmpty()
inline fun isEmpty(list: Map<*, *>?) = list == null || list.isEmpty()
inline fun isEmpty(str: CharSequence?) = str == null || str.isEmpty()
inline fun hasEmpty(vararg str: CharSequence?): Boolean {
    str.forEach {
        if (isEmpty(it)) return true
    }
    return false
}

inline fun exists(file: File?) = file != null && file.exists()

inline fun exists(file: String?) = file != null && exists(File(file))

inline fun largerSize(list: Array<*>?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(list: BooleanArray?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(list: CharArray?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(list: DoubleArray?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(list: FloatArray?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(list: IntArray?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(list: LongArray?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(list: ShortArray?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(list: SparseIntArray?, size: Int) = if (isEmpty(list)) false else list!!.size() > size
inline fun largerSize(list: SparseBooleanArray?, size: Int) = if (isEmpty(list)) false else list!!.size() > size
inline fun largerSize(list: SparseArray<*>?, size: Int) = if (isEmpty(list)) false else list!!.size() > size
inline fun largerSize(list: Collection<*>?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(list: Map<*, *>?, size: Int) = if (isEmpty(list)) false else list!!.size > size
inline fun largerSize(str: CharSequence?, size: Int) = if (isEmpty(str)) false else str!!.length > size

inline fun sameSize(list: Array<*>?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(list: BooleanArray?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(list: CharArray?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(list: DoubleArray?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(list: FloatArray?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(list: IntArray?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(list: LongArray?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(list: ShortArray?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(list: SparseIntArray?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size() == size)
inline fun sameSize(list: SparseBooleanArray?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size() == size)
inline fun sameSize(list: SparseArray<*>?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size() == size)
inline fun sameSize(list: Collection<*>?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(list: Map<*, *>?, size: Int) = (isEmpty(list) && size <= 0) || (list != null && list.size == size)
inline fun sameSize(str: CharSequence?, size: Int) = (isEmpty(str) && size <= 0) || (str != null && str.length == size)