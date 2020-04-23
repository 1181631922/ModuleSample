package com.ripple.media.picker.image.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ripple.media.picker.R
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.model.RippleFolderModel
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.util.dp2px
import com.ripple.media.picker.view.RippleImageView

/**
 * Author: fanyafeng
 * Data: 2020/4/22 17:53
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleFolderAdapter(
    private val mContext: Context,
    private val list: List<RippleFolderModel>
) :
    RecyclerView.Adapter<RippleFolderAdapter.RippleFolderViewHolder>() {

    var onItemListener: ((model: RippleFolderModel, view: View, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RippleFolderViewHolder {
        val layout =
            LayoutInflater.from(mContext).inflate(R.layout.ripple_item_folder_layout, parent, false)
        return RippleFolderViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RippleFolderViewHolder, position: Int) {
        val model = list[position]
        val imageList = model.getMediaList()
        if (imageList.size > 0) {
            val imageModel = imageList[0]
            RippleMediaPick.getInstance().imageLoadFrame?.displayImage(
                mContext,
                imageModel.getPath(),
                holder.rippleFolderIcon!!,
                50.dp2px.toInt(),
                50.dp2px.toInt()
            )

            holder.rippleFolderName?.text = model.getName()
            holder.rippleFolderCount?.text = imageList.size.toString()

            holder.itemView.setOnClickListener {
                onItemListener?.invoke(model,it, position)
            }
        }
    }

    inner class RippleFolderViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var rippleFolderIcon: RippleImageView? = null
        var rippleFolderName: TextView? = null
        var rippleFolderCount: TextView? = null

        init {
            rippleFolderIcon = item.findViewById(R.id.rippleFolderIcon)
            rippleFolderName = item.findViewById(R.id.rippleFolderName)
            rippleFolderCount = item.findViewById(R.id.rippleFolderCount)
        }
    }
}