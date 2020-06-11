package com.ripple.task.task.impl

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.ripple.task.callback.*
import com.ripple.task.callback.result.OnAllResult
import com.ripple.task.callback.result.OnItemResult
import com.ripple.task.config.ProcessModel
import com.ripple.task.engine.ProcessEngine
import com.ripple.task.task.ProcessAllResultTask
import com.ripple.task.task.ProcessItemResultTask
import com.ripple.task.task.ProcessTask
import java.lang.ref.WeakReference
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Future

/**
 * Author: fanyafeng
 * Data: 2020/6/3 19:40
 * Email: fanyafeng@live.cn
 * Description:
 */
class ProcessTaskImpl @JvmOverloads constructor(
    /**
     * 多线程任务请求
     */
    private var handleProcessEngine: ProcessEngine = ProcessEngine.MULTI_THREAD_EXECUTOR_NORMAL
) : ProcessTask {

    /**
     * 所有任务回调
     */
    var onAllResult: OnAllResult<List<ProcessModel>>? = null

    /**
     * 单个任务回调
     */
    var onItemResult: OnItemResult<ProcessModel>? = null

    private var countDownLatch: CountDownLatch? = null


    private val handler = ProcessTaskImplHandler(this)

    private val failedResultList = arrayListOf<ProcessModel>()

    private val successResultList = arrayListOf<ProcessModel>()

    init {
        failedResultList.clear()
        successResultList.clear()
    }


    override fun getAllResult(): OnAllResult<List<ProcessModel>>? {
        return onAllResult
    }

    override fun getItemResult(): OnItemResult<ProcessModel>? {
        return onItemResult
    }

    override fun getProcessEngine(): ProcessEngine {
        return handleProcessEngine
    }

    fun handleTask(process: ProcessModel) {
        handleTaskList(listOf(process))
    }

    fun handleTaskList(processList: List<ProcessModel>) {
        var service = getProcessEngine().getExecutorService()
        if (service.isShutdown) {
            handleProcessEngine = ProcessEngine.MULTI_THREAD_EXECUTOR_NORMAL
            service = handleProcessEngine.getExecutorService()
        }

        countDownLatch = CountDownLatch(processList.size)

        val processAll = object : ProcessAllResultTask<List<ProcessModel>> {
            override fun getCountDownLatch(): CountDownLatch {
                return countDownLatch!!
            }

            override fun getAllResult(): OnAllResult<List<ProcessModel>> {
                return object : OnAllResult<List<ProcessModel>> {

                    override fun onStart() {
                        super.onStart()
                        Message.obtain(handler, OnStart.CODE_START, Bundle()).sendToTarget()
                    }

                    override fun onDoing(doingItem: List<ProcessModel>?) {
                        super.onDoing(doingItem)
                    }

                    override fun onFailed(failedResult: List<ProcessModel>?) {
                        super.onFailed(failedResult)
                        val bundle = Bundle()
                        bundle.putSerializable(
                            OnFailed.RESULT_FAILED,
                            failedResultList
                        )
                        Message.obtain(handler, OnFailed.CODE_FAILED, bundle).sendToTarget()
                    }

                    override fun onSuccess(successResult: List<ProcessModel>?) {
                        super.onSuccess(successResult)
                        val bundle = Bundle()
                        bundle.putSerializable(
                            OnSuccess.RESULT_SUCCESS,
                            successResultList
                        )
                        Message.obtain(handler, OnSuccess.CODE_SUCCESS, bundle).sendToTarget()
                    }

                    override fun onFinish(
                        finishResult: List<ProcessModel>?,
                        unFinishResult: List<ProcessModel>?
                    ) {
                        val bundle = Bundle()
                        bundle.putSerializable(
                            OnSuccess.RESULT_SUCCESS,
                            successResultList
                        )
                        bundle.putSerializable(
                            OnFailed.RESULT_FAILED,
                            failedResultList
                        )
                        Message.obtain(handler, OnFinish.CODE_FINISH, bundle).sendToTarget()
                    }
                }
            }
        }
        val allFuture: Future<*> = service.submit(processAll)

        processList.forEachIndexed { _, processModel ->
            val bundle = Bundle()

            val processItem = object : ProcessItemResultTask<ProcessModel> {

                override fun getProcessModel(): ProcessModel {
                    return processModel
                }

                override fun getCountDownLatch(): CountDownLatch {
                    return countDownLatch!!
                }

                override fun getItemResult(): OnItemResult<ProcessModel>? {
                    return object : OnItemResult<ProcessModel> {

                        override fun onItemStart(startResult: ProcessModel) {
                            super.onItemStart(startResult)
                            bundle.putSerializable(ProcessModel.PROCESS_ITEM, startResult)
                            Message.obtain(handler, OnItemStart.CODE_ITEM_START, bundle)
                                .sendToTarget()
                        }

                        override fun onItemDoing(doingResult: ProcessModel) {
                            super.onItemDoing(doingResult)
                            bundle.putSerializable(ProcessModel.PROCESS_ITEM, doingResult)
                            Message.obtain(handler, OnItemDoing.CODE_ITEM_DOING, bundle)
                                .sendToTarget()

                            bundle.putSerializable(
                                OnDoing.RESULT_DOING,
                                doingResult
                            )
                            Message.obtain(handler, OnDoing.CODE_DOING, bundle).sendToTarget()
                        }

                        override fun onItemInterrupted(interruptedResult: ProcessModel) {
                            super.onItemInterrupted(interruptedResult)
                            bundle.putSerializable(ProcessModel.PROCESS_ITEM, interruptedResult)
                            Message.obtain(handler, OnItemInterrupted.CODE_ITEM_INTERRUPTED, bundle)
                                .sendToTarget()
                        }

                        override fun onItemFailed(failedResult: ProcessModel) {
                            super.onItemFailed(failedResult)
                            failedResultList.add(failedResult)
                            bundle.putSerializable(ProcessModel.PROCESS_ITEM, failedResult)
                            Message.obtain(handler, OnItemFailed.CODE_ITEM_FAILED, bundle)
                                .sendToTarget()
                        }

                        override fun onItemSuccess(successResult: ProcessModel) {
                            super.onItemSuccess(successResult)
                            successResultList.add(successResult)
                            bundle.putSerializable(ProcessModel.PROCESS_ITEM, successResult)
                            Message.obtain(handler, OnItemSuccess.CODE_ITEM_SUCCESS, bundle)
                                .sendToTarget()
                        }

                        override fun onItemFinish(finishResult: ProcessModel) {
                            bundle.putSerializable(ProcessModel.PROCESS_ITEM, finishResult)
                            Message.obtain(handler, OnItemFinish.CODE_ITEM_FINISH, bundle)
                                .sendToTarget()
                        }
                    }
                }
            }
            val itemFuture = service.submit(processItem)
        }
    }

    class ProcessTaskImplHandler(processTask: ProcessTask) :
        Handler(Looper.getMainLooper()) {

        private val weakReference: WeakReference<ProcessTask> = WeakReference(processTask)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val task = weakReference.get()
            task?.let { taskEngine ->
                val bundle = msg.obj as Bundle
                val processItem: ProcessModel
                val successResult: List<ProcessModel>
                val failedResult: List<ProcessModel>
                when (msg.what) {
                    OnItemStart.CODE_ITEM_START -> {
                        bundle.getSerializable(ProcessModel.PROCESS_ITEM)?.let {
                            processItem =
                                it as ProcessModel
                            taskEngine.getItemResult()?.onItemStart(processItem)
                            Log.d("CODE_ITEM_START ITEM", processItem.getSourcePath())
                        }

                    }
                    OnItemDoing.CODE_ITEM_DOING -> {
                        processItem =
                            bundle.getSerializable(ProcessModel.PROCESS_ITEM) as ProcessModel
                        taskEngine.getItemResult()?.onItemDoing(processItem)
                    }
                    OnItemInterrupted.CODE_ITEM_INTERRUPTED -> {
                        processItem =
                            bundle.getSerializable(ProcessModel.PROCESS_ITEM) as ProcessModel
                        taskEngine.getItemResult()?.onItemInterrupted(processItem)
                    }
                    OnItemFailed.CODE_ITEM_FAILED -> {
                        processItem =
                            bundle.getSerializable(ProcessModel.PROCESS_ITEM) as ProcessModel
                        taskEngine.getItemResult()?.onItemFailed(processItem)
                    }

                    OnItemSuccess.CODE_ITEM_SUCCESS -> {
                        processItem =
                            bundle.getSerializable(ProcessModel.PROCESS_ITEM) as ProcessModel
                        taskEngine.getItemResult()?.onItemSuccess(processItem)
                    }

                    OnItemFinish.CODE_ITEM_FINISH -> {
                        processItem =
                            bundle.getSerializable(ProcessModel.PROCESS_ITEM) as ProcessModel
                        taskEngine.getItemResult()?.onItemFinish(processItem)
                    }

                    OnStart.CODE_START -> {
                        taskEngine.getAllResult()?.onStart()
                    }

                    OnDoing.CODE_DOING -> {
                        processItem =
                            bundle.getSerializable(OnDoing.RESULT_DOING) as ProcessModel
                        taskEngine.getAllResult()?.onDoing(listOf(processItem))
                    }
                    OnFailed.CODE_FAILED -> {
                        failedResult =
                            bundle.getSerializable(OnFailed.RESULT_FAILED) as List<ProcessModel>
                        taskEngine.getAllResult()?.onFailed(failedResult)
                    }
                    OnSuccess.CODE_SUCCESS -> {
                        successResult =
                            bundle.getSerializable(OnSuccess.RESULT_SUCCESS) as List<ProcessModel>
                        taskEngine.getAllResult()?.onSuccess(successResult)
                    }
                    OnFinish.CODE_FINISH -> {
                        failedResult =
                            bundle.getSerializable(OnFailed.RESULT_FAILED) as List<ProcessModel>
                        successResult =
                            bundle.getSerializable(OnSuccess.RESULT_SUCCESS) as List<ProcessModel>
                        taskEngine.getAllResult()?.onFinish(successResult, failedResult)
                    }
                    else -> {

                    }
                }

            }
        }

    }


}