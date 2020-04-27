package com.ripple.media.picker.image.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ripple.media.picker.R
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.base.RippleBaseActivity
import com.ripple.media.picker.camera.PictureGalleryUtil
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.image.RippleImagePick
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

    private val handler = Handler()
    private var scanImageSource: ScanImageSource? = null
    private var adapter: RippleImageAdapter? = null
    private var list = ArrayList<RippleMediaModel>()

    private var folderAdapter: RippleFolderAdapter? = null
    private var folderList = ArrayList<RippleFolderModel>()

    private var config: IImagePickConfig = RippleMediaPick.getInstance().imagePickConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_image_picker)

        config = if (intent.getSerializableExtra(IImagePickConfig.IMAGE_CONFIG_NAME) != null) {
            intent.getSerializableExtra(IImagePickConfig.IMAGE_CONFIG_NAME) as IImagePickConfig
        } else {
            RippleMediaPick.getInstance().imagePickConfig
        }

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

    private fun loadData() {
        ScanImageSource(this, null, object : ScanImageSource.ImageSourceListener {
            override fun onMediaLoaded(mediaList: List<RippleFolderModel>) {
                LogUtil.d(TAG, mediaList.toString())
                if (mediaList.isNotEmpty()) {
                    if (reloadPicture) {
                        LogUtil.d("picture:", msg = "用户拍照新添加了一张图片")
                        val model = mediaList[0].getMediaList()[0]
                        LogUtil.d(
                            "picture:",
                            "新拍的图：" + takePicturePath + ";第一个图：" + model.getPath()
                        )
                        model.setCheck(true)
                        model.setTag(RippleMediaPick.getInstance().imageList.size + 1)
                        RippleMediaPick.getInstance().imageList.add(model)
                        reloadPicture = false
                    }

                    val selectSize = RippleMediaPick.getInstance().imageList.size

                    runOnUiThread {

                    }

                    /**
                     * 所有图片
                     */
                    adapter =
                        RippleImageAdapter(
                            this@RippleImagePickerActivity,
                            mediaList[0].getMediaList(),
                            config = config,
                            line = LINE
                        )
                    rippleImageRV.adapter = adapter
                    adapter?.notifyDataSetChanged()
                    toolbarCenterTitle?.text = "所有图片"
                    LogUtil.d("picture:", msg = "size:" + selectSize + ";所有图片刷新")

                    /**
                     * 文件夹
                     */

                    folderAdapter = RippleFolderAdapter(this@RippleImagePickerActivity, mediaList)
                    rippleFolderRV.adapter = folderAdapter
                    folderAdapter?.notifyDataSetChanged()
                    LogUtil.d("picture:", msg = "size:" + selectSize + ";文件夹刷新")

                    folderAdapter?.onItemListener = { model, _, position ->

                        adapter = RippleImageAdapter(
                            this@RippleImagePickerActivity,
                            mediaList[position].getMediaList(), config = config, line = LINE
                        )
                        rippleImageRV.adapter = adapter
                        toolbarCenterTitle?.text = model.getName()
                        setRippleFolderRV()
                        adapter?.itemClickListener = { view, model, position ->
                            setRightTitle(RippleMediaPick.getInstance().imageList.size)
                        }
                    }

                    adapter?.itemClickListener = { view, model, position ->
                        setRightTitle(RippleMediaPick.getInstance().imageList.size)
                    }

                    setRightTitle(RippleMediaPick.getInstance().imageList.size)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        updateData()
        setRightTitle(0)

        adapter?.notifyDataSetChanged()
    }

    private fun updateData() {
        toolbar?.navigationIcon = null
        toolbarCenterTitle!!.setOnClickListener {
            setRippleFolderRV()
        }

        toolbarRightTitle?.setOnClickListener {
            val imageList = RippleMediaPick.getInstance().imageList
            if (imageList.size > 0) {
                val listener = RippleImagePick.getInstance().selectImageListListener
                if (listener != null) {
                    listener.selectImageList(imageList)
                    RippleImagePick.getInstance().selectImageListListener = null
                }

                val intent = Intent()
                intent.putExtra(
                    RippleImagePick.RESULT_IMG_LIST,
                    imageList
                )
                setResult(RESULT_OK, intent)
                finish()
                RippleMediaPick.getInstance().imageList.clear()
            }
        }

        toolbarLeftTitle.text = "取消"
        toolbarLeftTitle.setTextColor(Color.parseColor("#ffffff"))
        toolbarLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        toolbarLeftTitle.setOnClickListener {
            finish()
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

    private fun setRightTitle(count: Int) {
        if (count > 0) {
            toolbarRightTitle?.setTextColor(Color.WHITE)
            toolbarRightTitle?.background =
                resources.getDrawable(R.drawable.ripple_next_step_shape)
            toolbarRightTitle?.text = "下一步($count)"
        } else {
            toolbarRightTitle?.setTextColor(Color.GRAY)
            toolbarRightTitle?.background =
                resources.getDrawable(R.drawable.ripple_next_step_unable_shape)
            toolbarRightTitle?.text = "下一步"
        }

    }

    /**
     * 图片加载完成
     */
    override fun onMediaLoaded(mediaList: List<RippleFolderModel>) {
        LogUtil.d(TAG, mediaList.toString())
        if (mediaList.isNotEmpty()) {
            if (reloadPicture) {
                LogUtil.d("picture:", msg = "用户拍照新添加了一张图片")
                val model = mediaList[0].getMediaList()[0]
                LogUtil.d("picture:", "新拍的图：" + takePicturePath + ";第一个图：" + model.getPath())
                model.setCheck(true)
                model.setTag(RippleMediaPick.getInstance().imageList.size + 1)
                RippleMediaPick.getInstance().imageList.add(model)
                reloadPicture = false
            }

            val selectSize = RippleMediaPick.getInstance().imageList.size

            /**
             * 所有图片
             */
            adapter =
                RippleImageAdapter(
                    this,
                    mediaList[0].getMediaList(),
                    config = config,
                    line = LINE
                )
            rippleImageRV.adapter = adapter
            adapter?.notifyDataSetChanged()
            toolbarCenterTitle?.text = "所有图片"
            LogUtil.d("picture:", msg = "size:" + selectSize + ";所有图片刷新")

            /**
             * 文件夹
             */

            folderAdapter = RippleFolderAdapter(this, mediaList)
            rippleFolderRV.adapter = folderAdapter
            folderAdapter?.notifyDataSetChanged()
            LogUtil.d("picture:", msg = "size:" + selectSize + ";文件夹刷新")

            folderAdapter?.onItemListener = { model, _, position ->

                adapter = RippleImageAdapter(
                    this,
                    mediaList[position].getMediaList(), config = config, line = LINE
                )
                rippleImageRV.adapter = adapter
                toolbarCenterTitle?.text = model.getName()
                setRippleFolderRV()
                adapter?.itemClickListener = { view, model, position ->
                    setRightTitle(RippleMediaPick.getInstance().imageList.size)
                }
            }

            adapter?.itemClickListener = { view, model, position ->
                setRightTitle(RippleMediaPick.getInstance().imageList.size)
            }

            setRightTitle(RippleMediaPick.getInstance().imageList.size)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IImagePickConfig.TAKE_PICTURE_CODE -> {
                    if (RippleImagePick.getInstance().takePictureFile != null) {
                        PictureGalleryUtil.updatePictureGallery(
                            this,
                            RippleImagePick.getInstance().takePictureFile!!
                        )
                        takePicturePath =
                            RippleImagePick.getInstance().takePictureFile?.absolutePath
                        MediaScannerConnection.scanFile(
                            this,
                            arrayOf(takePicturePath),
                            null
                        ) { path, uri ->
                            handler.post {
                                scanImageSource?.reloadAll()
                            }
                        }
                        reloadPicture = true
                        RippleImagePick.getInstance().takePictureFile = null
                    }
                }
            }
        }
    }

    private var takePicturePath: String? = null

    private var reloadPicture = false

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(msg = RippleMediaPick.getInstance().imageList.toString())
    }

    override fun onStop() {
        super.onStop()
        if (isFinishing && scanImageSource != null) {
            scanImageSource?.recycle();
            scanImageSource = null;
        }
    }
}
