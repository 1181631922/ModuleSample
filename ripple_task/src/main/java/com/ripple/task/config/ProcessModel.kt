package com.ripple.task.config

import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/6/3 19:15
 * Email: fanyafeng@live.cn
 * Description:
 */
interface ProcessModel : Serializable {

    companion object {
        const val PROCESS_ITEM = "process_item"
        const val PROCESS_LIST = "process_list"
    }

    /**
     * 获取需要处理的源路径
     */
    fun getSourcePath(): String

    /**
     * 目标路径
     */
    fun getTargetPath(): String?

    /**
     * 处理后的目标路径不能为空
     */
    fun setTargetPath(target: String)

    /**
     * 任务解析器
     * 这里按道理说如果有了targetPath那么这个返回值是可以不需要的
     * 但是就是因为如果你去处理一个任务但是有规则没有输出那么这个返回值就是必须的了
     * 而且不能为空
     *
     * 分为以下两种以下两种情况：
     * 1.处理文件类
     * @param sourcePath 文件原路径
     * @param targetPath 文件目标路径
     *
     * 2.有处理规则，和原路径，那么方法的返回值就是处理结果
     * @param sourcePath 文件原路径
     *
     */
    fun parse(sourcePath: String, targetPath: String?): String
}