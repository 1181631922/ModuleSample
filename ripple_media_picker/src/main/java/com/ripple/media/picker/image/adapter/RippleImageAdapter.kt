package com.ripple.media.picker.image.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ripple.media.picker.R
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.camera.TakePicture
import com.ripple.media.picker.config.CropImageConfig
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.config.IPreviewImageConfig
import com.ripple.media.picker.config.MediaThemeConfig
import com.ripple.media.picker.config.impl.PreviewImageConfig
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.util.LogUtil
import com.ripple.media.picker.util.dp2px
import com.ripple.media.picker.util.screenwidth
import com.ripple.media.picker.view.RippleImageView

/**
 * Author: fanyafeng
 * Data: 2020/4/22 13:41
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleImageAdapter @JvmOverloads constructor(
    private val mContext: Context,
    private val list: List<RippleMediaModel>,
    private val config: IImagePickConfig = RippleMediaPick.getInstance().imagePickConfig,
    private val line: Int = 4
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_TYPE_PICTURE = 1
        private const val ITEM_TYPE_CAMERA = 2
    }

    var imageCropConfig: CropImageConfig? = null

    var themeConfig: MediaThemeConfig? = null

    var SCREEN_WIDTH = (mContext as Activity).screenwidth()
    var ITEM_WIDTH = ((SCREEN_WIDTH - 4.dp2px) / line).toInt()

    var itemClickListener: ((view: View, model: RippleMediaModel, position: Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_CAMERA -> {
                val cameraLayout =
                    LayoutInflater.from(mContext)
                        .inflate(R.layout.ripple_item_take_photo_layout, parent, false)
                return RippleTakePhotoViewHolder(cameraLayout)
            }
            ITEM_TYPE_PICTURE -> {
                val imageLayout =
                    LayoutInflater.from(mContext)
                        .inflate(R.layout.ripple_item_image_layout, parent, false)
                return RippleImageViewHolder(imageLayout)
            }

        }
        val imageLayout =
            LayoutInflater.from(mContext)
                .inflate(R.layout.ripple_item_image_layout, parent, false)
        return RippleImageViewHolder(imageLayout)
    }

    override fun getItemCount(): Int {
        return if (config.showCamera())
            list.size + 1
        else
            list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (config.showCamera()) {
            if (position == 0) ITEM_TYPE_CAMERA else ITEM_TYPE_PICTURE
        } else {
            ITEM_TYPE_PICTURE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is RippleImageViewHolder -> {
                if (imageCropConfig == null) {
                    holder.checkLayout?.visibility = View.VISIBLE
                } else {
                    holder.checkLayout?.visibility = View.GONE
                }

                val realPosition = if (config.showCamera()) position - 1 else position
                val model = list[realPosition]
                holder.imageItemCheck?.text = ""
                holder.imageItemCheck?.background =
                    mContext.resources.getDrawable(R.drawable.ripple_image_uncheck_shape)

                val selectList = RippleMediaPick.getInstance().imageList
                if (selectList.contains(model)) {
                    val index = selectList.indexOf(model) + 1
                    holder.imageItemCheck?.text = index.toString()
                    holder.imageItemCheck?.background =
                        mContext.resources.getDrawable(R.drawable.ripple_image_check_shape)
                }

                val params = holder.imageItemLayout?.layoutParams
                params?.width = ITEM_WIDTH
                params?.height = ITEM_WIDTH

                val loadFrame = RippleMediaPick.getInstance().imageLoadFrame
                loadFrame?.displayImage(
                    mContext,
                    model.getPath(),
                    holder.imageItemIcon!!,
                    ITEM_WIDTH,
                    ITEM_WIDTH
                )

                holder.checkLayout?.setOnClickListener {

                    //点击选择图片
                    val modelList = RippleMediaPick.getInstance().imageList

                    LogUtil.d("选取图片数量图片数量：", msg = modelList.toString())

                    /**
                     * 如果选中的图片包括当前的图则取消选择
                     * 否则的话添加选择
                     *
                     * 取消，选择后需要更新个数显示
                     */
                    if (modelList.contains(model)) {
                        model.setCheck(false)
                        model.setTag(null)
                        modelList.remove(model)
                        updateCount(list, modelList, holder.imageItemCheck)
                        notifyDataSetChanged()
                    } else {
                        if (modelList.size < config.getCount()) {
                            if (config.getSize() == -1L) {
                                model.setCheck(true)
                                modelList.add(model)
                                updateCount(list, modelList, holder.imageItemCheck)
                                notifyDataSetChanged()
                            } else {
                                if (model.getSize() > config.getSize()) {
                                    Toast.makeText(mContext, "选取的图片大小不符合规格", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    model.setCheck(true)
                                    modelList.add(model)
                                    updateCount(list, modelList, holder.imageItemCheck)
                                    notifyDataSetChanged()
                                }
                            }
                        } else {
                            Toast.makeText(
                                mContext,
                                "图片最多选取" + config.getCount() + "张",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                    itemClickListener?.invoke(it, model, realPosition)
                }

                holder.itemView.setOnClickListener {
                    if (imageCropConfig == null) {
                        //跳转到
                        val config =
                            PreviewImageConfig.Builder().setCurrentPosition(realPosition)
                                .setImageList(list)
                                .setSelectModel(IPreviewImageConfig.PreviewModel.NORMAL)
                                .build()
                        RippleImagePick.getInstance()
                            .imagePreview(mContext, config, IPreviewImageConfig.PREVIEW_RESULT)
                    } else {
                        //此时跳转到裁剪页面
                        RippleImagePick.getInstance().imageCrop(
                            mContext,
                            model.getPath(),
                            CropImageConfig.CROP_IMAGE_REQUEST_CODE,
                            imageCropConfig,
                            themeConfig
                        )
                    }
                }

            }
            is RippleTakePhotoViewHolder -> {
                val params = holder.rippleTakePhotoLayout?.layoutParams
                params?.width = ITEM_WIDTH
                params?.height = ITEM_WIDTH
                holder.itemView.setOnClickListener {
                    val modelList = RippleMediaPick.getInstance().imageList
                    if (config.getCount()!=-1){
                        if (modelList.size < config.getCount()) {
                            //申请相机权限
                            TakePicture(config).openCamera(
                                mContext,
                                IImagePickConfig.TAKE_PICTURE_CODE
                            )
                        } else {
                            Toast.makeText(
                                mContext,
                                "图片最多选取" + config.getCount() + "张",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }else{
                        TakePicture(config).openCamera(
                            mContext,
                            IImagePickConfig.TAKE_PICTURE_CODE
                        )
                    }
                }
            }
        }
    }


    /**
     * 更新选中右上角显示
     */
    private fun updateCount(
        allList: List<RippleMediaModel>,
        selectList: List<RippleMediaModel>,
        view: TextView?
    ) {
        allList.forEachIndexed { _, allItem ->
            selectList.forEachIndexed { selectIndex, selectItem ->
                if (allItem == selectItem) {
                    val seeIndex = selectIndex + 1
                    selectItem.setTag(seeIndex)
                    allItem.setTag(seeIndex)
                    view?.text = seeIndex.toString()
                }
            }
        }
    }

    inner class RippleImageViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        var imageItemLayout: RelativeLayout? = null
        var imageItemIcon: RippleImageView? = null
        var imageItemCheck: TextView? = null
        var imageItemUnCheck: TextView? = null
        var checkLayout: RelativeLayout? = null

        init {
            imageItemLayout = item.findViewById(R.id.imageItemLayout)
            imageItemIcon = item.findViewById(R.id.imageItemIcon)
            imageItemCheck = item.findViewById(R.id.imageItemCheck)
            imageItemUnCheck = item.findViewById(R.id.imageItemUnCheck)
            checkLayout = item.findViewById(R.id.checkLayout)
        }
    }

    inner class RippleTakePhotoViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var rippleTakePhotoLayout: RelativeLayout? = null
        var rippleTakePhoto: RippleImageView? = null


        init {
            rippleTakePhotoLayout = item.findViewById(R.id.rippleTakePhotoLayout)
            rippleTakePhoto = item.findViewById(R.id.rippleTakePhoto)
        }
    }


}