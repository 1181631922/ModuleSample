package com.fanyafeng.modules.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fanyafeng.modules.R
import com.ripple.task.callback.result.OnAllResult
import com.ripple.task.config.ProcessModel
import com.ripple.task.engine.ProcessEngine
import com.ripple.task.extend.handleTaskList
import com.ripple.task.task.impl.ProcessTaskImpl
import kotlinx.android.synthetic.main.activity_handle_task.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class HandleTaskActivity : AppCompatActivity() {

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

                onFinish { finishResult, unFinishResult ->
                    println("结果回调" + finishResult.toString())

                    println(unFinishResult.toString())
                }
            }
        }

        btn2.setOnClickListener {
            Executors.newScheduledThreadPool(3).scheduleAtFixedRate({
                handleTaskList(
                    listOf(
                        Task1("abdaafda"),
                        Task2("不会输出"),
                        Task3("abdaafda又变大写了")
                    )
                ) {

                    onFinish { finishResult, unFinishResult ->
                        println("结果回调" + finishResult.toString())

                        println(unFinishResult.toString())
                    }
                }
            }, 0L, 1L, TimeUnit.SECONDS)
        }


    }

    private fun initData() {

    }
}

data class Task1 @JvmOverloads constructor(
    private val sourcePath: String,
    private var targetPath: String = ""
) : ProcessModel {
    override fun getSourcePath(): String {
        return sourcePath
    }

    override fun getTargetPath(): String? {
        return targetPath
    }

    override fun setTargetPath(target: String) {
        this.targetPath = target
    }

    override fun parse(sourcePath: String, targetPath: String?): String {
        return sourcePath.toUpperCase()
    }
}

data class Task2 @JvmOverloads constructor(
    private val sourcePath: String,
    private var targetPath: String = "任务2目标路径"
) : ProcessModel {
    override fun getSourcePath(): String {
        return sourcePath
    }

    override fun getTargetPath(): String? {
        return targetPath
    }

    override fun setTargetPath(target: String) {
        this.targetPath = target
    }

    override fun parse(sourcePath: String, targetPath: String?): String {

        return "我是任务2$targetPath"
    }

}

data class Task3 @JvmOverloads constructor(
    private val sourcePath: String,
    private var targetPath: String = "任务三目标路径"
) : ProcessModel {
    override fun getSourcePath(): String {
        return sourcePath
    }

    override fun getTargetPath(): String? {
        return targetPath
    }

    override fun setTargetPath(target: String) {
        this.targetPath = target
    }

    override fun parse(sourcePath: String, targetPath: String?): String {

        return sourcePath.toUpperCase() + "在来个任务3一起走"
    }

}
