package com.ripple.task.task

import com.ripple.task.callback.result.OnAllResult
import com.ripple.task.callback.result.OnItemResult
import com.ripple.task.config.ProcessModel
import com.ripple.task.engine.ProcessEngine

/**
 * Author: fanyafeng
 * Data: 2020/6/3 19:39
 * Email: fanyafeng@live.cn
 * Description:
 */
interface ProcessTask {

    /**
     * 所有单个任务回调
     */
    fun getItemResult(): OnItemResult<ProcessModel>?

    /**
     * 所有任务回调
     */
    fun getAllResult(): OnAllResult<List<ProcessModel>>?

    /**
     * 获取任务处理器引擎
     */
    fun getProcessEngine(): ProcessEngine





}