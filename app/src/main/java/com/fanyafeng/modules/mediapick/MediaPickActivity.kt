package com.fanyafeng.modules.mediapick

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fanyafeng.modules.R
import com.ripple.media.picker.image.activity.RippleImagePickerActivity
import com.ripple.permission.annotation.NeedPermission

class MediaPickActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_pick)
        title = "媒体库选择选择"

        initView()
        initData()
    }

    private fun initView() {
    }

    private fun initData() {
        swipePic()
    }

    @NeedPermission(permissions = [Manifest.permission.WRITE_EXTERNAL_STORAGE], method = "onFail")
    private fun swipePic() {
        startActivity(Intent(this, RippleImagePickerActivity::class.java))
    }

    private fun onFail() {

    }
}
