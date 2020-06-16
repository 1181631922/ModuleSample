package com.ripple.task.config.impl

import com.ripple.task.config.ProcessModel

/**
 * Author: fanyafeng
 * Data: 2020/6/4 16:55
 * Email: fanyafeng@live.cn
 * Description:
 */
class ProcessModelImpl : ProcessModel<String, String> {
    override fun getSourcePath(): String {
        return ""
    }

    override fun getTargetPath(): String? {
        return ""
    }

    override fun setTargetPath(target: String) {
    }

    override fun parse(sourcePath: String, targetPath: String?): String {
        return ""
    }

}