package com.fanyafeng.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ripple.dialog.widget.impl.RippleToast
import com.ripple.log.tpyeextend.toLogD
import com.ripple.tool.date.long2Date

open class BaseActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "http://10.12.16.198:8080"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toLogD("页面显示：" + System.currentTimeMillis().long2Date())
    }

    fun showToast(message: CharSequence) {
        RippleToast.show(this, message)
    }
}
