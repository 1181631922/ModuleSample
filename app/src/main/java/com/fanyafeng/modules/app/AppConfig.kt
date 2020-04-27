package com.fanyafeng.modules.app

import android.app.Application
import com.fanyafeng.modules.mediapick.config.RippleImageLoadFrameImpl
import com.ripple.media.picker.RippleMediaPick
import com.ripple.permission.RipplePermissionImpl

/**
 * Author: fanyafeng
 * Data: 2020/4/26 17:30
 * Email: fanyafeng@live.cn
 * Description:
 */
class AppConfig : Application() {
    override fun onCreate() {
        super.onCreate()
        RipplePermissionImpl.init(this)
        RippleMediaPick
            .getInstance()
            .setImageLoadFrame(RippleImageLoadFrameImpl())
    }
}