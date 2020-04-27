package com.fanyafeng.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fanyafeng.modules.mediapick.config.RippleImageLoadFrameImpl
import com.ripple.media.picker.RippleMediaPick
import com.ripple.permission.RipplePermissionImpl

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
