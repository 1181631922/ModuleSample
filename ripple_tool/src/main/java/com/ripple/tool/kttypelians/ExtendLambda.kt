package com.ripple.tool.kttypelians

import android.view.View

/**
 * Author: fanyafeng
 * Data: 2020/6/19 14:32
 * Email: fanyafeng@live.cn
 * Description: 扩展lambda
 */

/**
 * 空参数回调Lambda
 */
typealias UnitLambda = (() -> Unit)?

/**
 * 开始回调
 */
typealias StartLambda = UnitLambda

/**
 * 单个参数成功回调Lambda
 */
typealias SuccessLambda<T> = ((result: T) -> Unit)?

/**
 * 取消回调
 */
typealias CancelLambda = SuccessLambda<Boolean>

/**
 * 完成回调
 */
typealias FinishLambda = SuccessLambda<Boolean>

/**
 * 两个参数回调Lambda
 */
typealias PairLambda<F, S> = ((first: F?, second: S?) -> Unit)?

/**
 * 三个参数回调
 */
typealias TripleLambda<F, S, T> = ((first: F?, second: S?, third: T?) -> Unit)?

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

