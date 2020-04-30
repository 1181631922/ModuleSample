package com.ripple.media.picker.image.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ripple.media.picker.R
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.base.RippleBaseActivity
import com.ripple.media.picker.camera.PictureGalleryUtil
import com.ripple.media.picker.camera.TakePicture
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.image.RippleImagePick


class RippleTakePhotoAgencyActivity : RippleBaseActivity() {

    companion object {
        private const val REQUEST_CODE_CAMERA_AND_STORAGE = 1000

        private const val REQUEST_CODE_CAMERA = 1001

        private const val REQUEST_CODE_STORAGE = 1002

        private const val TAKE_PHOTO_REQUEST_CODE = 1313
    }

    private var config: IImagePickConfig = RippleMediaPick.getInstance().imagePickConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_take_photo_agency)

        if (intent.getSerializableExtra(IImagePickConfig.IMAGE_CONFIG_NAME) != null) {
            config =
                intent.getSerializableExtra(IImagePickConfig.IMAGE_CONFIG_NAME) as IImagePickConfig
        }

        requestMediaPermission()
    }

    private fun requestMediaPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    REQUEST_CODE_CAMERA_AND_STORAGE
                )
            } else if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_STORAGE
                )
            } else if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.CAMERA),
                    REQUEST_CODE_CAMERA
                )
            } else {
                TakePicture(config).openCamera(
                    this,
                    TAKE_PHOTO_REQUEST_CODE
                )
            }
        } else {
            TakePicture(config).openCamera(
                this,
                TAKE_PHOTO_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_CAMERA_AND_STORAGE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                TakePicture(config).openCamera(
                    this,
                    TAKE_PHOTO_REQUEST_CODE
                )
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                finish()
            }
            REQUEST_CODE_CAMERA -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                TakePicture(config).openCamera(
                    this,
                    TAKE_PHOTO_REQUEST_CODE
                )
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                finish()
            }
            REQUEST_CODE_STORAGE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                TakePicture(config).openCamera(
                    this,
                    TAKE_PHOTO_REQUEST_CODE
                )
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TAKE_PHOTO_REQUEST_CODE -> {
                    val listener = RippleImagePick.getInstance().takePhotoListener
                    if (listener != null) {
                        listener.onTakePhotoListener(RippleImagePick.getInstance().takePictureFile)

                        PictureGalleryUtil.updatePictureGallery(
                            this,
                            RippleImagePick.getInstance().takePictureFile
                        )
                        RippleImagePick.getInstance().takePhotoListener = null
                    }
                    val intent = Intent()
                    intent.putExtra(
                        IImagePickConfig.TAKE_PHOTO_PATH,
                        RippleImagePick.getInstance().takePictureFile?.absolutePath
                    )
                    setResult(Activity.RESULT_OK, intent)
                }
            }
        }
        finish()
    }

}
