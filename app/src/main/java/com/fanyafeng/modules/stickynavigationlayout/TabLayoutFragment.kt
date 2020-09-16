package com.fanyafeng.modules.stickynavigationlayout

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.facebook.drawee.view.SimpleDraweeView
import com.fanyafeng.modules.R
import com.ripple.ui.widget.RippleImageView
import kotlinx.android.synthetic.main.fragment_tab_layout.*
import java.util.*
import com.ripple.tool.extend.forEach

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "flag"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabLayoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabLayoutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        rvTabView.setHasFixedSize(true)
        when (param1) {
            "0" -> layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            "1" -> layoutManager =
                GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            "2" -> layoutManager =
                GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
            "3" -> layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            "4" -> layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        rvTabView.layoutManager = layoutManager
        val stringList = ArrayList<String>()
        10.forEach {
            stringList.add(IMG_URL)
        }

        val rvAdapter = RVAdapter(context as Activity, stringList)
        rvTabView.adapter = rvAdapter
    }

    private fun initData() {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TabLayoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TabLayoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        val IMG_URL =
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594009379309&di=32738ab79a38d91af96097a536c632da&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg"
    }
}

class RVAdapter(
    private val mContext: Context,
    private val stringList: List<String>
) :
    RecyclerView.Adapter<RVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(mContext).inflate(R.layout.item_rv_layout, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return stringList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(mContext).load(stringList[position]).into(holder.sdvRvItem)
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val sdvRvItem = itemView.findViewById<RippleImageView>(R.id.sdvRvItem)
    }

}