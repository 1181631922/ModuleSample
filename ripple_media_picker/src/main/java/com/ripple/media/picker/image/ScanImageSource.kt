package com.ripple.media.picker.image

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.ripple.media.picker.callback.ScanMediaSource
import com.ripple.media.picker.model.RippleFolderModel
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.model.impl.RippleFolderImpl
import com.ripple.media.picker.model.impl.RippleImageImpl
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Author: fanyafeng
 * Data: 2020/4/21 10:45
 * Email: fanyafeng@live.cn
 * Description:
 */
class ScanImageSource @JvmOverloads constructor(
    private var appCompatActivity: AppCompatActivity? = null,
    val path: String? = null,
    private val imageSourceListener: ImageSourceListener
) :
    LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private val SCAN_ALL_IMAGES = 10000
        private val SCAN_FOLDER = 10001
        private val locker = Any()
        private lateinit var fixedThreadPool: ExecutorService
    }

    private val loaderManager = LoaderManager.getInstance(appCompatActivity!!)

    private val IMAGE_ATTRIBUTE = arrayOf(
        MediaStore.Images.ImageColumns.DATE_MODIFIED,
        MediaStore.Images.ImageColumns.DATE_ADDED,
        MediaStore.Images.ImageColumns.DISPLAY_NAME,
        MediaStore.Images.ImageColumns.SIZE,
        MediaStore.Images.ImageColumns.WIDTH,
        MediaStore.Images.ImageColumns.HEIGHT,
        MediaStore.Images.ImageColumns.MIME_TYPE,
        MediaStore.Images.ImageColumns.TITLE,
        MediaStore.Images.Media.DATA
    )

    init {
        synchronized(locker) {
            fixedThreadPool = Executors.newFixedThreadPool(1)
        }

        if (path == null) {
            loaderManager.initLoader(SCAN_ALL_IMAGES, null, this)
        } else {
            val bundle = Bundle()
            bundle.putString("path", path)
            loaderManager.initLoader(SCAN_FOLDER, bundle, this)
        }
    }

    fun reloadAll() {
        loaderManager.restartLoader(SCAN_ALL_IMAGES, null, this)
    }

    private fun destroyAllLoader() {
        loaderManager.getLoader<Cursor>(SCAN_ALL_IMAGES)?.let {
            loaderManager.destroyLoader(SCAN_ALL_IMAGES)
        }
        loaderManager.getLoader<Cursor>(SCAN_FOLDER)?.let {
            loaderManager.destroyLoader(SCAN_FOLDER)
        }
    }

    override fun onCreateLoader(id: Int, bundle: Bundle?): Loader<Cursor> {
        return if (id == SCAN_ALL_IMAGES) {
            CursorLoader(
                appCompatActivity!!,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_ATTRIBUTE,
                null,
                null,
                IMAGE_ATTRIBUTE[0]
            )
        } else {
            CursorLoader(
                appCompatActivity!!,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_ATTRIBUTE,
                "_data like %" + bundle!!.getString("path") + "%",
                null,
                IMAGE_ATTRIBUTE[0]
            )
        }
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        val imageFolderList = mutableListOf<RippleFolderModel>()
        val imageList = mutableListOf<RippleMediaModel>()
        while (data?.moveToNext() == true) {
            val path = data.getString(data.getColumnIndexOrThrow(IMAGE_ATTRIBUTE[8]))
            val dateModified = data.getLong(data.getColumnIndexOrThrow(IMAGE_ATTRIBUTE[0]))
            val dateAdded = data.getLong(data.getColumnIndexOrThrow(IMAGE_ATTRIBUTE[1]))
            val size = data.getLong(data.getColumnIndexOrThrow(IMAGE_ATTRIBUTE[3]))
            val displayName = data.getString(data.getColumnIndexOrThrow(IMAGE_ATTRIBUTE[2]))
            val type = data.getString(data.getColumnIndexOrThrow(IMAGE_ATTRIBUTE[6]))
            val width = data.getInt(data.getColumnIndexOrThrow(IMAGE_ATTRIBUTE[3]))
            val height = data.getInt(data.getColumnIndexOrThrow(IMAGE_ATTRIBUTE[4]))
            val title = data.getString(data.getColumnIndexOrThrow(IMAGE_ATTRIBUTE[7]))
            val item = RippleImageImpl(
                "parentPath",
                path,
                dateModified,
                dateAdded,
                size,
                title,
                displayName,
                height,
                width,
                type
            )
            imageList.add(item)
        }
        if (imageList.size <= 0) {
            imageSourceListener.onMediaLoaded(imageFolderList)
            return
        }

        fixedThreadPool.execute {
            if (appCompatActivity == null) return@execute
            imageList.forEachIndexed { _, item ->
                val file = File(item.getPath())
                if (file.exists() && file.length() > 0) {
                    val imageFolder = RippleFolderImpl()
                    val imageParentFile = file.parentFile
                    if (imageParentFile != null) {
                        imageFolder.setName(imageParentFile.name)
                        imageFolder.setPath(imageParentFile.absolutePath)
                        val index = imageFolderList.indexOf(imageFolder)
                        if (index < 0) {
                            val list = mutableListOf<RippleMediaModel>()
                            list.add(item)
                            imageFolder.setMediaList(list)
                            imageFolderList.add(imageFolder)
                        } else {
                            val imageFolderItem = imageFolderList[index]
                            imageFolderItem.getMediaList().add(item)
                        }
                    }
                }
            }

            imageList.sort()
            if (imageList.size > 0) {
                val imageFolder = RippleFolderImpl()
                imageFolder.setName("所有图片")
                imageFolder.setPath("/")
                imageFolder.setMediaList(imageList)
                imageFolderList.add(0, imageFolder)
            }

            synchronized(locker) {
                val act = appCompatActivity
                act?.let {
                    act.runOnUiThread {
                        imageSourceListener.onMediaLoaded(imageFolderList)
                        destroyAllLoader()
                    }
                }
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }


    interface ImageSourceListener :
        ScanMediaSource<RippleFolderModel>


    fun recycle() {
        synchronized(locker) {
            appCompatActivity = null
        }
    }


}