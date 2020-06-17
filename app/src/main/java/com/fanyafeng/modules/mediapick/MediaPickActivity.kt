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
import com.fanyafeng.modules.dealpicture.DownloadPicture
import com.fanyafeng.modules.dealpicture.impl.Base64DownloadPicture
import com.fanyafeng.modules.dealpicture.impl.FrescoDownloadPicture
import com.fanyafeng.modules.dealpicture.impl.GlideDownloadPicture
import com.fanyafeng.modules.dealpicture.impl.Test
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.config.MediaPickConfig
import com.ripple.media.picker.config.impl.ImagePickConfig
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.image.activity.RippleImagePickerActivity
import com.ripple.media.picker.image.activity.RippleTakePhotoAgencyActivity
import com.ripple.media.picker.image.extend.imagePick
import com.ripple.media.picker.image.extend.takePhoto
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

        mediaPick3.setOnClickListener {
            RippleImagePick.getInstance().takePhoto(this, 10001)
        }

        mediaPick4.setOnClickListener {
            takePhoto {
                Log.d("拍照后的路径：", it?.absolutePath ?: "为获取到图片")
            }
        }

        mediaPick5.setOnClickListener {
            GlideDownloadPicture().download(
                this,
                "https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2534506313,1688529724&fm=26&gp=0.jpg",
                null,
                object : DownloadPicture.SimpleResultCallBack {
                    override fun onSuccess() {
                        super.onSuccess()

                    }
                })
        }

        mediaPick6.setOnClickListener {
            FrescoDownloadPicture().download(
                this,
                "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3252521864,872614242&fm=26&gp=0.jpg",
                null,
                object : DownloadPicture.SimpleResultCallBack {

                })
        }

        mediaPick7.setOnClickListener {
            Base64DownloadPicture().download(
                this,
                Test.test,
                null,
                object : DownloadPicture.SimpleResultCallBack {
                    override fun onSuccess() {

                    }
                }
            )
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
            when (requestCode) {
                10000 -> {
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
                10001 -> {
                    Log.d(
                        "result返回的拍照路径:",
                        data!!.getStringExtra(IImagePickConfig.TAKE_PHOTO_PATH)
                    )
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
