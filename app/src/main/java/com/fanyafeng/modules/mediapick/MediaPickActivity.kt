package com.fanyafeng.modules.mediapick

import android.Manifest
import android.app.Activity
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
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.image.activity.RippleImagePickerActivity
import com.ripple.media.picker.image.extend.imagePick
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.permission.annotation.NeedPermission
import kotlinx.android.synthetic.main.activity_media_pick.*

class MediaPickActivity : AppCompatActivity() {

    companion object {
        private val TAG = MediaPickActivity::class.java.simpleName
    }

    private var testList = mutableListOf<RippleMediaModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_pick)
        title = "媒体库选择"

        initView()
        initData()

    }

    private fun initView() {
        mediaPick1.setOnClickListener {
            RippleImagePick.getInstance().imagePickForMulti(this, 10000)
        }

        mediaPick2.setOnClickListener {
            val config = ImagePickConfig.Builder().setSelectList(testList).build()
            imagePick(config) {
                Log.d("选取的图片:", it.toString())
                Log.d("选取的图片数量:", it.size.toString())
                testList.addAll(it)
            }
        }
    }

    private fun initData() {

    }

    @NeedPermission(
        permissions = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE],
        method = "onFail"
    )
    private fun swipePic() {
        RippleMediaPick.getInstance().imageList.clear()
        startActivity(Intent(this, RippleImagePickerActivity::class.java))
    }

    private fun onFail() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10000) {
                Log.d(
                    "result返回的数据:",
                    (data!!.getSerializableExtra(RippleImagePick.RESULT_IMG_LIST))
                        .toString()
                )
                Log.d(
                    "result返回的数据数量:",
                    ((data!!.getSerializableExtra(RippleImagePick.RESULT_IMG_LIST)) as List<RippleMediaModel>).size
                        .toString()
                )
            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
