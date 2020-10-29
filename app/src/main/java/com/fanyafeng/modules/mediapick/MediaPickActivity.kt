package com.fanyafeng.modules.mediapick

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fanyafeng.modules.R
import com.fanyafeng.modules.dealpicture.DownloadPicture
import com.fanyafeng.modules.dealpicture.impl.Base64DownloadPicture
import com.fanyafeng.modules.dealpicture.impl.FrescoDownloadPicture
import com.fanyafeng.modules.dealpicture.impl.GlideDownloadPicture
import com.fanyafeng.modules.dealpicture.impl.Test
import com.ripple.image.compress.config.CompressConfig
import com.ripple.image.compress.config.impl.SimpleCompressConfig
import com.ripple.image.compress.extend.compressImageList
import com.ripple.image.compress.model.ImageItem
import com.ripple.log.tpyeextend.toLogD
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.config.CropImageConfig
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.config.impl.CropImageConfigImpl
import com.ripple.media.picker.config.impl.ImagePickConfig
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.image.activity.RippleImagePickerActivity
import com.ripple.media.picker.image.extend.imagePick
import com.ripple.media.picker.image.extend.takePhoto
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.model.SimpleImageModel
import com.ripple.permission.annotation.NeedPermission
import com.ripple.tool.screen.getScreenHeight
import com.ripple.tool.screen.getScreenWidth
import kotlinx.android.synthetic.main.activity_media_pick.*
import java.io.File

class MediaPickActivity : AppCompatActivity() {

    companion object {
        private val TAG = MediaPickActivity::class.java.simpleName
    }

    private var testList = mutableListOf<MyMediaModel>()

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
                it.forEach { item ->
                    testList.add(MyMediaModel(item.getPath()))
                }

                compressImageList(testList) {
                    onFinish { finishResult, unFinishResult ->
                        finishResult.toLogD()
                        val targetDir = finishResult!![0].getCompressConfig().getTargetDir()
                        val targetPath = finishResult!![0].getCompressConfig().getTargetPath()
                        val filePath = File(targetDir, targetPath).absolutePath.toLogD()


                    }
                }
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

        mediaPick8.setOnClickListener {
//            val intent = Intent(this, RippleCropImageActivity::class.java)
//            intent.putExtra(
//                CropImageConfig.CROP_IMAGE_URL,
//                "/storage/emulated/0/Pictures/Screenshots/Screenshot_20201018_130411_com.huawei.android.launcher.jpg"
//            )
////            intent.putExtra(CropImageConfig.CROP_IMAGE_CONFIG, CropImageConfigImpl())
//            startActivity(intent)

//            RippleImagePick.getInstance().imageCrop(
//                this,
//                "/storage/emulated/0/Pictures/Screenshots/Screenshot_20201018_130411_com.huawei.android.launcher.jpg",
//            )

            val cropLength =
                getScreenHeight().coerceAtMost(getScreenWidth())
            val config = CropImageConfigImpl(
                inWidth = cropLength,
                inHeight = cropLength,
                outWidth = cropLength,
                outHeight = cropLength
            )
            RippleImagePick.getInstance()
                .imageCrop(this, config, CropImageConfig.CROP_IMAGE_REQUEST_CODE)
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
                CropImageConfig.CROP_IMAGE_REQUEST_CODE -> {
                    Log.d(
                        "result返回的裁剪图片的保存路径:",
                        (data!!.getSerializableExtra(CropImageConfig.CROP_IMAGE_REQUEST_RESULT) as File).absolutePath
                    )
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}

class MyMediaModel(var imagePath: String) : RippleMediaModel, SimpleImageModel, ImageItem {
    override fun getPath(): String {
        return imagePath
    }

    override fun getSourcePath(): String {
        return imagePath
    }


    override fun getCompressConfig(): CompressConfig {
        return SimpleCompressConfig.Builder().create()
    }

    override fun getTag(): Any? {
        return null
    }

}
