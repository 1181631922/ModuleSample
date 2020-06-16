package com.fanyafeng.modules.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.fanyafeng.modules.R
import com.ripple.task.callback.result.OnAllResult
import com.ripple.task.config.ProcessModel
import com.ripple.task.engine.ProcessEngine
import com.ripple.task.engine.ScheduledProcessEngine
import com.ripple.task.extend.handleTaskList
import com.ripple.task.task.impl.ProcessTaskImpl
import com.ripple.task.task.impl.ScheduledProcessTaskImpl
import kotlinx.android.synthetic.main.activity_handle_task.*
import java.util.concurrent.TimeUnit

class HandleTaskActivity : AppCompatActivity() {

    companion object {
        private val TAG = HandleTaskActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_task)
        title = "多任务处理"

        initView()
        initData()
    }

    private fun initView() {
        btn1.setOnClickListener {
            handleTaskList(
                listOf(
                    Task1("abdaafda"),
                    Task2("不会输出"),
                    Task3("abdaafda又变大写了")
                )
            ) {

                onItemFinish {
                    println("单个成功结果回调：" + it.toString())
                }

                onFinish { finishResult, unFinishResult ->
                    println("成功结果回调：" + finishResult.toString())
                    println("失败结果回调：" + unFinishResult.toString())
                }
            }
        }

//        val scheduleTask = Executors.newScheduledThreadPool(3)
        val eng = ScheduledProcessEngine.SINGLE_THREAD_EXECUTOR

        val scheduleTask = ScheduledProcessTaskImpl(eng)

        btn2.setOnClickListener {
            scheduleTask.scheduleAtFixedRate({
                handleTaskList(
                    listOf(
                        Task1("abdaafda"),
                        Task2("不会输出"),
                        Task3("abdaafda又变大写了")
                    )
                ) {

                    onItemFinish {

                    }

                    onFinish { finishResult, unFinishResult ->
                        println("成功结果回调：" + finishResult.toString())
                        println("失败结果回调：" + unFinishResult.toString())
                    }
                }
            }, 0L, 1L, TimeUnit.SECONDS)

        }

        btn4.setOnClickListener {
            eng.shutdown()
        }

        btn3.setOnClickListener {
            val executor = ProcessEngine.SINGLE_THREAD_EXECUTOR
            val task = ProcessTaskImpl<String, String>(executor)
            task.onAllResult = object : OnAllResult<List<ProcessModel<String, String>>> {
                override fun onFinish(
                    finishResult: List<ProcessModel<String, String>>?,
                    unFinishResult: List<ProcessModel<String, String>>?
                ) {
                    println("成功结果回调：" + finishResult.toString())
                    println("失败结果回调：" + unFinishResult.toString())
                }


            }
            task.handleTaskList(
                listOf(
                    Task2("不会输出"),
                    Task1("abdaafda"),
                    Task3("abdaafda又变大写了"),
                    Task2("不会输出"),
                    Task1("abdaafda"),
                    Task2("不会输出"),
                    Task3("abdaafda又变大写了")
                )
            )

            Handler().postDelayed({
                executor.shutdown()
            }, 300)
        }


    }

    private fun initData() {

    }
}

data class Task1 @JvmOverloads constructor(
    private val sourcePath: String,
    private var targetPath: String? = null
) : ProcessModel<String, String> {
    override fun getSource(): String {
        return sourcePath
    }

    override fun getTarget(): String? {
        return targetPath
    }

    override fun setTarget(target: String) {
        this.targetPath = target
    }

    override fun parse(source: String, target: String?): String {
        return source.toUpperCase()
    }
}

data class Task2 @JvmOverloads constructor(
    private val sourcePath: String,
    private var targetPath: String = "任务2目标路径"
) : ProcessModel<String, String> {
    override fun getSource(): String {
        return sourcePath
    }

    override fun getTarget(): String? {
        return targetPath
    }

    override fun setTarget(target: String) {
        this.targetPath = target
    }

    override fun parse(source: String, target: String?): String {
        Thread.sleep(2000)
        return "我是任务2$target"
    }

}

data class Task3 @JvmOverloads constructor(
    private val sourcePath: String,
    private var targetPath: String = "任务三目标路径"
) : ProcessModel<String, String> {
    override fun getSource(): String {
        return sourcePath
    }

    override fun getTarget(): String? {
        return targetPath
    }

    override fun setTarget(target: String) {
        this.targetPath = target
    }

    override fun parse(source: String, target: String?): String {
        Thread.sleep(3000)
        return source.toUpperCase() + "在来个任务3一起走"
    }

}
