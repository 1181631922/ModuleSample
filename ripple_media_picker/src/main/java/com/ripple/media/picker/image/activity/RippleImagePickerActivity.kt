package com.ripple.media.picker.image.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
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
import com.ripple.media.picker.config.CropImageConfig
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.config.IPreviewImageConfig
import com.ripple.media.picker.config.MediaThemeConfig
import com.ripple.media.picker.config.impl.ThemeConfig
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
        private const val LINE = 3
        const val REQUEST_CODE = 100
        const val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 101
        const val REQUEST_CODE_CAMERA = 102
    }

    private val handler = Handler()
    private var scanImageSource: ScanImageSource? = null
    private var adapter: RippleImageAdapter? = null
    private var list = ArrayList<RippleMediaModel>()

    private var folderAdapter: RippleFolderAdapter? = null
    private var folderList = ArrayList<RippleFolderModel>()

    private var config: IImagePickConfig = RippleMediaPick.getInstance().imagePickConfig

    private var imageCropConfig: CropImageConfig? = null

    private var closeArrow: Drawable? = null
    private var openArrow: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_image_picker)
        initArrow()

        config = if (intent.getSerializableExtra(IImagePickConfig.IMAGE_CONFIG_NAME) != null) {
            intent.getSerializableExtra(IImagePickConfig.IMAGE_CONFIG_NAME) as IImagePickConfig
        } else {
            RippleMediaPick.getInstance().imagePickConfig
        }

        if (intent.getSerializableExtra(CropImageConfig.CROP_IMAGE_CONFIG) != null) {
            imageCropConfig =
                intent.getSerializableExtra(CropImageConfig.CROP_IMAGE_CONFIG) as CropImageConfig
        }


        RippleMediaPick.getInstance().imageList.addAll(config.getSelectList())

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
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    REQUEST_CODE
                )
            } else if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE
                )
            } else if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CODE_CAMERA
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
        initArrow()
    }

    private fun initData() {
        rippleFolderShape.setOnClickListener {
            setRippleFolderRV()
        }
    }

    private fun initArrow() {
        closeArrow = resources.getDrawable(R.drawable.folder_close_arrow)
        closeArrow?.setBounds(0, 0, closeArrow?.minimumWidth ?: 0, closeArrow?.minimumHeight ?: 0)
        openArrow = resources.getDrawable(R.drawable.folder_open_arrow)
        openArrow?.setBounds(0, 0, openArrow?.minimumWidth ?: 0, openArrow?.minimumHeight ?: 0)
    }

    override fun onResume() {
        super.onResume()
        initArrow()
        updateData()
        setRightTitle(RippleMediaPick.getInstance().imageList.size)
        if (intent.getSerializableExtra(CropImageConfig.CROP_IMAGE_CONFIG) != null) {
            imageCropConfig =
                intent.getSerializableExtra(CropImageConfig.CROP_IMAGE_CONFIG) as CropImageConfig
            toolbarRightTitle?.visibility = View.GONE
        }
        adapter?.notifyDataSetChanged()
    }

    private fun updateData() {
        toolbar?.navigationIcon = null
        toolbarCenterTitle!!.setOnClickListener {
            setRippleFolderRV()
        }
        toolbarCenterTitle?.setCompoundDrawables(null, null, closeArrow, null)

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
            toolbarCenterTitle?.setCompoundDrawables(null, null, closeArrow, null)
        } else {
            rippleFolderRV.visibility = View.VISIBLE
            rippleFolderShape.visibility = View.VISIBLE
            toolbarCenterTitle?.setCompoundDrawables(null, null, openArrow, null)
        }
    }

    private fun setRightTitle(count: Int) {
        if (count > 0) {
            toolbarRightTitle?.setTextColor(Color.WHITE)
            toolbarRightTitle?.text = "确认($count)"
        } else {
            toolbarRightTitle?.setTextColor(Color.GRAY)
            toolbarRightTitle?.text = "确认"
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
            adapter?.imageCropConfig = imageCropConfig
            adapter?.themeConfig = themeConfig
            rippleImageRV.adapter = adapter
            adapter?.notifyDataSetChanged()
            toolbarCenterTitle?.text = "所有图片"
//            LogUtil.d("picture:", msg = "size:$selectSize;所有图片刷新")

            /**
             * 文件夹
             */
            folderAdapter = RippleFolderAdapter(this, mediaList)
            rippleFolderRV.adapter = folderAdapter
            folderAdapter?.notifyDataSetChanged()
//            LogUtil.d("picture:", msg = "size:$selectSize;文件夹刷新")

            folderAdapter?.onItemListener = { model, _, position ->

                adapter = RippleImageAdapter(
                    this,
                    mediaList[position].getMediaList(), config = config, line = LINE
                )
                adapter?.imageCropConfig = imageCropConfig
                adapter?.themeConfig = themeConfig
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
            REQUEST_CODE -> if (grantResults[0] === PackageManager.PERMISSION_GRANTED && grantResults[1] === PackageManager.PERMISSION_GRANTED
            ) {
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
            REQUEST_CODE_CAMERA -> if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
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
            REQUEST_CODE_WRITE_EXTERNAL_STORAGE -> if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
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
                        ) { _, _ ->
                            handler.post {
                                scanImageSource?.reloadAll()
                            }
                        }
                        reloadPicture = true
                        RippleImagePick.getInstance().takePictureFile = null
                    }
                }
                IPreviewImageConfig.PREVIEW_RESULT -> {
                    val imageList = RippleMediaPick.getInstance().imageList
                    val intent = Intent()
                    intent.putExtra(
                        RippleImagePick.RESULT_IMG_LIST,
                        imageList
                    )
                    setResult(RESULT_OK, intent)
                    finish()
                }
                CropImageConfig.CROP_IMAGE_REQUEST_CODE -> {
                    if (data != null) {
                        val intent = Intent()
                        intent.putExtra(
                            CropImageConfig.CROP_IMAGE_REQUEST_RESULT,
                            data.getSerializableExtra(CropImageConfig.CROP_IMAGE_REQUEST_RESULT)
                        )
                        setResult(RESULT_OK, intent)
                    } else {
                        setResult(RESULT_CANCELED)
                    }
                    finish()
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
