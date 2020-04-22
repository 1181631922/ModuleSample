package com.ripple.media.picker.image.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.ripple.media.picker.R
import com.ripple.media.picker.base.RippleBaseActivity
import com.ripple.media.picker.image.ScanImageSource
import com.ripple.media.picker.image.adapter.RippleImageAdapter
import com.ripple.media.picker.model.RippleFolderModel
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.util.LogUtil
import kotlinx.android.synthetic.main.activity_ripple_image_picker.*


class RippleImagePickerActivity : RippleBaseActivity(), ScanImageSource.ImageSourceListener {

    companion object {
        private val TAG = RippleImagePickerActivity.javaClass.simpleName
        private val LINE = 4
        val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 101
    }

    private lateinit var scanImageSource: ScanImageSource
    private var adapter: RippleImageAdapter? = null
    private var list = ArrayList<RippleMediaModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_image_picker)

//        initView()
//        initData()
        requestMediaPermission()
    }

    private fun requestMediaPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE
                )
            } else {
                operateData()
            }
        } else {
            operateData()
        }
    }

    private fun operateData() {
        initView()
        initData()
    }

    private fun initView() {
        scanImageSource = ScanImageSource(this, null, this)
        rippleImageRV.layoutManager =
            GridLayoutManager(this, LINE, GridLayoutManager.VERTICAL, false)
    }

    private fun initData() {

    }

    /**
     * 图片加载完成
     */
    override fun onMediaLoaded(mediaList: List<RippleFolderModel>) {
        LogUtil.d(TAG, mediaList.toString())
        if (mediaList.isNotEmpty()) {
            list.addAll(mediaList[0].getMediaList())
            adapter = RippleImageAdapter(this, list, LINE)
            rippleImageRV.adapter = adapter
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_WRITE_EXTERNAL_STORAGE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                operateData()
            } else {
                Toast.makeText(
                    this@RippleImagePickerActivity,
                    "Permission Denied",
                    Toast.LENGTH_SHORT
                )
                    .show()
                finish()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
