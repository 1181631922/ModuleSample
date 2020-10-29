package com.ripple.media.picker.image.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.ripple.media.picker.R
import com.ripple.media.picker.base.RippleBaseActivity
import com.ripple.media.picker.config.CropImageConfig
import com.ripple.media.picker.config.impl.CropImageConfigImpl
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.util.screenHeight
import com.ripple.media.picker.util.screenwidth
import com.ripple.media.picker.view.CropImageView
import kotlinx.android.synthetic.main.activity_ripple_crop_image.*
import java.io.File

class RippleCropImageActivity : RippleBaseActivity(), CropImageView.OnBitmapSaveCompleteListener {

    private var currBitmap: Bitmap? = null

    private var cropImageConfig: CropImageConfig? = null
    private var cropImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_crop_image)
        cropImageConfig =
            if (intent.getSerializableExtra(CropImageConfig.CROP_IMAGE_CONFIG) != null) {
                intent.getSerializableExtra("crop_image_config") as CropImageConfig
            } else {
                val cropLength = screenHeight().coerceAtMost(screenwidth())
                CropImageConfigImpl(
                    inWidth = cropLength,
                    inHeight = cropLength,
                    outWidth = cropLength,
                    outHeight = cropLength
                )
            }
        if (intent.getStringExtra(CropImageConfig.CROP_IMAGE_URL) != null) {
            cropImageUrl = intent.getStringExtra(CropImageConfig.CROP_IMAGE_URL)
        } else {
            Toast.makeText(this, "请选择需要裁剪的图片!!!", Toast.LENGTH_SHORT).show()
            finish()
        }
        initView()
        initData()
    }

    private fun initView() {
        cropImageView.setOnBitmapSaveCompleteListener(this)
        val cropLength = screenHeight().coerceAtMost(screenwidth())
        cropImageView.inWidth = cropImageConfig?.getInWidth() ?: cropLength
        cropImageView.inHeight = cropImageConfig?.getInHeight() ?: cropLength
        cropImageView.focusStyle =
            cropImageConfig?.getCropImageStyle() ?: CropImageView.Style.RECTANGLE
    }

    override fun onResume() {
        super.onResume()
        toolbarCenterTitle?.text = "裁剪图片"
        toolbarRightTitle?.setOnClickListener {
            val cropLength = screenHeight().coerceAtMost(screenwidth())
            cropImageView.saveBitmapToFile(
                cropImageConfig?.getCropCacheFolder()
                    ?: if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                        File(Environment.getExternalStorageDirectory(), "/DCIM/camera/")
                    } else {
                        Environment.getDataDirectory()
                    }, cropImageConfig?.getOutWidth() ?: cropLength,
                cropImageConfig?.getOutHeight() ?: cropLength,
                (cropImageConfig?.getCropImageStyle()
                    ?: CropImageView.Style.RECTANGLE) == CropImageView.Style.RECTANGLE
            )
        }
    }

    private fun initData() {
        if (cropImageUrl.isNullOrBlank()) {
            finish()
        } else {
            loadPicture(cropImageUrl!!)
        }
    }

    private fun loadPicture(picUrl: String) {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(picUrl, options)
        val displayMetrics = resources.displayMetrics
        options.inSampleSize =
            calculateInSampleSize(options, displayMetrics.widthPixels, displayMetrics.heightPixels)
        options.inJustDecodeBounds = false
        currBitmap = BitmapFactory.decodeFile(picUrl, options)
        cropImageView.rotate(
            currBitmap,
            getBitmapDegree(picUrl)
        )?.let {
            cropImageView.setImageBitmap(
                it
            )
        }

    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val width = options.outWidth
        val height = options.outHeight
        var inSampleSize = 1
        inSampleSize = if (height > reqHeight) {
            width / reqHeight
        } else {
            height / reqHeight
        }
        return inSampleSize
    }

    private fun getBitmapDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    degree = 90
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    degree = 180
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    degree = 270
                }
            }
        } catch (e: Exception) {
//            e.printStackTrace()
            degree = 0
        }
        return degree
    }

    /**
     * 裁剪图片保存成功
     * 需要进行回调
     */
    override fun onBitmapSaveSuccess(file: File?) {
        Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show()
        val listener = RippleImagePick.getInstance().imageCropListener
        if (listener != null) {
            listener.onImageCropSaveResult(file)
            RippleImagePick.getInstance().imageCropListener = null
        }
        val intent = Intent()
        intent.putExtra(CropImageConfig.CROP_IMAGE_REQUEST_RESULT, file)
        setResult(RESULT_OK, intent)
        finish()
    }

    /**
     * 裁剪图片保存失败
     * 需要进行回调
     */
    override fun onBitmapSaveError(file: File?) {
        val listener = RippleImagePick.getInstance().imageCropListener
        if (listener != null) {
            listener.onImageCropSaveResult(null)
            RippleImagePick.getInstance().imageCropListener = null
        }
        finish()
    }
}