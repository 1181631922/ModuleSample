package com.ripple.task.extend

import android.os.Handler
import com.ripple.task.callback.result.OnAllResult
import com.ripple.task.callback.result.OnItemResult
import com.ripple.task.config.ProcessModel
import com.ripple.task.engine.ProcessEngine
import com.ripple.task.lambda.PairLambda
import com.ripple.task.lambda.SuccessLambda
import com.ripple.task.task.impl.ProcessTaskImpl

/**
 * Author: fanyafeng
 * Data: 2020/6/4 15:24
 * Email: fanyafeng@live.cn
 * Description:
 */

/**
 * 默认为并行多线程任务
 */
@JvmOverloads
fun handleTask(
    process: ProcessModel,
    processEngine: ProcessEngine = ProcessEngine.MULTI_THREAD_EXECUTOR,
    lambda: HandleTaskExtra.() -> Unit
) {
    handleTaskList(listOf(process), processEngine, lambda)
}

/**
 * 默认为并行多线程任务
 */
@JvmOverloads
fun handleTaskList(
    processList: List<ProcessModel>,
    processEngine: ProcessEngine = ProcessEngine.MULTI_THREAD_EXECUTOR,
    lambda: HandleTaskExtra.() -> Unit
) {
    val handleTaskExtra = HandleTaskExtra(processEngine, processList)
    handleTaskExtra.apply {
        lambda()
    }
}

class HandleTaskExtra(processEngine: ProcessEngine, processList: List<ProcessModel>) {

    private var itemStartLambda: SuccessLambda<ProcessModel> = null
    private var itemDoingLambda: SuccessLambda<ProcessModel> = null
    private var itemInterruptedLambda: SuccessLambda<ProcessModel> = null
    private var itemFailedLambda: SuccessLambda<ProcessModel> = null
    private var itemSuccessLambda: SuccessLambda<ProcessModel> = null
    private var itemFinishLambda: SuccessLambda<ProcessModel> = null
    private var startLambda: SuccessLambda<Unit> = null
    private var doingLambda: SuccessLambda<List<ProcessModel>?> = null
    private var failedLambda: SuccessLambda<List<ProcessModel>?> = null
    private var successLambda: SuccessLambda<List<ProcessModel>?> = null
    private var finishLambda: PairLambda<List<ProcessModel>?> = null

    private val engine = ProcessTaskImpl()


    init {
        engine.handleProcessEngine = processEngine
        engine.handleTaskList(processList)
    }


    fun onItemStart(itemStartLambda: SuccessLambda<ProcessModel>) {
        this.itemStartLambda = itemStartLambda
        engine.onItemResult = object : OnItemResult.OnItemSimpleResult<ProcessModel> {
            override fun onItemStart(startResult: ProcessModel) {
                super.onItemStart(startResult)
                this@HandleTaskExtra.itemStartLambda?.invoke(startResult)
            }
        }
    }

    fun onItemDoing(itemDoingLambda: SuccessLambda<ProcessModel>) {
        this.itemDoingLambda = itemDoingLambda
        engine.onItemResult = object : OnItemResult.OnItemSimpleResult<ProcessModel> {
            override fun onItemDoing(doingResult: ProcessModel) {
                super.onItemDoing(doingResult)
                this@HandleTaskExtra.itemDoingLambda?.invoke(doingResult)
            }
        }
    }

    fun onItemInterrupted(itemInterruptedLambda: SuccessLambda<ProcessModel>) {
        this.itemInterruptedLambda = itemInterruptedLambda
        engine.onItemResult = object : OnItemResult.OnItemSimpleResult<ProcessModel> {
            override fun onItemInterrupted(interruptedResult: ProcessModel) {
                super.onItemInterrupted(interruptedResult)
                this@HandleTaskExtra.itemInterruptedLambda?.invoke(interruptedResult)
            }
        }
    }

    fun onItemFailed(itemFailedLambda: SuccessLambda<ProcessModel>) {
        engine.onItemResult = object : OnItemResult.OnItemSimpleResult<ProcessModel> {
            override fun onItemFailed(failedResult: ProcessModel) {
                super.onItemFailed(failedResult)
                this@HandleTaskExtra.itemFailedLambda?.invoke(failedResult)
            }
        }
    }

    fun onItemSuccess(itemSuccessLambda: SuccessLambda<ProcessModel>) {
        this.itemSuccessLambda = itemSuccessLambda
        engine.onItemResult = object : OnItemResult.OnItemSimpleResult<ProcessModel> {
            override fun onItemSuccess(successResult: ProcessModel) {
                super.onItemSuccess(successResult)
                this@HandleTaskExtra.itemSuccessLambda?.invoke(successResult)
            }
        }
    }

    fun onItemFinish(itemFinishLambda: SuccessLambda<ProcessModel>) {
        this.itemSuccessLambda = itemSuccessLambda
        engine.onItemResult = object : OnItemResult.OnItemSimpleResult<ProcessModel> {
            override fun onItemFinish(finishResult: ProcessModel) {
                super.onItemFinish(finishResult)
                this@HandleTaskExtra.itemFinishLambda?.invoke(finishResult)
            }
        }

    }

    fun onStart(startLambda: SuccessLambda<Unit>) {
        this.startLambda = startLambda
        engine.onAllResult = object : OnAllResult.OnAllSimpleResult<List<ProcessModel>> {
            override fun onStart() {
                super.onStart()
                this@HandleTaskExtra.startLambda?.invoke(Unit)
            }
        }
    }

    fun onDoing(doingLambda: SuccessLambda<List<ProcessModel>?>) {
        this.doingLambda = doingLambda
        engine.onAllResult = object : OnAllResult.OnAllSimpleResult<List<ProcessModel>> {
            override fun onDoing(doingItem: List<ProcessModel>?) {
                super.onDoing(doingItem)
                this@HandleTaskExtra.doingLambda?.invoke(doingItem)
            }
        }
    }

    fun onFailed(failedLambda: SuccessLambda<List<ProcessModel>?>) {
        this.failedLambda = failedLambda
        engine.onAllResult = object : OnAllResult.OnAllSimpleResult<List<ProcessModel>> {
            override fun onFailed(failedResult: List<ProcessModel>?) {
                super.onFailed(failedResult)
                this@HandleTaskExtra.failedLambda?.invoke(failedResult)
            }
        }
    }

    fun onSuccess(successLambda: SuccessLambda<List<ProcessModel>?>) {
        this.successLambda = successLambda
        engine.onAllResult = object : OnAllResult.OnAllSimpleResult<List<ProcessModel>> {
            override fun onSuccess(successResult: List<ProcessModel>?) {
                super.onSuccess(successResult)
                this@HandleTaskExtra.successLambda?.invoke(successResult)
            }
        }
    }

    fun onFinish(
        finishLambda: PairLambda<List<ProcessModel>?>
    ) {
        this.finishLambda = finishLambda
        engine.onAllResult = object : OnAllResult.OnAllSimpleResult<List<ProcessModel>> {
            override fun onFinish(
                finishResult: List<ProcessModel>?,
                unFinishResult: List<ProcessModel>?
            ) {
                super.onFinish(finishResult, unFinishResult)
                this@HandleTaskExtra.finishLambda?.invoke(finishResult, unFinishResult)
            }
        }
    }

}