package com.fanyafeng.modules.mediapick

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.fanyafeng.modules.R
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.config.MediaPickConfig
import com.ripple.media.picker.config.impl.ImagePickConfig
import com.ripple.media.picker.image.activity.RippleImagePickerActivity
import com.ripple.permission.annotation.NeedPermission

class MediaPickActivity : AppCompatActivity() {

    companion object {
        private val TAG = MediaPickActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_pick)
        title = "媒体库选择"

        initView()
        initData()
    }

    private fun initView() {
        val config = ImagePickConfig.Builder().setCount(9).setSize(100L)
            .setChooseType(MediaPickConfig.ChooseType.MULTIPLE_CHOOSE_TYPE).build()

        Log.d(TAG, "数量：" + config.getSize())
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
