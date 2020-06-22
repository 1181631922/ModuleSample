package com.ripple.tool.kttypelians

import android.view.View

/**
 * Author: fanyafeng
 * Data: 2020/6/19 14:32
 * Email: fanyafeng@live.cn
 * Description: 扩展lambda
 */

/**
 * 有返回值的Lambda
 */
typealias ReturnLambda<R> = (() -> R)?

/**
 * 空参数回调Lambda
 */
typealias UnitLambda = ReturnLambda<Unit>

/**
 * 开始回调
 */
typealias StartLambda = UnitLambda

/**
 * 有返回值的回调Lambda
 */
typealias SuccessReturnLambda<T, R> = ((result: T) -> R)?

/**
 * 单个参数成功回调Lambda
 */
typealias SuccessLambda<T> = SuccessReturnLambda<T, Unit>

/**
 * 取消回调
 */
typealias CancelLambda = SuccessLambda<Boolean>

/**
 * 完成回调
 */
typealias FinishLambda = SuccessLambda<Boolean>

/**
 * 有返回值的两个参数回调Lambda
 */
typealias PairReturnLambda<F, S, R> = ((first: F?, second: S) -> R)?

/**
 * 两个参数回调Lambda
 */
typealias PairLambda<F, S> = PairReturnLambda<F, S, Unit>

/**
 * 有返回值的三个参数回调Lambda
 */
typealias TripleReturnLambda<F, S, T, R> = ((first: F?, second: S?, third: T?) -> R)?

/**
 * 三个参数回调
 */
typealias TripleLambda<F, S, T> = TripleReturnLambda<F, S, T, Unit>

/**
 * 有返回值的四个参数回调
 */
typealias QuadraReturnLambda<F, S, T, O, R> = ((first: F?, second: S?, third: T?, fourth: O?) -> R)?

/**
 * 四个参数回调
 */
typealias QuadraLambda<F, S, T, O> = QuadraReturnLambda<F, S, T, O, Unit>

/**
 * 有返回值的五个参数回调
 */
typealias PentaReturnLambda<F, S, T, O, I, R> = ((first: F?, second: S?, third: T?, fourth: O?, fifth: I?) -> R)?

/**
 * 五个参数回调
 */
typealias PentaLambda<F, S, T, O, I> = PentaReturnLambda<F, S, T, O, I, Unit>


/**
 * 进度回调
 */
typealias ProgressLambda = PairLambda<Long, Long>

/**
 * View点击回调
 * 通常是RecyclerView中的Adapter中回调
 */
typealias OnItemClickListener = PairLambda<View, Int>

/**
 * 带有data model的回调
 */
typealias OnItemModelClickListener<M> = TripleLambda<View, Int, M>

