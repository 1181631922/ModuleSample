package com.ripple.image.compress.config.impl

import android.os.Environment
import com.ripple.image.compress.config.CompressConfig
import com.ripple.image.compress.config.CompressOption
import com.ripple.task.util.Preconditions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: fanyafeng
 * Data: 2020/5/6 17:16
 * Email: fanyafeng@live.cn
 * Description: 压缩配置的简单实现封装
 */
class SimpleCompressConfig private constructor(builder: Builder) : CompressConfig {

    private var targetDir: File? = null

    private var targetPath: String? = null

    private var compressOption: CompressOption? = null

    init {
        this.targetDir = builder.targetDir
        this.targetPath = builder.targetPath
        this.compressOption = builder.compressOption
    }

    override fun getTargetDir(): File {
        return Preconditions.checkNotNull(targetDir)
    }

    override fun getTargetPath(): String {
        return Preconditions.checkNotNull(targetPath)
    }

    override fun getCompressOption(): CompressOption {
        return Preconditions.checkNotNull(compressOption)
    }

    class Builder {
        var targetDir: File? = null
            set(value) {
                field = value
            }

        var targetPath: String? = null
            private set(value) {
                field = value
            }

        var compressOption: CompressOption? = null
            private set(value) {
                field = value
            }

        fun setTargetDir(targetDir: File): Builder {
            this.targetDir = targetDir
            return this
        }

        fun setTargetPath(targetPath: String): Builder {
            this.targetPath = targetPath
            return this
        }

        fun setCompressOption(compressOption: CompressOption): Builder {
            this.compressOption = compressOption
            return this
        }

        fun build(): SimpleCompressConfig {
            return SimpleCompressConfig(this)
        }

        fun create(): SimpleCompressConfig {
            val targetDir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
                File(Environment.getExternalStorageDirectory(), "/DCIM/camera/")
            else
                Environment.getDataDirectory()
            val dateFormat = SimpleDateFormat("yyyyMMdd-", Locale.CHINA)
            val targetPath =
                "IMG-" + dateFormat.format(Date(System.currentTimeMillis()))
                    .toString() + UUID.randomUUID() + ".jpg"
//            val targetPath = File(targetDir, filename).absolutePath

            return Builder()
                .setCompressOption(CompressOption.getLD())
                .setTargetDir(targetDir)
                .setTargetPath(targetPath)
                .build()
        }
    }

}