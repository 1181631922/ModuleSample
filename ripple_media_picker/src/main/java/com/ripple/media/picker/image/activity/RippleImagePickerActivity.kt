package com.ripple.media.picker.image.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ripple.media.picker.R
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.base.RippleBaseActivity
import com.ripple.media.picker.image.ScanImageSource
import com.ripple.media.picker.image.adapter.RippleFolderAdapter
import com.ripple.media.picker.image.adapter.RippleImageAdapter
import com.ripple.media.picker.model.RippleFolderModel
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.util.LogUtil
import kotlinx.android.synthetic.main.activity_ripple_image_picker.*


class RippleImagePickerActivity : RippleBaseActivity(), ScanImageSource.ImageSourceListener {

    companion object {
        private val TAG = RippleImagePickerActivity.javaClass.simpleName
        private val LINE = 3
        val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 101
    }

    private lateinit var scanImageSource: ScanImageSource
    private var adapter: RippleImageAdapter? = null
    private var list = ArrayList<RippleMediaModel>()

    private var folderAdapter: RippleFolderAdapter? = null
    private var folderList = ArrayList<RippleFolderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_image_picker)

        requestMediaPermission()
    }

    private fun requestMediaPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
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
        rippleFolderRV.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
        (rippleImageRV.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        (rippleFolderRV.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun initData() {
        rippleFolderShape.setOnClickListener {
            setRippleFolderRV()
        }

    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        toolbar?.navigationIcon = null
        toolbarCenterTitle!!.setOnClickListener {
            setRippleFolderRV()
        }
    }

    private fun setRippleFolderRV() {
        //点击切换文件夹
        val isShown = rippleFolderRV.isShown
        if (isShown) {
            rippleFolderRV.visibility = View.GONE
            rippleFolderShape.visibility = View.GONE
        } else {
            rippleFolderRV.visibility = View.VISIBLE
            rippleFolderShape.visibility = View.VISIBLE
        }
    }

    /**
     * 图片加载完成
     */
    override fun onMediaLoaded(mediaList: List<RippleFolderModel>) {
        LogUtil.d(TAG, mediaList.toString())
        if (mediaList.isNotEmpty()) {

            /**
             * 所有图片
             */
            adapter = RippleImageAdapter(this, mediaList[0].getMediaList(), LINE)
            rippleImageRV.adapter = adapter
            adapter?.notifyDataSetChanged()
            toolbarCenterTitle?.text = "所有图片"

            /**
             * 文件夹
             */

            folderAdapter = RippleFolderAdapter(this, mediaList)
            rippleFolderRV.adapter = folderAdapter
            folderAdapter?.notifyDataSetChanged()

            folderAdapter?.onItemListener = { model, _, position ->

                adapter = RippleImageAdapter(this, mediaList[position].getMediaList(), LINE)
                rippleImageRV.adapter = adapter
                toolbarCenterTitle?.text = model.getName()
                setRippleFolderRV()
            }
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

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(msg = RippleMediaPick.getInstance().imageList.toString())
    }
}
