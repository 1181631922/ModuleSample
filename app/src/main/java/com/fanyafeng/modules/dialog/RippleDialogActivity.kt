package com.fanyafeng.modules.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.fanyafeng.modules.R
import com.ripple.dialog.widget.impl.RippleDialog
import com.ripple.dialog.widget.impl.RippleToast
import kotlinx.android.synthetic.main.activity_ripple_dialog.*

class RippleDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_dialog)

        title = "dialog封装"

        initView()
        initData()
    }

    private fun initView() {
        btn1.setOnClickListener {
            val view =
                LayoutInflater.from(this).inflate(R.layout.ripple_dialog_layout_center_dialog, null)
            val dialog = RippleDialog(this, view)
            dialog.showCenter()
        }

        btn2.setOnClickListener {
            val view =
                LayoutInflater.from(this).inflate(R.layout.ripple_dialog_layout_center_dialog, null)
            val dialog = RippleDialog(this, view)
            dialog.showBottom()
        }

        btn3.setOnClickListener {
            RippleToast.show(this, "toast提示")
        }

    }

    private fun initData() {

    }
}